const API_BASE = 'http://localhost:8090/api';
const ACTIVITIES_BASE = `${API_BASE}/activities`;
const ACTIVITIES_ALL_ROUTE = `${ACTIVITIES_BASE}/all`;
const USER_PROFILE_ROUTE = `${API_BASE}/user-profile`;
const USER_PROFILE_ROUTE_FALLBACK = `${API_BASE}/userProfile`;
const DEFAULT_PLACEHOLDER = '-';

let activities = [];
let selectedId = null;
let map;
let mapPolyline;
let hrChart;
let powerChart;
let altitudeChart;
let cadenceChart;
let groundTimeChart;
let hrZonesChart;

const selectEl = document.getElementById('activity-select');
const listEl = document.getElementById('activity-list');
const summaryEl = document.getElementById('summary-cards');
const kmSplitsBodyEl = document.getElementById('km-splits-body');
const hrZonesChartEl = document.getElementById('hr-zones-chart');
const sportBadgeEl = document.getElementById('sport-badge');
const statusMessageEl = document.getElementById('status-message');
const powerChartPanelEl = document.getElementById('power-chart-panel');
const cadenceChartPanelEl = document.getElementById('cadence-chart-panel');
const groundTimeChartPanelEl = document.getElementById('ground-time-chart-panel');
const profileSettingsBtnEl = document.getElementById('profile-settings-btn');
const profileModalEl = document.getElementById('profile-modal');
const profileFormEl = document.getElementById('profile-form');
const profileCancelBtnEl = document.getElementById('profile-cancel-btn');
const profileGenderEl = document.getElementById('profile-gender');
const profileAgeEl = document.getElementById('profile-age');
const profileWeightEl = document.getElementById('profile-weight');
const profileHeightEl = document.getElementById('profile-height');

init().catch((error) => {
  setStatus(`Initialization failed: ${error.message}`, 'error');
});

async function init() {
  initMap();
  initCharts();
  initProfileModal();
  renderConditionalMetricPanels(null);
  await loadActivities();

  selectEl.addEventListener('change', async () => {
    selectedId = selectEl.value;
    await loadActivity(selectedId);
  });
}

function initProfileModal() {
  if (!profileSettingsBtnEl || !profileModalEl || !profileFormEl) {
    return;
  }

  profileSettingsBtnEl.addEventListener('click', async () => {
    await preloadUserProfile();
    profileModalEl.classList.remove('is-hidden');
  });

  profileCancelBtnEl?.addEventListener('click', () => {
    closeProfileModal();
  });

  profileModalEl.addEventListener('click', (event) => {
    if (event.target === profileModalEl) {
      closeProfileModal();
    }
  });

  profileFormEl.addEventListener('submit', async (event) => {
    event.preventDefault();
    await saveUserProfile();
  });
}

function closeProfileModal() {
  profileModalEl?.classList.add('is-hidden');
}

async function loadActivities() {
  setStatus('Loading activities...', 'success');
  const response = await fetch(ACTIVITIES_ALL_ROUTE);
  if (!response.ok) {
    throw new Error('Cannot load activities');
  }
  const payload = await parseApiResponse(response);
  console.log('Raw activities payload:', payload);
  activities = parseActivitiesPayload(payload);
  activities = activities.map((activity, index) => normalizeActivitySummary(activity, index));
  console.log('Loaded activities:', activities);

  if (!activities.length) {
    summaryEl.innerHTML = '<p>No activities found in dataset.</p>';
    setStatus('No activity found in current dataset.', 'error');
    return;
  }

  selectEl.innerHTML = activities
    .map((a) => `<option value="${escapeHtml(a.id)}">${escapeHtml(a.id)} (${escapeHtml(safeDisplay(a.sportType))})</option>`)
    .join('');

  renderActivityList();

  selectedId = activities[0].id;
  selectEl.value = selectedId;
  await loadActivity(selectedId);
  setStatus(`Loaded ${activities.length} activities.`, 'success');
}

function parseActivitiesPayload(payload) {
  if (typeof payload === 'string') {
    try {
      return parseActivitiesPayload(JSON.parse(payload));
    } catch {
      return [];
    }
  }

  if (Array.isArray(payload)) {
    return payload;
  }

  if (payload && Array.isArray(payload.activities)) {
    return payload.activities;
  }

  return [];
}

async function parseApiResponse(response) {
  const text = await response.text();
  try {
    return JSON.parse(text);
  } catch {
    return text;
  }
}

function renderActivityList() {
  listEl.innerHTML = activities
    .map((a) => {
      const active = a.id === selectedId ? 'active' : '';
      const distance = formatNumber(a.distanceKm, ' km');
      return `
        <li class="activity-item ${active}" data-id="${escapeHtml(a.id)}">
          <div class="title">${escapeHtml(a.id)}</div>
          <div class="meta">${escapeHtml(safeDisplay(a.sportType))} · ${distance}</div>
        </li>
      `;
    })
    .join('');

  listEl.querySelectorAll('.activity-item').forEach((item) => {
    item.addEventListener('click', async () => {
      selectedId = item.dataset.id;
      selectEl.value = selectedId;
      await loadActivity(selectedId);
    });
  });
}

async function loadActivity(activityId) {
  try {
    const encodedId = encodeURIComponent(activityId);
    const [summaryResult, routeResult, altitudeResult, heartRateResult, powerResult, cadenceResult, groundTimeResult, kmSplitsResult, zonesResult] = await Promise.allSettled([
      fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/summary`),
      fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/metrics/route`),
      fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/metrics/altitude`),
      fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/metrics/heart-rate`),
      fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/metrics/power`),
      fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/metrics/cadence`),
      fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/metrics/ground-time`),
      fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/metrics/pace`),
      fetchZonesMetric(encodedId)
    ]);

    const summary = normalizeSummaryPayload(getSettledValue(summaryResult, null));
    const route = normalizeRoutePayload(getSettledValue(routeResult, null));
    const altitude = normalizeMetricPayload(getSettledValue(altitudeResult, null), 'Altitude (m)');
    const heartRate = normalizeMetricPayload(getSettledValue(heartRateResult, null), 'Heart Rate (bpm)');
    const power = normalizeMetricPayload(getSettledValue(powerResult, null), 'Power (W)');
    const cadence = normalizeMetricPayload(getSettledValue(cadenceResult, null), 'Cadence');
    const groundTime = normalizeMetricPayload(getSettledValue(groundTimeResult, null), 'Ground Time (ms)');
    const kmSplits = normalizeKmSplitsPayload(getSettledValue(kmSplitsResult, null));
    const zones = normalizeZonesPayload(getSettledValue(zonesResult, null));

    logRejectedRequest('summary', summaryResult, activityId);
    logRejectedRequest('route', routeResult, activityId);
    logRejectedRequest('altitude', altitudeResult, activityId);
    logRejectedRequest('heart-rate', heartRateResult, activityId);
    logRejectedRequest('power', powerResult, activityId);
    logRejectedRequest('cadence', cadenceResult, activityId);
    logRejectedRequest('ground-time', groundTimeResult, activityId);
    logRejectedRequest('pace', kmSplitsResult, activityId);
    logRejectedRequest('zone', zonesResult, activityId);

    safeRenderStep('activity list', () => renderActivityList());
    safeRenderStep('summary cards', () => renderSummary(summary));
    safeRenderStep('sport theme', () => renderSportTheme(summary.sportType));
    safeRenderStep('conditional metric panels', () => renderConditionalMetricPanels(summary.sportType));
    safeRenderStep('route map', () => renderRoute(route.points));
    safeRenderStep('heart-rate chart', () => renderHeartRateChart(heartRate));
    safeRenderStep('power chart', () => renderPowerChart(isCyclingSport(summary.sportType) ? power : null));
    safeRenderStep('altitude chart', () => renderMetricLineChart(altitudeChart, altitude, {
      label: 'Altitude Above Min (m)',
      borderColor: '#8D5524',
      backgroundColor: 'rgba(141,85,36,0.22)',
      fill: true,
      relativeToMin: true,
      forceYAxisFromZero: true
    }));
    safeRenderStep('cadence chart', () => renderMetricLineChart(cadenceChart, isCyclingSport(summary.sportType) ? cadence : null, {
      label: 'Cadence',
      borderColor: '#FC4C02',
      backgroundColor: 'rgba(252,76,2,0.2)'
    }));
    safeRenderStep('ground-time chart', () => renderMetricLineChart(groundTimeChart, isRunningSport(summary.sportType) ? groundTime : null, {
      label: 'Ground Time (ms)',
      borderColor: '#8D5524',
      backgroundColor: 'rgba(141,85,36,0.2)'
    }));
    safeRenderStep('km splits', () => renderKmSplitsGrid(kmSplits));
    safeRenderStep('hr zones', () => renderZones(zones));
    setStatus(`Loaded activity: ${activityId}`, 'success');
  } catch (error) {
    setStatus(`Failed to load activity ${activityId}: ${error.message}`, 'error');
  }
}

async function saveUserProfile() {
  const gender = normalizeGender(profileGenderEl?.value);
  const age = Number(profileAgeEl?.value);
  const weight = Number(profileWeightEl?.value);
  const height = Number(profileHeightEl?.value);

  if (!gender) {
    setStatus('Please select a gender.', 'error');
    return;
  }

  if (!Number.isFinite(age) || !Number.isFinite(weight) || !Number.isFinite(height)) {
    setStatus('Please provide valid age, weight and height values.', 'error');
    return;
  }

  const payload = normalizeUserProfilePayload({
    gender,
    age,
    weight,
    height
  });

  try {
    await postUserProfile(payload);
    setStatus('User profile saved.', 'success');
    closeProfileModal();
    await refreshHrZones();
  } catch (error) {
    setStatus(`Failed to save profile: ${error.message}`, 'error');
  }
}

async function preloadUserProfile() {
  try {
    const profile = normalizeUserProfilePayload(await getUserProfile());
    populateProfileForm(profile);
  } catch (error) {
    setStatus(`Failed to load profile: ${error.message}`, 'error');
  }
}

function populateProfileForm(profile) {
  const safeProfile = normalizeUserProfilePayload(profile);
  if (!safeProfile) {
    if (profileFormEl) {
      profileFormEl.reset();
    }
    return;
  }

  if (profileGenderEl) {
    profileGenderEl.value = safeProfile.gender || '';
  }
  if (profileAgeEl) {
    profileAgeEl.value = safeProfile.age ?? '';
  }
  if (profileWeightEl) {
    profileWeightEl.value = safeProfile.weight ?? '';
  }
  if (profileHeightEl) {
    profileHeightEl.value = safeProfile.height ?? '';
  }
}

async function refreshHrZones() {
  if (!selectedId) {
    renderZones({ zones: [] });
    return;
  }

  const encodedId = encodeURIComponent(selectedId);
  const zonesPayload = await fetchZonesMetric(encodedId);
  renderZones(zonesPayload);
}

async function getUserProfile() {
  try {
    return await fetchJsonOptional(USER_PROFILE_ROUTE);
  } catch {
    return fetchJsonOptional(USER_PROFILE_ROUTE_FALLBACK);
  }
}

async function postUserProfile(payload) {
  try {
    return await postJson(USER_PROFILE_ROUTE, payload);
  } catch {
    return postJson(USER_PROFILE_ROUTE_FALLBACK, payload);
  }
}

async function fetchZonesMetric(encodedId) {
  return fetchJsonOptional(`${ACTIVITIES_BASE}/${encodedId}/metrics/zone`);
}

function renderSummary(summary) {
  const safeSummary = normalizeSummaryPayload(summary);
  const rows = [
    ['Sport', safeDisplay(safeSummary.sportType)],
    ['Distance', formatNumber(safeSummary.distanceKm, ' km')],
    ['Duration', formatDuration(safeSummary.durationSeconds)],
    ['Avg HR', formatNumber(safeSummary.avgHeartRate, ' bpm')],
    ['Max HR', safeDisplay(safeSummary.maxHeartRate)],
    ['Avg Speed', formatNumber(safeSummary.avgSpeedKmh, ' km/h')],
    ['Avg Power', formatNumber(safeSummary.avgPower, ' W')]
  ];

  summaryEl.innerHTML = rows
    .map(([label, value]) => `
      <div class="card">
        <div class="label">${label}</div>
        <div class="value">${value ?? '-'}</div>
      </div>
    `)
    .join('');
}

function renderSportTheme(sportType) {
  if (!sportBadgeEl) {
    console.error('Sport badge element not found: #sport-badge');
    return;
  }

  const isRun = sportType === 'Run';
  sportBadgeEl.textContent = safeDisplay(sportType);
  sportBadgeEl.classList.toggle('run', isRun);
  sportBadgeEl.classList.toggle('bike', !isRun);
}

function initMap() {
  map = L.map('route-map').setView([48.85, 2.35], 12);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; OpenStreetMap contributors'
  }).addTo(map);
}

function renderRoute(points) {
  try {
    if (mapPolyline) {
      map.removeLayer(mapPolyline);
      mapPolyline = null;
    }

    const incomingPoints = Array.isArray(points) ? points : [];
    const latLngs = incomingPoints
      .map((p) => {
        const lat = Number(p?.lat ?? p?.latitude);
        const lon = Number(p?.lon ?? p?.longitude);
        return Number.isFinite(lat) && Number.isFinite(lon) ? [lat, lon] : null;
      })
      .filter(Boolean);

    if (!latLngs.length) {
      if (incomingPoints.length) {
        console.error('Route cannot be displayed: no valid latitude/longitude points.', {
          samplePoints: incomingPoints.slice(0, 5)
        });
      } else {
        console.error('Route cannot be displayed: route payload has no points.');
      }

      map.setView([48.85, 2.35], 12);
      return;
    }

    mapPolyline = L.polyline(latLngs, { color: '#FC4C02', weight: 4 }).addTo(map);
    map.fitBounds(mapPolyline.getBounds(), { padding: [20, 20] });
  } catch (error) {
    console.error('Route rendering failed on map.', {
      error,
      pointsPreview: Array.isArray(points) ? points.slice(0, 5) : points
    });
    map.setView([48.85, 2.35], 12);
  }
}

function initCharts() {
  hrChart = createLineChart('hr-chart', 'Heart Rate (bpm)');
  powerChart = createLineChart('power-chart', 'Power (W)');
  altitudeChart = createLineChart('altitude-chart', 'Altitude (m)');
  cadenceChart = createLineChart('cadence-chart', 'Cadence');
  groundTimeChart = createLineChart('ground-time-chart', 'Ground Time (ms)');

  hrZonesChart = new Chart(hrZonesChartEl, {
    type: 'bar',
    data: {
      labels: ['Z1', 'Z2', 'Z3', 'Z4', 'Z5'],
      datasets: [{
        label: 'Time in zone (%)',
        data: [0, 0, 0, 0, 0],
        backgroundColor: ['#1e3a8a', '#60a5fa', '#86efac', '#fc4c02', '#a855f7'],
        borderRadius: 6,
        maxBarThickness: 46
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        x: {
          grid: { display: false },
          ticks: { color: '#d1d5db' }
        },
        y: {
          beginAtZero: true,
          suggestedMax: 100,
          title: { display: true, text: '%', color: '#d1d5db' },
          ticks: { color: '#d1d5db' },
          grid: { color: 'rgba(255,255,255,0.08)' }
        }
      },
      plugins: {
        legend: { display: false }
      }
    }
  });
}

function createLineChart(canvasId, label) {
  return new Chart(document.getElementById(canvasId), {
    type: 'line',
    data: emptyDataset(label),
    options: baseChartOptions()
  });
}

function renderConditionalMetricPanels(sportType) {
  const isBike = isCyclingSport(sportType);
  const isRun = isRunningSport(sportType);

  if (powerChartPanelEl) powerChartPanelEl.classList.toggle('is-hidden', !isBike);
  if (cadenceChartPanelEl) cadenceChartPanelEl.classList.toggle('is-hidden', !isBike);
  if (groundTimeChartPanelEl) groundTimeChartPanelEl.classList.toggle('is-hidden', !isRun);
}

function isRunningSport(sportType) {
  return String(sportType || '').toLowerCase().includes('run');
}

function isCyclingSport(sportType) {
  const normalized = String(sportType || '').toLowerCase();
  return normalized.includes('bike') || normalized.includes('ride') || normalized.includes('cycl');
}

function renderHeartRateChart(payload) {
  const safePayload = normalizeMetricPayload(payload, 'Heart Rate (bpm)');
  hrChart.data.labels = safePayload.points.map((p) => p.timestamp);
  hrChart.data.datasets = [{
    label: safePayload.metric,
    data: safePayload.points.map((p) => p.value),
    borderColor: '#ef4444',
    backgroundColor: 'rgba(239,68,68,0.2)',
    fill: true,
    tension: 0.2,
    pointRadius: 0
  }];
  hrChart.update();
}

function renderPowerChart(payload) {
  if (!payload || !Array.isArray(payload.points)) {
    powerChart.data.labels = [];
    powerChart.data.datasets = [{
      label: 'Power (W)',
      data: [],
      borderColor: '#7c3aed',
      backgroundColor: 'rgba(124,58,237,0.2)',
      fill: true,
      tension: 0.2,
      pointRadius: 0
    }];
    powerChart.update();
    return;
  }

  powerChart.data.labels = payload.points.map((p) => p.timestamp);
  powerChart.data.datasets = [{
    label: payload.metric,
    data: payload.points.map((p) => p.value),
    borderColor: '#7c3aed',
    backgroundColor: 'rgba(124,58,237,0.2)',
    fill: true,
    tension: 0.2,
    pointRadius: 0
  }];
  powerChart.update();
}

function renderMetricLineChart(chart, payload, config) {
  if (!chart) {
    return;
  }

  if (!payload || !Array.isArray(payload.points)) {
    chart.data.labels = [];
    chart.data.datasets = [{
      label: config.label,
      data: [],
      borderColor: config.borderColor,
      backgroundColor: config.backgroundColor,
      fill: config.fill !== false,
      tension: 0.2,
      pointRadius: 0
    }];
    chart.update();
    return;
  }

  const safePayload = normalizeMetricPayload(payload, config.label);
  const sourceValues = safePayload.points.map((p) => Number(p.value));
  const finiteValues = sourceValues.filter((value) => Number.isFinite(value));
  const minValue = finiteValues.length ? Math.min(...finiteValues) : 0;
  const adjustedValues = config.relativeToMin
    ? sourceValues.map((value) => (Number.isFinite(value) ? value - minValue : value))
    : sourceValues;

  chart.data.labels = safePayload.points.map((p) => p.timestamp);
  chart.data.datasets = [{
    label: safePayload.metric || config.label,
    data: adjustedValues,
    borderColor: config.borderColor,
    backgroundColor: config.backgroundColor,
    fill: config.fill !== false,
    tension: 0.2,
    pointRadius: 0
  }];
  chart.options.scales.y.beginAtZero = Boolean(config.forceYAxisFromZero);
  chart.options.scales.y.min = config.forceYAxisFromZero ? 0 : undefined;
  chart.options.scales.y.title = {
    display: true,
    text: config.label
  };
  chart.update();
}

function renderKmSplitsGrid(payload) {
  const splits = payload?.splits;
  if (!Array.isArray(splits) || !splits.length) {
    kmSplitsBodyEl.innerHTML = '<tr><td colspan="4">No kilometer split data available.</td></tr>';
    return;
  }

  kmSplitsBodyEl.innerHTML = splits
    .map((split, index) => {
      const km = Number(split.km);
      const splitSeconds = Number(split.splitSeconds);
      const avgHr = split.avgHeartRate;

      let trendHtml = '<td>-</td>';
      if (index > 0) {
        const prevSplitSeconds = Number(splits[index - 1].splitSeconds);
        const isImprovement = splitSeconds < prevSplitSeconds;
        const arrow = isImprovement ? '↑' : '↓';
        const color = isImprovement ? '#9ce5b5' : '#ff8d8d';
        trendHtml = `<td style="color: ${color}; font-weight: bold; font-size: 1.1rem;">${arrow}</td>`;
      }

      return `
        <tr>
          <td>${Number.isFinite(km) ? km : '-'}</td>
          <td>${Number.isFinite(splitSeconds) ? formatDuration(splitSeconds) : '-'}</td>
          <td>${avgHr === null || avgHr === undefined ? '-' : `${Number(avgHr).toFixed(0)} bpm`}</td>
          ${trendHtml}
        </tr>
      `;
    })
    .join('');
}

function renderZones(payload) {
  const defaultLabels = ['Z1', 'Z2', 'Z3', 'Z4', 'Z5'];
  const zones = payload?.zones || [];

  let zoneData = [0, 0, 0, 0, 0];

  if (Array.isArray(zones) && zones.length > 0) {
    if (typeof zones[0] === 'number') {
      zoneData = zones.slice(0, 5).map((v) => Number(v) || 0);
    } else if (typeof zones[0] === 'object') {
      const percentageByZone = new Map(zones.map((zone) => [zone.zone, Number(zone.percentage || 0)]));
      zoneData = defaultLabels.map((zone) => percentageByZone.get(zone) ?? 0);
    }
  }

  hrZonesChart.data.labels = defaultLabels;
  hrZonesChart.data.datasets[0].data = zoneData;
  hrZonesChart.update();
}

function emptyDataset(label) {
  return {
    labels: [],
    datasets: [{ label, data: [] }]
  };
}

function baseChartOptions() {
  return {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        display: false,
        ticks: { color: '#d1d5db' },
        grid: { color: 'rgba(255,255,255,0.08)' }
      },
      y: {
        beginAtZero: true,
        ticks: { color: '#d1d5db' },
        grid: { color: 'rgba(255,255,255,0.08)' }
      }
    },
    plugins: {
      legend: {
        display: true,
        labels: { color: '#f3f4f6' }
      }
    }
  };
}

async function fetchJson(url) {
  const response = await fetch(url);
  if (!response.ok) {
    throw new Error(`API error: ${response.status}`);
  }
  return response.json();
}

async function fetchJsonOptional(url) {
  const response = await fetch(url);
  if (!response.ok) {
    return null;
  }
  return parseApiResponse(response);
}

async function postJson(url, body) {
  const response = await fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(body)
  });

  if (!response.ok) {
    throw new Error(`API error: ${response.status}`);
  }

  return parseApiResponse(response);
}

function formatNumber(value, suffix = '') {
  if (value === null || value === undefined || Number.isNaN(Number(value))) {
    return DEFAULT_PLACEHOLDER;
  }
  return `${Number(value).toFixed(2)}${suffix}`;
}

function formatDuration(seconds) {
  const parsedSeconds = Number(seconds);
  if (!Number.isFinite(parsedSeconds)) return DEFAULT_PLACEHOLDER;
  const total = Math.max(0, Math.floor(parsedSeconds));
  const hh = String(Math.floor(total / 3600)).padStart(2, '0');
  const mm = String(Math.floor((total % 3600) / 60)).padStart(2, '0');
  const ss = String(total % 60).padStart(2, '0');
  return `${hh}:${mm}:${ss}`;
}

function safeDisplay(value) {
  if (value === null || value === undefined) {
    return DEFAULT_PLACEHOLDER;
  }

  const text = String(value).trim();
  return text ? text : DEFAULT_PLACEHOLDER;
}

function getSettledValue(result, fallback) {
  if (!result || result.status !== 'fulfilled') {
    return fallback;
  }

  return result.value ?? fallback;
}

function normalizeActivitySummary(activity, index) {
  const fallbackId = `activity-${index + 1}`;
  return {
    id: safeDisplay(activity?.id) === DEFAULT_PLACEHOLDER ? fallbackId : String(activity.id),
    sportType: safeDisplay(activity?.sportType),
    distanceKm: Number.isFinite(Number(activity?.distanceKm)) ? Number(activity.distanceKm) : null
  };
}

function normalizeGender(value) {
  const text = String(value ?? '').trim().toUpperCase();
  return text === 'MALE' || text === 'FEMALE' ? text : '';
}

function normalizeUserProfilePayload(profile) {
  if (!profile || typeof profile !== 'object') {
    return null;
  }

  const gender = normalizeGender(profile.gender);
  const age = Number(profile.age);
  const weight = Number(profile.weight);
  const height = Number(profile.height);

  return {
    gender,
    age: Number.isFinite(age) ? Math.trunc(age) : null,
    weight: Number.isFinite(weight) ? weight : null,
    height: Number.isFinite(height) ? height : null
  };
}

function normalizeSummaryPayload(summary) {
  if (!summary || typeof summary !== 'object') {
    return {
      sportType: DEFAULT_PLACEHOLDER,
      distanceKm: null,
      durationSeconds: null,
      avgHeartRate: null,
      maxHeartRate: null,
      avgSpeedKmh: null,
      avgPower: null
    };
  }

  return {
    sportType: safeDisplay(summary.sportType),
    distanceKm: Number.isFinite(Number(summary.distanceKm)) ? Number(summary.distanceKm) : null,
    durationSeconds: Number.isFinite(Number(summary.durationSeconds)) ? Number(summary.durationSeconds) : null,
    avgHeartRate: Number.isFinite(Number(summary.avgHeartRate)) ? Number(summary.avgHeartRate) : null,
    maxHeartRate: Number.isFinite(Number(summary.maxHeartRate)) ? Number(summary.maxHeartRate) : null,
    avgSpeedKmh: Number.isFinite(Number(summary.avgSpeedKmh)) ? Number(summary.avgSpeedKmh) : null,
    avgPower: Number.isFinite(Number(summary.avgPower)) ? Number(summary.avgPower) : null
  };
}

function normalizeRoutePayload(payload) {
  if (!payload || typeof payload !== 'object') {
    console.error('Route payload is invalid: expected object.', { payload });
    return { points: [] };
  }

  if (!Array.isArray(payload.points)) {
    console.error('Route payload is invalid: expected points array.', { payload });
    return { points: [] };
  }

  if (!payload.points.length) {
    console.error('Route payload contains an empty points array.');
  }

  return {
    points: payload.points.filter((point) => point && typeof point === 'object')
  };
}

function logRejectedRequest(metricName, settledResult, activityId) {
  if (!settledResult || settledResult.status !== 'rejected') {
    return;
  }

  console.error(`Failed to load metric ${metricName} for activity ${activityId}.`, settledResult.reason);
}

function safeRenderStep(stepName, renderFn) {
  try {
    renderFn();
  } catch (error) {
    console.error(`Render step failed: ${stepName}.`, error);
  }
}

function normalizeMetricPayload(payload, defaultMetricLabel) {
  if (!payload || typeof payload !== 'object') {
    return {
      metric: defaultMetricLabel,
      points: []
    };
  }

  const points = Array.isArray(payload.points)
    ? payload.points
      .filter((point) => point && typeof point === 'object')
      .map((point) => ({
        timestamp: safeDisplay(point.timestamp),
        value: Number(point.value)
      }))
    : [];

  return {
    metric: safeDisplay(payload.metric) === DEFAULT_PLACEHOLDER ? defaultMetricLabel : String(payload.metric),
    points
  };
}

function normalizeKmSplitsPayload(payload) {
  if (!payload || !Array.isArray(payload.splits)) {
    return { splits: [] };
  }

  return {
    splits: payload.splits.filter((split) => split && typeof split === 'object')
  };
}

function normalizeZonesPayload(payload) {
  if (!payload || typeof payload !== 'object') {
    return { zones: [] };
  }

  if (!Array.isArray(payload.zones)) {
    return { zones: [] };
  }

  return {
    zones: payload.zones
  };
}

function escapeHtml(value) {
  return String(value)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#039;');
}

function setStatus(message, type = 'success') {
  statusMessageEl.textContent = message || '';
  statusMessageEl.classList.remove('success', 'error');
  if (message) {
    statusMessageEl.classList.add(type);
  }
}

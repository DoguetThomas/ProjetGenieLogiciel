export class Dashboard {
  #activities;
  #filteredActivities;
  #selectedDatafile;
  #selectedSport;
  #selectedActivity;
  #root;
  #headerWidget;
  #controlsWidget;
  #globalStatsWidget;
  #activityListWidget;
  #activityDetailsWidget;

  constructor(activities) {
    this.#activities = activities;
    this.#filteredActivities = [...activities];
    this.#selectedDatafile = 'ALL';
    this.#selectedSport = 'All';
    this.#selectedActivity = null;
    this.#root = null;
    this.#headerWidget = new HeaderWidget();
    this.#controlsWidget = new ControlsWidget();
    this.#activityListWidget = new ActivityListWidget(activities);
    this.#globalStatsWidget = new GlobalStatsWidget(activities);
    this.#activityDetailsWidget = new ActivityDetailsWidget();
  }

  render(container) {
    container.innerHTML = '';

    this.#root = document.createElement('div');
    this.#root.className = 'dashboard-root';
    container.appendChild(this.#root);

    this.#headerWidget.render(this.#root);

    this.#controlsWidget.render(this.#root, {
      onSportChange: (sport) => {
        this.#selectedSport = sport;
        this.#selectedDatafile = 'ALL';
        this.#applyFilters();
      },
      onDatafileChange: (datafile) => {
        this.#selectedDatafile = datafile;
        this.#applyFilters();
      }
    });

    this.#globalStatsWidget.render(this.#root, this.#activities, this.#filteredActivities);
    this.#activityDetailsWidget.render(this.#root, this.#selectedActivity);
    this.#activityListWidget.render(this.#root, this.#filteredActivities);

    this.#controlsWidget.updateDatafileOptions(this.#uniqueActivities(this.#filteredActivities), this.#selectedDatafile);
  }

  #applyFilters() {
    this.#filteredActivities = this.#activities.filter((activity) => {
      const sportOk = this.#selectedSport === 'All' || activity.sportType === this.#selectedSport;
      const datafileOk = this.#selectedDatafile === 'ALL' || activity.datafile === this.#selectedDatafile;
      return sportOk && datafileOk;
    });

    this.#selectedActivity = this.#buildSelectedSummary(this.#filteredActivities);
    this.#globalStatsWidget.update(this.#activities, this.#filteredActivities);
    this.#activityDetailsWidget.update(this.#selectedActivity);
    this.#activityListWidget.update(this.#filteredActivities);
    this.#controlsWidget.updateDatafileOptions(this.#uniqueActivities(this.#activities.filter((a) => this.#selectedSport === 'All' || a.sportType === this.#selectedSport)), this.#selectedDatafile);
  }

  #uniqueActivities(rows) {
    const map = new Map();
    for (const row of rows) {
      if (!map.has(row.datafile)) {
        map.set(row.datafile, row);
      }
    }
    return [...map.values()];
  }

  #buildSelectedSummary(rows) {
    if (!rows.length) return null;

    const first = rows[0];
    let maxDistance = null;
    let maxHR = null;
    let sumHR = 0;
    let countHR = 0;
    let maxSpeed = null;
    let sumSpeed = 0;
    let countSpeed = 0;
    let tssSum = 0;

    for (const row of rows) {
      if (row.distance != null) maxDistance = maxDistance == null ? row.distance : Math.max(maxDistance, row.distance);
      if (row.heartRate != null) {
        maxHR = maxHR == null ? row.heartRate : Math.max(maxHR, row.heartRate);
        sumHR += row.heartRate;
        countHR++;
      }
      if (row.speed != null) {
        maxSpeed = maxSpeed == null ? row.speed : Math.max(maxSpeed, row.speed);
        sumSpeed += row.speed;
        countSpeed++;
      }
      tssSum += row.calculateTrainingStressScore();
    }

    return {
      datafile: first.datafile,
      sportType: first.sportType,
      samples: rows.length,
      distance: maxDistance,
      avgHeartRate: countHR ? sumHR / countHR : null,
      maxHeartRate: maxHR,
      avgSpeed: countSpeed ? sumSpeed / countSpeed : null,
      maxSpeed,
      avgTSS: rows.length ? tssSum / rows.length : null
    };
  }
}

class HeaderWidget {
  render(container) {
    const section = document.createElement('section');
    section.className = 'widget';
    section.innerHTML = `<h3>Dashboard</h3><p class="muted">Ready-to-use frontend with OOP classes and composition.</p>`;
    container.appendChild(section);
  }
}

class ControlsWidget {
  #sportSelect;
  #activitySelect;

  render(container, handlers) {
    const section = document.createElement('section');
    section.className = 'widget controls-widget';

    const sportLabel = document.createElement('label');
    sportLabel.textContent = 'Sport';
    this.#sportSelect = document.createElement('select');
    this.#sportSelect.innerHTML = `
      <option value="All">All</option>
      <option value="Running">Running</option>
      <option value="Cycling">Cycling</option>
    `;

    const activityLabel = document.createElement('label');
    activityLabel.textContent = 'Activity (datafile)';
    this.#activitySelect = document.createElement('select');
    this.#activitySelect.innerHTML = `<option value="ALL">All activities</option>`;

    this.#sportSelect.addEventListener('change', () => handlers.onSportChange(this.#sportSelect.value));
    this.#activitySelect.addEventListener('change', () => handlers.onDatafileChange(this.#activitySelect.value));

    section.appendChild(sportLabel);
    section.appendChild(this.#sportSelect);
    section.appendChild(activityLabel);
    section.appendChild(this.#activitySelect);

    container.appendChild(section);
  }

  updateDatafileOptions(rows, selectedValue) {
    if (!this.#activitySelect) return;

    this.#activitySelect.innerHTML = '<option value="ALL">All activities</option>';
    for (const row of rows) {
      const option = document.createElement('option');
      option.value = row.datafile;
      option.textContent = `${row.sportType} | ${row.datafile}`;
      this.#activitySelect.appendChild(option);
    }

    const values = [...this.#activitySelect.options].map((o) => o.value);
    this.#activitySelect.value = values.includes(selectedValue) ? selectedValue : 'ALL';
  }
}

export class GlobalStatsWidget {
  #el;

  render(container, allRows, filteredRows) {
    this.#el = document.createElement('section');
    this.#el.className = 'widget';
    container.appendChild(this.#el);
    this.update(allRows, filteredRows);
  }

  update(allRows, filteredRows) {
    if (!this.#el) return;

    const uniqueAll = new Set(allRows.map((a) => a.datafile)).size;
    const uniqueFiltered = new Set(filteredRows.map((a) => a.datafile)).size;
    const running = filteredRows.filter((a) => a.sportType === 'Running').length;
    const cycling = filteredRows.filter((a) => a.sportType === 'Cycling').length;
    const avgTSS = filteredRows.length
      ? filteredRows.reduce((sum, a) => sum + a.calculateTrainingStressScore(), 0) / filteredRows.length
      : null;

    this.#el.innerHTML = `
      <h3>Global Stats</h3>
      <div class="stats-grid">
        <div class="stat"><span>Total Unique Activities</span><strong>${uniqueAll}</strong></div>
        <div class="stat"><span>Filtered Unique Activities</span><strong>${uniqueFiltered}</strong></div>
        <div class="stat"><span>Running Samples</span><strong>${running}</strong></div>
        <div class="stat"><span>Cycling Samples</span><strong>${cycling}</strong></div>
        <div class="stat"><span>Avg Training Stress Score</span><strong>${avgTSS == null ? '-' : avgTSS.toFixed(1)}</strong></div>
      </div>
    `;
  }
}

export class ActivityListWidget {
  #el;

  constructor() {
    this.#el = null;
  }

  render(container, activities) {
    this.#el = document.createElement('section');
    this.#el.className = 'widget';
    container.appendChild(this.#el);
    this.update(activities);
  }

  update(activities) {
    if (!this.#el) return;

    const preview = activities.slice(0, 40);
    const rows = preview.map((a) => {
      return `<tr>
        <td>${a.timestamp || '-'}</td>
        <td>${a.datafile}</td>
        <td><span class="badge ${a.sportType === 'Running' ? 'running' : 'cycling'}">${a.sportType}</span></td>
        <td>${a.distance == null ? '-' : a.distance.toFixed(1)}</td>
        <td>${a.heartRate ?? '-'}</td>
        <td>${a.cadence ?? '-'}</td>
        <td>${a.speed == null ? '-' : a.speed.toFixed(1)}</td>
        <td>${a.calculateTrainingStressScore().toFixed(1)}</td>
      </tr>`;
    }).join('');

    this.#el.innerHTML = `
      <h3>Activity Samples (first ${preview.length})</h3>
      <div class="table-wrap">
        <table>
          <thead>
            <tr>
              <th>Timestamp</th>
              <th>Datafile</th>
              <th>Sport</th>
              <th>Distance (m)</th>
              <th>HR</th>
              <th>Cadence</th>
              <th>Speed (km/h)</th>
              <th>TSS</th>
            </tr>
          </thead>
          <tbody>${rows}</tbody>
        </table>
      </div>
    `;
  }
}

class ActivityDetailsWidget {
  #el;

  render(container, summary) {
    this.#el = document.createElement('section');
    this.#el.className = 'widget';
    container.appendChild(this.#el);
    this.update(summary);
  }

  update(summary) {
    if (!this.#el) return;

    if (!summary) {
      this.#el.innerHTML = '<h3>Selected Activity</h3><p class="muted">No activity selected.</p>';
      return;
    }

    this.#el.innerHTML = `
      <h3>Selected Activity</h3>
      <div class="stats-grid">
        <div class="stat"><span>Datafile</span><strong>${summary.datafile}</strong></div>
        <div class="stat"><span>Sport</span><strong>${summary.sportType}</strong></div>
        <div class="stat"><span>Samples</span><strong>${summary.samples}</strong></div>
        <div class="stat"><span>Distance (m)</span><strong>${summary.distance == null ? '-' : summary.distance.toFixed(1)}</strong></div>
        <div class="stat"><span>Avg HR</span><strong>${summary.avgHeartRate == null ? '-' : summary.avgHeartRate.toFixed(1)}</strong></div>
        <div class="stat"><span>Max HR</span><strong>${summary.maxHeartRate ?? '-'}</strong></div>
        <div class="stat"><span>Avg Speed (km/h)</span><strong>${summary.avgSpeed == null ? '-' : summary.avgSpeed.toFixed(1)}</strong></div>
        <div class="stat"><span>Max Speed (km/h)</span><strong>${summary.maxSpeed == null ? '-' : summary.maxSpeed.toFixed(1)}</strong></div>
        <div class="stat"><span>Avg TSS</span><strong>${summary.avgTSS == null ? '-' : summary.avgTSS.toFixed(1)}</strong></div>
      </div>
    `;
  }
}

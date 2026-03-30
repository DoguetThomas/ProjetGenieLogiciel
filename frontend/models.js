/**
 * OOP base class for all activities.
 */
export class Activity {
  #timestamp;
  #datafile;
  #distance;
  #heartRate;
  #cadence;
  #speed;

  constructor(raw) {
    this.#timestamp = raw.timestamp ?? '';
    this.#datafile = raw.datafile ?? 'unknown_activity';
    this.#distance = toNumber(raw.distance);
    this.#heartRate = toNumber(raw.heart_rate);
    this.#cadence = toNumber(raw.cadence);
    this.#speed = normalizeSpeed(raw.speed);
  }

  get timestamp() {
    return this.#timestamp;
  }

  get datafile() {
    return this.#datafile;
  }

  get distance() {
    return this.#distance;
  }

  get heartRate() {
    return this.#heartRate;
  }

  get cadence() {
    return this.#cadence;
  }

  get speed() {
    return this.#speed;
  }

  get sportType() {
    return 'Activity';
  }

  /**
   * POLYMORPHISM in JS:
   * - Same method name in all subclasses
   * - Different behavior per sport
   */
  calculateTrainingStressScore() {
    throw new Error('Subclasses must implement calculateTrainingStressScore()');
  }
}

export class RunningActivity extends Activity {
  #airPower;

  constructor(raw) {
    super(raw);
    this.#airPower = toNumber(raw.airPower);
  }

  get airPower() {
    return this.#airPower;
  }

  get sportType() {
    return 'Running';
  }

  calculateTrainingStressScore() {
    const hr = this.heartRate ?? 0;
    const speed = this.speed ?? 0;
    const power = this.airPower ?? 0;
    return Number((0.45 * hr + 0.35 * speed + 0.20 * power).toFixed(1));
  }
}

export class CyclingActivity extends Activity {
  get sportType() {
    return 'Cycling';
  }

  calculateTrainingStressScore() {
    const hr = this.heartRate ?? 0;
    const speed = this.speed ?? 0;
    const cadence = this.cadence ?? 0;
    return Number((0.40 * hr + 0.40 * speed + 0.20 * cadence).toFixed(1));
  }
}

export class UserProfileModel {
  #gender;
  #age;
  #weight;
  #height;

  constructor(raw = {}) {
    this.#gender = normalizeGender(raw.gender);
    this.#age = toNumber(raw.age);
    this.#weight = toNumber(raw.weight);
    this.#height = toNumber(raw.height);
  }

  get gender() {
    return this.#gender;
  }

  get age() {
    return this.#age;
  }

  get weight() {
    return this.#weight;
  }

  get height() {
    return this.#height;
  }

  toDto() {
    return {
      gender: this.#gender,
      age: this.#age,
      weight: this.#weight,
      height: this.#height
    };
  }
}

function toNumber(value) {
  const n = Number(value);
  return Number.isFinite(n) ? n : null;
}

function normalizeSpeed(rawSpeed) {
  const speed = toNumber(rawSpeed);
  if (speed === null) return null;
  return speed < 30 ? speed * 3.6 : speed;
}

function normalizeGender(value) {
  const text = String(value ?? '').trim().toUpperCase();
  if (text === 'MALE' || text === 'FEMALE') {
    return text;
  }
  return '';
}

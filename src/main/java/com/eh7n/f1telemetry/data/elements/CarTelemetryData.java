package com.eh7n.f1telemetry.data.elements;

public class CarTelemetryData {

	private int carIndex;
	private boolean playersCar;
	private int speed;
	private float throttle;
	private float steer;
	private float brake;
	private int clutch;
	private int gear;
	private int engineRpm;
	private boolean drsOn;
	private int revLightsPercent;
	private WheelData<Integer> brakesTemperature;
	private WheelData<Integer> tiresSurfaceTemperature;
	private WheelData<Integer> tiresInnerTemperature;
	private int engineTemperature;
	private WheelData<Float> tiresPressure;
	private WheelData<Integer> surfaceType;
	
	public CarTelemetryData() {}

	public int getCarIndex() {
		return carIndex;
	}

	public void setCarIndex(int carIndex) {
		this.carIndex = carIndex;
	}

	public boolean isPlayersCar() {
		return playersCar;
	}

	public void setPlayersCar(boolean playersCar) {
		this.playersCar = playersCar;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public float getThrottle() {
		return throttle;
	}

	public void setThrottle(float throttle) {
		this.throttle = throttle;
	}

	public float getSteer() {
		return steer;
	}

	public void setSteer(float steer) {
		this.steer = steer;
	}

	public float getBrake() {
		return brake;
	}

	public void setBrake(float brake) {
		this.brake = brake;
	}

	public int getClutch() {
		return clutch;
	}

	public void setClutch(int clutch) {
		this.clutch = clutch;
	}

	public int getGear() {
		return gear;
	}

	public void setGear(int gear) {
		this.gear = gear;
	}

	public int getEngineRpm() {
		return engineRpm;
	}

	public void setEngineRpm(int engineRpm) {
		this.engineRpm = engineRpm;
	}

	public boolean isDrsOn() {
		return drsOn;
	}

	public void setDrsOn(boolean drsOn) {
		this.drsOn = drsOn;
	}

	public int getRevLightsPercent() {
		return revLightsPercent;
	}

	public void setRevLightsPercent(int revLightsPercent) {
		this.revLightsPercent = revLightsPercent;
	}

	public WheelData<Integer> getBrakesTemperature() {
		return brakesTemperature;
	}

	public void setBrakesTemperature(WheelData<Integer> brakesTemperature) {
		this.brakesTemperature = brakesTemperature;
	}

	public WheelData<Integer> getTiresSurfaceTemperature() {
		return tiresSurfaceTemperature;
	}

	public void setTiresSurfaceTemperature(WheelData<Integer> tiresSurfaceTemperature) {
		this.tiresSurfaceTemperature = tiresSurfaceTemperature;
	}

	public WheelData<Integer> getTiresInnerTemperature() {
		return tiresInnerTemperature;
	}

	public void setTiresInnerTemperature(WheelData<Integer> tiresInnerTemperature) {
		this.tiresInnerTemperature = tiresInnerTemperature;
	}

	public int getEngineTemperature() {
		return engineTemperature;
	}

	public void setEngineTemperature(int engineTemperature) {
		this.engineTemperature = engineTemperature;
	}

	public WheelData<Float> getTiresPressure() {
		return tiresPressure;
	}

	public void setTiresPressure(WheelData<Float> tiresPressure) {
		this.tiresPressure = tiresPressure;
	}

	public WheelData<Integer> getSurfaceType() {
		return surfaceType;
	}

	public void setSurfaceType(WheelData<Integer> surfaceType) {
		this.surfaceType = surfaceType;
	}

}

package com.eh7n.f1telemetry.packet.data;

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
	private WheelData<Integer> tyresSurfaceTemperature;
	private WheelData<Integer> tyresInnerTemperature;
	private int engineTemperature;
	private WheelData<Float> tyresPressure;
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

	public WheelData<Integer> getTyresSurfaceTemperature() {
		return tyresSurfaceTemperature;
	}

	public void setTyresSurfaceTemperature(WheelData<Integer> tyresSurfaceTemperature) {
		this.tyresSurfaceTemperature = tyresSurfaceTemperature;
	}

	public WheelData<Integer> getTyresInnerTemperature() {
		return tyresInnerTemperature;
	}

	public void setTyresInnerTemperature(WheelData<Integer> tyresInnerTemperature) {
		this.tyresInnerTemperature = tyresInnerTemperature;
	}

	public int getEngineTemperature() {
		return engineTemperature;
	}

	public void setEngineTemperature(int engineTemperature) {
		this.engineTemperature = engineTemperature;
	}

	public WheelData<Float> getTyresPressure() {
		return tyresPressure;
	}

	public void setTyresPressure(WheelData<Float> tyresPressure) {
		this.tyresPressure = tyresPressure;
	}

	public WheelData<Integer> getSurfaceType() {
		return surfaceType;
	}

	public void setSurfaceType(WheelData<Integer> surfaceType) {
		this.surfaceType = surfaceType;
	}

}

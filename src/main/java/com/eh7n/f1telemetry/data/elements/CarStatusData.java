package com.eh7n.f1telemetry.data.elements;

public class CarStatusData {

	private int carIndex;
	private boolean playersCar;
	private int tractionControl;
	private boolean antiLockBrakesOn;
	private int fuelMix;
	private int frontBrakeBias;
	private boolean pitLimiterOn;
	private float fuelInTank;
	private float fuelCapacity;
	private float fuelRemainingLaps;
	private int maxRpm;
	private int idleRpm;
	private int maxGears;
	private int drsAllowed;
	private int drsActivationDistance;
	private WheelData<Integer> tiresWear;
	private int actualTyreCompound;
	private int visualTyreCompound;
	private int tyresAgeLaps;
	private WheelData<Integer> tiresDamage;
	private int frontLeftWingDamage;
	private int frontRightWingDamage;
	private int rearWingDamage;
	private boolean drsFault;
	private int engineDamage;
	private int gearBoxDamage;
	private ZoneFlag vehicleFiaFlags;
	private float ersStoreEnergy;
	private int ersDeployMode;
	private float ersHarvestedThisLapMGUK;
	private float ersHarvestedThisLapMGUH;
	private float ersDeployedThisLap;

	public CarStatusData() {}
	
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

	public int getTractionControl() {
		return tractionControl;
	}

	public void setTractionControl(int tractionControl) {
		this.tractionControl = tractionControl;
	}

	public boolean isAntiLockBrakesOn() {
		return antiLockBrakesOn;
	}

	public void setAntiLockBrakesOn(boolean antiLockBrakesOn) {
		this.antiLockBrakesOn = antiLockBrakesOn;
	}

	public int getFuelMix() {
		return fuelMix;
	}

	public void setFuelMix(int fuelMix) {
		this.fuelMix = fuelMix;
	}

	public int getFrontBrakeBias() {
		return frontBrakeBias;
	}

	public void setFrontBrakeBias(int frontBrakeBias) {
		this.frontBrakeBias = frontBrakeBias;
	}

	public boolean isPitLimiterOn() {
		return pitLimiterOn;
	}

	public void setPitLimiterOn(boolean pitLimiterOn) {
		this.pitLimiterOn = pitLimiterOn;
	}

	public float getFuelInTank() {
		return fuelInTank;
	}

	public void setFuelInTank(float fuelInTank) {
		this.fuelInTank = fuelInTank;
	}

	public float getFuelCapacity() {
		return fuelCapacity;
	}

	public void setFuelCapacity(float fuelCapacity) {
		this.fuelCapacity = fuelCapacity;
	}

	public float getFuelRemainingLaps() {
		return fuelRemainingLaps;
	}

	public void setFuelRemainingLaps(float fuelRemainingLaps) {
		this.fuelRemainingLaps = fuelRemainingLaps;
	}

	public int getMaxRpm() {
		return maxRpm;
	}

	public void setMaxRpm(int maxRpm) {
		this.maxRpm = maxRpm;
	}

	public int getIdleRpm() {
		return idleRpm;
	}

	public void setIdleRpm(int idleRpm) {
		this.idleRpm = idleRpm;
	}

	public int getMaxGears() {
		return maxGears;
	}

	public void setMaxGears(int maxGears) {
		this.maxGears = maxGears;
	}

	public int getDrsAllowed() {
		return drsAllowed;
	}

	public void setDrsAllowed(int drsAllowed) {
		this.drsAllowed = drsAllowed;
	}

	public int getDrsActivationDistance() {
		return drsActivationDistance;
	}

	public void setDrsActivationDistance(int drsActivationDistance) {
		this.drsActivationDistance = drsActivationDistance;
	}

	public WheelData<Integer> getTiresWear() {
		return tiresWear;
	}

	public void setTiresWear(WheelData<Integer> tiresWear) {
		this.tiresWear = tiresWear;
	}

	public int getActualTyreCompound() {
		return actualTyreCompound;
	}

	public void setActualTyreCompound(int actualTyreCompound) {
		this.actualTyreCompound = actualTyreCompound;
	}

	public int getVisualTyreCompound() {
		return visualTyreCompound;
	}

	public void setVisualTyreCompound(int visualTyreCompound) {
		this.visualTyreCompound = visualTyreCompound;
	}

	public int getTyresAgeLaps() {
		return tyresAgeLaps;
	}

	public void setTyresAgeLaps(int tyresAgeLaps) {
		this.tyresAgeLaps = tyresAgeLaps;
	}

	public WheelData<Integer> getTiresDamage() {
		return tiresDamage;
	}

	public void setTiresDamage(WheelData<Integer> tiresDamage) {
		this.tiresDamage = tiresDamage;
	}

	public int getFrontLeftWingDamage() {
		return frontLeftWingDamage;
	}

	public void setFrontLeftWingDamage(int frontLeftWheelDamage) {
		this.frontLeftWingDamage = frontLeftWheelDamage;
	}

	public int getFrontRightWingDamage() {
		return frontRightWingDamage;
	}

	public void setFrontRightWingDamage(int frontRightWingDamage) {
		this.frontRightWingDamage = frontRightWingDamage;
	}

	public int getRearWingDamage() {
		return rearWingDamage;
	}

	public void setRearWingDamage(int rearWingDamage) {
		this.rearWingDamage = rearWingDamage;
	}

	public boolean isDrsFault() {
		return drsFault;
	}

	public void setDrsFault(boolean drsFault) {
		this.drsFault = drsFault;
	}

	public int getEngineDamage() {
		return engineDamage;
	}

	public void setEngineDamage(int engineDamage) {
		this.engineDamage = engineDamage;
	}

	public int getGearBoxDamage() {
		return gearBoxDamage;
	}

	public void setGearBoxDamage(int gearBoxDamage) {
		this.gearBoxDamage = gearBoxDamage;
	}

	public ZoneFlag getVehicleFiaFlags() {
		return vehicleFiaFlags;
	}

	public void setVehicleFiaFlags(ZoneFlag vehicleFiaFlags) {
		this.vehicleFiaFlags = vehicleFiaFlags;
	}

	public float getErsStoreEnergy() {
		return ersStoreEnergy;
	}

	public void setErsStoreEnergy(float ersStoreEnergy) {
		this.ersStoreEnergy = ersStoreEnergy;
	}

	public int getErsDeployMode() {
		return ersDeployMode;
	}

	public void setErsDeployMode(int ersDeployMode) {
		this.ersDeployMode = ersDeployMode;
	}

	public float getErsHarvestedThisLapMGUK() {
		return ersHarvestedThisLapMGUK;
	}

	public void setErsHarvestedThisLapMGUK(float ersHarvestedThisLapMGUK) {
		this.ersHarvestedThisLapMGUK = ersHarvestedThisLapMGUK;
	}

	public float getErsHarvestedThisLapMGUH() {
		return ersHarvestedThisLapMGUH;
	}

	public void setErsHarvestedThisLapMGUH(float ersHarvestedThisLapMGUH) {
		this.ersHarvestedThisLapMGUH = ersHarvestedThisLapMGUH;
	}

	public float getErsDeployedThisLap() {
		return ersDeployedThisLap;
	}

	public void setErsDeployedThisLap(float ersDeployedThisLap) {
		this.ersDeployedThisLap = ersDeployedThisLap;
	}
	
}

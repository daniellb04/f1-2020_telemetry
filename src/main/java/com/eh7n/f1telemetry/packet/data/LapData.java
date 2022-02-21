package com.eh7n.f1telemetry.packet.data;

import com.eh7n.f1telemetry.packet.enums.DriverStatus;
import com.eh7n.f1telemetry.packet.enums.PitStatus;
import com.eh7n.f1telemetry.packet.enums.ResultStatus;

public class LapData {
	
	private int carIndex;
	private boolean playersCar;
	private float lastLapTime;
	private float currentLapTime;
	private int sector1TimeInMS;
	private int sector2TimeInMS;
	private float bestLapTime;
	private int bestLapNum;
	
	private int bestLapSector1TimeInMS;
	private int bestLapSector2TimeInMS;
	private int bestLapSector3TimeInMS;
	private int bestOverallSector1TimeInMS;
	private int bestOverallSector1LapNum;
	private int bestOverallSector2TimeInMS;
	private int bestOverallSector2LapNum;
	private int bestOverallSector3TimeInMS;
	private int bestOverallSector3LapNum;
	
	private float lapDistance;
	private float totalDistance;
	private float safetyCarDelta;
	private int carPosition;
	private int currentLapNum;
	private PitStatus pitStatus;
	private int sector;
	private boolean currentLapInvalid;
	private int penalties;
	private int gridPosition;
	private DriverStatus driverStatus;
	private ResultStatus resultStatus;
	
	public LapData() {}

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
	public float getLastLapTime() {
		return lastLapTime;
	}
	public void setLastLapTime(float lastLapTime) {
		this.lastLapTime = lastLapTime;
	}
	public float getCurrentLapTime() {
		return currentLapTime;
	}
	public void setCurrentLapTime(float currentLapTime) {
		this.currentLapTime = currentLapTime;
	}
	public int getSector1TimeInMS() {
		return sector1TimeInMS;
	}
	public void setSector1TimeInMS(int sector1TimeInMS) {
		this.sector1TimeInMS = sector1TimeInMS;
	}
	public int getSector2TimeInMS() {
		return sector2TimeInMS;
	}
	public void setSector2TimeInMS(int sector2TimeInMS) {
		this.sector2TimeInMS = sector2TimeInMS;
	}
	public float getBestLapTime() {
		return bestLapTime;
	}
	public void setBestLapTime(float bestLapTime) {
		this.bestLapTime = bestLapTime;
	}
	public int getBestLapNum() {
		return bestLapNum;
	}
	public void setBestLapNum(int bestLapNum) {
		this.bestLapNum = bestLapNum;
	}
	public int getBestLapSector1TimeInMS() {
		return bestLapSector1TimeInMS;
	}
	public void setBestLapSector1TimeInMS(int bestLapSector1TimeInMS) {
		this.bestLapSector1TimeInMS = bestLapSector1TimeInMS;
	}
	public int getBestLapSector2TimeInMS() {
		return bestLapSector2TimeInMS;
	}
	public void setBestLapSector2TimeInMS(int bestLapSector2TimeInMS) {
		this.bestLapSector2TimeInMS = bestLapSector2TimeInMS;
	}
	public int getBestLapSector3TimeInMS() {
		return bestLapSector3TimeInMS;
	}
	public void setBestLapSector3TimeInMS(int bestLapSector3TimeInMS) {
		this.bestLapSector3TimeInMS = bestLapSector3TimeInMS;
	}
	public int getBestOverallSector1TimeInMS() {
		return bestOverallSector1TimeInMS;
	}
	public void setBestOverallSector1TimeInMS(int bestOverallSector1TimeInMS) {
		this.bestOverallSector1TimeInMS = bestOverallSector1TimeInMS;
	}
	public int getBestOverallSector1LapNum() {
		return bestOverallSector1LapNum;
	}
	public void setBestOverallSector1LapNum(int bestOverallSector1LapNum) {
		this.bestOverallSector1LapNum = bestOverallSector1LapNum;
	}
	public int getBestOverallSector2TimeInMS() {
		return bestOverallSector2TimeInMS;
	}
	public void setBestOverallSector2TimeInMS(int bestOverallSector2TimeInMS) {
		this.bestOverallSector2TimeInMS = bestOverallSector2TimeInMS;
	}
	public int getBestOverallSector2LapNum() {
		return bestOverallSector2LapNum;
	}
	public void setBestOverallSector2LapNum(int bestOverallSector2LapNum) {
		this.bestOverallSector2LapNum = bestOverallSector2LapNum;
	}
	public int getBestOverallSector3TimeInMS() {
		return bestOverallSector3TimeInMS;
	}
	public void setBestOverallSector3TimeInMS(int bestOverallSector3TimeInMS) {
		this.bestOverallSector3TimeInMS = bestOverallSector3TimeInMS;
	}
	public int getBestOverallSector3LapNum() {
		return bestOverallSector3LapNum;
	}
	public void setBestOverallSector3LapNum(int bestOverallSector3LapNum) {
		this.bestOverallSector3LapNum = bestOverallSector3LapNum;
	}
	public float getLapDistance() {
		return lapDistance;
	}
	public void setLapDistance(float lapDistance) {
		this.lapDistance = lapDistance;
	}
	public float getTotalDistance() {
		return totalDistance;
	}
	public void setTotalDistance(float totalDistance) {
		this.totalDistance = totalDistance;
	}
	public float getSafetyCarDelta() {
		return safetyCarDelta;
	}
	public void setSafetyCarDelta(float safetyCarDelta) {
		this.safetyCarDelta = safetyCarDelta;
	}
	public int getCarPosition() {
		return carPosition;
	}
	public void setCarPosition(int carPosition) {
		this.carPosition = carPosition;
	}
	public int getCurrentLapNum() {
		return currentLapNum;
	}
	public void setCurrentLapNum(int currentLapNum) {
		this.currentLapNum = currentLapNum;
	}
	public PitStatus getPitStatus() {
		return pitStatus;
	}
	public void setPitStatus(PitStatus pitStatus) {
		this.pitStatus = pitStatus;
	}
	public int getSector() {
		return sector;
	}
	public void setSector(int sector) {
		this.sector = sector;
	}
	public boolean isCurrentLapInvalid() {
		return currentLapInvalid;
	}
	public void setCurrentLapInvalid(boolean currentLapInvalid) {
		this.currentLapInvalid = currentLapInvalid;
	}
	public int getPenalties() {
		return penalties;
	}
	public void setPenalties(int penalties) {
		this.penalties = penalties;
	}
	public int getGridPosition() {
		return gridPosition;
	}
	public void setGridPosition(int gridPosition) {
		this.gridPosition = gridPosition;
	}
	public DriverStatus getDriverStatus() {
		return driverStatus;
	}
	public void setDriverStatus(DriverStatus driverStatus) {
		this.driverStatus = driverStatus;
	}
	public ResultStatus getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}
	
}

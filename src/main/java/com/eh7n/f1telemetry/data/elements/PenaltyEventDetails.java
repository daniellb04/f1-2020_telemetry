package com.eh7n.f1telemetry.data.elements;

public class PenaltyEventDetails extends EventDataDetails {
	
	private PenaltyType penaltyType;
	private InfringementType infringementType;
	private int otherVehicleIdx;
	private int time;
	private int lapNum;
	private int placesGained;
	
	public PenaltyType getPenaltyType() {
		return penaltyType;
	}
	public void setPenaltyType(PenaltyType penaltyType) {
		this.penaltyType = penaltyType;
	}
	
	public InfringementType getInfringementType() {
		return infringementType;
	}
	public void setInfringementType(InfringementType infringementType) {
		this.infringementType = infringementType;
	}
	
	public int getOtherVehicleIdx() {
		return otherVehicleIdx;
	}
	public void setOtherVehicleIdx(int otherVehicleIdx) {
		this.otherVehicleIdx = otherVehicleIdx;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public int getLapNum() {
		return lapNum;
	}
	public void setLapNum(int lapNum) {
		this.lapNum = lapNum;
	}
	
	public int getPlacesGained() {
		return placesGained;
	}
	public void setPlacesGained(int placesGained) {
		this.placesGained = placesGained;
	}
	
}

package com.eh7n.f1telemetry.packet.data;

public class ParticipantData {
	
	private int carIndex;
	private boolean playersCar;
	private boolean aiControlled;
	private int driverId;
	private int teamId;
	private int raceNumber;
	private int nationality;
	private String name;
	private boolean publicTelemetry;
	
	public ParticipantData() {}
	
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

	public boolean isAiControlled() {
		return aiControlled;
	}
	public void setAiControlled(boolean aiControlled) {
		this.aiControlled = aiControlled;
	}
	public int getDriverId() {
		return driverId;
	}
	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getRaceNumber() {
		return raceNumber;
	}
	public void setRaceNumber(int raceNumber) {
		this.raceNumber = raceNumber;
	}
	public int getNationality() {
		return nationality;
	}
	public void setNationality(int nationality) {
		this.nationality = nationality;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPublicTelemetry() {
		return publicTelemetry;
	}
	public void setPublicTelemetry(boolean publicTelemetry) {
		this.publicTelemetry = publicTelemetry;
	}

}

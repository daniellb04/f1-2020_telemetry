package com.eh7n.f1telemetry.packet.data;

import com.eh7n.f1telemetry.packet.enums.ReadyStatus;

public class LobbyInfoData {
	private int carIndex;
	private boolean playersCar;
	private boolean aiControlled;
	private int teamId;
	private int nationality;
	private String name;
	private ReadyStatus readyStatus;
	
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
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
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
	public ReadyStatus getReadyStatus() {
		return readyStatus;
	}
	public void setReadyStatus(ReadyStatus readyStatus) {
		this.readyStatus = readyStatus;
	}
	
}

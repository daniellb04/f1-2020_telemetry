package com.eh7n.f1telemetry.packet;

import java.math.BigInteger;

public class Header {
	
	private int packetFormat;
	private int gameMajorVersion;
	private int gameMinorVersion;
	private int packetVersion;
	private int packetId;
	private BigInteger sessionUID;
	private float sessionTime;
	private long frameIdentifier;
	private int playerCarIndex;
	private int secondaryPlayerCarIndex;
	
	public Header() {}

	public int getPacketFormat() {
		return packetFormat;
	}
	
	public void setPacketFormat(int packetFormat) {
		this.packetFormat = packetFormat;
	}
	
	public int getGameMajorVersion() {
		return gameMajorVersion;
	}

	public void setGameMajorVersion(int gameMajorVersion) {
		this.gameMajorVersion = gameMajorVersion;
	}

	public int getGameMinorVersion() {
		return gameMinorVersion;
	}

	public void setGameMinorVersion(int gameMinorVersion) {
		this.gameMinorVersion = gameMinorVersion;
	}

	public int getPacketVersion() {
		return packetVersion;
	}
	
	public void setPacketVersion(int packetVersion) {
		this.packetVersion = packetVersion;
	}
	
	public int getPacketId() {
		return packetId;
	}
	
	public void setPacketId(int packetId) {
		this.packetId = packetId;
	}
	
	public BigInteger getSessionUID() {
		return sessionUID;
	}
	
	public void setSessionUID(BigInteger sessionUID) {
		this.sessionUID = sessionUID;
	}
	
	public float getSessionTime() {
		return sessionTime;
	}
	
	public void setSessionTime(float sessionTime) {
		this.sessionTime = sessionTime;
	}
	
	public long getFrameIdentifier() {
		return frameIdentifier;
	}
	
	public void setFrameIdentifier(long frameIdentifier) {
		this.frameIdentifier = frameIdentifier;
	}
	
	public int getPlayerCarIndex() {
		return playerCarIndex;
	}
	
	public void setPlayerCarIndex(int playerCarIndex) {
		this.playerCarIndex = playerCarIndex;
	}
	
	public int getSecondaryPlayerCarIndex() {
		return secondaryPlayerCarIndex;
	}

	public void setSecondaryPlayerCarIndex(int secondaryPlayerCarIndex) {
		this.secondaryPlayerCarIndex = secondaryPlayerCarIndex;
	}

	@Override
	public String toString() {
		return "Header :: packetFormat:" + this.getPacketFormat() + 
		", game version:" + this.getGameMajorVersion() + '.' + this.getGameMinorVersion() +
		", version:" + this.getPacketVersion() +
		", packetId:" + this.getPacketId() + 
		", sessionUID:" + this.getSessionUID() + 
		", sessionTime:" + this.getSessionTime() +
		", frameIdentifier:" + this.getFrameIdentifier() +
		", playerCarIndex:" + this.getPlayerCarIndex() +
		", secondaryPlayerCarIndex:" + this.getSecondaryPlayerCarIndex();
	}

}

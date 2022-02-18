package com.eh7n.f1telemetry.data;

import java.util.List;

import com.eh7n.f1telemetry.data.elements.ParticipantData;

public class PacketParticipantsData extends Packet {
	
	private int numActiveCars;
	private List<ParticipantData> participants;
	
	public PacketParticipantsData() {}
	
	public int getNumActiveCars() {
		return numActiveCars;
	}
	public void setNumActiveCars(int numActiveCars) {
		this.numActiveCars = numActiveCars;
	}
	public List<ParticipantData> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ParticipantData> participants) {
		this.participants = participants;
	}
}

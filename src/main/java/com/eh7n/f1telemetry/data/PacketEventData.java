package com.eh7n.f1telemetry.data;

import com.eh7n.f1telemetry.data.elements.EventDataDetails;
import com.eh7n.f1telemetry.data.elements.EventType;

public class PacketEventData extends Packet {

	private EventType eventCode;
	private EventDataDetails eventDetails;

	public PacketEventData() {}
	
	public EventType getEventCode() {
		return eventCode;
	}

	public void setEventCode(EventType eventCode) {
		this.eventCode = eventCode;
	}

	public EventDataDetails getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(EventDataDetails eventDetails) {
		this.eventDetails = eventDetails;
	}

}

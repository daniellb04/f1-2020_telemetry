package com.eh7n.f1telemetry.data;

import com.eh7n.f1telemetry.data.elements.EventDataDetails;
import com.eh7n.f1telemetry.data.elements.EventType;

public class PacketEventData extends Packet {

	private EventType eventType;
	private EventDataDetails eventDetails;

	public PacketEventData() {}
	
	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public EventDataDetails getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(EventDataDetails eventDetails) {
		this.eventDetails = eventDetails;
	}

}

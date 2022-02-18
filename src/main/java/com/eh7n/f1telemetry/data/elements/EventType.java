package com.eh7n.f1telemetry.data.elements;

public enum EventType {
	
	SESSION_STARTED,		// Sent when the session starts
	SESSION_ENDED,			// Sent when the session ends
	FASTEST_LAP,			// When a driver achieves the fastest lap
	RETIREMENT,				// When a driver retires
	DRS_ENABLED,			// Race control have enabled DRS
	DRS_DISABLED,			// Race control have disabled DRS
	TEAM_MATE_IN_PITS,		// Your team mate has entered the pits
	CHEQUERED_FLAG,			// The chequered flag has been waved
	RACE_WINER,				// The race winner is announced
	PENALTY_ISSUED,			// A penalty has been issued
	SPEED_TRAP_TRIGGERED;	// Speed trap has been triggered by fastest speed
	
	public static EventType fromCode(String code) {
		switch(code) {
			case "SSTA":
				return EventType.SESSION_STARTED;
			case "SEND":
				return EventType.SESSION_ENDED;
			case "FTLP":
				return EventType.FASTEST_LAP;
			case "RTMT":
				return EventType.RETIREMENT;
			case "DRSE":
				return EventType.DRS_ENABLED;
			case "DRSD":
				return EventType.DRS_DISABLED;
			case "TMPT":
				return EventType.TEAM_MATE_IN_PITS;
			case "CHQF":
				return EventType.CHEQUERED_FLAG;
			case "RCWN":
				return EventType.RACE_WINER;
			case "PENA":
				return EventType.PENALTY_ISSUED;
			case "SPTP":
				return EventType.SPEED_TRAP_TRIGGERED;
			default:
				return null;
		}
	}
}

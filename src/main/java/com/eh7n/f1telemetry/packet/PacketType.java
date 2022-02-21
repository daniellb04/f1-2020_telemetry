package com.eh7n.f1telemetry.packet;

public enum PacketType {
	
	MOTION,					// Contains all motion data for player’s car – only sent while player is in control
	SESSION,				// Data about the session – track, time left
	LAP_DATA,				// Data about all the lap times of cars in the session
	EVENT,					// Various notable events that happen during a session
	PARTICIPANTS,			// List of participants in the session, mostly relevant for multiplayer
	CAR_SETUP,				// Packet detailing car setups for cars in the race
	CAR_TELEMETRY,			// Telemetry data for all cars
	CAR_STATUS,				// Status data for all cars such as damage
	FINAL_CLASSIFICATION,	// Final classification confirmation at the end of a race
	LOBBY_INFO;				// Information about players in a multiplayer lobby

	public static PacketType fromInt(int i) {
		switch(i) {
			case 0:
				return MOTION;
			case 1:
				return SESSION;
			case 2:
				return LAP_DATA;
			case 3:
				return EVENT;
			case 4:
				return PARTICIPANTS;
			case 5:
				return CAR_SETUP;
			case 6:
				return CAR_TELEMETRY;
			case 7:
				return CAR_STATUS;
			case 8:
				return FINAL_CLASSIFICATION;
			case 9:
				return LOBBY_INFO;
			default:
				return null;
		}
	}

}

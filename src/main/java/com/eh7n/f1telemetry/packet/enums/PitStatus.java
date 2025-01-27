package com.eh7n.f1telemetry.packet.enums;

public enum PitStatus {
	
	NONE,
	PITTING,
	IN_PIT;

	public static PitStatus fromInt(int i) {
		switch(i) {
			case 1:
				return PitStatus.PITTING;
			case 2:
				return PitStatus.IN_PIT;
			default:
				return PitStatus.NONE;
		}
	}
}

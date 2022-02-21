package com.eh7n.f1telemetry.packet.enums;

public enum ReadyStatus {
	
	NOT_READY,
	READY,
	SPECTATING;

	public static ReadyStatus fromInt(int i) {
		switch(i) {
			case 0:
				return ReadyStatus.NOT_READY;
			case 1:
				return ReadyStatus.READY;
			case 2:
				return ReadyStatus.SPECTATING;
			default:
				return null;
		}
	}
}

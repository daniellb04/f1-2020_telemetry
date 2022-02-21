package com.eh7n.f1telemetry.packet.data;

import com.eh7n.f1telemetry.packet.enums.ZoneFlag;

public class MarshalZone {

	private float zoneStart;
	private ZoneFlag zoneFlag;
	
	public float getZoneStart() {
		return zoneStart;
	}
	
	public void setZoneStart(float zoneStart) {
		this.zoneStart = zoneStart;
	}
	
	public ZoneFlag getZoneFlag() {
		return zoneFlag;
	}
	
	public void setZoneFlag(ZoneFlag zoneFlag) {
		this.zoneFlag = zoneFlag;
	}
	
}

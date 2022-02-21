package com.eh7n.f1telemetry.packet;

import com.eh7n.f1telemetry.util.PacketBuffer;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Packet {
	
	public static final int TOTAL_NBR_CARS=22;
	
	private PacketType type;
	private Header header;

	public PacketType getType() {
		return type;
	}
	
	public Header getHeader() {
		return header;
	}
/*
	public void setHeader(Header header) {
		this.header = header;
		this.type = PacketType.fromInt(header.getPacketId());
	}
*/	
	public String toJSON() {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(this);
		}catch(Exception e) {
			//TODO: Handle this exception
		}
		return json.replace("\\u0000", "");
	}

	public Packet withHeader(Header header) {
		this.header = header;
		this.type = PacketType.fromInt(header.getPacketId());
		return this;
	}
	
	public abstract Packet build(PacketBuffer buffer);

}

package com.eh7n.f1telemetry.data;

import com.eh7n.f1telemetry.data.elements.Header;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Packet {
	
	private PacketType type;
	private Header header;

	public PacketType getType() {
		return type;
	}
	
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
		this.type = PacketType.fromInt(header.getPacketId());
	}

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

}

package com.eh7n.f1telemetry.util;

import java.util.Arrays;
import java.util.List;

import com.eh7n.f1telemetry.packet.CarSetupPacket;
import com.eh7n.f1telemetry.packet.CarStatusPacket;
import com.eh7n.f1telemetry.packet.CarTelemetryPacket;
import com.eh7n.f1telemetry.packet.EventPacket;
import com.eh7n.f1telemetry.packet.FinalClassificationPacket;
import com.eh7n.f1telemetry.packet.Header;
import com.eh7n.f1telemetry.packet.LapDataPacket;
import com.eh7n.f1telemetry.packet.LobbyInfoPacket;
import com.eh7n.f1telemetry.packet.MotionPacket;
import com.eh7n.f1telemetry.packet.Packet;
import com.eh7n.f1telemetry.packet.PacketType;
import com.eh7n.f1telemetry.packet.ParticipantsPacket;
import com.eh7n.f1telemetry.packet.SessionPacket;

/**
 * F1 2020 PacketReader is the master class for reading and construct
 * the packet object from the incoming UDP packets
 */
public class PacketReader {
	
	private static final List<PacketType> ALL_PACKETS = Arrays.asList(
			PacketType.MOTION,
			PacketType.SESSION,
			PacketType.LAP_DATA,
			PacketType.EVENT,
			PacketType.PARTICIPANTS,
			PacketType.CAR_SETUP,
			PacketType.CAR_TELEMETRY,
			PacketType.CAR_STATUS,
			PacketType.FINAL_CLASSIFICATION,
			PacketType.LOBBY_INFO
			);
	
	private PacketBuffer buffer;

	private PacketReader(byte[] data) {
		buffer = PacketBuffer.wrap(data);
	}

	/**
	 * Read all types of packet data from a byte array
	 * 
	 * @param data : a F1 2020 UDP packet
	 * @return a Packet POJO
	 */
	public static Packet read(byte[] data) {
		return read(data, ALL_PACKETS);
	}

	/**
	 * Read specific types of packet data from a byte array
	 * 
	 * @param data : a F1 2020 UDP packet
	 * @param types : a list of types that must be processed
	 * @return a Packet POJO or null if the packet type isn't in the list
	 */
	public static Packet read(byte[] data, List<PacketType> types) {
		return new PacketReader(data).buildPacket(types);
	}

	private Packet buildPacket(List<PacketType> types) {

		Header header = buildHeader();
		PacketType packetType = PacketType.fromInt(header.getPacketId());
		
		if (!types.contains(packetType))
			return null;

		switch (packetType) {
			case MOTION:
				return new MotionPacket().withHeader(header).build(buffer);
			case SESSION:
				return new SessionPacket().withHeader(header).build(buffer);
			case LAP_DATA:
				return new LapDataPacket().withHeader(header).build(buffer);
			case EVENT:
				return new EventPacket().withHeader(header).build(buffer);
			case PARTICIPANTS:
				return new ParticipantsPacket().withHeader(header).build(buffer);
			case CAR_SETUP:
				return new CarSetupPacket().withHeader(header).build(buffer);
			case CAR_TELEMETRY:
				return new CarTelemetryPacket().withHeader(header).build(buffer);
			case CAR_STATUS:
				return new CarStatusPacket().withHeader(header).build(buffer);
			case FINAL_CLASSIFICATION:
				return new FinalClassificationPacket().withHeader(header).build(buffer);
			case LOBBY_INFO:
				return new LobbyInfoPacket().withHeader(header).build(buffer);
		}

		return null;
	}
	
	/**
	 * HEADER
	 * 
	 * Each packet has the following header
	 * 
	 * <pre>
	 * {@code 
	 	struct PacketHeader
		{
			uint16	m_packetFormat;				// 2020
			uint8	m_gameMajorVersion;			// Game major version - "X.00"
			uint8	m_gameMinorVersion;			// Game minor version - "1.XX"
			uint8	m_packetVersion;			// Version of this packet type, all start from 1
			uint8	m_packetId;					// Identifier for the packet type, see below
			uint64	m_sessionUID;				// Unique identifier for the session
			float	m_sessionTime;				// Session timestamp
			uint32	m_frameIdentifier;			// Identifier for the frame the data was retrieved on
			uint8	m_playerCarIndex;			// Index of player's car in the array
			uint8	m_secondaryPlayerCarIndex;	// Index of secondary player's car in the
												// array (splitscreen)
												// 255 if no second player
		};
	 * }
	 * </pre>
	 */
	private Header buildHeader() {

		Header header = new Header();

		header.setPacketFormat(buffer.getNextUInt16AsInt());
		header.setGameMajorVersion(buffer.getNextUInt8AsInt());
		header.setGameMinorVersion(buffer.getNextUInt8AsInt());
		header.setPacketVersion(buffer.getNextUInt8AsInt());
		header.setPacketId(buffer.getNextUInt8AsInt());
		header.setSessionUID(buffer.getNextUInt64AsBigInteger());
		header.setSessionTime(buffer.getNextFloat());
		header.setFrameIdentifier(buffer.getNextUInt32AsLong());
		header.setPlayerCarIndex(buffer.getNextUInt8AsInt());
		header.setSecondaryPlayerCarIndex(buffer.getNextUInt8AsInt());
		return header;
	}

}


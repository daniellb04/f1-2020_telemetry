package com.eh7n.f1telemetry.util;

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
 * F1 2020 PacketDeserializer is the main class for deserializing the incoming
 * UDP packets and building Packet POJOs from the byte arrays
 * 
 * This class was created based on the documented UDP Specification located on
 * the Codemasters forums.
 * 
 * @author eh7n
 * @see https://forums.codemasters.com/topic/50942-f1-2020-udp-specification
 *
 */
public class PacketDeserializer {
	
	private static int currentNumParticipants = 0;

	private PacketBuffer buffer;

	private PacketDeserializer(byte[] data) {
		buffer = PacketBuffer.wrap(data);
	}

	/**
	 * Read the packet data from a byte array
	 * 
	 * @param data : a F1 2020 UDP packet
	 * @return a Packet POJO
	 */
	public static Packet read(byte[] data) {
		return new PacketDeserializer(data).buildPacket();
	}

	private Packet buildPacket() {

		Header header = buildHeader();

		switch (PacketType.fromInt(header.getPacketId())) {
			case MOTION:
				return build(new MotionPacket().withHeader(header));
			case SESSION:
				return build(new SessionPacket().withHeader(header));
			case LAP_DATA:
				return build(new LapDataPacket().withHeader(header));
			case EVENT:
				return build(new EventPacket().withHeader(header));
			case PARTICIPANTS:
				ParticipantsPacket packet = (ParticipantsPacket) build(new ParticipantsPacket().withHeader(header));
				currentNumParticipants = packet.getNumActiveCars();
				return packet;
			case CAR_SETUP:
				return build(new CarSetupPacket().withHeader(header));
			case CAR_TELEMETRY:
				return build(new CarTelemetryPacket().withHeader(header));
			case CAR_STATUS:
				return build(new CarStatusPacket().withHeader(header));
			case FINAL_CLASSIFICATION:
				return build(new FinalClassificationPacket().withHeader(header));
			case LOBBY_INFO:
				return build(new LobbyInfoPacket().withHeader(header));
		}

		return null;
	}
	
	private Packet build(Packet packet) {
		return packet.build(buffer, currentNumParticipants);
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


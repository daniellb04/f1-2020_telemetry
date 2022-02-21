package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.ParticipantData;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * PARTICIPANTS PACKET
 * 
 * This is a list of participants in the race. If the vehicle is controlled by
 * AI, then the name will be the driver name. If this is a multiplayer game, the
 * names will be the Steam Id on PC, or the LAN name if appropriate. On Xbox
 * One, the names will always be the driver name, on PS4 the name will be the
 * LAN name if playing a LAN game, otherwise it will be the driver name.
 * 
 * Frequency: Every 5 seconds
 * 
 * Size: 1213 bytes
 * 
 * <pre>
 * {@code
	struct ParticipantData
	{
	    uint8      m_aiControlled;           // Whether the vehicle is AI (1) or Human (0) controlled
	    uint8      m_driverId;               // Driver id - see appendix
	    uint8      m_teamId;                 // Team id - see appendix
	    uint8      m_raceNumber;             // Race number of the car
	    uint8      m_nationality;            // Nationality of the driver
	    char       m_name[48];               // Name of participant in UTF-8 format – null terminated
	                                         // Will be truncated with … (U+2026) if too long
    	uint8      m_yourTelemetry;          // The player's UDP setting, 0 = restricted, 1 = public 
    };

	struct PacketParticipantsData
	{
	    PacketHeader    m_header;            // Header
	    uint8           m_numCars;           // Number of cars in the data
	    ParticipantData m_participants[22];
	};	 
 * }
 * </pre>
 */
public class ParticipantsPacket extends Packet {
	
	private int numActiveCars;
	private List<ParticipantData> participants;
	
	public ParticipantsPacket() {}
	
	public int getNumActiveCars() {
		return numActiveCars;
	}
	public void setNumActiveCars(int numActiveCars) {
		this.numActiveCars = numActiveCars;
	}
	public List<ParticipantData> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ParticipantData> participants) {
		this.participants = participants;
	}

	@Override
	public Packet build(PacketBuffer buffer) {
		setNumActiveCars(buffer.getNextUInt8AsInt());
		List<ParticipantData> participants = new ArrayList<>();
		for (int k = 0; k < TOTAL_NBR_CARS; k++) {
			participants.add(buildParticipantData(buffer, k, k == getHeader().getPlayerCarIndex()));
		}
		setParticipants(participants);
		return this;
	}

	private ParticipantData buildParticipantData(PacketBuffer buffer, int carIndex, boolean playersCar) {
		ParticipantData participant = new ParticipantData();
		participant.setCarIndex(carIndex);
		participant.setPlayersCar(playersCar);
		participant.setAiControlled(buffer.getNextUInt8AsBoolean());
		participant.setDriverId(buffer.getNextUInt8AsInt());
		participant.setTeamId(buffer.getNextUInt8AsInt());
		participant.setRaceNumber(buffer.getNextUInt8AsInt());
		participant.setNationality(buffer.getNextUInt8AsInt());
		participant.setName(buffer.getNextCharArrayAsString(48));
		participant.setPublicTelemetry(buffer.getNextUInt8AsBoolean());
		return participant;
	}

}

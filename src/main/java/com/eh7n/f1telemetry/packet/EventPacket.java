package com.eh7n.f1telemetry.packet;

import com.eh7n.f1telemetry.packet.data.EventDataDetails;
import com.eh7n.f1telemetry.packet.data.FastestLapEventDetails;
import com.eh7n.f1telemetry.packet.data.PenaltyEventDetails;
import com.eh7n.f1telemetry.packet.data.SpeedTrapEventDetails;
import com.eh7n.f1telemetry.packet.enums.EventType;
import com.eh7n.f1telemetry.packet.enums.InfringementType;
import com.eh7n.f1telemetry.packet.enums.PenaltyType;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * EVENT PACKET
 * 
 * This packet gives details of events that happen during the course of the
 * race.
 * 
 * Frequency: When the event occurs
 * 
 * Size: 35 bytes
 * 
 * <pre>
 * {@code 
	union EventDataDetails
	{
	    struct
	    {
	        uint8	vehicleIdx;         // Vehicle index of car achieving fastest lap
	        float	lapTime;            // Lap time is in seconds
	    } FastestLap;
	
	    struct
	    {
	        uint8   vehicleIdx;         // Vehicle index of car retiring
	    } Retirement;
	
	    struct
	    {
	        uint8   vehicleIdx;         // Vehicle index of team mate
	    } TeamMateInPits;
	
	    struct
	    {
	        uint8   vehicleIdx;         // Vehicle index of the race winner
	    } RaceWinner;
	
	    struct
	    {
	    	uint8 penaltyType;          // Penalty type – see Appendices
	        uint8 infringementType;     // Infringement type – see Appendices
	        uint8 vehicleIdx;           // Vehicle index of the car the penalty is applied to
	        uint8 otherVehicleIdx;      // Vehicle index of the other car involved
	        uint8 time;                 // Time gained, or time spent doing action in seconds
	        uint8 lapNum;               // Lap the penalty occurred on
	        uint8 placesGained;         // Number of places gained by this
	    } Penalty;
	
	    struct
	    {
	        uint8 vehicleIdx;           // Vehicle index of the vehicle triggering speed trap
	        float speed;                // Top speed achieved in kilometres per hour
	    } SpeedTrap;
	};

	struct PacketEventData
	{
	    PacketHeader   		m_header;               // Header
	    char	           	m_eventStringCode[4];   // Event string code, see above
	    EventDataDetails 	m_eventDetails;         // Event details - should be interpreted differently
	    									        // for each type
	};
 * }
 * </pre>
 */
public class EventPacket extends Packet {

	private EventType eventType;
	private EventDataDetails eventDetails;

	public EventPacket() {}
	
	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public EventDataDetails getEventDetails() {
		return eventDetails;
	}

	public void setEventDetails(EventDataDetails eventDetails) {
		this.eventDetails = eventDetails;
	}

	@Override
	public Packet build(PacketBuffer buffer) {
		setEventType(EventType.fromCode(buffer.getNextCharArrayAsString(4)));
		setEventDetails(buildEventDataDetails(buffer, getEventType()));
		return this;
	}

	private EventDataDetails buildEventDataDetails(PacketBuffer buffer, EventType eventType) {
		if (eventType == null ||
			EventType.SESSION_STARTED.equals(eventType) ||
			EventType.SESSION_ENDED.equals(eventType) ||
			EventType.DRS_ENABLED.equals(eventType) ||
			EventType.DRS_DISABLED.equals(eventType) ||
			EventType.CHEQUERED_FLAG.equals(eventType))
			return null; // Events without details
		
		EventDataDetails data;
		if (EventType.PENALTY_ISSUED.equals(eventType)) {
			data = buildPenaltyEventDetails(buffer);
		}
		else {
			int vehicleIdx = buffer.getNextUInt8AsInt();
			switch(eventType) {
				case FASTEST_LAP:
					data = buildFastestLapEventDetails(buffer);
					break;
				case SPEED_TRAP_TRIGGERED:
					data = buildSpeedTrapEventDetails(buffer);
					break;
				default:
					data = new EventDataDetails();
			}
			data.setVehicleIdx(vehicleIdx);
		}
		return data;
	}
	
	private FastestLapEventDetails buildFastestLapEventDetails(PacketBuffer buffer) {
		FastestLapEventDetails data = new FastestLapEventDetails();
		data.setLapTime(buffer.getNextFloat());
		return data;
	}
	
	private PenaltyEventDetails buildPenaltyEventDetails(PacketBuffer buffer) {
		PenaltyEventDetails data = new PenaltyEventDetails();
		data.setPenaltyType(PenaltyType.fromInt(buffer.getNextUInt8AsInt()));
		data.setInfringementType(InfringementType.fromInt(buffer.getNextUInt8AsInt()));
		data.setVehicleIdx(buffer.getNextUInt8AsInt());
		data.setOtherVehicleIdx(buffer.getNextUInt8AsInt());
		data.setTime(buffer.getNextUInt8AsInt());
		data.setLapNum(buffer.getNextUInt8AsInt());
		data.setPlacesGained(buffer.getNextUInt8AsInt());
		return data;
	}
	
	private SpeedTrapEventDetails buildSpeedTrapEventDetails(PacketBuffer buffer) {
		SpeedTrapEventDetails data = new SpeedTrapEventDetails();
		data.setSpeed(buffer.getNextFloat());
		return data;
	}

}

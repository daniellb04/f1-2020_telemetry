package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.LapData;
import com.eh7n.f1telemetry.packet.enums.DriverStatus;
import com.eh7n.f1telemetry.packet.enums.PitStatus;
import com.eh7n.f1telemetry.packet.enums.ResultStatus;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * LAP DATA PACKET
 * 
 * The lap data packet gives details of all the cars in the session.
 * 
 * Frequency: Rate as specified in menus
 * 
 * Size: 1190 bytes
 * 
 * <pre>
 * {@code 
	struct LapData
	{
	    float       m_lastLapTime;               // Last lap time in seconds
	    float       m_currentLapTime;            // Current time around the lap in seconds
	    uint16      m_sector1TimeInMS;           // Sector 1 time in milliseconds
    	uint16      m_sector2TimeInMS;           // Sector 2 time in milliseconds
	    float       m_bestLapTime;               // Best lap time of the session in seconds
	    uint8       m_bestLapNum;                // Lap number best time achieved on
	    uint16      m_bestLapSector1TimeInMS;    // Sector 1 time of best lap in the session in milliseconds
	    uint16      m_bestLapSector2TimeInMS;    // Sector 2 time of best lap in the session in milliseconds
	    uint16      m_bestLapSector3TimeInMS;    // Sector 3 time of best lap in the session in milliseconds
	    uint16      m_bestOverallSector1TimeInMS;// Best overall sector 1 time of the session in milliseconds
	    uint8       m_bestOverallSector1LapNum;  // Lap number best overall sector 1 time achieved on
	    uint16      m_bestOverallSector2TimeInMS;// Best overall sector 2 time of the session in milliseconds
	    uint8       m_bestOverallSector2LapNum;  // Lap number best overall sector 2 time achieved on
	    uint16      m_bestOverallSector3TimeInMS;// Best overall sector 3 time of the session in milliseconds
	    uint8       m_bestOverallSector3LapNum;  // Lap number best overall sector 3 time achieved on
	    float       m_lapDistance;               // Distance vehicle is around current lap in metres – could
	                                             // be negative if line hasn’t been crossed yet
	    float       m_totalDistance;             // Total distance travelled in session in metres – could
	                                             // be negative if line hasn’t been crossed yet
	    float       m_safetyCarDelta;            // Delta in seconds for safety car
	    uint8       m_carPosition;               // Car race position
	    uint8       m_currentLapNum;             // Current lap number
	    uint8       m_pitStatus;                 // 0 = none, 1 = pitting, 2 = in pit area
	    uint8       m_sector;                    // 0 = sector1, 1 = sector2, 2 = sector3
	    uint8       m_currentLapInvalid;         // Current lap invalid - 0 = valid, 1 = invalid
	    uint8       m_penalties;                 // Accumulated time penalties in seconds to be added
	    uint8       m_gridPosition;              // Grid position the vehicle started the race in
	    uint8       m_driverStatus;              // Status of driver - 0 = in garage, 1 = flying lap
	                                             // 2 = in lap, 3 = out lap, 4 = on track
	    uint8       m_resultStatus;              // Result status - 0 = invalid, 1 = inactive, 2 = active
	                                             // 3 = finished, 4 = disqualified, 5 = not classified
	                                             // 6 = retired
    }
    
	struct PacketLapData
	{
		PacketHeader    m_header;              // Header
		LapData         m_lapData[22];         // Lap data for all cars on track
	};
 * }
 * </pre>
 */
public class LapDataPacket extends Packet {
	
	private List<LapData> lapDataList;
	
	public LapDataPacket() {}
	
	public List<LapData> getLapDataList() {
		return lapDataList;
	}
	
	public void setLapDataList(List<LapData> lapDataList) {
		this.lapDataList = lapDataList;
	}

	@Override
	public Packet build(PacketBuffer buffer, int numParticipants) {
		List<LapData> lapDataList = new ArrayList<>();
		for (int i = 0; i < numParticipants; i++) {
			lapDataList.add(buildLapData(buffer, i, i == getHeader().getPlayerCarIndex()));
		}
		setLapDataList(lapDataList);
		return this;
	}

	private LapData buildLapData(PacketBuffer buffer, int carIndex, boolean playersCar) {

		LapData lapData = new LapData();

		lapData.setCarIndex(carIndex);
		lapData.setPlayersCar(playersCar);
		lapData.setLastLapTime(buffer.getNextFloat());
		lapData.setCurrentLapTime(buffer.getNextFloat());
		lapData.setSector1TimeInMS(buffer.getNextUInt16AsInt());
		lapData.setSector2TimeInMS(buffer.getNextUInt16AsInt());
		lapData.setBestLapTime(buffer.getNextFloat());
		lapData.setBestLapNum(buffer.getNextUInt8AsInt());
		
		lapData.setBestLapSector1TimeInMS(buffer.getNextUInt16AsInt());
		lapData.setBestLapSector2TimeInMS(buffer.getNextUInt16AsInt());
		lapData.setBestLapSector3TimeInMS(buffer.getNextUInt16AsInt());
		lapData.setBestOverallSector1TimeInMS(buffer.getNextUInt16AsInt());
		lapData.setBestOverallSector1LapNum(buffer.getNextUInt8AsInt());
		lapData.setBestOverallSector2TimeInMS(buffer.getNextUInt16AsInt());
		lapData.setBestOverallSector2LapNum(buffer.getNextUInt8AsInt());
		lapData.setBestOverallSector3TimeInMS(buffer.getNextUInt16AsInt());
		lapData.setBestOverallSector3LapNum(buffer.getNextUInt8AsInt());
		
		lapData.setLapDistance(buffer.getNextFloat());
		lapData.setTotalDistance(buffer.getNextFloat());
		lapData.setSafetyCarDelta(buffer.getNextFloat());
		lapData.setCarPosition(buffer.getNextUInt8AsInt());
		lapData.setCurrentLapNum(buffer.getNextUInt8AsInt());
		lapData.setPitStatus(PitStatus.fromInt(buffer.getNextUInt8AsInt()));
		lapData.setSector(buffer.getNextUInt8AsInt() + 1);
		lapData.setCurrentLapInvalid(buffer.getNextUInt8AsBoolean());
		lapData.setPenalties(buffer.getNextUInt8AsInt());
		lapData.setGridPosition(buffer.getNextUInt8AsInt());
		lapData.setDriverStatus(DriverStatus.fromInt(buffer.getNextUInt8AsInt()));
		lapData.setResultStatus(ResultStatus.fromInt(buffer.getNextUInt8AsInt()));

		return lapData;
	}

}

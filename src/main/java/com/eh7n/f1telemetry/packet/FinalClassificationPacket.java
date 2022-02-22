package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.FinalClassificationData;
import com.eh7n.f1telemetry.packet.enums.ResultStatus;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * FINAL CLASSIFICATION PACKET
 * 
 * This packet details the final classification at the end of the race, and the data will 
 * match with the post race results screen. This is especially useful for multiplayer games 
 * where it is not always possible to send lap times on the final frame because of network delay.
 *
 * Frequency: Once at the end of a race
 * 
 * Size: 839 bytes
 * 
 * <pre>
 * {@code 
		struct FinalClassificationData
		{
		    uint8     m_position;              // Finishing position
		    uint8     m_numLaps;               // Number of laps completed
		    uint8     m_gridPosition;          // Grid position of the car
		    uint8     m_points;                // Number of points scored
		    uint8     m_numPitStops;           // Number of pit stops made
		    uint8     m_resultStatus;          // Result status - 0 = invalid, 1 = inactive, 2 = active
		                                       // 3 = finished, 4 = disqualified, 5 = not classified
		                                       // 6 = retired
		    float     m_bestLapTime;           // Best lap time of the session in seconds
		    double    m_totalRaceTime;         // Total race time in seconds without penalties
		    uint8     m_penaltiesTime;         // Total penalties accumulated in seconds
		    uint8     m_numPenalties;          // Number of penalties applied to this driver
		    uint8     m_numTyreStints;         // Number of tyres stints up to maximum
		    uint8     m_tyreStintsActual[8];   // Actual tyres used by this driver
		    uint8     m_tyreStintsVisual[8];   // Visual tyres used by this driver
		};
		
		struct PacketFinalClassificationData
		{
		    PacketHeader    m_header;                             // Header
		
		    uint8                      m_numCars;                 // Number of cars in the final classification
		    FinalClassificationData    m_classificationData[22];
		};
 * }
 * </pre>
 */
public class FinalClassificationPacket extends Packet {
	
	public static final int MAX_NBR_TYRE_STINTS=8;
	
	private int numCars;
	private List<FinalClassificationData> finalClassifications;
	
	public int getNumCars() {
		return numCars;
	}
	public void setNumCars(int numCars) {
		this.numCars = numCars;
	}
	public List<FinalClassificationData> getFinalClassifications() {
		return finalClassifications;
	}
	public void setFinalClassifications(List<FinalClassificationData> finalClassifications) {
		this.finalClassifications = finalClassifications;
	}
	
	@Override
	public Packet build(PacketBuffer buffer) {
		setNumCars(buffer.getNextUInt8AsInt());
		List<FinalClassificationData> finalClassifications = new ArrayList<>();
		for (int k = 0; k < TOTAL_NBR_CARS; k++) {
			finalClassifications.add(buildFinalClassificationData(buffer, k, k == getHeader().getPlayerCarIndex()));
		}
		setFinalClassifications(finalClassifications);
		return this;
	}
	
	private FinalClassificationData buildFinalClassificationData(PacketBuffer buffer, int carIndex, boolean playersCar) {
		FinalClassificationData finalClassificationData = new FinalClassificationData();
		finalClassificationData.setCarIndex(carIndex);
		finalClassificationData.setPlayersCar(playersCar);
		finalClassificationData.setPosition(buffer.getNextUInt8AsInt());
		finalClassificationData.setNumLaps(buffer.getNextUInt8AsInt());
		finalClassificationData.setGridPosition(buffer.getNextUInt8AsInt());
		finalClassificationData.setPoints(buffer.getNextUInt8AsInt());
		finalClassificationData.setNumPitStops(buffer.getNextUInt8AsInt());
		finalClassificationData.setResultStatus(ResultStatus.fromInt(buffer.getNextUInt8AsInt()));
		finalClassificationData.setBestLapTime(buffer.getNextFloat());
		finalClassificationData.setTotalRaceTime(buffer.getNextDouble());
		finalClassificationData.setPenaltiesTime(buffer.getNextUInt8AsInt());
		finalClassificationData.setNumPenalties(buffer.getNextUInt8AsInt());
		finalClassificationData.setNumTyreStints(buffer.getNextUInt8AsInt());
		List<Integer> tyreStintsActual = new ArrayList<>();
		for (int i = 0; i < MAX_NBR_TYRE_STINTS; i++) {
			tyreStintsActual.add(buffer.getNextUInt8AsInt());
		}
		finalClassificationData.setTyreStintsActual(tyreStintsActual);
		List<Integer> tyreStintsVisual = new ArrayList<>();
		for (int i = 0; i < MAX_NBR_TYRE_STINTS; i++) {
			tyreStintsVisual.add(buffer.getNextUInt8AsInt());
		}
		finalClassificationData.setTyreStintsVisual(tyreStintsVisual);
		return finalClassificationData;
	}

}


package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.CarStatusData;
import com.eh7n.f1telemetry.packet.data.WheelData;
import com.eh7n.f1telemetry.packet.enums.ZoneFlag;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * CAR STATUS PACKET
 * 
 * This packet details car statuses for all the cars in the race. It includes
 * values such as the damage readings on the car.
 * 
 * Frequency: Rate as specified in menus
 * 
 * Size: 1344 bytes
 * 
 * <pre>
 * {@code 
	struct CarStatusData
	{
	    uint8       m_tractionControl;          // 0 (off) - 2 (high)
	    uint8       m_antiLockBrakes;           // 0 (off) - 1 (on)
	    uint8       m_fuelMix;                  // Fuel mix - 0 = lean, 1 = standard, 2 = rich, 3 = max
	    uint8       m_frontBrakeBias;           // Front brake bias (percentage)
	    uint8       m_pitLimiterStatus;         // Pit limiter status - 0 = off, 1 = on
	    float       m_fuelInTank;               // Current fuel mass
	    float       m_fuelCapacity;             // Fuel capacity
	    float       m_fuelRemainingLaps;        // Fuel remaining in terms of laps (value on MFD)
	    uint16      m_maxRPM;                   // Cars max RPM, point of rev limiter
	    uint16      m_idleRPM;                  // Cars idle RPM
	    uint8       m_maxGears;                 // Maximum number of gears
	    uint8       m_drsAllowed;               // 0 = not allowed, 1 = allowed, -1 = unknown
	    uint16      m_drsActivationDistance;    // 0 = DRS not available, non-zero - DRS will be available in [X] metres
	    uint8       m_tyresWear[4];             // Tyre wear percentage
	    uint8       m_actualTyreCompound;	    // F1 Modern - 16 = C5, 17 = C4, 18 = C3, 19 = C2, 20 = C1
												// 7 = inter, 8 = wet
												// F1 Classic - 9 = dry, 10 = wet
												// F2 – 11 = super soft, 12 = soft, 13 = medium, 14 = hard
												// 15 = wet

	    uint8       m_visualTyreCompound;       // F1 visual (can be different from actual compound)
                                                // 16 = soft, 17 = medium, 18 = hard, 7 = inter, 8 = wet
                                                // F1 Classic – same as above
                                                // F2 – same as above

        uint8       m_tyresAgeLaps;             // Age in laps of the current set of tyres
	    uint8       m_tyresDamage[4];           // Tyre damage (percentage)
	    uint8       m_frontLeftWingDamage;      // Front left wing damage (percentage)
	    uint8       m_frontRightWingDamage;     // Front right wing damage (percentage)
	    uint8       m_rearWingDamage;           // Rear wing damage (percentage)
	    uint8       m_drsFault;                 // Indicator for DRS fault, 0 = OK, 1 = fault
	    uint8       m_engineDamage;             // Engine damage (percentage)
	    uint8       m_gearBoxDamage;            // Gear box damage (percentage)
	    int8        m_vehicleFiaFlags;          // -1 = invalid/unknown, 0 = none, 1 = green
	                                            // 2 = blue, 3 = yellow, 4 = red
	    float       m_ersStoreEnergy;           // ERS energy store in Joules
	    uint8       m_ersDeployMode;            // ERS deployment mode, 0 = none, 1 = medium
                                                // 2 = overtake, 3 = hotlap
	    float       m_ersHarvestedThisLapMGUK;  // ERS energy harvested this lap by MGU-K
	    float       m_ersHarvestedThisLapMGUH;  // ERS energy harvested this lap by MGU-H
	    float       m_ersDeployedThisLap;       // ERS energy deployed this lap
	};

	struct PacketCarStatusData
	{
	    PacketHeader        m_header;            // Header
	    CarStatusData       m_carStatusData[22];
	};
 * }
 * </pre>
 */
public class CarStatusPacket extends Packet {

	private List<CarStatusData> carStatuses;

	public CarStatusPacket() {}

	public List<CarStatusData> getCarStatuses() {
		return carStatuses;
	}

	public void setCarStatuses(List<CarStatusData> carStatuses) {
		this.carStatuses = carStatuses;
	}

	@Override
	public Packet build(PacketBuffer buffer, int numParticipants) {
		List<CarStatusData> carStatuses = new ArrayList<>();
		for (int k = 0; k < numParticipants; k++) {
			carStatuses.add(buildCarStatusData(buffer, k, k == getHeader().getPlayerCarIndex()));
		}
		setCarStatuses(carStatuses);
		return this;
	}
	
	private CarStatusData buildCarStatusData(PacketBuffer buffer, int carIndex, boolean playersCar) {

		CarStatusData carStatus = new CarStatusData();
		carStatus.setCarIndex(carIndex);
		carStatus.setPlayersCar(playersCar);
		carStatus.setTractionControl(buffer.getNextUInt8AsInt());
		carStatus.setAntiLockBrakesOn(buffer.getNextUInt8AsBoolean());
		carStatus.setFuelMix(buffer.getNextUInt8AsInt());
		carStatus.setFrontBrakeBias(buffer.getNextUInt8AsInt());
		carStatus.setPitLimiterOn(buffer.getNextUInt8AsBoolean());
		carStatus.setFuelInTank(buffer.getNextFloat());
		carStatus.setFuelCapacity(buffer.getNextFloat());
		carStatus.setFuelRemainingLaps(buffer.getNextFloat());
		carStatus.setMaxRpm(buffer.getNextUInt16AsInt());
		carStatus.setIdleRpm(buffer.getNextUInt16AsInt());
		carStatus.setMaxGears(buffer.getNextUInt8AsInt());
		carStatus.setDrsAllowed(buffer.getNextUInt8AsInt());
		carStatus.setDrsActivationDistance(buffer.getNextUInt16AsInt());
		carStatus.setTyresWear(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));
		carStatus.setActualTyreCompound(buffer.getNextUInt8AsInt());
		carStatus.setVisualTyreCompound(buffer.getNextUInt8AsInt());
		carStatus.setTyresAgeLaps(buffer.getNextUInt8AsInt());
		carStatus.setTyresDamage(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));
		carStatus.setFrontLeftWingDamage(buffer.getNextUInt8AsInt());
		carStatus.setFrontRightWingDamage(buffer.getNextUInt8AsInt());
		carStatus.setRearWingDamage(buffer.getNextUInt8AsInt());
		carStatus.setDrsFault(buffer.getNextUInt8AsBoolean());
		carStatus.setEngineDamage(buffer.getNextUInt8AsInt());
		carStatus.setGearBoxDamage(buffer.getNextUInt8AsInt());
		carStatus.setVehicleFiaFlags(ZoneFlag.fromInt(buffer.getNextInt8AsInt()));
		carStatus.setErsStoreEnergy(buffer.getNextFloat());
		carStatus.setErsDeployMode(buffer.getNextUInt8AsInt());
		carStatus.setErsHarvestedThisLapMGUK(buffer.getNextFloat());
		carStatus.setErsHarvestedThisLapMGUH(buffer.getNextFloat());
		carStatus.setErsDeployedThisLap(buffer.getNextFloat());

		return carStatus;
	}

}

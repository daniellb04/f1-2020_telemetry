package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.CarSetupData;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * CAR SETUPS PACKET
 * 
 * This packet details the car setups for each vehicle in the session. Note that
 * in multiplayer games, other player cars will appear as blank, you will only
 * be able to see your car setup and AI cars.
 * 
 * Frequency: Every 2 seconds
 * 
 * Size: 1102 bytes
 * 
 * <pre>
 * {@code 
	struct CarSetupData
	{
		uint8     m_frontWing;                // Front wing aero
		uint8     m_rearWing;                 // Rear wing aero
		uint8     m_onThrottle;               // Differential adjustment on throttle (percentage)
		uint8     m_offThrottle;              // Differential adjustment off throttle (percentage)
		float     m_frontCamber;              // Front camber angle (suspension geometry)
		float     m_rearCamber;               // Rear camber angle (suspension geometry)
		float     m_frontToe;                 // Front toe angle (suspension geometry)
		float     m_rearToe;                  // Rear toe angle (suspension geometry)
		uint8     m_frontSuspension;          // Front suspension
		uint8     m_rearSuspension;           // Rear suspension
		uint8     m_frontAntiRollBar;         // Front anti-roll bar
		uint8     m_rearAntiRollBar;          // Front anti-roll bar
		uint8     m_frontSuspensionHeight;    // Front ride height
		uint8     m_rearSuspensionHeight;     // Rear ride height
		uint8     m_brakePressure;            // Brake pressure (percentage)
		uint8     m_brakeBias;                // Brake bias (percentage)
		float     m_rearLeftTyrePressure;     // Rear left tyre pressure (PSI)
		float     m_rearRightTyrePressure;    // Rear right tyre pressure (PSI)
		float     m_frontLeftTyrePressure;    // Front left tyre pressure (PSI)
		float     m_frontRightTyrePressure;   // Front right tyre pressure (PSI)
		uint8     m_ballast;                  // Ballast
		float     m_fuelLoad;                 // Fuel load
	};

	struct PacketCarSetupData
	{
	    PacketHeader    m_header;            // Header
	    CarSetupData    m_carSetups[22];
	};
 * }
 * </pre>
 */
public class CarSetupPacket extends Packet {

	private List<CarSetupData> carSetups;
	
	public CarSetupPacket() {}
	
	public List<CarSetupData> getCarSetups() {
		return carSetups;
	}
	public void setCarSetups(List<CarSetupData> carSetups) {
		this.carSetups = carSetups;
	}

	@Override
	public Packet build(PacketBuffer buffer) {
		List<CarSetupData> carSetups = new ArrayList<>();
		for (int k = 0; k < TOTAL_NBR_CARS; k++) {
			carSetups.add(buildCarSetupData(buffer, k, k == getHeader().getPlayerCarIndex()));
		}
		setCarSetups(carSetups);
		return this;
	}
	
	private CarSetupData buildCarSetupData(PacketBuffer buffer, int carIndex, boolean playersCar) {
		CarSetupData setupData = new CarSetupData();
		setupData.setCarIndex(carIndex);
		setupData.setPlayersCar(playersCar);
		setupData.setFrontWing(buffer.getNextUInt8AsInt());
		setupData.setRearWing(buffer.getNextUInt8AsInt());
		setupData.setOnThrottle(buffer.getNextUInt8AsInt());
		setupData.setOffThrottle(buffer.getNextUInt8AsInt());
		setupData.setFrontCamber(buffer.getNextFloat());
		setupData.setRearCamber(buffer.getNextFloat());
		setupData.setFrontToe(buffer.getNextFloat());
		setupData.setRearToe(buffer.getNextFloat());
		setupData.setFrontSuspension(buffer.getNextUInt8AsInt());
		setupData.setRearSuspension(buffer.getNextUInt8AsInt());
		setupData.setFrontAntiRollBar(buffer.getNextUInt8AsInt());
		setupData.setRearAntiRollBar(buffer.getNextUInt8AsInt());
		setupData.setFrontSuspensionHeight(buffer.getNextUInt8AsInt());
		setupData.setRearSuspensionHeight(buffer.getNextUInt8AsInt());
		setupData.setBrakePressure(buffer.getNextUInt8AsInt());
		setupData.setBrakeBias(buffer.getNextUInt8AsInt());
		setupData.setRearLeftTyrePressure(buffer.getNextFloat());
		setupData.setRearRightTyrePressure(buffer.getNextFloat());
		setupData.setFrontLeftTyrePressure(buffer.getNextFloat());
		setupData.setFrontRightTyrePressure(buffer.getNextFloat());
		setupData.setBallast(buffer.getNextUInt8AsInt());
		setupData.setFuelLoad(buffer.getNextFloat());
		return setupData;
	}
}

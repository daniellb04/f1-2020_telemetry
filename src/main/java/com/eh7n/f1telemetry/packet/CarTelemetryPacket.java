package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.ButtonStatus;
import com.eh7n.f1telemetry.packet.data.CarTelemetryData;
import com.eh7n.f1telemetry.packet.data.WheelData;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * CAR TELEMETRY PACKET
 * 
 * This packet details telemetry for all the cars in the race. It details
 * various values that would be recorded on the car such as speed, throttle
 * application, DRS etc.
 * 
 * Frequency: Rate as specified in menus
 * 
 * Size: 1307 bytes
 * 
 * <pre>
 * {@code
	struct CarTelemetryData
	{
	    uint16    m_speed;                         // Speed of car in kilometres per hour
	    float     m_throttle;                      // Amount of throttle applied (0.0 to 1.0)
	    float     m_steer;                         // Steering (-1.0 (full lock left) to 1.0 (full lock right))
	    float     m_brake;                         // Amount of brake applied (0.0 to 1.0)
	    uint8     m_clutch;                        // Amount of clutch applied (0 to 100)
	    int8      m_gear;                          // Gear selected (1-8, N=0, R=-1)
	    uint16    m_engineRPM;                     // Engine RPM
	    uint8     m_drs;                           // 0 = off, 1 = on
	    uint8     m_revLightsPercent;              // Rev lights indicator (percentage)
	    uint16    m_brakesTemperature[4];          // Brakes temperature (celsius)
	    uint8     m_tyresSurfaceTemperature[4];    // Tyres surface temperature (celsius)
	    uint8     m_tyresInnerTemperature[4];      // Tyres inner temperature (celsius)
	    uint16    m_engineTemperature;             // Engine temperature (celsius)
	    float     m_tyresPressure[4];              // Tyres pressure (PSI)
	    uint8     m_surfaceType[4];                // Driving surface, see appendices
	};
 
	struct PacketCarTelemetryData
	{
	    PacketHeader        m_header;                // Header
	    CarTelemetryData    m_carTelemetryData[22];
	    uint32              m_buttonStatus;         		// Bit flags specifying which buttons are being
	                                                		// pressed currently - see appendices
	    uint8               m_mfdPanelIndex;       			// Index of MFD panel open - 255 = MFD closed
	                                               			// Single player, race – 0 = Car setup, 1 = Pits
	                                               			// 2 = Damage, 3 =  Engine, 4 = Temperatures
	                                               			// May vary depending on game mode
	    uint8               m_mfdPanelIndexSecondaryPlayer;	// See above
	    int8                m_suggestedGear;       			// Suggested gear for the player (1-8)
	                                               			// 0 if no gear suggested
	};
 * }
 * </pre>
 */
public class CarTelemetryPacket extends Packet {
	
	private List<CarTelemetryData> carTelemetryData;
	private ButtonStatus buttonStatus; // TODO, create a representation of this data properly
	private int mfdPanelIndex;
	private int mfdPanelIndexSecondaryPlayer;
	private int suggestedGear;
	
	public CarTelemetryPacket() {}
	
	public List<CarTelemetryData> getCarTelemetryData() {
		return carTelemetryData;
	}
	
	public void setCarTelemetryData(List<CarTelemetryData> carTelemetryData) {
		this.carTelemetryData = carTelemetryData;
	}

	public ButtonStatus getButtonStatus() {
		return buttonStatus;
	}

	public void setButtonStatus(ButtonStatus buttonStatus) {
		this.buttonStatus = buttonStatus;
	}

	public int getMfdPanelIndex() {
		return mfdPanelIndex;
	}

	public void setMfdPanelIndex(int mfdPanelIndex) {
		this.mfdPanelIndex = mfdPanelIndex;
	}

	public int getMfdPanelIndexSecondaryPlayer() {
		return mfdPanelIndexSecondaryPlayer;
	}

	public void setMfdPanelIndexSecondaryPlayer(int mfdPanelIndexSecondaryPlayer) {
		this.mfdPanelIndexSecondaryPlayer = mfdPanelIndexSecondaryPlayer;
	}

	public int getSuggestedGear() {
		return suggestedGear;
	}

	public void setSuggestedGear(int suggestedGear) {
		this.suggestedGear = suggestedGear;
	}

	@Override
	public Packet build(PacketBuffer buffer) {
		List<CarTelemetryData> carsTelemetry = new ArrayList<>();
		for (int k = 0; k < TOTAL_NBR_CARS; k++) {
			carsTelemetry.add(buildCarTelemetryData(buffer, k, k == getHeader().getPlayerCarIndex()));
		}
		setCarTelemetryData(carsTelemetry);
		setButtonStatus(buildButtonStatus(buffer.getNextUInt32AsLong()));
		setMfdPanelIndex(buffer.getNextUInt8AsInt());
		setMfdPanelIndexSecondaryPlayer(buffer.getNextUInt8AsInt());
		setSuggestedGear(buffer.getNextInt8AsInt());
		return this;
	}
	
	private CarTelemetryData buildCarTelemetryData(PacketBuffer buffer, int carIndex, boolean playersCar) {
		CarTelemetryData carTelemetry = new CarTelemetryData();
		carTelemetry.setCarIndex(carIndex);
		carTelemetry.setPlayersCar(playersCar);
		carTelemetry.setSpeed(buffer.getNextUInt16AsInt());
		carTelemetry.setThrottle(buffer.getNextFloat());
		carTelemetry.setSteer(buffer.getNextFloat());
		carTelemetry.setBrake(buffer.getNextFloat());
		carTelemetry.setClutch(buffer.getNextUInt8AsInt());
		carTelemetry.setGear(buffer.getNextInt8AsInt());
		carTelemetry.setEngineRpm(buffer.getNextUInt16AsInt());
		carTelemetry.setDrsOn(buffer.getNextUInt8AsBoolean());
		carTelemetry.setRevLightsPercent(buffer.getNextUInt8AsInt());
		carTelemetry.setBrakesTemperature(new WheelData<Integer>(buffer.getNextUInt16ArrayAsIntegerArray(4)));
		carTelemetry.setTyresSurfaceTemperature(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));
		carTelemetry.setTyresInnerTemperature(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));
		carTelemetry.setEngineTemperature(buffer.getNextUInt16AsInt());
		carTelemetry.setTyresPressure(new WheelData<Float>(buffer.getNextFloatArray(4)));
		carTelemetry.setSurfaceType(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));

		return carTelemetry;
	}
	
	/**
	 * BUTTON FLAGS
	 * 
	 * These flags are used in the telemetry packet to determine if any buttons are
	 * being held on the controlling device. If the value below logical ANDed with
	 * the button status is set then the corresponding button is being held
	 * 
	 * @return the ButtonStatus pojo
	 */
	private ButtonStatus buildButtonStatus(long flags) {
		ButtonStatus controller = new ButtonStatus();
		controller.setCrossAPressed((flags & 0x0001) == 1);
		controller.setTriangleYPressed((flags & 0x0002) == 1);
		controller.setCircleBPressed((flags & 0x0004) == 1);
		controller.setSquareXPressed((flags & 0x0008) == 1);
		controller.setDpadLeftPressed((flags & 0x0010) == 1);
		controller.setDpadRightPressed((flags & 0x0020) == 1);
		controller.setDpadUpPressed((flags & 0x0040) == 1);
		controller.setDpadDownPressed((flags & 0x0080) == 1);
		controller.setOptionsMenuPressed((flags & 0x0100) == 1);
		controller.setL1LBPressed((flags & 0x0200) == 1);
		controller.setR1RBPressed((flags & 0x0400) == 1);
		controller.setL2LTPressed((flags & 0x0800) == 1);
		controller.setR2RTPressed((flags & 0x1000) == 1);
		controller.setLeftStickPressed((flags & 0x2000) == 1);
		controller.setRightStickPressed((flags & 0x4000) == 1);
		
		return controller;
	}

}

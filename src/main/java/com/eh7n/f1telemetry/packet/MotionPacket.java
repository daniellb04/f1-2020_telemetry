package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.CarMotionData;
import com.eh7n.f1telemetry.packet.data.WheelData;
import com.eh7n.f1telemetry.util.PacketBuffer;

/**
 * MOTION PACKET
 * 
 * The motion packet gives physics data for all the cars being driven. There is
 * additional data for the car being driven with the goal of being able to drive
 * a motion platform setup.
 * 
 * Frequency: Rate as specified in menus
 * 
 * Size: 1464 bytes
 * 
 * <pre>
 * {@code 
	struct CarMotionData
	{
	    float         m_worldPositionX;           // World space X position
	    float         m_worldPositionY;           // World space Y position
	    float         m_worldPositionZ;           // World space Z position
	    float         m_worldVelocityX;           // Velocity in world space X
	    float         m_worldVelocityY;           // Velocity in world space Y
	    float         m_worldVelocityZ;           // Velocity in world space Z
	    int16         m_worldForwardDirX;         // World space forward X direction (normalised)
	    int16         m_worldForwardDirY;         // World space forward Y direction (normalised)
	    int16         m_worldForwardDirZ;         // World space forward Z direction (normalised)
	    int16         m_worldRightDirX;           // World space right X direction (normalised)
	    int16         m_worldRightDirY;           // World space right Y direction (normalised)
	    int16         m_worldRightDirZ;           // World space right Z direction (normalised)
	    float         m_gForceLateral;            // Lateral G-Force component
	    float         m_gForceLongitudinal;       // Longitudinal G-Force component
	    float         m_gForceVertical;           // Vertical G-Force component
	    float         m_yaw;                      // Yaw angle in radians
	    float         m_pitch;                    // Pitch angle in radians
	    float         m_roll;                     // Roll angle in radians
	};

	struct PacketMotionData
	{
	    PacketHeader    m_header;               	// Header
	    CarMotionData   m_carMotionData[22];		// Data for all cars on track
	
	    // Extra player car ONLY data
	    float         m_suspensionPosition[4];       // Note: All wheel arrays have the following order:
	    float         m_suspensionVelocity[4];       // RL, RR, FL, FR
	    float         m_suspensionAcceleration[4];   // RL, RR, FL, FR
	    float         m_wheelSpeed[4];               // Speed of each wheel
	    float         m_wheelSlip[4];                // Slip ratio for each wheel
	    float         m_localVelocityX;              // Velocity in local space
	    float         m_localVelocityY;              // Velocity in local space
	    float         m_localVelocityZ;              // Velocity in local space
	    float         m_angularVelocityX;            // Angular velocity x-component
	    float         m_angularVelocityY;            // Angular velocity y-component
	    float         m_angularVelocityZ;            // Angular velocity z-component
	    float         m_angularAccelerationX;        // Angular acceleration x-component
	    float         m_angularAccelerationY;        // Angular acceleration y-component
	    float         m_angularAccelerationZ;        // Angular acceleration z-component
	    float         m_frontWheelsAngle;            // Current front wheels angle in radians
	};
 * }
 * </pre>
 */
public class MotionPacket extends Packet {
	
	private List<CarMotionData> carMotionDataList;
	private WheelData<Float> suspensionPosition;
	private WheelData<Float> suspensionVelocity;
	private WheelData<Float> suspensionAcceleration;
	private WheelData<Float> wheelSpeed;
	private WheelData<Float> wheelSlip;
	private float localVelocityX;
	private float localVelocityY;
	private float localVelocityZ;
	private float angularVelocityX;
	private float angularVelocityY;
	private float angularVelocityZ;
	private float angularAccelerationX;
	private float angularAccelerationY;
	private float angularAccelerationZ;
	private float frontWheelsAngle;

	public MotionPacket() {}

	public List<CarMotionData> getCarMotionDataList() {
		return carMotionDataList;
	}

	public void setCarMotionDataList(List<CarMotionData> carMotionDataList) {
		this.carMotionDataList = carMotionDataList;
	}

	public WheelData<Float> getSuspensionPosition() {
		return suspensionPosition;
	}

	public void setSuspensionPosition(WheelData<Float> suspensionPosition) {
		this.suspensionPosition = suspensionPosition;
	}

	public WheelData<Float> getSuspensionVelocity() {
		return suspensionVelocity;
	}

	public void setSuspensionVelocity(WheelData<Float> suspensionVelocity) {
		this.suspensionVelocity = suspensionVelocity;
	}

	public WheelData<Float> getSuspensionAcceleration() {
		return suspensionAcceleration;
	}

	public void setSuspensionAcceleration(WheelData<Float> suspensionAcceleration) {
		this.suspensionAcceleration = suspensionAcceleration;
	}

	public WheelData<Float> getWheelSpeed() {
		return wheelSpeed;
	}

	public void setWheelSpeed(WheelData<Float> wheelSpeed) {
		this.wheelSpeed = wheelSpeed;
	}

	public WheelData<Float> getWheelSlip() {
		return wheelSlip;
	}

	public void setWheelSlip(WheelData<Float> wheelSlip) {
		this.wheelSlip = wheelSlip;
	}

	public float getLocalVelocityX() {
		return localVelocityX;
	}

	public void setLocalVelocityX(float localVelocityX) {
		this.localVelocityX = localVelocityX;
	}

	public float getLocalVelocityY() {
		return localVelocityY;
	}

	public void setLocalVelocityY(float localVelocityY) {
		this.localVelocityY = localVelocityY;
	}

	public float getLocalVelocityZ() {
		return localVelocityZ;
	}

	public void setLocalVelocityZ(float localVelocityZ) {
		this.localVelocityZ = localVelocityZ;
	}

	public float getAngularVelocityX() {
		return angularVelocityX;
	}

	public void setAngularVelocityX(float angularVelocityX) {
		this.angularVelocityX = angularVelocityX;
	}

	public float getAngularVelocityY() {
		return angularVelocityY;
	}

	public void setAngularVelocityY(float angularVelocityY) {
		this.angularVelocityY = angularVelocityY;
	}

	public float getAngularVelocityZ() {
		return angularVelocityZ;
	}

	public void setAngularVelocityZ(float angularVelocityZ) {
		this.angularVelocityZ = angularVelocityZ;
	}

	public float getAngularAccelerationX() {
		return angularAccelerationX;
	}

	public void setAngularAccelerationX(float angularAccelerationX) {
		this.angularAccelerationX = angularAccelerationX;
	}

	public float getAngularAccelerationY() {
		return angularAccelerationY;
	}

	public void setAngularAccelerationY(float angularAccelerationY) {
		this.angularAccelerationY = angularAccelerationY;
	}

	public float getAngularAccelerationZ() {
		return angularAccelerationZ;
	}

	public void setAngularAccelerationZ(float angularAccelerationZ) {
		this.angularAccelerationZ = angularAccelerationZ;
	}

	public float getFrontWheelsAngle() {
		return frontWheelsAngle;
	}

	public void setFrontWheelsAngle(float frontWheelsAngle) {
		this.frontWheelsAngle = frontWheelsAngle;
	}

	@Override
	public Packet build(PacketBuffer buffer) {
		List<CarMotionData> carMotionDataList = new ArrayList<>();
		for (int carIndex = 0; carIndex < TOTAL_NBR_CARS; carIndex++) {
			carMotionDataList.add(buildCarMotionData(buffer, carIndex, carIndex == getHeader().getPlayerCarIndex()));
		}
		setCarMotionDataList(carMotionDataList);
		setSuspensionPosition(new WheelData<Float>(buffer.getNextFloatArray(4)));
		setSuspensionVelocity(new WheelData<Float>(buffer.getNextFloatArray(4)));
		setSuspensionAcceleration(new WheelData<Float>(buffer.getNextFloatArray(4)));
		setWheelSpeed(new WheelData<Float>(buffer.getNextFloatArray(4)));
		setWheelSlip(new WheelData<Float>(buffer.getNextFloatArray(4)));
		setLocalVelocityX(buffer.getNextFloat());
		setLocalVelocityY(buffer.getNextFloat());
		setLocalVelocityZ(buffer.getNextFloat());
		setAngularVelocityX(buffer.getNextFloat());
		setAngularVelocityY(buffer.getNextFloat());
		setAngularVelocityZ(buffer.getNextFloat());
		setAngularAccelerationX(buffer.getNextFloat());
		setAngularAccelerationY(buffer.getNextFloat());
		setAngularAccelerationZ(buffer.getNextFloat());
		setFrontWheelsAngle(buffer.getNextFloat());

		return this;
	}
	
	private CarMotionData buildCarMotionData(PacketBuffer buffer, int carIndex, boolean playersCar) {

		final float denormalizer = 32767.0f;

		CarMotionData carMotionData = new CarMotionData();

		carMotionData.setCarIndex(carIndex);
		carMotionData.setPlayersCar(playersCar);
		carMotionData.setWorldPositionX(buffer.getNextFloat());
		carMotionData.setWorldPositionY(buffer.getNextFloat());
		carMotionData.setWorldPositionZ(buffer.getNextFloat());
		carMotionData.setWorldVelocityX(buffer.getNextFloat());
		carMotionData.setWorldVelocityY(buffer.getNextFloat());
		carMotionData.setWorldVelocityZ(buffer.getNextFloat());
		carMotionData.setWorldForwardDirX(buffer.getNextUInt16AsInt() / denormalizer);
		carMotionData.setWorldForwardDirY(buffer.getNextUInt16AsInt() / denormalizer);
		carMotionData.setWorldForwardDirZ(buffer.getNextUInt16AsInt() / denormalizer);
		carMotionData.setWorldRightDirX(buffer.getNextUInt16AsInt() / denormalizer);
		carMotionData.setWorldRightDirY(buffer.getNextUInt16AsInt() / denormalizer);
		carMotionData.setWorldRightDirZ(buffer.getNextUInt16AsInt() / denormalizer);
		carMotionData.setgForceLateral(buffer.getNextFloat());
		carMotionData.setgForceLongitudinal(buffer.getNextFloat());
		carMotionData.setgForceVertical(buffer.getNextFloat());
		carMotionData.setYaw(buffer.getNextFloat());
		carMotionData.setPitch(buffer.getNextFloat());
		carMotionData.setRoll(buffer.getNextFloat());

		return carMotionData;

	}
}

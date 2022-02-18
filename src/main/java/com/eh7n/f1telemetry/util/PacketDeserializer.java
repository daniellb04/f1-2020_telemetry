package com.eh7n.f1telemetry.util;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.data.Packet;
import com.eh7n.f1telemetry.data.PacketCarSetupData;
import com.eh7n.f1telemetry.data.PacketCarStatusData;
import com.eh7n.f1telemetry.data.PacketCarTelemetryData;
import com.eh7n.f1telemetry.data.PacketEventData;
import com.eh7n.f1telemetry.data.PacketLapData;
import com.eh7n.f1telemetry.data.PacketMotionData;
import com.eh7n.f1telemetry.data.PacketParticipantsData;
import com.eh7n.f1telemetry.data.PacketSessionData;
import com.eh7n.f1telemetry.data.PacketType;
import com.eh7n.f1telemetry.data.elements.ButtonStatus;
import com.eh7n.f1telemetry.data.elements.CarMotionData;
import com.eh7n.f1telemetry.data.elements.CarSetupData;
import com.eh7n.f1telemetry.data.elements.CarStatusData;
import com.eh7n.f1telemetry.data.elements.CarTelemetryData;
import com.eh7n.f1telemetry.data.elements.DriverStatus;
import com.eh7n.f1telemetry.data.elements.EventDataDetails;
import com.eh7n.f1telemetry.data.elements.EventType;
import com.eh7n.f1telemetry.data.elements.FastestLapEventDetails;
import com.eh7n.f1telemetry.data.elements.Formula;
import com.eh7n.f1telemetry.data.elements.Header;
import com.eh7n.f1telemetry.data.elements.InfringementType;
import com.eh7n.f1telemetry.data.elements.LapData;
import com.eh7n.f1telemetry.data.elements.MarshalZone;
import com.eh7n.f1telemetry.data.elements.ParticipantData;
import com.eh7n.f1telemetry.data.elements.PenaltyEventDetails;
import com.eh7n.f1telemetry.data.elements.PenaltyType;
import com.eh7n.f1telemetry.data.elements.PitStatus;
import com.eh7n.f1telemetry.data.elements.ResultStatus;
import com.eh7n.f1telemetry.data.elements.SafetyCarStatus;
import com.eh7n.f1telemetry.data.elements.SessionType;
import com.eh7n.f1telemetry.data.elements.SpeedTrapEventDetails;
import com.eh7n.f1telemetry.data.elements.Weather;
import com.eh7n.f1telemetry.data.elements.WeatherForecastSample;
import com.eh7n.f1telemetry.data.elements.WheelData;
import com.eh7n.f1telemetry.data.elements.ZoneFlag;

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
	
	public static final int MAX_NBR_CARS=22;
	public static final int MAX_NBR_MARSHAL_ZONES=21;
	public static final int MAX_WEATHER_FORECAST_SAMPLES=20;

	private PacketBuffer buffer;
	private int numParticipants = 0;

	private PacketDeserializer(byte[] data) {
		buffer = PacketBuffer.wrap(data);
		System.out.println(buffer);
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
				return buildPacketMotionData(header);
			case SESSION:
				return buildPacketSessionData(header);
			case LAP_DATA:
				return buildPacketLapData(header);
			case EVENT:
				return buildPacketEventData(header);
			case PARTICIPANTS:
				return buildPacketParticipantsData(header);
			case CAR_SETUP:
				return buildPacketCarSetupData(header);
			case CAR_TELEMETRY:
				return buildPacketCarTelemetryData(header);
			case CAR_STATUS:
				return buildPacketCarStatusData(header);
				/*
			case FINAL_CLASSIFICATION:
				return null; //buildPacketFinalClassificationData(header);
			case LOBBY_INFO:
				return null; //buildPacketLobbyInfoData(header);
				*/
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
			uint16	m_packetFormat;		// 2020
			uint8	m_gameMajorVersion;	// Game major version - "X.00"
			uint8	m_gameMinorVersion;	// Game minor version - "1.XX"
			uint8	m_packetVersion;	// Version of this packet type, all start from 1
			uint8	m_packetId;			// Identifier for the packet type, see below
			uint64	m_sessionUID;		// Unique identifier for the session
			float	m_sessionTime;		// Session timestamp
			uint32	m_frameIdentifier;	// Identifier for the frame the data was retrieved on
			uint8	m_playerCarIndex;	// Index of player's car in the array
		};
	 * }
	 * </pre>
	 * 
	 * @return the Header pojo
	 */
	private Header buildHeader() {

		Header header = new Header();

		header.setPacketFormat(buffer.getNextUInt16AsInt()); // 2
		header.setGameMajorVersion(buffer.getNextUInt8AsInt());
		header.setGameMinorVersion(buffer.getNextUInt8AsInt());
		header.setPacketVersion(buffer.getNextUInt8AsInt()); // 1
		header.setPacketId(buffer.getNextUInt8AsInt()); // 1
		header.setSessionUID(buffer.getNextUInt64AsBigInteger()); // 8
		header.setSessionTime(buffer.getNextFloat());// 4
		header.setFrameIdentifier(buffer.getNextUInt32AsLong());// 4
		header.setPlayerCarIndex(buffer.getNextUInt8AsInt()); // 1
		return header;
	}

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
		struct PacketLapData
		{
			PacketHeader    m_header;              // Header
			LapData         m_lapData[22];         // Lap data for all cars on track
		};
	 * }
	 * </pre>
	 * 
	 * @return the PacketLapData pojo
	 */
	private PacketLapData buildPacketLapData(Header header) {

		PacketLapData packetData = new PacketLapData();
		List<LapData> lapDataList = new ArrayList<>();

		int i = 0;
		int playersIndex = header.getPlayerCarIndex();
		while (i < this.numParticipants) {
			lapDataList.add(buildLapData(i, i == playersIndex));
			i++;
		}

		packetData.setHeader(header);
		packetData.setLapDataList(lapDataList);
		return packetData;
	}

	/**
	 * LAP DATA
	 * 
	 * <pre>
	 * {@code 
		struct LapData
		{
		    float       m_lastLapTime;           // Last lap time in seconds
		    float       m_currentLapTime;        // Current time around the lap in seconds
		    float       m_bestLapTime;           // Best lap time of the session in seconds
		    float       m_sector1Time;           // Sector 1 time in seconds
		    float       m_sector2Time;           // Sector 2 time in seconds
		    float       m_lapDistance;           // Distance vehicle is around current lap in metres – could
		                                         // be negative if line hasn’t been crossed yet
		    float       m_totalDistance;         // Total distance travelled in session in metres – could
		                                         // be negative if line hasn’t been crossed yet
		    float       m_safetyCarDelta;        // Delta in seconds for safety car
		    uint8       m_carPosition;           // Car race position
		    uint8       m_currentLapNum;         // Current lap number
		    uint8       m_pitStatus;             // 0 = none, 1 = pitting, 2 = in pit area
		    uint8       m_sector;                // 0 = sector1, 1 = sector2, 2 = sector3
		    uint8       m_currentLapInvalid;     // Current lap invalid - 0 = valid, 1 = invalid
		    uint8       m_penalties;             // Accumulated time penalties in seconds to be added
		    uint8       m_gridPosition;          // Grid position the vehicle started the race in
		    uint8       m_driverStatus;          // Status of driver - 0 = in garage, 1 = flying lap
		                                         // 2 = in lap, 3 = out lap, 4 = on track
		    uint8       m_resultStatus;          // Result status - 0 = invalid, 1 = inactive, 2 = active
		                                         // 3 = finished, 4 = disqualified, 5 = not classified
		                                         // 6 = retired
		};
	 * }
	 * </pre>
	 */
	private LapData buildLapData(int carIndex, boolean playersCar) {

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
		lapData.setCurrentLapInvalid(buffer.getNextUInt8AsInt() == 1);
		lapData.setPenalties(buffer.getNextUInt8AsInt());
		lapData.setGridPosition(buffer.getNextUInt8AsInt());
		lapData.setDriverStatus(DriverStatus.fromInt(buffer.getNextUInt8AsInt()));
		lapData.setResultStatus(ResultStatus.fromInt(buffer.getNextUInt8AsInt()));

		return lapData;
	}

	/**
	 * MOTION PACKET
	 * 
	 * The motion packet gives physics data for all the cars being driven. There is
	 * additional data for the car being driven with the goal of being able to drive
	 * a motion platform setup.
	 * 
	 * Frequency: Rate as specified in menus
	 * 
	 * Size: 1341 bytes
	 * 
	 * <pre>
	 * {@code 
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
		    float         m_angularAccelerationX;        // Angular velocity x-component
		    float         m_angularAccelerationY;        // Angular velocity y-component
		    float         m_angularAccelerationZ;        // Angular velocity z-component
		    float         m_frontWheelsAngle;            // Current front wheels angle in radians
		};
	 * }
	 * </pre>
	 * 
	 * @return the PacketMotionData pojo
	 */
	private PacketMotionData buildPacketMotionData(Header header) {

		PacketMotionData packetMotionData = new PacketMotionData();

		packetMotionData.setHeader(header);
		List<CarMotionData> carMotionDataList = new ArrayList<>();
		int carIndex = 0;
		int playersCarIndex = header.getPlayerCarIndex();
		while (carIndex < this.numParticipants) {
			carMotionDataList.add(buildCarMotionData(carIndex, carIndex == playersCarIndex));
			carIndex++;
		}
		packetMotionData.setCarMotionDataList(carMotionDataList);
		packetMotionData.setSuspensionPosition(new WheelData<Float>(buffer.getNextFloatArray(4)));
		packetMotionData.setSuspensionVelocity(new WheelData<Float>(buffer.getNextFloatArray(4)));
		packetMotionData.setSuspensionAcceleration(new WheelData<Float>(buffer.getNextFloatArray(4)));
		packetMotionData.setWheelSpeed(new WheelData<Float>(buffer.getNextFloatArray(4)));
		packetMotionData.setWheelSlip(new WheelData<Float>(buffer.getNextFloatArray(4)));
		packetMotionData.setLocalVelocityX(buffer.getNextFloat());
		packetMotionData.setLocalVelocityY(buffer.getNextFloat());
		packetMotionData.setLocalVelocityZ(buffer.getNextFloat());
		packetMotionData.setAngularVelocityX(buffer.getNextFloat());
		packetMotionData.setAngularVelocityY(buffer.getNextFloat());
		packetMotionData.setAngularVelocityZ(buffer.getNextFloat());
		packetMotionData.setAngularAccelerationX(buffer.getNextFloat());
		packetMotionData.setAngularAccelerationY(buffer.getNextFloat());
		packetMotionData.setAngularAccelerationZ(buffer.getNextFloat());
		packetMotionData.setFrontWheelsAngle(buffer.getNextFloat());

		return packetMotionData;
	}

	/**
	 * CAR MOTION DATA
	 * 
	 * N.B. For the normalised vectors below, to convert to float values divide by
	 * 32767.0f. 16-bit signed values are used to pack the data and on the
	 * assumption that direction values are always between -1.0f and 1.0f.
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
	 * }
	 * </pre>
	 */
	private CarMotionData buildCarMotionData(int carIndex, boolean playersCar) {

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

	/**
	 * SESSION PACKET
	 * 
	 * The session packet includes details about the current session in progress.
	 * 
	 * Frequency: 2 per second
	 * 
	 * Size: 251 bytes
	 * 
	 * <pre>
	 * {@code 
		struct PacketSessionData
		{
		    PacketHeader    m_header;               // Header
		
		    uint8           m_weather;              // Weather - 0 = clear, 1 = light cloud, 2 = overcast
		                                            // 3 = light rain, 4 = heavy rain, 5 = storm
		    int8	    	m_trackTemperature;    	// Track temp. in degrees celsius
		    int8	    	m_airTemperature;      	// Air temp. in degrees celsius
		    uint8           m_totalLaps;           	// Total number of laps in this race
		    uint16          m_trackLength;          // Track length in metres
		    uint8           m_sessionType;         	// 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P
		                                            // 5 = Q1, 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ
		                                            // 10 = R, 11 = R2, 12 = Time Trial
		    int8            m_trackId;         		// -1 for unknown, 0-21 for tracks, see appendix
		    uint8           m_era;                  // Era, 0 = modern, 1 = classic
		    uint16          m_sessionTimeLeft;    	// Time left in session in seconds
		    uint16          m_sessionDuration;     	// Session duration in seconds
		    uint8           m_pitSpeedLimit;      	// Pit speed limit in kilometres per hour
		    uint8           m_gamePaused;           // Whether the game is paused
		    uint8           m_isSpectating;        	// Whether the player is spectating
		    uint8           m_spectatorCarIndex;  	// Index of the car being spectated
		    uint8           m_sliProNativeSupport;	// SLI Pro support, 0 = inactive, 1 = active
		    uint8           m_numMarshalZones;      // Number of marshal zones to follow
		    MarshalZone     m_marshalZones[21];     // List of marshal zones – max 21
		    uint8           m_safetyCarStatus;      // 0 = no safety car, 1 = full safety car
		                                            // 2 = virtual safety car
		    uint8          m_networkGame;           // 0 = offline, 1 = online
		};
	 * }
	 * </pre>
	 */
	private PacketSessionData buildPacketSessionData(Header header) {

		PacketSessionData sessionData = new PacketSessionData();

		sessionData.setHeader(header);
		sessionData.setWeather(Weather.fromInt(buffer.getNextUInt8AsInt()));
		sessionData.setTrackTemperature(buffer.getNextInt8AsInt());
		sessionData.setAirTemperature(buffer.getNextInt8AsInt());
		sessionData.setTotalLaps(buffer.getNextUInt8AsInt());
		sessionData.setTrackLength(buffer.getNextUInt16AsInt());
		sessionData.setSessionType(SessionType.fromInt(buffer.getNextUInt8AsInt()));
		sessionData.setTrackId(buffer.getNextInt8AsInt());
		sessionData.setFormula(Formula.fromInt(buffer.getNextUInt8AsInt()));
		sessionData.setSessionTimeLeft(buffer.getNextUInt16AsInt());
		sessionData.setSessionDuration(buffer.getNextUInt16AsInt());
		sessionData.setPitSpeedLimit(buffer.getNextUInt8AsInt());
		sessionData.setGamePaused(buffer.getNextUInt8AsBoolean());
		sessionData.setSpectating(buffer.getNextUInt8AsBoolean());
		sessionData.setSliProNativeSupport(buffer.getNextUInt8AsBoolean());
		sessionData.setNumMarshalZones(buffer.getNextUInt8AsInt());
		sessionData.setMarshalZones(buildMarshalZones(sessionData.getNumMarshalZones()));
		sessionData.setSafetyCarStatus(SafetyCarStatus.fromInt(buffer.getNextUInt8AsInt()));
		sessionData.setNetworkGame(buffer.getNextUInt8AsBoolean());
		sessionData.setNumWeatherForecastSamples(buffer.getNextUInt8AsInt());
		sessionData.setWeatherForecastSamples(buildWeatherForecastSamples(sessionData.getNumWeatherForecastSamples()));
		return sessionData;
	}

	/**
	 * MARSHAL ZONE
	 * 
	 * <pre>
	 * {@code 
		struct MarshalZone
		{
		    float  m_zoneStart;   // Fraction (0..1) of way through the lap the marshal zone starts
		    int8   m_zoneFlag;    // -1 = invalid/unknown, 0 = none, 1 = green, 2 = blue, 3 = yellow, 4 = red
		};
	 * }
	 * </pre>
	 */
	private List<MarshalZone> buildMarshalZones(int numMarshalZones) {
		List<MarshalZone> marshalZones = new ArrayList<>();
		int num = (numMarshalZones < MAX_NBR_MARSHAL_ZONES)?
				numMarshalZones : MAX_NBR_MARSHAL_ZONES;
		for (int k = 0; k < num; k++) {
			MarshalZone marshalZone = new MarshalZone();
			marshalZone.setZoneStart(buffer.getNextFloat());
			marshalZone.setZoneFlag(ZoneFlag.fromInt(buffer.getNextInt8AsInt()));
			marshalZones.add(marshalZone);
		}
		return marshalZones;
	}

	/**
	 * WEATHER FORECAST SAMPLES
	 * 
	 * <pre>
	 * {@code 
		struct WeatherForecastSample
		{
			uint8	m_sessionType;		// 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P, 5 = Q1
			          					// 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ, 10 = R, 11 = R2
										// 12 = Time Trial
			uint8	m_timeOffset;		// Time in minutes the forecast is for
			uint8	m_weather;			// Weather - 0 = clear, 1 = light cloud, 2 = overcast
										//           3 = light rain, 4 = heavy rain, 5 = storm
			int8	m_trackTemperature;	// Track temp. in degrees celsius
			int8	m_airTemperature;	// Air temp. in degrees celsius
		};
	 * }
	 * </pre>
	 */
	private List<WeatherForecastSample> buildWeatherForecastSamples(int numWeatherForecastSamples) {
		List<WeatherForecastSample> weatherForecastSamples = new ArrayList<>();
		int num = (numWeatherForecastSamples < MAX_WEATHER_FORECAST_SAMPLES)?
				numWeatherForecastSamples : MAX_WEATHER_FORECAST_SAMPLES;
		for (int k = 0; k < num; k++) {
			WeatherForecastSample weatherForecastSample = new WeatherForecastSample();
			weatherForecastSample.setSessionType(SessionType.fromInt(buffer.getNextUInt8AsInt()));
			weatherForecastSample.setTimeOffset(buffer.getNextUInt8AsInt());
			weatherForecastSample.setWeather(Weather.fromInt(buffer.getNextUInt8AsInt()));
			weatherForecastSample.setTrackTemperature(buffer.getNextInt8AsInt());
			weatherForecastSample.setAirTemperature(buffer.getNextInt8AsInt());
			weatherForecastSamples.add(weatherForecastSample);
		}
		return weatherForecastSamples;
	}
	
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
		struct PacketEventData
		{
		    PacketHeader   		m_header;               // Header
		    
		    uint8           	m_eventStringCode[4];   // Event string code, see above
		    EventDataDetails 	m_eventDetails; // Event details - should be interpreted differently
		    									// for each type
		};
	 * }
	 * </pre>
	 * 
	 * @param header
	 * @return the EventData packet
	 */
	private PacketEventData buildPacketEventData(Header header) {
		PacketEventData eventData = new PacketEventData();
		eventData.setHeader(header);
		buffer.skip(1);
		eventData.setEventType(EventType.fromCode(buffer.getNextCharArrayAsString(4)));
		eventData.setEventDetails(buildEventDataDetails(eventData.getEventType()));
		return eventData;
	}
	
	private EventDataDetails buildEventDataDetails(EventType eventType) {
		if (eventType == null ||
			EventType.SESSION_STARTED.equals(eventType) ||
			EventType.SESSION_ENDED.equals(eventType) ||
			EventType.DRS_ENABLED.equals(eventType) ||
			EventType.DRS_DISABLED.equals(eventType) ||
			EventType.CHEQUERED_FLAG.equals(eventType))
			return null; // Events without details
		
		EventDataDetails data;
		if (EventType.PENALTY_ISSUED.equals(eventType)) {
			data = buildPenaltyEventDetails();
		}
		else {
			int vehicleIdx = buffer.getNextUInt8AsInt();
			switch(eventType) {
				case FASTEST_LAP:
					data = buildFastestLapEventDetails();
					break;
				case SPEED_TRAP_TRIGGERED:
					data = buildSpeedTrapEventDetails();
					break;
				default:
					data = new EventDataDetails();
			}
			data.setVehicleIdx(vehicleIdx);
		}
		return data;
	}
	
	private FastestLapEventDetails buildFastestLapEventDetails() {
		FastestLapEventDetails data = new FastestLapEventDetails();
		data.setLapTime(buffer.getNextFloat());
		return data;
	}
	
	private PenaltyEventDetails buildPenaltyEventDetails() {
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
	
	private SpeedTrapEventDetails buildSpeedTrapEventDetails() {
		SpeedTrapEventDetails data = new SpeedTrapEventDetails();
		data.setSpeed(buffer.getNextFloat());
		return data;
	}

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
		struct PacketParticipantsData
		{
		    PacketHeader    m_header;            // Header
		
		    uint8           m_numCars;           // Number of cars in the data
		    ParticipantData m_participants[22];
		};	 
	 * }
	 * </pre>
	 * 
	 * @param header
	 * @return a PacketParticipantsData pojo
	 * 
	 */
	private PacketParticipantsData buildPacketParticipantsData(Header header) {

		PacketParticipantsData participantsData = new PacketParticipantsData();
		participantsData.setHeader(header);
		this.numParticipants = buffer.getNextUInt8AsInt();
		participantsData.setNumActiveCars(this.numParticipants);
		List<ParticipantData> participants = new ArrayList<>();
		for (int k = 0; k < this.numParticipants; k++) {
			participants.add(buildParticipantData(k, k == header.getPlayerCarIndex()));
		}
		participantsData.setParticipants(participants);
		// Ignore the rest of the data in the buffer
		return participantsData;

	}

	/**
	 * PARTICIPANT DATA
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
	 * }; }
	 * 
	 * @return a ParticipantData pojo
	 */
	private ParticipantData buildParticipantData(int carIndex, boolean playersCar) {
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
		struct PacketCarSetupData
		{
		    PacketHeader    m_header;            // Header
		
		    CarSetupData    m_carSetups[22];
		};
	 * }
	 * </pre>
	 * 
	 * @param header
	 * @return
	 */
	private PacketCarSetupData buildPacketCarSetupData(Header header) {
		PacketCarSetupData carSetupData = new PacketCarSetupData();
		carSetupData.setHeader(header);
		List<CarSetupData> carSetups = new ArrayList<>();
		for (int k = 0; k < this.numParticipants; k++) {
			carSetups.add(buildCarSetupData(k, k == header.getPlayerCarIndex()));
		}
		carSetupData.setCarSetups(carSetups);
		return carSetupData;
	}

	/**
	 * CAR SETUP DATA
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
	 * }
	 * </pre>
	 * 
	 * @return a CarSetupData pojo
	 */
	private CarSetupData buildCarSetupData(int carIndex, boolean playersCar) {
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
		setupData.setRearLeftTirePressure(buffer.getNextFloat());
		setupData.setRearRightTirePressure(buffer.getNextFloat());
		setupData.setFrontLeftTirePressure(buffer.getNextFloat());
		setupData.setFrontRightTirePressure(buffer.getNextFloat());
		setupData.setBallast(buffer.getNextUInt8AsInt());
		setupData.setFuelLoad(buffer.getNextFloat());
		return setupData;
	}

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
		struct PacketCarTelemetryData
		{
		    PacketHeader        m_header;                // Header
		
		    CarTelemetryData    m_carTelemetryData[22];
		
		    uint32              m_buttonStatus;         // Bit flags specifying which buttons are being
		                                                // pressed currently - see appendices
		};
	 * }
	 * </pre>
	 * 
	 * @param header
	 * @return a PacketCarTelemetryData pojo
	 */
	private PacketCarTelemetryData buildPacketCarTelemetryData(Header header) {
		PacketCarTelemetryData packetCarTelemetry = new PacketCarTelemetryData();
		packetCarTelemetry.setHeader(header);
		List<CarTelemetryData> carsTelemetry = new ArrayList<>();
		for (int k = 0; k < this.numParticipants; k++) {
			carsTelemetry.add(buildCarTelemetryData(k, k == header.getPlayerCarIndex()));
		}
		packetCarTelemetry.setCarTelemetryData(carsTelemetry);
		packetCarTelemetry.setButtonStatus(buildButtonStatus());
		packetCarTelemetry.setMfdPanelIndex(buffer.getNextUInt8AsInt());
		packetCarTelemetry.setMfdPanelIndexSecondaryPlayer(buffer.getNextUInt8AsInt());
		packetCarTelemetry.setSuggestedGear(buffer.getNextInt8AsInt());
		return packetCarTelemetry;
	}

	/**
	 * CAR TELEMETRY DATA
	 * 
	 * <pre>
	 * {@code 
		struct CarTelemetryData
		{
		    uint16    m_speed;                      // Speed of car in kilometres per hour
		    uint8     m_throttle;                   // Amount of throttle applied (0 to 100)
		    int8      m_steer;                      // Steering (-100 (full lock left) to 100 (full lock right))
		    uint8     m_brake;                      // Amount of brake applied (0 to 100)
		    uint8     m_clutch;                     // Amount of clutch applied (0 to 100)
		    int8      m_gear;                       // Gear selected (1-8, N=0, R=-1)
		    uint16    m_engineRPM;                  // Engine RPM
		    uint8     m_drs;                        // 0 = off, 1 = on
		    uint8     m_revLightsPercent;           // Rev lights indicator (percentage)
		    uint16    m_brakesTemperature[4];       // Brakes temperature (celsius)
		    uint16    m_tyresSurfaceTemperature[4]; // Tyres surface temperature (celsius)
		    uint16    m_tyresInnerTemperature[4];   // Tyres inner temperature (celsius)
		    uint16    m_engineTemperature;          // Engine temperature (celsius)
		    float     m_tyresPressure[4];           // Tyres pressure (PSI)
		    uint8     m_surfaceType[4];             // Driving surface
		};
	 * }
	 * </pre>
	 * 
	 * @return a CarTelemetryData pojo
	 */
	private CarTelemetryData buildCarTelemetryData(int carIndex, boolean playersCar) {
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
		carTelemetry.setTiresSurfaceTemperature(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));
		carTelemetry.setTiresInnerTemperature(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));
		carTelemetry.setEngineTemperature(buffer.getNextUInt16AsInt());
		carTelemetry.setTiresPressure(new WheelData<Float>(buffer.getNextFloatArray(4)));
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
	private ButtonStatus buildButtonStatus() {
		
		long flags = buffer.getNextUInt32AsLong();
		
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

	/**
	 * CAR STATUS PACKET
	 * 
	 * This packet details car statuses for all the cars in the race. It includes
	 * values such as the damage readings on the car.
	 * 
	 * Frequency: 2 per second
	 * 
	 * Size: 1344 bytes
	 * 
	 * <pre>
	 * {@code 
		struct PacketCarStatusData
		{
		    PacketHeader        m_header;            // Header
		
		    CarStatusData       m_carStatusData[22];
		};
	 * }
	 * </pre>
	 * 
	 * @param header
	 * @return a PacketCarStatusData packet
	 */
	private PacketCarStatusData buildPacketCarStatusData(Header header) {

		PacketCarStatusData packetCarStatus = new PacketCarStatusData();

		packetCarStatus.setHeader(header);
		List<CarStatusData> carStatuses = new ArrayList<>();
		for (int k = 0; k < this.numParticipants; k++) {
			carStatuses.add(buildCarStatusData(k, k == header.getPlayerCarIndex()));
		}
		packetCarStatus.setCarStatuses(carStatuses);

		return packetCarStatus;
	}

	/**
	 * CAR STATUS DATA
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
	 * }
	 * </pre>
	 * 
	 * @return a CarStatusData pojo
	 */
	private CarStatusData buildCarStatusData(int carIndex, boolean playersCar) {

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
		carStatus.setTiresWear(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));
		carStatus.setActualTyreCompound(buffer.getNextUInt8AsInt());
		carStatus.setVisualTyreCompound(buffer.getNextUInt8AsInt());
		carStatus.setTyresAgeLaps(buffer.getNextUInt8AsInt());
		carStatus.setTiresDamage(new WheelData<Integer>(buffer.getNextUInt8ArrayAsIntegerArray(4)));
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



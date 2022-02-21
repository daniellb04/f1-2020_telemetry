package com.eh7n.f1telemetry.packet;

import java.util.ArrayList;
import java.util.List;

import com.eh7n.f1telemetry.packet.data.MarshalZone;
import com.eh7n.f1telemetry.packet.data.WeatherForecastSample;
import com.eh7n.f1telemetry.packet.enums.FormulaType;
import com.eh7n.f1telemetry.packet.enums.SafetyCarStatus;
import com.eh7n.f1telemetry.packet.enums.SessionType;
import com.eh7n.f1telemetry.packet.enums.Weather;
import com.eh7n.f1telemetry.packet.enums.ZoneFlag;
import com.eh7n.f1telemetry.util.PacketBuffer;

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

	struct MarshalZone
	{
	    float  m_zoneStart;   // Fraction (0..1) of way through the lap the marshal zone starts
	    int8   m_zoneFlag;    // -1 = invalid/unknown, 0 = none, 1 = green, 2 = blue, 3 = yellow, 4 = red
	};
	
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
	    uint8           m_formula;              // Formula, 0 = F1 Modern, 1 = F1 Classic, 2 = F2;
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
	                                            // 2 = virtual safety car, 3 = formation lap
	    uint8           m_networkGame;           // 0 = offline, 1 = online
	    uint8           m_numWeatherForecastSamples; // Number of weather samples to follow
        WeatherForecastSample m_weatherForecastSamples[20];   // Array of weather forecast samples
	};
 * }
 * </pre>
 */
public class SessionPacket extends Packet {
	
	public static final int MAX_NBR_MARSHAL_ZONES=21;
	public static final int MAX_WEATHER_FORECAST_SAMPLES=20;

	private Weather weather;
	private int trackTemperature;
	private int airTemperature;
	private int totalLaps;
	private int trackLength;
	private SessionType sessionType;
	private int trackId;
	private FormulaType formula;
	private int sessionTimeLeft;
	private int sessionDuration;
	private int pitSpeedLimit;
	private boolean gamePaused;
	private boolean spectating;
	private int spectatorCarIndex;
	private boolean sliProNativeSupport;
	private int numMarshalZones;
	private List<MarshalZone> marshalZones;
	private SafetyCarStatus safetyCarStatus;
	private boolean networkGame;
	private int numWeatherForecastSamples;
	private List<WeatherForecastSample> weatherForecastSamples;
	
	public SessionPacket() {}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public int getTrackTemperature() {
		return trackTemperature;
	}

	public void setTrackTemperature(int trackTemperature) {
		this.trackTemperature = trackTemperature;
	}

	public int getAirTemperature() {
		return airTemperature;
	}

	public void setAirTemperature(int airTemperature) {
		this.airTemperature = airTemperature;
	}

	public int getTotalLaps() {
		return totalLaps;
	}

	public void setTotalLaps(int totalLaps) {
		this.totalLaps = totalLaps;
	}

	public int getTrackLength() {
		return trackLength;
	}

	public void setTrackLength(int trackLength) {
		this.trackLength = trackLength;
	}

	public SessionType getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}

	public int getTrackId() {
		return trackId;
	}

	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

	public FormulaType getFormula() {
		return formula;
	}

	public void setFormula(FormulaType era) {
		this.formula = era;
	}

	public int getSessionTimeLeft() {
		return sessionTimeLeft;
	}

	public void setSessionTimeLeft(int sessionTimeLeft) {
		this.sessionTimeLeft = sessionTimeLeft;
	}

	public int getSessionDuration() {
		return sessionDuration;
	}

	public void setSessionDuration(int sessionDuration) {
		this.sessionDuration = sessionDuration;
	}

	public int getPitSpeedLimit() {
		return pitSpeedLimit;
	}

	public void setPitSpeedLimit(int pitSpeedLimit) {
		this.pitSpeedLimit = pitSpeedLimit;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	public boolean isSpectating() {
		return spectating;
	}

	public void setSpectating(boolean spectating) {
		this.spectating = spectating;
	}

	public int getSpectatorCarIndex() {
		return spectatorCarIndex;
	}

	public void setSpectatorCarIndex(int spectatorCarIndex) {
		this.spectatorCarIndex = spectatorCarIndex;
	}

	public boolean isSliProNativeSupport() {
		return sliProNativeSupport;
	}

	public void setSliProNativeSupport(boolean sliProNativeSupport) {
		this.sliProNativeSupport = sliProNativeSupport;
	}

	public int getNumMarshalZones() {
		return numMarshalZones;
	}

	public void setNumMarshalZones(int numMarshalZones) {
		this.numMarshalZones = numMarshalZones;
	}

	public List<MarshalZone> getMarshalZones() {
		return marshalZones;
	}

	public void setMarshalZones(List<MarshalZone> marshalZones) {
		this.marshalZones = marshalZones;
	}

	public SafetyCarStatus getSafetyCarStatus() {
		return safetyCarStatus;
	}

	public void setSafetyCarStatus(SafetyCarStatus safetyCarStatus) {
		this.safetyCarStatus = safetyCarStatus;
	}

	public boolean isNetworkGame() {
		return networkGame;
	}

	public void setNetworkGame(boolean networkGame) {
		this.networkGame = networkGame;
	}

	public int getNumWeatherForecastSamples() {
		return numWeatherForecastSamples;
	}

	public void setNumWeatherForecastSamples(int numWeatherForecastSamples) {
		this.numWeatherForecastSamples = numWeatherForecastSamples;
	}

	public List<WeatherForecastSample> getWeatherForecastSamples() {
		return weatherForecastSamples;
	}

	public void setWeatherForecastSamples(List<WeatherForecastSample> weatherForecastSamples) {
		this.weatherForecastSamples = weatherForecastSamples;
	}

	@Override
	public Packet build(PacketBuffer buffer) {
		setWeather(Weather.fromInt(buffer.getNextUInt8AsInt()));
		setTrackTemperature(buffer.getNextInt8AsInt());
		setAirTemperature(buffer.getNextInt8AsInt());
		setTotalLaps(buffer.getNextUInt8AsInt());
		setTrackLength(buffer.getNextUInt16AsInt());
		setSessionType(SessionType.fromInt(buffer.getNextUInt8AsInt()));
		setTrackId(buffer.getNextInt8AsInt());
		setFormula(FormulaType.fromInt(buffer.getNextUInt8AsInt()));
		setSessionTimeLeft(buffer.getNextUInt16AsInt());
		setSessionDuration(buffer.getNextUInt16AsInt());
		setPitSpeedLimit(buffer.getNextUInt8AsInt());
		setGamePaused(buffer.getNextUInt8AsBoolean());
		setSpectating(buffer.getNextUInt8AsBoolean());
		setSpectatorCarIndex(buffer.getNextUInt8AsInt());
		setSliProNativeSupport(buffer.getNextUInt8AsBoolean());
		setNumMarshalZones(buffer.getNextUInt8AsInt());
		setMarshalZones(buildMarshalZones(buffer));
		setSafetyCarStatus(SafetyCarStatus.fromInt(buffer.getNextUInt8AsInt()));
		setNetworkGame(buffer.getNextUInt8AsBoolean());
		setNumWeatherForecastSamples(buffer.getNextUInt8AsInt());
		setWeatherForecastSamples(buildWeatherForecastSamples(buffer));
		return this;
	}

	private List<MarshalZone> buildMarshalZones(PacketBuffer buffer) {
		List<MarshalZone> marshalZones = new ArrayList<>();
		for (int k = 0; k < MAX_NBR_MARSHAL_ZONES; k++) {
			MarshalZone marshalZone = new MarshalZone();
			marshalZone.setZoneStart(buffer.getNextFloat());
			marshalZone.setZoneFlag(ZoneFlag.fromInt(buffer.getNextInt8AsInt()));
			marshalZones.add(marshalZone);
		}
		return marshalZones;
	}
	
	private List<WeatherForecastSample> buildWeatherForecastSamples(PacketBuffer buffer) {
		List<WeatherForecastSample> weatherForecastSamples = new ArrayList<>();
		for (int k = 0; k < MAX_WEATHER_FORECAST_SAMPLES; k++) {
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
	
}

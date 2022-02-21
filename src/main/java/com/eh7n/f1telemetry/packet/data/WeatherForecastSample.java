package com.eh7n.f1telemetry.packet.data;

import com.eh7n.f1telemetry.packet.enums.SessionType;
import com.eh7n.f1telemetry.packet.enums.Weather;

public class WeatherForecastSample {

	private SessionType sessionType;
	private int timeOffset;
	private Weather weather;
	private int trackTemperature;
	private int airTemperature;
	
	public SessionType getSessionType() {
		return sessionType;
	}
	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}
	public int getTimeOffset() {
		return timeOffset;
	}
	public void setTimeOffset(int timeOffset) {
		this.timeOffset = timeOffset;
	}
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
}

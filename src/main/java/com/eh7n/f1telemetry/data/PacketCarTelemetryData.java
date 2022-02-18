package com.eh7n.f1telemetry.data;

import java.util.List;

import com.eh7n.f1telemetry.data.elements.ButtonStatus;
import com.eh7n.f1telemetry.data.elements.CarTelemetryData;

public class PacketCarTelemetryData extends Packet {
	
	private List<CarTelemetryData> carTelemetryData;
	private ButtonStatus buttonStatus; // TODO, create a representation of this data properly
	private int mfdPanelIndex;
	private int mfdPanelIndexSecondaryPlayer;
	private int suggestedGear;
	
	public PacketCarTelemetryData() {}
	
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

}

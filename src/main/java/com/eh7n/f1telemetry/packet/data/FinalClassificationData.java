package com.eh7n.f1telemetry.packet.data;

import java.util.List;

import com.eh7n.f1telemetry.packet.enums.ResultStatus;

public class FinalClassificationData {

	private int carIndex;
	private boolean playersCar;
	private int position;
	private int numLaps;
	private int gridPosition;
	private int points;
	private int numPitStops;
	private ResultStatus resultStatus;
	private float bestLapTime;
	private double totalRaceTime;
	private int penaltiesTime;
	private int numPenalties;
	private int numTyreStints;
	private List<Integer> tyreStintsActual;
	private List<Integer> tyreStintsVisual;
	public int getCarIndex() {
		return carIndex;
	}
	public void setCarIndex(int carIndex) {
		this.carIndex = carIndex;
	}
	public boolean isPlayersCar() {
		return playersCar;
	}
	public void setPlayersCar(boolean playersCar) {
		this.playersCar = playersCar;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getNumLaps() {
		return numLaps;
	}
	public void setNumLaps(int numLaps) {
		this.numLaps = numLaps;
	}
	public int getGridPosition() {
		return gridPosition;
	}
	public void setGridPosition(int gridPosition) {
		this.gridPosition = gridPosition;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public int getNumPitStops() {
		return numPitStops;
	}
	public void setNumPitStops(int numPitStops) {
		this.numPitStops = numPitStops;
	}
	public ResultStatus getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}
	public float getBestLapTime() {
		return bestLapTime;
	}
	public void setBestLapTime(float bestLapTime) {
		this.bestLapTime = bestLapTime;
	}
	public double getTotalRaceTime() {
		return totalRaceTime;
	}
	public void setTotalRaceTime(double totalRaceTime) {
		this.totalRaceTime = totalRaceTime;
	}
	public int getPenaltiesTime() {
		return penaltiesTime;
	}
	public void setPenaltiesTime(int penaltiesTime) {
		this.penaltiesTime = penaltiesTime;
	}
	public int getNumPenalties() {
		return numPenalties;
	}
	public void setNumPenalties(int numPenalties) {
		this.numPenalties = numPenalties;
	}
	public int getNumTyreStints() {
		return numTyreStints;
	}
	public void setNumTyreStints(int numTyreStints) {
		this.numTyreStints = numTyreStints;
	}
	public List<Integer> getTyreStintsActual() {
		return tyreStintsActual;
	}
	public void setTyreStintsActual(List<Integer> tyreStintsActual) {
		this.tyreStintsActual = tyreStintsActual;
	}
	public List<Integer> getTyreStintsVisual() {
		return tyreStintsVisual;
	}
	public void setTyreStintsVisual(List<Integer> tyreStintsVisual) {
		this.tyreStintsVisual = tyreStintsVisual;
	}
	
}

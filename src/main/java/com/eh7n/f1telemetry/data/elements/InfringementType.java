package com.eh7n.f1telemetry.data.elements;

public enum InfringementType {

	/*
	0 Blocking by slow driving
	1 Blocking by wrong way driving
	2 Reversing off the start line
	3 Big Collision
	4 Small Collision
	5 Collision failed to hand back position single
	6 Collision failed to hand back position multiple
	7 Corner cutting gained time
	8 Corner cutting overtake single
	9 Corner cutting overtake multiple
	10 Crossed pit exit lane
	11 Ignoring blue flags
	12 Ignoring yellow flags
	13 Ignoring drive through
	14 Too many drive throughs
	15 Drive through reminder serve within n laps
	16 Drive through reminder serve this lap
	17 Pit lane speeding
	18 Parked for too long
	19 Ignoring tyre regulations
	20 Too many penalties
	21 Multiple warnings
	22 Approaching disqualification
	23 Tyre regulations select single
	24 Tyre regulations select multiple
	25 Lap invalidated corner cutting
	26 Lap invalidated running wide
	27 Corner cutting ran wide gained time minor
	28 Corner cutting ran wide gained time significant
	29 Corner cutting ran wide gained time extreme
	30 Lap invalidated wall riding
	31 Lap invalidated flashback used
	32 Lap invalidated reset to track
	33 Blocking the pitlane
	34 Jump start
	35 Safety car to car collision
	36 Safety car illegal overtake
	37 Safety car exceeding allowed pace
	38 Virtual safety car exceeding allowed pace
	39 Formation lap below allowed speed
	40 Retired mechanical failure
	41 Retired terminally damaged
	42 Safety car falling too far back
	43 Black flag timer
	44 Unserved stop go penalty
	45 Unserved drive through penalty
	46 Engine component change
	47 Gearbox change
	48 League grid penalty
	49 Retry penalty
	50 Illegal time gain
	51 Mandatory pitstop
	*/
	
	UNKNOWN,
	P1,
	P2,
	P3,
	SHORT_P,
	Q1,
	Q2,
	Q3,
	SHORT_Q,
	OSQ,
	R,
	R2,
	TIME_TRIAL;

	public static InfringementType fromInt(int i) {
		switch(i) {
			case 0:
				return InfringementType.UNKNOWN;
			case 1:
				return InfringementType.P1;
			case 2:
				return InfringementType.P2;
			case 3:
				return InfringementType.P3;
			case 4:
				return InfringementType.SHORT_P;
			case 5:
				return InfringementType.Q1;
			case 6:
				return InfringementType.Q2;
			case 7:
				return InfringementType.Q3;
			case 8:
				return InfringementType.SHORT_Q;
			case 9:
				return InfringementType.OSQ;
			case 10:
				return InfringementType.R;
			case 11:
				return InfringementType.R2;
			case 12:
				return InfringementType.TIME_TRIAL;
			default:
				return null;
		}
	}
	
}

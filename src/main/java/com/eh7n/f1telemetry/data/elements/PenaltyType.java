package com.eh7n.f1telemetry.data.elements;

public enum PenaltyType {

	/*
	0 Drive through
	1 Stop Go
	2 Grid penalty
	3 Penalty reminder
	4 Time penalty
	5 Warning
	6 Disqualified
	7 Removed from formation lap
	8 Parked too long timer
	9 Tyre regulations
	10 This lap invalidated
	11 This and next lap invalidated
	12 This lap invalidated without reason
	13 This and next lap invalidated without reason
	14 This and previous lap invalidated
	15 This and previous lap invalidated without reason
	16 Retired
	17 Black flag timer
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

	public static PenaltyType fromInt(int i) {
		switch(i) {
			case 0:
				return PenaltyType.UNKNOWN;
			case 1:
				return PenaltyType.P1;
			case 2:
				return PenaltyType.P2;
			case 3:
				return PenaltyType.P3;
			case 4:
				return PenaltyType.SHORT_P;
			case 5:
				return PenaltyType.Q1;
			case 6:
				return PenaltyType.Q2;
			case 7:
				return PenaltyType.Q3;
			case 8:
				return PenaltyType.SHORT_Q;
			case 9:
				return PenaltyType.OSQ;
			case 10:
				return PenaltyType.R;
			case 11:
				return PenaltyType.R2;
			case 12:
				return PenaltyType.TIME_TRIAL;
			default:
				return null;
		}
	}
	
}

package com.eh7n.f1telemetry.data.elements;

public enum PenaltyType {
	
	DRIVE_THROUGH,
	STOP_GO,
	GRID_PENALTY,
	PENALTY_REMINDER,
	TIME_PENALTY,
	WARNING,
	DISQUALIFIED,
	REM_FORMATION_LAP,
	PARKED_TOO_LONG,
	TYRE_REGULATIONS,
	THIS_LAP_INVALIDATED,
	THIS_AND_NEXT_LAP_INVALIDATED,
	THIS_LAP_INVALIDATED_WITHOUT_REASON,
	THIS_AND_NEXT_LAP_INVALIDATED_WITHOUT_REASON,
	THIS_AND_PREVIOUS_LAP_INVALIDATED,
	THIS_AND_PREVIOUS_LAP_INVALIDATED_WITHOUT_REASON,
	RETIRED,
	BLACK_FLAG_TIMER;

	public static PenaltyType fromInt(int i) {
		switch(i) {
			case 0:
				return PenaltyType.DRIVE_THROUGH;
			case 1:
				return PenaltyType.STOP_GO;
			case 2:
				return PenaltyType.GRID_PENALTY;
			case 3:
				return PenaltyType.PENALTY_REMINDER;
			case 4:
				return PenaltyType.TIME_PENALTY;
			case 5:
				return PenaltyType.WARNING;
			case 6:
				return PenaltyType.DISQUALIFIED;
			case 7:
				return PenaltyType.REM_FORMATION_LAP;
			case 8:
				return PenaltyType.PARKED_TOO_LONG;
			case 9:
				return PenaltyType.TYRE_REGULATIONS;
			case 10:
				return PenaltyType.THIS_LAP_INVALIDATED;
			case 11:
				return PenaltyType.THIS_AND_NEXT_LAP_INVALIDATED;
			case 12:
				return PenaltyType.THIS_LAP_INVALIDATED_WITHOUT_REASON;
			case 13:
				return PenaltyType.THIS_AND_NEXT_LAP_INVALIDATED_WITHOUT_REASON;
			case 14:
				return PenaltyType.THIS_AND_PREVIOUS_LAP_INVALIDATED;
			case 15:
				return PenaltyType.THIS_AND_PREVIOUS_LAP_INVALIDATED_WITHOUT_REASON;
			case 16:
				return PenaltyType.RETIRED;
			case 17:
				return PenaltyType.BLACK_FLAG_TIMER;
			default:
				return null;
		}
	}
	
}

package com.eh7n.f1telemetry.packet.enums;

public enum InfringementType {

	BLOCKING_SLOW_DRIVING,
	BLOCKING_WRONG_WAY_DRIVING,
	REVERSING_OFF_START_LINE,
	BIG_COLLISION,
	SMALL_COLLISION,
	COLLISION_FAILED_POSITION_SINGLE,
	COLLISION_FAILED_POSITION_MULTIPLE,
	CORNER_CUTTING_GAINED_TIME,
	CORNER_CUTTING_OVERTAKE_SINGLE,
	CORNER_CUTTING_OVERTAKE_MULTIPLE,
	CROSSED_PIT_EXIT_LANE,
	IGNORING_BLUE_FLAGS,
	IGNORING_YELLOW_FLAGS,
	IGNORING_DRIVE_THROUGH,
	TOO_MANY_DRIVE_THROUGHS,
	DT_REMINDER_WITHIN_N_LAPS,
	DT_REMINDER_THIS_LAP,
	PIT_LANE_SPEEDING,
	PARKED_FOR_TOO_LONG,
	IGNORING_TYRE_REGULATIONS,
	TOO_MANY_PENALTIES,
	MULTIPLE_WARNINGS,
	APPROACHING_DISQUALIFICATION,
	TYRE_REGULATIONS_SELECT_SINGLE,
	TYRE_REGULATIONS_SELECT_MULTIPLE,
	LAP_INVALIDATED_CORNER_CUTTING,
	LAP_INVALIDATED_RUNNING_WIDE,
	CORNER_CUTTING_GAINED_TIME_MINOR,
	CORNER_CUTTING_GAINED_TIME_SIGNIFICANT,
	CORNER_CUTTING_GAINED_TIME_EXTREME,
	LAP_INVALIDATED_WALL_RIDING,
	LAP_INVALIDATED_FLASHBACK_USED,
	LAP_INVALIDATED_RESET_TO_TRACK,
	BLOCKING_THE_PITLANE,
	JUMP_START,
	SC_CAR_COLLISION,
	SC_ILLEGAL_OVERTAKE,
	SC_EXCEEDING_ALLOWED_PACE,
	VSC_EXCEEDING_ALLOWED_PACE,
	FORMATION_LAP_BELOW_ALLOWED_SPEED,
	RETIRED_MECHANICAL_FAILURE,
	RETIRED_TERMINALLY_DAMAGED,
	SC_FALLING_TOO_FAR_BACK,
	BLACK_FLAG_TIMER,
	UNSERVED_STOP_GO_PENALTY,
	UNSERVED_DRIVE_THROUGH_PENALTY,
	ENGINE_COMPONENT_CHANGE,
	GEARBOX_CHANGE,
	LEAGUE_GRID_PENALTY,
	RETRY_PENALTY,
	ILLEGAL_TIME_GAIN,
	MANDATORY_PITSTOP;

	public static InfringementType fromInt(int i) {
		switch(i) {
			case 0:
				return InfringementType.BLOCKING_SLOW_DRIVING;
			case 1:
				return InfringementType.BLOCKING_WRONG_WAY_DRIVING;
			case 2:
				return InfringementType.REVERSING_OFF_START_LINE;
			case 3:
				return InfringementType.BIG_COLLISION;
			case 4:
				return InfringementType.SMALL_COLLISION;
			case 5:
				return InfringementType.COLLISION_FAILED_POSITION_SINGLE;
			case 6:
				return InfringementType.COLLISION_FAILED_POSITION_MULTIPLE;
			case 7:
				return InfringementType.CORNER_CUTTING_GAINED_TIME;
			case 8:
				return InfringementType.CORNER_CUTTING_OVERTAKE_SINGLE;
			case 9:
				return InfringementType.CORNER_CUTTING_OVERTAKE_MULTIPLE;
			case 10:
				return InfringementType.CROSSED_PIT_EXIT_LANE;
			case 11:
				return InfringementType.IGNORING_BLUE_FLAGS;
			case 12:
				return InfringementType.IGNORING_YELLOW_FLAGS;
			case 13:
				return InfringementType.IGNORING_DRIVE_THROUGH;
			case 14:
				return InfringementType.TOO_MANY_DRIVE_THROUGHS;
			case 15:
				return InfringementType.DT_REMINDER_WITHIN_N_LAPS;
			case 16:
				return InfringementType.DT_REMINDER_THIS_LAP;
			case 17:
				return InfringementType.PIT_LANE_SPEEDING;
			case 18:
				return InfringementType.PARKED_FOR_TOO_LONG;
			case 19:
				return InfringementType.IGNORING_TYRE_REGULATIONS;
			case 20:
				return InfringementType.TOO_MANY_PENALTIES;
			case 21:
				return InfringementType.MULTIPLE_WARNINGS;
			case 22:
				return InfringementType.APPROACHING_DISQUALIFICATION;
			case 23:
				return InfringementType.TYRE_REGULATIONS_SELECT_SINGLE;
			case 24:
				return InfringementType.TYRE_REGULATIONS_SELECT_MULTIPLE;
			case 25:
				return InfringementType.LAP_INVALIDATED_CORNER_CUTTING;
			case 26:
				return InfringementType.LAP_INVALIDATED_RUNNING_WIDE;
			case 27:
				return InfringementType.CORNER_CUTTING_GAINED_TIME_MINOR;
			case 28:
				return InfringementType.CORNER_CUTTING_GAINED_TIME_SIGNIFICANT;
			case 29:
				return InfringementType.CORNER_CUTTING_GAINED_TIME_EXTREME;
			case 30:
				return InfringementType.LAP_INVALIDATED_WALL_RIDING;
			case 31:
				return InfringementType.LAP_INVALIDATED_FLASHBACK_USED;
			case 32:
				return InfringementType.LAP_INVALIDATED_RESET_TO_TRACK;
			case 33:
				return InfringementType.BLOCKING_THE_PITLANE;
			case 34:
				return InfringementType.JUMP_START;
			case 35:
				return InfringementType.SC_CAR_COLLISION;
			case 36:
				return InfringementType.SC_ILLEGAL_OVERTAKE;
			case 37:
				return InfringementType.SC_EXCEEDING_ALLOWED_PACE;
			case 38:
				return InfringementType.VSC_EXCEEDING_ALLOWED_PACE;
			case 39:
				return InfringementType.FORMATION_LAP_BELOW_ALLOWED_SPEED;
			case 40:
				return InfringementType.RETIRED_MECHANICAL_FAILURE;
			case 41:
				return InfringementType.RETIRED_TERMINALLY_DAMAGED;
			case 42:
				return InfringementType.SC_FALLING_TOO_FAR_BACK;
			case 43:
				return InfringementType.BLACK_FLAG_TIMER;
			case 44:
				return InfringementType.UNSERVED_STOP_GO_PENALTY;
			case 45:
				return InfringementType.UNSERVED_DRIVE_THROUGH_PENALTY;
			case 46:
				return InfringementType.ENGINE_COMPONENT_CHANGE;
			case 47:
				return InfringementType.GEARBOX_CHANGE;
			case 48:
				return InfringementType.LEAGUE_GRID_PENALTY;
			case 49:
				return InfringementType.RETRY_PENALTY;
			case 50:
				return InfringementType.ILLEGAL_TIME_GAIN;
			case 51:
				return InfringementType.MANDATORY_PITSTOP;			
			default:
				return null;
		}
	}
	
}

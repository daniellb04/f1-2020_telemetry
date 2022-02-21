package com.eh7n.f1telemetry.packet.enums;

public enum FormulaType {

	F1_MODERN,
	F1_CLASSIC,
	F2,
	F1_GENERIC;
	
	public static FormulaType fromInt(int i) {
		switch(i) {
			case 0:
				return FormulaType.F1_MODERN;
			case 1:
				return FormulaType.F1_CLASSIC;
			case 2:
				return FormulaType.F2;
			case 3:
				return FormulaType.F1_GENERIC;
			default:
				return null;
		}
	}
}

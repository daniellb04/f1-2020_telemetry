package com.eh7n.f1telemetry.data.elements;

public enum Formula {

	F1_MODERN,
	F1_CLASSIC,
	F2,
	F1_GENERIC;
	
	public static Formula fromInt(int i) {
		switch(i) {
			case 0:
				return Formula.F1_MODERN;
			case 1:
				return Formula.F1_CLASSIC;
			case 2:
				return Formula.F2;
			case 3:
				return Formula.F1_GENERIC;
			default:
				return null;
		}
	}
}

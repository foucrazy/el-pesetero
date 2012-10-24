package es.elpesetero;

import java.util.Calendar;

public enum FrequencyType {
	annually(Calendar.DAY_OF_YEAR),
	monthly(Calendar.DAY_OF_MONTH),
	weekly(Calendar.DAY_OF_WEEK),
	dayly(0);
	
	private FrequencyType(int field) {
		this.field = field;
	}
	private int field;
	
	public int getPertinentDay() {
		Calendar today = Calendar.getInstance();
		int thisDay = today.get(field);
		System.out.println("This day "+thisDay);
		return thisDay;
	}
	
}

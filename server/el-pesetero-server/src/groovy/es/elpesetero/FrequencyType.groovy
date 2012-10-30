package es.elpesetero;

import java.util.Calendar

public enum FrequencyType {
	annually(Calendar.DAY_OF_YEAR),
	monthly(Calendar.DAY_OF_MONTH),
	weekly(Calendar.DAY_OF_WEEK),
	dayly(0);
	
	private int field;
	
	private FrequencyType(int field) { this.field = field	}
	
	def int getPertinentDay() {
		Calendar today = Calendar.getInstance()
		def thisDay = today.get(field)
		println "This day $thisDay"
		thisDay
	}
	
}

package com.rds.rodalies.alarmas;

import android.provider.BaseColumns;

public final class AlarmContract {
	
	public AlarmContract() {}
	
	public static abstract class Alarm implements BaseColumns {
		public static final String TABLE_NAME = "alarm";
		public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
		public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";
		public static final String COLUMN_NAME_ALARM_REPEAT_DAYS = "days";
        public static final String COLUMN_NAME_ALARM_REPEAT_WEEKLY = "weekly";

    }
}
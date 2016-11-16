package com.sysbuddy.backup;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class used  to format names of backed up files and archives.
 * TODO: Load default constants from global property/configuration file.
 * @author darius
 */
public class BackupNameFormatter {

	/**
	 * The default time stamp format
	 */
	private final static String DEFAULT_TIME_STAMP = "MM-dd-yyyy hh mm aaa";

	/**
	 * The default time stamp's time zone
	 * TODO: Set to system based time zone
	 */
	private final static String DEFAULT_TIME_ZONE = "America/Chicago";
	
	/**
	 * The default file backup name prefix
	 */
	private final static String DEFAULT_PREFIX = "";

	/**
	 * The time stamp format for file naming.
	 */
	private String timeFormat;

	/**
	 * The time zone for the file name's time stamp.
	 */
	private String timeZone;

	/**
	 * Prefix for the backup file name.
	 */
	private String prefix;
	
	/**
	 * Creates a backup name formatter.
	 * @param prefix Prefix for the backup file name.
	 * @param timeFormat The time stamp format for file naming.
	 */
	public BackupNameFormatter(String prefix, String timeFormat) {
		this.prefix = prefix;
		this.timeFormat = timeFormat;
		this.timeZone = DEFAULT_TIME_ZONE;
	}
	
	/**
	 * Creates a default backup name formatter.
	 */
	public BackupNameFormatter(){
		this.timeFormat = DEFAULT_TIME_STAMP;
		this.prefix = DEFAULT_PREFIX;
		this.timeZone = DEFAULT_TIME_ZONE;
	}
	
	/**
	 * Creates the file directory string
	 * @param parent Parent directory. Leave blank or {@code null} if no parent.
	 * @return
	 */
	public String toDirectory(String parent) {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat(timeFormat);
		format.setTimeZone(TimeZone.getTimeZone(timeZone));
		String name = (prefix == null || prefix.isEmpty() ? "" : prefix) + format.format(date);
		return (parent == null ? "" : parent) + File.separator + name;
	}
	
	/**
	 * Gets the time stamp format.
	 * @return The time stamp format.
	 */
	public String getTimeFormat() {
		return timeFormat;
	}

	/**
	 * Sets the time stamp format.
	 * @param timeStampFormat The time stamp format.
	 */
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	
	/**
	 * Gets the prefix.
	 * @return Prefix for the backup file name. 
	 */
	public String getPrefix() {
		return prefix;
	}
	
	/**
	 * Sets the prefix.
	 * @param prefix The prefix for the backup file name. 
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * @return The timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}

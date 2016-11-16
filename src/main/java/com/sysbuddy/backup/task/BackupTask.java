package com.sysbuddy.backup.task;

import java.nio.file.Path;

import com.sysbuddy.backup.BackupNameFormatter;
import com.sysbuddy.config.GlobalConfiguration;

/**
 * Abstract class for backup schedule tasks.
 * See package {@link com.sysbuddy.backup.task.impl} for examples
 * @author darius
 */
public abstract class BackupTask {
	
	/**
	 * The amount of time between each routine backup.
	 */
	protected final long delay;
	
	/**
	 * The name of the schedule task.
	 */
	protected final String name;

	/**
	 * Used for formatting the name of the backed up files and archives.
	 */
	protected BackupNameFormatter formatter;
	
	/**
	 * The last time (milliseconds) a backup was executed.
	 */
	protected long last;
	
	/**
	 * If the backup task is still working or not.
	 */
	private boolean working;
	
	/**
	 * Creates a backup task
	 * @param name The name of the task.
	 * @param delay The delay, in milliseconds, between each routine backup.
	 */
	public BackupTask(String name, long delay) {
		this.name = name;
		this.delay = delay;
		this.formatter = new BackupNameFormatter();
		this.last = GlobalConfiguration.LAUNCH_TASKS_IMMEDIATELY ? 0 : System.currentTimeMillis();
	}
	
	/**
	 * Executes the backup upon scheduled occurrence
	 * @return The path that the file was copied to
	 */
	public abstract Path backup();
	
	/**
	 * Checks if the schedule task is ready to execute.
	 * @return {@code true} if task endured delay's duration, {@code false} if still sleeping or working.
	 */
	public boolean ready() {
		if (working) return false;
		return (System.currentTimeMillis() - last) >= delay;
	}
	
	/**
	 * Sets the last time the schedule was backed up.
	 * @param last The last time (milliseconds)
	 */
	public void setLast(long last) {
		this.last = last;
	}
	
	/**
	 * Sets whether or not the task is owrking.
	 * @param working If the task is working.
	 */
	public void setWorking(boolean working) {
		this.working = working;
	}
	
	/**
	 * Gets the last time the schedule was backed up.
	 * @return The last time (milliseconds)
	 */
	public long getLast() {
		return last;
	}
	
	/**
	 * Get the name formatter.
	 * @return The name formatter.
	 */
	public BackupNameFormatter getFormatter() {
		return formatter;
	}
}

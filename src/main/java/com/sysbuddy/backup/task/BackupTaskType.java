package com.sysbuddy.backup.task;

import com.sysbuddy.backup.task.impl.*;

/**
 * Enum to determine the type of task being loaded in the XML configuration file.
 * @author darius
 */
public enum BackupTaskType {

	LOCAL_FILE_1("file", LocalFileTaskLoader.class),
	LOCAL_FILE_2("files", LocalFileTaskLoader.class),
	SFTP_FILE("sftp", SFTPBackupTaskLoader.class);
	
	/**
	 * The key that matches with the given type.
	 */
	private final String key;
	
	/**
	 * The class type of the loader associated with the key.
	 */
	private final Class<?> loader;
	
	/**
	 * Creates a backup task type.
	 * @param key The key that matches with the given type.
	 * @param loader The class type of the loader associated with the key.
	 */
	private BackupTaskType(String key, Class<?> loader) {
		this.key = key;
		this.loader = loader;
	}
	
	/**
	 * Gets the type based on the given key.
	 * @param key The key.
	 * @return The task, and {@code null} if no matches.
	 */
	public static BackupTaskType get(String key) {
		for (BackupTaskType candidate : values()) {
			if (candidate.key.equalsIgnoreCase(key)) {
				return candidate;
			}
		}
		return null;
	}
	
	/**
	 * Gets the loader class type.
	 * @return The loader class type.
	 */
	public Class<?> getLoader() {
		return loader;
	}
}

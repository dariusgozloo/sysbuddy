package com.sysbuddy.backup.task;

import com.sysbuddy.io.Node;

/**
 * Parent class for reading backup task via XML configuration files 
 * @author darius
 */
public abstract class BackupTaskLoader {

	/**
	 * The data set for the backup task configuration
	 */
	protected Node node;
	
	/**
	 * Creates a backup task loader.
	 * @param node
	 */
	public BackupTaskLoader(Node node) {
		this.node = node;
	}
	
	/**
	 * Builds the backup task based on the configuration.
	 * @return The {@link BackupTask} that is built.
	 * @throws Exception If the file operation is unsuccessful.
	 */
	public abstract BackupTask load() throws Exception;
}

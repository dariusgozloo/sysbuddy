package com.sysbuddy.backup.task.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import com.sysbuddy.backup.asset.impl.LocalArchiveAsset;
import com.sysbuddy.backup.task.BackupTask;

/**
 * Copies an archived backup of desired files in a local directory.
 * @author darius
 */
public class LocalFileTask extends BackupTask {

	/**
	 * The logger of the backup task.
	 */
	private final static Logger logger = Logger.getLogger(LocalFileTask.class.getName());
	
	/**
	 * The local asset that will be backed up.
	 */
	private final LocalArchiveAsset asset;
	
	/**
	 * The local directory in which the file should be placed.
	 */
	protected final String destination;
	
	/**
	 * Creates a local backup task.
	 * @param name The name of the task.
	 * @param asset The asset being backed up.
	 * @param delay The time, in milliseconds, between each routine backup.
	 */
	public LocalFileTask(String name, LocalArchiveAsset asset, String destination, long delay) {
		super(name, delay);
		this.asset = asset;
		this.destination = destination;
	}

	@Override
	public Path backup() {
		logger.info("Starting routine backup for \"" + name + "\"..");

		String directory = formatter.toDirectory(destination);
		
		try {
			Path result = asset.copy(directory);
			logger.info("Backup for \"" + name + "\" was successful! Archive directory: " + result.toString());
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}

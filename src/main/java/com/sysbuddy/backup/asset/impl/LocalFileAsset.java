package com.sysbuddy.backup.asset.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sysbuddy.backup.asset.BackupAsset;

/**
 * Locally stored (single) file asset.
 * @author darius
 */
public class LocalFileAsset extends BackupAsset {

	/**
	 * The directory of the local file that will be backed up.
	 */
	private final String directory;
	
	/**
	 * Creates a local file backup asset.
	 * @param directory The directory of the local file to backup.
	 */
	public LocalFileAsset(String directory) {
		this.directory = directory;
	}
	
	@Override
	public Path copy(String destination) throws IOException {
		return Files.copy(Paths.get(directory), Paths.get(destination));
	}

}

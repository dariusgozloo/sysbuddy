package com.sysbuddy.backup.asset;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Parent class for assets that will be backed up.
 * @author darius
 */
public abstract class BackupAsset {
	
	/**
	 * Copies the selected asset to the given local directory.
	 * @param destination The local directory for the asset to be stored.
	 * @return The path of the locally stored backup, {@code null} if operation is unsuccessful.
	 * @throws IOException If the file operation is unsuccessful.
	 */
	public abstract Path copy(final String destination) throws IOException;
	
}

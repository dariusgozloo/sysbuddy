package com.sysbuddy.backup.asset.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sysbuddy.backup.asset.BackupAsset;

/**
 * Archiving local directories to be backed up.
 * Supports multiple files/directories in one archive with the array {@link directories}
 * @author darius
 */
public class LocalArchiveAsset extends BackupAsset {

	/**
	 * The directories of the local folders/files that will be archived.
	 */
	private final String[] directories;
	
	/**
	 * Creates a local archive backup asset.
	 * @param directories The directories of the local folders/files that will be archived.
	 */
	public LocalArchiveAsset(String[] directories) {
		this.directories = directories;
	}
	
	/**
	 * Creates a local archive backup asset.
	 * @param directory The directory of the local folder/file that will be archived.
	 */
	public LocalArchiveAsset(String directory) {
		this.directories = new String[] {directory};
	}
	
	@Override
	public Path copy(String destination) throws IOException {
		if (!destination.endsWith(".zip"))
			destination += ".zip";
		
		FileOutputStream fileStream = new FileOutputStream(destination);
		ZipOutputStream zipStream = new ZipOutputStream(fileStream);
		
		try {
			for (String directory : directories) {
				archive(new File(directory), null, fileStream, zipStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		zipStream.close();
		fileStream.close();
		
		return Paths.get(destination);
	}

	/**
	 * Archives based on given parameters. This method works recursively
	 * @param target The directory that will be archived.
	 * @param parent The parent directory, {@code null} if the target is not a child directory
	 * @param fileStream The {@link FileOutputStream} for the worker
	 * @param zipStream The {@link ZipOutputStream} for the worker
	 * @throws IOException If the operation is unsuccessful
	 */
	private void archive(File target, String parent, FileOutputStream fileStream, ZipOutputStream zipStream) throws IOException {
		if (!target.exists()) {
			return;
		}
		
		String entry = target.getName();
		
		if (parent != null && !parent.isEmpty()) {
			entry = parent + "/" + target.getName();
		}
		
		if (target.isDirectory()) {
			for (File file : target.listFiles())
				archive(file, entry, fileStream, zipStream);
		} else {
			byte[] buffer = new byte[1024];
			
			FileInputStream inputStream = new FileInputStream(target);
			zipStream.putNextEntry(new ZipEntry(entry));
			
			int length;
			while ((length = inputStream.read(buffer)) > 0) {
				zipStream.write(buffer, 0, length);
			}
			
			zipStream.closeEntry();
			inputStream.close();
		}
	}
}

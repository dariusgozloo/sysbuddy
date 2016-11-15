package com.sysbuddy.backup.task.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.sysbuddy.backup.asset.impl.LocalArchiveAsset;
import com.sysbuddy.util.FTPCredentials;

/**
 * Backup task for copying local files to a remote FTP server.
 * Option of keeping backed up archives locally.
 * 
 * TODO: Condense constructor
 * 
 * @author darius
 */
public class SFTPBackupTask extends LocalFileTask {

	/**
	 * The logger
	 */
	private final static Logger logger = Logger.getLogger(SFTPBackupTask.class.getName());

	/**
	 * The credentials for the SFTP server.
	 */
	private final FTPCredentials credentials;
	
	/**
	 * The directory on the SFTP server that the archive will be sent to. 
	 */
	private final String remoteDestination;
	
	/**
	 * Whether or not the task will keep the locally stored backups.
	 */
	private boolean local;
	
	/**
	 * Creates an FTP backup task
	 * @param name The name of the task.
	 * @param info The credentials of the SFTP server.
	 * @param asset The file asset to backup.
	 * @param localDir The local directory that will hold the backup until sent.
	 * @param remoteDir The remote directory on the FTP that the archive will be sent to.
	 * @param delay The delay, in milliseconds, between each backup.
	 */
	public SFTPBackupTask(String name, FTPCredentials info, LocalArchiveAsset asset, String localDir, String remoteDir, long delay) {
		super(name, asset, localDir, delay);
		this.credentials = info;
		this.remoteDestination = remoteDir;
		this.local = true;
	}

	@Override
	public Path backup() {
		Path localPath = super.backup();
		String directory = localPath.toString();
		
		if (localPath == null || !Files.exists(Paths.get(directory))) {
			logger.info("Could not find local file copy (" + directory + ")! Aborting FTP session..");
			return null;
		}

        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;

        logger.info("Connecting to SFTP " + credentials.getServer() + ":" + credentials.getPort() + "..");
        
		try {
			JSch jsch = new JSch();
	        session = jsch.getSession(credentials.getUsername(), credentials.getServer(), 22);
	        session.setPassword(credentials.getPassword());
	        java.util.Properties config = new java.util.Properties();
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);
	        session.connect();
	        channel = session.openChannel("sftp");
	        channel.connect();
	        logger.info("Connected to " + credentials.getServer() + ":" + credentials.getPort() + "! Uploading file..");
	        channelSftp = (ChannelSftp) channel;
	        channelSftp.cd(remoteDestination);
	        File localFile = new File(directory);
	        String remote = localFile.getName();
	        InputStream inputStream = new FileInputStream(localFile);
	        channelSftp.put(inputStream, remote);
	        logger.info("File transfered successfully to SFTP server " + credentials.getServer() + ":" + credentials.getPort() + " (" + remote + ")!");
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!local) {
	        	try {
					Files.delete(localPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	logger.info("Deleted local tmp file: " + directory);
	        }
		}
		
		return localPath;
	}
	
	/**
	 * Set whether or not the task will keep the local copy. 
	 * @param local Whether or not the task will keep the local copy.
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}
}

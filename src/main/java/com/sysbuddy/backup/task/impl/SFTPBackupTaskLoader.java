package com.sysbuddy.backup.task.impl;

import com.sysbuddy.backup.asset.impl.LocalArchiveAsset;
import com.sysbuddy.backup.task.BackupTask;
import com.sysbuddy.backup.task.BackupTaskLoader;
import com.sysbuddy.io.Node;
import com.sysbuddy.util.FTPCredentials;

public class SFTPBackupTaskLoader extends BackupTaskLoader {

	public SFTPBackupTaskLoader(Node node) {
		super(node);
	}

	@Override
	public BackupTask load() throws Exception {
		String name = node.getChild("name").getValue();
		String localDestination = node.getChild("local_destination").getValue();
		long delay = Long.parseLong(node.getChild("delay").getValue());

		Node[] directoryNodes = node.getChildren("directory");
		String[] directories = new String[directoryNodes.length];
		
		for (int i = 0; i < directories.length; i++) {
			directories[i] = directoryNodes[i].getValue();
		}

		boolean local = true;
		
		if (node.getChild("keep_local") != null) {
			local = Boolean.parseBoolean(node.getChild("keep_local").getValue());
		}
		
		Node remote = node.getChild("server");
		
		String address = remote.getChild("address").getValue();
		String user = remote.getChild("user").getValue();
		String pass = remote.getChild("pass").getValue();
		String remoteDestination = remote.getChild("destination").getValue();
		
		LocalArchiveAsset asset = new LocalArchiveAsset(directories);
		FTPCredentials credentials = new FTPCredentials(address, user, pass);
		
		SFTPBackupTask task = new SFTPBackupTask(name, credentials, asset, localDestination, remoteDestination, delay);
		task.setLocal(local);
		return task;
	}

}

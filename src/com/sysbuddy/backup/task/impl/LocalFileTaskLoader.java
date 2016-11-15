package com.sysbuddy.backup.task.impl;

import com.sysbuddy.backup.asset.impl.LocalArchiveAsset;
import com.sysbuddy.backup.task.BackupTask;
import com.sysbuddy.backup.task.BackupTaskLoader;
import com.sysbuddy.io.Node;

/**
 * The loader for {@link LocalFileTask}
 * @author darius
 */
public class LocalFileTaskLoader extends BackupTaskLoader {

	public LocalFileTaskLoader(Node node) {
		super(node);
	}

	@Override
	public BackupTask load() {
		String name = node.getChild("name").getValue();
		String destination = node.getChild("destination").getValue();
		long delay = Long.parseLong(node.getChild("delay").getValue());

		Node[] directoryNodes = node.getChildren("directory");
		String[] directories = new String[directoryNodes.length];
		
		for (int i = 0; i < directories.length; i++) {
			directories[i] = directoryNodes[i].getValue();
		}
		
		LocalArchiveAsset asset = new LocalArchiveAsset(directories);
		return new LocalFileTask(name, asset, destination, delay);
	}

}

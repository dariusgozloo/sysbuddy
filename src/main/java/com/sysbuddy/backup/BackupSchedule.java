package com.sysbuddy.backup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sysbuddy.backup.task.BackupTask;
import com.sysbuddy.backup.task.BackupTaskWorker;

/**
 * Contains an array list of backup schedule tasks.
 * @author darius
 */
public class BackupSchedule {
	
	/**
	 * The list of backup schedule tasks to be executed.
	 */
	private List<BackupTask> tasks;
	
	/**
	 * Creates a backup schedule
	 */
	public BackupSchedule() {
		this.tasks = new ArrayList<BackupTask>();
	}
	
	/**
	 * Iterates through the task list and executes those pending execution.
	 */
	public void run() {
		BackupTask task;
		
		for (Iterator<BackupTask> it = tasks.iterator(); it.hasNext();) {
			task = it.next();
			if (task.ready()) {
				BackupTaskWorker worker = new BackupTaskWorker(task);
				Thread thread = new Thread(worker);
				thread.start();
			}
		}
	}
	
	/**
	 * Gets the backup schedule tasks.
	 * @return The tasks.
	 */
	public List<BackupTask> getTasks() {
		return tasks;
	}
}

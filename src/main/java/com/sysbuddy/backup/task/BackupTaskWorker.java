package com.sysbuddy.backup.task;

/**
 * Worker thread class for backup tasks.
 * @author darius
 */
public class BackupTaskWorker implements Runnable {

	/**
	 * The backup task
	 */
	private BackupTask task;

	/**
	 * Creates the backup schedule worker.
	 * @param schedule The backup task schedule
	 */
	public BackupTaskWorker(BackupTask task) {
		this.task = task;
	}
	
	@Override
	public void run() {
		task.setWorking(true);
		task.backup();
		task.setWorking(false);
		task.setLast(System.currentTimeMillis());
	}
}

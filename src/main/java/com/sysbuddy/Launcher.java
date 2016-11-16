package com.sysbuddy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.SAXException;

import com.sysbuddy.backup.BackupSchedule;
import com.sysbuddy.backup.BackupScheduleLoader;
import com.sysbuddy.io.NodeParser;
import com.sysbuddy.io.XmlParser;

/**
 * Executor, main method class
 * @author darius
 */
public class Launcher {
	
	/**
	 * The logger of the launcher
	 */
	private final static Logger logger = Logger.getLogger(Launcher.class.getName());

	/**
	 * The amount of time between each tick.
	 */
	private final static long TICK_DELAY = 1000;
	
	/**
	 * If the program is running.
	 */
	private static boolean RUNNING = true;
	
	/**
	 * File directory for backup configuration file.
	 * TODO: Separate configuration class
	 * TODO: Properties file
	 */
	private final static String BACKUP_DIRECTORY = "./config/backups.xml";
	
	/**
	 * Main method
	 * @param args Execution arguments
	 */
	public static void main(String[] args) {
		logger.info("Loading configuration files..");
		
		File file = new File(BACKUP_DIRECTORY);
		
		NodeParser parser = null;
		try {
			parser = new XmlParser(new FileInputStream(file));
		} catch (FileNotFoundException | SAXException e) {
			logger.log(Level.SEVERE, "There was an error loading the backup schedule configuration", e);
		}
		
		BackupScheduleLoader backupScheduleLoader = new BackupScheduleLoader(parser);
		BackupSchedule backupSchedule = backupScheduleLoader.load();
		
		logger.info("Loaded " + backupSchedule.getTasks().size() + " backup schedule task(s)!");
		
		while(RUNNING) {
			long start = System.currentTimeMillis();
			backupSchedule.run();
			
			long delay = TICK_DELAY - (System.currentTimeMillis() - start);
			
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

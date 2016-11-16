package com.sysbuddy.backup;

import java.io.IOException;
import java.lang.reflect.Constructor;

import com.sysbuddy.backup.task.BackupTask;
import com.sysbuddy.backup.task.BackupTaskLoader;
import com.sysbuddy.backup.task.BackupTaskType;
import com.sysbuddy.io.Node;
import com.sysbuddy.io.NodeParser;

/**
 * Loads schedule from desired configuration file.
 * @author darius
 */
public class BackupScheduleLoader {
	
	/**
	 * The configuration parser.
	 */
	private final NodeParser parser;
	
	/**
	 * Creates a backup schedule loader.
	 * @param parser The configuration parser.
	 */
	public BackupScheduleLoader(NodeParser parser) {
		this.parser = parser;
	}
	
	/**
	 * Loads the backup schedule.
	 */
	public BackupSchedule load() {
		BackupSchedule schedule = new BackupSchedule();
		
		try {
			Node root = parser.parse();
			
			for (Node child : root.getChildren()) {
				if (child.getName().equalsIgnoreCase("task")) {
					Node type = child.getChild("type");
					if (type != null) {
						BackupTaskType candidate = BackupTaskType.get(type.getValue());
						if (candidate != null) {
							try {
								Constructor<?> constructor = candidate.getLoader().getConstructor(Node.class);
								
								BackupTaskLoader loader = (BackupTaskLoader) constructor.newInstance(child);
								BackupTask task = loader.load();
								
								Node formatter = child.getChild("formatter");
								
								if (formatter != null) {
									Node format = formatter.getChild("format");
									Node timeZone = formatter.getChild("timezone");
									Node prefix = formatter.getChild("prefix");
									
									if (format != null)
										task.getFormatter().setTimeFormat(format.getValue());
									
									if (timeZone != null)
										task.getFormatter().setTimeZone(timeZone.getValue());
									
									if (prefix != null)
										task.getFormatter().setPrefix(prefix.getValue());
								}
								
								schedule.getTasks().add(task);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return schedule;
	}
}

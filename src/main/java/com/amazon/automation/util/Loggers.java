package com.amazon.automation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Loggers {
	public int m = 7;
	public java.util.logging.Logger log;
	public FileHandler filehandler;
	public StringBuilder exacttime;

	/**
	 * This method is for create a logger system where all execution logs takes
	 * place
	 * 
	 * @return
	 * @throws Exception
	 */
	public void configureLoggerSystem(String className) throws Exception {
		try {
			Random random = new Random();
			Thread.sleep(1000 * random.nextInt(m));

			String recentCreatedFile=null;
			try {
				recentCreatedFile = ElementUtil.getfolder(System.getProperty("user.dir") + "\\" + "test-output\\");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			log = Logger.getLogger(className);

			try {
				File file = new File(recentCreatedFile + "\\" + "log" + "\\" + className);
				if (!file.exists()) {
					file.mkdirs();
				}
				filehandler = new FileHandler(
						recentCreatedFile + "\\" + "log" + "\\" + className + "\\" + "AmazonAutomation" + ".log");
				log.addHandler(filehandler);

				SimpleFormatter formatter = new SimpleFormatter();
				filehandler.setFormatter(formatter);

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("*************************************The Execution is started********************************************************");
		} catch (Exception e) {
			throw new FileNotFoundException("Failure Alert: File is not created. Need immediate attention on the same");
		}
	}

	public Logger loggingInstance() {
		return log;
	}

	public void setLoggerInfo(String msg) {
		log.info(msg);
	}

	public void setLoggerInfoArray(ArrayList<String> string) {
		for (String eachmsg : string) {
			log.info(eachmsg);
		}
	}

	public void setLoggerError(String err) {
		log.severe(err);
	}

	public void closeTheHandler() {
		log.info("Closing File");
		filehandler.close();
	}

}

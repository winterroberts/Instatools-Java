package com.instatools.web;

import com.instatools.web.errors.ErrorsSet;
import com.instatools.web.errors.NotFoundProcessor;
import com.instatools.web.postcard.CardProcessor;
import com.instatools.web.postcard.PostCardProcessor;
import com.instatools.web.postcard.PostCardSet;

import net.aionstudios.jdc.JDC;
import net.aionstudios.jdc.cron.CronManager;
import net.aionstudios.jdc.database.DatabaseConnector;
import net.aionstudios.jdc.util.DatabaseUtils;

public class Main extends JDC {

	String createSchema = "CREATE DATABASE `instatools` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;";
	String createTable = "CREATE TABLE `instatools`.`postcards` (\r\n" + 
			"  `cardID` varchar(32) NOT NULL,\r\n" + 
			"  `cardImage` varchar(255) NOT NULL,\r\n" + 
			"  `cardCaption` varchar(64) NOT NULL,\r\n" + 
			"  `cardRedirect` varchar(255) NOT NULL,\r\n" + 
			"  PRIMARY KEY (`cardID`),\r\n" + 
			"  UNIQUE KEY `cardID_UNIQUE` (`cardID`)\r\n" + 
			") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;";
	String createTrackingTable = "CREATE TABLE `instatools`.`tracking` (\r\n" + 
			"  `cardID` varchar(32) NOT NULL,\r\n" + 
			"  `referer` varchar(255) NULL,\r\n" + 
			"  `user_agent` text NULL,\r\n" + 
			"  `click_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP\r\n" + 
			") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;";
	
	@Override
	public void initialize() {
		DatabaseUtils.prepareAndExecute(createSchema, false);
		DatabaseUtils.prepareAndExecute(createTable, false);
		DatabaseUtils.prepareAndExecute(createTrackingTable, false);
		
		/*Errors*/
		ErrorsSet es = new ErrorsSet(getProcessorManager());
		NotFoundProcessor esnfp = new NotFoundProcessor(es);
		
		/*Post Car*/
		PostCardSet pcs = new PostCardSet(getProcessorManager());
		PostCardProcessor pcspcp = new PostCardProcessor(pcs);
		CardProcessor pcscp = new CardProcessor(pcs);
	}

}

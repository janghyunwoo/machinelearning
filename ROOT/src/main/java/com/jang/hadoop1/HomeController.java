package com.jang.hadoop1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private Configuration hdConf;

	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.info("성공! {}.", hdConf);
		
		
		try {

			Path filePath = new Path("/userrating.csv");

			FileSystem hdfs = null;

			
			hdfs = FileSystem.get(filePath.toUri(), hdConf);
			
			logger.info("hdfs!! "+hdfs.toString());

			

			if (hdfs.exists(filePath)) {

				logger.info(hdfs.toString());

				BufferedReader reader = new BufferedReader(new InputStreamReader(hdfs.open(filePath), "utf-8"));

				String line = null;

				do {

					line = reader.readLine();

					logger.info(line);

				} while(line != null);

			} else {

				logger.info("없으면 만들어줘야 하지만 그거까진 안할래...");

			}

		} catch (Exception e) {

			logger.error(e.getMessage());

		}

		
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
}

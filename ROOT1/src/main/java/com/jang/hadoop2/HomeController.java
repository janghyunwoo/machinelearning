package com.jang.hadoop2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.mapreduce.JobRunner;
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
	
    //�ϵ� HDFS ��ü
	@Autowired
	private Configuration hdConf;

	// �ʸ��ེ ó���� JobRunner ��ü
	@Autowired
	private JobRunner wordCountJobRunner;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("���� ����!!!!!!!!!!!!!! {}.", hdConf);
		String formattedDate = "";
		try {
			// HDFS�� ��� ����
			Path inputfilePath = new Path("/input/subway.csv");
			// ���� (���⼭ inhdfs�� inputfilePath ��θ� �����ϴ°� �ƴ� HDFS ��ü ���� �ý����� �����ϴ� �Ͱ��� )
			FileSystem inhdfs = FileSystem.get(inputfilePath.toUri(), hdConf);

			// Job-Runner ȣ��
		    wordCountJobRunner.call();

			
			// ��� ���� ���
			Path outputFilePath = new Path("/output/part-r-00000");
			
			// outputFilePath ��� �� ����(part-r-00000)�� �����ϸ� ����
			if (inhdfs.exists(outputFilePath)) {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(inhdfs.open(outputFilePath), "utf-8"));
				String line = null;
				do {
					line = reader.readLine();
					formattedDate += line+"<br>";
					// �α� ���
					logger.info(line);
				} while(line != null);
			} else {
				logger.info("���� ����!");
			}
			
			// �� ������ ���
			model.addAttribute("serverTime", formattedDate );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		logger.info("��", hdConf);
		
		return "home";
	}
	
}

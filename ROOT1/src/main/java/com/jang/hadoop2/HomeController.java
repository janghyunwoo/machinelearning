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
	
    //하둡 HDFS 객체
	@Autowired
	private Configuration hdConf;

	// 맵리듀스 처리할 JobRunner 객체
	@Autowired
	private JobRunner wordCountJobRunner;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("접속 성공!!!!!!!!!!!!!! {}.", hdConf);
		String formattedDate = "";
		try {
			// HDFS상 경로 설정
			Path inputfilePath = new Path("/input/subway.csv");
			// 연결 (여기서 inhdfs는 inputfilePath 경로를 기준하는게 아닌 HDFS 자체 파일 시스템을 기준하는 것같음 )
			FileSystem inhdfs = FileSystem.get(inputfilePath.toUri(), hdConf);

			// Job-Runner 호출
		    wordCountJobRunner.call();

			
			// 출력 파일 경로
			Path outputFilePath = new Path("/output/part-r-00000");
			
			// outputFilePath 경로 상에 파일(part-r-00000)이 존재하면 실행
			if (inhdfs.exists(outputFilePath)) {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(inhdfs.open(outputFilePath), "utf-8"));
				String line = null;
				do {
					line = reader.readLine();
					formattedDate += line+"<br>";
					// 로그 출력
					logger.info(line);
				} while(line != null);
			} else {
				logger.info("파일 없음!");
			}
			
			// 웹 페이지 출력
			model.addAttribute("serverTime", formattedDate );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		logger.info("끝", hdConf);
		
		return "home";
	}
	
}

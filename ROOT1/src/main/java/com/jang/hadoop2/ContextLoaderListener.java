package com.jang.hadoop2;

import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ContextLoaderListener implements ApplicationListener<ContextRefreshedEvent>{

	@Autowired
	private Configuration hdConf;
	
	private static final Logger logger = LoggerFactory.getLogger( ContextLoaderListener.class );


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		
		// HDFS 상 출력 폴더 경로
		Path outputFilePath = new Path("/output/");
		
		FileSystem outhdfs;
		try {
			// HDFS 연결
			outhdfs = FileSystem.get(outputFilePath.toUri(), hdConf);
			
			// outputFilePath 경로상에 폴더가 존제하면 제거
			if(outhdfs.exists(outputFilePath)) {
				outhdfs.delete(outputFilePath, true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info( "최초 실행 메소드 실행 성공!" );
	}


}

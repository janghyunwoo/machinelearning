package com.jang.hadoop2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// linux는 확장자 개념이 없음

// txt : 정리 안된 문장
// csv(comma separated value) : 엑셀파일(x), 글자

// 들어오는 데이터 - 20170101,1호선,서울역,43319,32946
// 나가는 데이터 - 서울역 : 76265
public class SubCMapper extends Mapper<Object, Text, Text, LongWritable> {
	
	private static final Text STATION = new Text();
	private static final LongWritable COUNT = new LongWritable();
	
	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		
		// 20170101,1호선,서울역,43319,32946
		String line = value.toString();
		
		// txt(단어 위치가 중요한 상황은 아닐 때) : StringTokenizer
		// csv(단어 위치가 중요한 상황) : xxx.split("구분자");
		// ,로 분리
		String[] line2 = line.split(",");
		
		long in = Long.parseLong(line2[3]); // 43319
		long out = Long.parseLong(line2[4]); // 32946
		long count = in + out; // 76265
		
		STATION.set(line2[2]); // 서울역
		COUNT.set(count); // 76265
		
		context.write(STATION, COUNT);	
	}
	
}








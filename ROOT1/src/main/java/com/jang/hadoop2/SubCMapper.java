package com.jang.hadoop2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// linux�� Ȯ���� ������ ����

// txt : ���� �ȵ� ����
// csv(comma separated value) : ��������(x), ����

// ������ ������ - 20170101,1ȣ��,���￪,43319,32946
// ������ ������ - ���￪ : 76265
public class SubCMapper extends Mapper<Object, Text, Text, LongWritable> {
	
	private static final Text STATION = new Text();
	private static final LongWritable COUNT = new LongWritable();
	
	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		
		// 20170101,1ȣ��,���￪,43319,32946
		String line = value.toString();
		
		// txt(�ܾ� ��ġ�� �߿��� ��Ȳ�� �ƴ� ��) : StringTokenizer
		// csv(�ܾ� ��ġ�� �߿��� ��Ȳ) : xxx.split("������");
		// ,�� �и�
		String[] line2 = line.split(",");
		
		long in = Long.parseLong(line2[3]); // 43319
		long out = Long.parseLong(line2[4]); // 32946
		long count = in + out; // 76265
		
		STATION.set(line2[2]); // ���￪
		COUNT.set(count); // 76265
		
		context.write(STATION, COUNT);	
	}
	
}








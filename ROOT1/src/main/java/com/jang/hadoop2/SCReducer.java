package com.jang.hadoop2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SCReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
	private static final LongWritable COUNT = new LongWritable();

	@Override
	protected void reduce(Text arg0, Iterable<LongWritable> arg1,
			Reducer<Text, LongWritable, Text, LongWritable>.Context arg2) throws IOException, InterruptedException {

		long sum = 0;

		for (LongWritable lw : arg1) {
			sum += lw.get();
		}
		
		// if(sum > 30000) {
			COUNT.set(sum);
			arg2.write(arg0, COUNT);			
		// }
	}
}









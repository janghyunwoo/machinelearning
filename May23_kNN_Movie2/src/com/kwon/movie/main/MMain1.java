package com.kwon.movie.main;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

//내가 짠것
public class MMain1 {
	private static Vector<Data> trainingData = new Vector<>();
	private static Vector<Matching> returnData = new Vector<>();

	public static void main(String[] args) {
		trainingData.add(new Data(new double[] { 80, 20 }, "액션"));
		trainingData.add(new Data(new double[] { 95, 5 }, "액션"));
		trainingData.add(new Data(new double[] { 10, 90 }, "조폭"));
		trainingData.add(new Data(new double[] { 90, 10 }, "액션"));
		trainingData.add(new Data(new double[] { 5, 95 }, "조폭"));

		// 입력 받기
		Scanner s = new Scanner(System.in);

		System.out.print("격투씬 몇번 : ");
		double fight = s.nextDouble();

		System.out.print("욕씬 몇번 : ");
		double yok = s.nextDouble();

		System.out.print("k : ");
		int k = s.nextInt();

		double[] testData = {fight, yok};
		
		runTrainingKNN(testData,k);
		
	}

	// 트레이닝 데이터 가지고 결과 산출
	@SuppressWarnings("unchecked")
	public static void runTrainingKNN(double[] testData, int k) {
		int length = trainingData.get(0).getData().length;
		double sum = 0;

		for (int i = 0; i < trainingData.size(); i++) {
			sum =0;

			for (int j = 0; j < length; j++) {
				sum += Math.pow(testData[j] - trainingData.get(i).getData()[j], 2);
			}

			returnData.add(new Matching(Math.sqrt(sum), trainingData.get(i).getResult()));
		}

		 Collections.sort(returnData);

		HashMap<String, Integer> resultMap = new HashMap<>();
		for (Matching s : returnData) {
			resultMap.put(s.getResult(), 0);
		}
		
		/*for (int i = 0; i < returnData.size(); i++) {
			System.out.println(returnData.get(i).getDistance()+":"+returnData.get(i).getResult());
		}*/
		
		for (int j = 0; j < k; j++) {
			resultMap.replace(returnData.get(j).getResult(), resultMap.get(returnData.get(j).getResult()) + 1);
		}
			
		System.out.println(resultMap);
		
		String[] keys = resultMap.keySet().toArray(new String[0]);
		
		int max = 0;
		String str ="";
		for (String key : keys) {
			if(max <= resultMap.get(key)) {
				max = resultMap.get(key);
				str = key;
			}
		} 
		System.out.println(str);
	}

}

class Matching implements Comparable<Matching>{
	private double distance;
	private String result;

	public Matching(double distance, String result) {
		this.distance = distance;
		this.result = result;
	}

	public double getDistance() {
		return distance;
	}

	public String getResult() {
		return result;
	}
	
	public int compareTo(Matching m) {

		if (this.distance < m.distance) {
			return -1;
		} else if (this.distance == m.distance) {
			return 0;
		} else {
			return 1;
		}

	}
}


class Data {
	private double[] data;
	private String result;

	public Data(double[] data, String result) {
		// TODO Auto-generated constructor stub
		this.data = data;
		this.result = result;
	}

	public double[] getData() {
		return data;
	}

	public String getResult() {
		return result;
	}

}

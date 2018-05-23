package com.kwon.movie.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

//����� §��
public class kNN {

	// (�׽�Ʈx - ����x)^2 + (�׽�Ʈy - ����y)^2 + (�׽�Ʈz - ����z)^2�� ���� ������
	// �Ÿ�����ؼ� double[]��
	// p : double[] testData, ArrayList<double[]> datas
	private static double[] calculateDistance(double[] testData, ArrayList<double[]> datas) {
		double[] distance = new double[datas.size()];
		double[] data;
		double sum;
		for (int i = 0; i < datas.size(); i++) {
			sum = 0;
			data = datas.get(i);
			for (int j = 0; j < data.length; j++) {
				sum += ((testData[j] - data[j]) * (testData[j] - data[j]));
			}
			distance[i] = Math.sqrt(sum);
		}
		return distance;
	}

	// kNN�����ؼ� ����� String����
	// p : double[] testData, HashMap<String, ArrayList> trainData, int k
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String do_kNN(double[] testData, HashMap<String, ArrayList> trainData, int k) {
		// �н��� �� Ƚ�� ��
		ArrayList<double[]> trainDatas 
			= trainData.get("datas");

		// �н��� �帣 ��
		ArrayList<String> trainLabels 
			= trainData.get("labels");

		// �н��� �͵�� �׽�Ʈ�� �����Ͱ��� �Ÿ����
		double[] distance = calculateDistance(testData, trainDatas);
		
		// �Ÿ����� �迭�� �����͸� �������� �����ؼ� ���ο� �迭
		double[] sortedDistance = Arrays.copyOf(distance, distance.length);
		Arrays.sort(sortedDistance);

		for (int i = 0; i < sortedDistance.length; i++) {
			System.out.println(sortedDistance[i]+":");
		}
		
		// k�� ��ŭ ����, HashMap<String, Integer>���� ��� �����
		// "�׼�" : x��
		// "����" : x��
		HashMap<String, Integer> resultMap = new HashMap<>();
		String[] labelSet = getLabelSet(trainLabels);
		for (String s : labelSet) {
			resultMap.put(s, 0);
		}
		
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < distance.length; j++) {
				if (sortedDistance[i] == distance[j]) {
					resultMap.replace(trainLabels.get(j), resultMap.get(trainLabels.get(j)) + 1);
					break;
				}
			}
		}

		// Ƚ�� ���� ������ ��(�帣)�� ����
		int max = 0;
		String result = null;
		for (String s : labelSet) {
			if (max <= resultMap.get(s)) {
				max = resultMap.get(s);
				result = s;
			}
		}
		System.out.println(resultMap);
		return result;
	}

	// ��(�帣)�� �ߺ����ŵ� String[]��
	private static String[] getLabelSet(ArrayList<String> labels) {
		HashSet<String> labelSet = new HashSet<>(labels);
		String[] labelSetAr = labelSet.toArray(new String[labelSet.size()]);
		return labelSetAr;
	}

	// �׽�Ʈ �� ������ ����ȭ
	// p : double[] testData, HashMap<String, ArrayList> normalizedTrainData
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static double[] normalizeTestData(double[] testData, HashMap<String, ArrayList> normalizedTrainData) {
		double[] normalizedTestData = new double[testData.length];
		ArrayList<Double> minVals = normalizedTrainData.get("minVals");
		ArrayList<Double> ranges = normalizedTrainData.get("ranges");
		for (int i = 0; i < testData.length; i++) {
			normalizedTestData[i] = (testData[i] - minVals.get(i)) / ranges.get(i);
		}
		return normalizedTestData;
	}

	// HashMap<String, ArrayList>����
		// "labels" : ArrayList<String>
			// 0 : "�α⸹��"
			// 1 : "����"
			// 2 : "����"
		// "datas" : ArrayList<double[]>
			// 0 : {40920, 8.33, 0.95} -> {(40920-14488)/60648, (8.33-1.44)/11.71, (0.95-0.13)/1.54}
			// 1 : {14488, 7.15, 1.67} -> ?
			// 2 : {26052, 1.44, 0.81} -> ?
			// ...
		// "minVals" : ArrayList<Double>
			// 0 : 14488
			// 1 : 1.44
			// 2 : 0.13
		// "ranges" : ArrayList<Double>
			// 0 : 60648
			// 1 : 11.71
			// 2 : 1.54
	// �н� ������ ����ȭ
	// p : HashMap<String, ArrayList> trainData
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static HashMap<String, ArrayList> normalizeTrainData(HashMap<String, ArrayList> trainData) {
		HashMap<String, ArrayList> normalizedTrainData = new HashMap<>();
		ArrayList<Double> minVals = new ArrayList<>();
		ArrayList<Double> ranges = new ArrayList<>();
		ArrayList<double[]> datas = trainData.get("datas");
		double min;
		double max;
		for (int i = 0; i < datas.get(0).length; i++) {
			min = datas.get(0)[i];
			max = datas.get(0)[i];
			for (int j = 0; j < datas.size(); j++) {
				if (min > datas.get(j)[i]) {
					min = datas.get(j)[i];
				}
				if (max < datas.get(j)[i]) {
					max = datas.get(j)[i];
				}
			}
			minVals.add(min);
			ranges.add(max - min);
		}

		ArrayList<double[]> normalizedDatas = new ArrayList<>();
		double[] normD;
		for (double[] ds : datas) {
			normD = new double[ds.length];
			for (int i = 0; i < ds.length; i++) {
				normD[i] = (ds[i] - minVals.get(i)) / ranges.get(i);
			}
			normalizedDatas.add(normD);
		}

		normalizedTrainData.put("minVals", minVals);
		normalizedTrainData.put("ranges", ranges);
		normalizedTrainData.put("datas", normalizedDatas);
		normalizedTrainData.put("labels", trainData.get("labels"));

		return normalizedTrainData;
	}

	@SuppressWarnings("rawtypes")
	public static HashMap<String, ArrayList> trainFromFile(String file) {
		HashMap<String, ArrayList> trainData = new HashMap<>();
		ArrayList<double[]> datas = new ArrayList<>();
		ArrayList<String> labels = new ArrayList<>();
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			String[] ar;
			double[] dAr;
			while ((line = br.readLine()) != null) {
				ar = line.split("\t");
				dAr = new double[ar.length - 1];
				for (int i = 0; i < dAr.length; i++) {
					dAr[i] = Double.parseDouble(ar[i]);
				}
				datas.add(dAr);
				labels.add(ar[ar.length - 1]);
			}
			trainData.put("labels", labels);
			trainData.put("datas", datas);
			return trainData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

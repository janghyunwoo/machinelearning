package com.kwon.movie.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//����� §��
public class MMain {
	public static void main(String[] args) {
		
		// ��ǻ�� ����ĥ ������
		HashMap<String, ArrayList> movieData 
			= new HashMap<>();
		
		// ������ �� Ƚ��
		ArrayList<double[]> scene = new ArrayList<>();
		scene.add(new double[] {80, 20});
		scene.add(new double[] {95, 5});
		scene.add(new double[] {10, 90});
		scene.add(new double[] {90, 10});
		scene.add(new double[] {5, 95});
		movieData.put("datas", scene);
		
		// �帣
		ArrayList<String> genre = new ArrayList<>();
		genre.add("�׼�");
		genre.add("�׼�");
		genre.add("����");
		genre.add("�׼�");
		genre.add("����");
		movieData.put("labels", genre);
		
		// �Է� �ޱ�
		Scanner s = new Scanner(System.in);
		
		System.out.print("������ ��� : ");
		double fight = s.nextDouble();
		
		System.out.print("��� ��� : ");
		double yok = s.nextDouble();
		
		System.out.print("k : ");
		int k = s.nextInt();
		
		double[] testData = {fight, yok};
		
		String result = kNN.do_kNN(testData, movieData, k);
		System.out.println(result);
		
		s.close();
		
		
		
		
		
		
		
		
		
		
	}
}











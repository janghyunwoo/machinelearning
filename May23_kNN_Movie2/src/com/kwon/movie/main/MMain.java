package com.kwon.movie.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//°­»ç´Ô Â§°Å
public class MMain {
	public static void main(String[] args) {
		
		// ÄÄÇ»ÅÍ °¡¸£Ä¥ µ¥ÀÌÅÍ
		HashMap<String, ArrayList> movieData 
			= new HashMap<>();
		
		// ³ª¿À´Â ¾À È½¼ö
		ArrayList<double[]> scene = new ArrayList<>();
		scene.add(new double[] {80, 20});
		scene.add(new double[] {95, 5});
		scene.add(new double[] {10, 90});
		scene.add(new double[] {90, 10});
		scene.add(new double[] {5, 95});
		movieData.put("datas", scene);
		
		// Àå¸£
		ArrayList<String> genre = new ArrayList<>();
		genre.add("¾×¼Ç");
		genre.add("¾×¼Ç");
		genre.add("Á¶Æø");
		genre.add("¾×¼Ç");
		genre.add("Á¶Æø");
		movieData.put("labels", genre);
		
		// ÀÔ·Â ¹Þ±â
		Scanner s = new Scanner(System.in);
		
		System.out.print("°ÝÅõ¾À ¸î¹ø : ");
		double fight = s.nextDouble();
		
		System.out.print("¿å¾À ¸î¹ø : ");
		double yok = s.nextDouble();
		
		System.out.print("k : ");
		int k = s.nextInt();
		
		double[] testData = {fight, yok};
		
		String result = kNN.do_kNN(testData, movieData, k);
		System.out.println(result);
		
		s.close();
		
		
		
		
		
		
		
		
		
		
	}
}











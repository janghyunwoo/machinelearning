package com.kwon.movie.main;

import java.util.Comparator;
import java.util.Vector;
import java.util.Collections;

//��ü ���� �׽�Ʈ �ڵ�    
public class VectorSort {
	public static void main(String[] args) {
		Vector m = new Vector();
		Member m1 = new Member();
		m1.name = "ȫ�浿";
		m1.age = 30;
		m.add(m1);
		m1 = new Member();
		m1.name = "�蹮��";
		m1.age = 20;
		m.add(m1);
		m1 = new Member();
		m1.name = "����";
		m1.age = 24;
		m.add(m1);
		Collections.sort(m);
		for (int i = 0; i < m.size(); i++) {
			Member mm = (Member) m.get(i);
			System.out.println("�̸� = " + mm.name + " ���� = " + mm.age);
		}
	}
}

class Member implements Comparable<Member>{
	public String name;
	public int age;
	
	public int compareTo(Member man) {



		if (this.age < man.age) {

			return -1;

		} else if (this.age == man.age) {

			return 0;

		} else {

			return 1;

		}

	}



	
}


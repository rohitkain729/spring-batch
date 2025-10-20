package com.app.core;

public class Test {

	public void m1() {
		System.out.println("-----------m1------------");
	}

	public static void main(String[] args) {

		Test t1 = new Test();

		t1.m1();

		System.out.println("--------------------t1-----------------" + t1.getClass());

		Test t2 = new Test();

		t2.m1();
		System.out.println("------------------t2-------------------" + t2.getClass());

		Test t3 = new Test() {

			{
				m1();
			}
		};

		System.out.println("---t3 obj class name-------" + t3.getClass());

	}
}

package org.xiem.com.junit;

public class App {// 由ECLIPSE生成测试类: JUnit Test Case

	// TESTNG自动化测试(JUNIT单元测试)

	public static void main(String[] args) {
        System.out.println( "Hello World!" );
    }

	private static int result; // 静态变量，用于存储运行结果

	public void add(int n) {
        result = result + n;
    }

	public void substract(int n) {
        result = result - 1;  //Bug: 正确的应该是 result =result-n
    }

	public void multiply(int n) {
    } // 此方法尚未写好

	public void divide(int n) {
        result = result / n;
    }

	public void square(int n) {
        result = n * n;
    }

	public void squareRoot(int n) {
		for (;;)
			; // Bug : 死循环
    }

	public void clear() { // 将结果清零
        result = 0;
    }

	public int getResult() {
        return result;
    }
}
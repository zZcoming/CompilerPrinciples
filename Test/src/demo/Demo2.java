package demo;

import util.DFSA;

/**
 * Created by zZ on 2016-4-27.
 */
public class Demo2 {
	public static void main(String[] args) {
		DFSA dfsa = new DFSA();
		dfsa.input();
/*
示例输入：
0 y 1 n 2 n 3 n #
a b #
1 3 0 2 3 1 2 0
 */
//		dfsa.print();
		String str = "abaababb";  // 输入的字符串
		System.out.println(dfsa.isAccept(str));
	}
}

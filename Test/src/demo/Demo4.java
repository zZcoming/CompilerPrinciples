package demo;

import util.Grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zZ on 2016-5-3.
 */
public class Demo4 {
	public static void main(String[] args) {
		Grammar g = new Grammar();
		g.input();
/*
Test Input：
====1.===
S
ABCD
A
aA|#
B
bB|#
C
c
D
d
#
====2.===
A
BC|AaB
B
bc|#
C
c|#
#
 */
		g.print();
		for (char left : g.getData().keySet()) {
			System.out.print("first(" + left + ") = ");
			char sep = '{';
			for(char c : g.first(left)) {
				System.out.print("" + sep + c);
				sep = ',';
			}
			System.out.println("}");
		}

	}
}

package demo;

import util.Grammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zZ on 2016-4-26.
 */
public class Demo1 {

	public static void main(String[] args) {
		Grammar grammar = new Grammar();
		System.out.println("请输入文法：");
		grammar.input();
/*
示例输入：
A
AB|B|a
B
B|b|C|A|#
C
BC|AC|c
#
	*/
		List<Character> list = listCanToNull(grammar);
		if(list.size() >= 1) {
			System.out.print("可导空的非终结符有：");
			for(Character c : list) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
		else {
			System.out.println("不存在可导空的非终结符！");
		}
	}

	/**
	 * 对于一给定文法，编程求其能导空的非终结符的集合
	 * @param grammar
	 * @return 能导空的非终结符的集合
	 */
	public static List<Character> listCanToNull(Grammar grammar) {
		List<Character> list = new ArrayList<>();
		// 对于每一个左部（非终结符），判断是否能导空，能则加入list
		for(Character c : grammar.getData().keySet()) {
			if (grammar.canToNull(c)) {
				list.add(c);
			}
		}
		return list;
	}
}

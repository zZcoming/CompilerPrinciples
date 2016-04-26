package util;

import java.util.*;

/**
 * Created by zZ on 2016-4-25.
 */

/**
 * 该类对应“文法”
 * @property data : key对应左部  value对应右部
 * @property NULL : 对应“空”
 */
public class Grammar {
	private Map<Character, String[]> data;
	public static final String NULL = "#";

	public Grammar() {
		data = new HashMap<>();
	}

	/**
	 * 本类内部功能测试
	 * @param args
	 */
	public static void main(String[] args) {
		Grammar g = new Grammar();
		g.input();
		g.print();
		System.out.println(g.canToNull('A'));
	}

	// 输入文法
	public void input() {
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
		Scanner scan = new Scanner(System.in);
		char key = scan.next().charAt(0);
		while(key != NULL.charAt(0)) {
			String value = scan.next();
			String[] values = value.split("\\|");
			data.put(key, values);
			key = scan.next().charAt(0);
		}
	}

	/**
	 * 判断该终结符是否可以导空
	 * key：左部
	 * @param key
	 * @return
	 */
	public boolean canToNull(Character key) {
		return canToNull(key, new ArrayList<Character>());
	}

	public boolean canToNull(Character key, List<Character> list) {
		if(list.contains(key)) {
			return false;
		}
		list.add(key);
		// 获取右部
		String[] values = data.get(key);
		if(values == null) {
			System.out.println("未找到左部:" + key);
			return false;
		}
		// 分析右部
		for(String value : values) {
			// 右部为空
			if(NULL.equals(value)) {
				return true;
			}
			else {
				// 该右部是否可以导空
				boolean can = true;
				// 逐个分析右部中的字符
				for(char c : value.toCharArray()) {
					// 存在非终结符，不符合
					if (isTerminal(c)) {
						can = false;
						break;
					}
					else {
						// 递归，不符合
						if(key.equals(c)) {
							can = false;
							break;
						}
						// 该非终结符是否可以导空，是：继续 - 不是：退出
						else if(!canToNull(c, list)) {
							can = false;
							break;
						}
					}
				}
				// 该右部可以导空，返回true，否则继续检查下一个右部
				if(can == true) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否为终结符
	 * @param c
	 * @return
	 */
	private boolean isTerminal(char c) {
		Set<Character> set = data.keySet();
		for(Character key : set) {
			if(key.equals(c)) {
				return false;
			}
		}
		return true;
	}

	// 输出文法
	public void print() {
		Set<Character> keys = data.keySet();
		for(Character key : keys) {
			System.out.print(key + " -> ");
			String [] values = data.get(key);
			String sep = "";
			for(String value : values) {
				System.out.print(sep + value);
				sep = "|";
			}
			System.out.println();
		}
	}

	/**
	 * 加入一个产生式，key为左部，values为右部
	 * @param key
	 * @param values
	 */
	public void put(Character key, String[] values) {
		data.put(key, values);
	}

	public Map<Character, String[]> getData() {
		return data;
	}

	public void setData(Map<Character, String[]> data) {
		this.data = data;
	}
}

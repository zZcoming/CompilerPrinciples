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
	private Map<Character, Set<Character>> firstSet;
	public static final String NULL = "#";

	public Grammar() {
		data = new HashMap<>();
		firstSet = new HashMap<>();
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
	 * 求某个非终结符的first集
	 * @param c : 非终结符
	 * @return
	 */
	public Set<Character> first(Character c) {
		return first(c, new ArrayList<Character>());
	}

	/**
	 *
	 * @param c
	 * @param solving : 正在求first集的非终结符集
	 * @return
	 */
	private Set<Character> first(Character c, List<Character> solving) {
		Set<Character> result = null;
		// 如果是终结符，直接返回
		if (isTerminal(c)) {
			result = new HashSet<>();
			result.add(c);
			return result;
		}
		// 如果有结果，则直接返回
		result = firstSet.get(c);
		if (result != null && result.size() > 0) {
			return result;
		}
		// 是否正在求解；是：（返回空集）
		if(solving.contains(c)) {
			return new HashSet<>();
		}
		// 将自己加入求解队列
		solving.add(c);
		// 结果集
		result = new HashSet<>();
		// 获取右部
		String[] rights = data.get(c);
		// 逐个分析右部
		for (String right : rights) {
			char f = right.charAt(0);
			if (NULL.charAt(0) != f) {
				if(isTerminal(f)) {
					result.add(f);
				}
				else {
					result.addAll(first(f, solving));
					for (int i = 1; i < right.length() && !isTerminal(right.charAt(i-1)) && canToNull(right.charAt(i-1)); i++) {
						result.addAll(first(right.charAt(i), solving));
					}
				}
			}
		}
		// 是否加入 空
		if (canToNull(c)) {
			result.add(NULL.charAt(0));
		}
		else {
			result.remove(NULL.charAt(0));
		}
		// 将已经求解完的非终结符移除list
		solving.remove(c);
		// 将first集保存
		firstSet.put(c, result);
		return result;
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

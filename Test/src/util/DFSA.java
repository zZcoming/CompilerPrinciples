package util;

import java.util.Scanner;

/**
 * DFSA:Deterministic Finite State Automata（确定有穷自动机）
 * 以状态转换表的形式存储
 * Created by zZ on 2016-4-27.
 */
public class DFSA {
	private char[] statuses;  // 状态集
	private char[] endStatuses;  // 终止状态集
	private char[] chars;  // 有穷输入字母表
	private char[][] statusTable;  // 状态转换表
	public static final char NULL = '#';  // 空

	public void input() {
/*
示例输入：
0 y 1 n 2 n 3 n #
a b #
1 3 0 2 3 1 2 0
 */
		// 输入状态集
		System.out.print("input status and isEndStatus(Y/N): ");
		Scanner scan = new Scanner(System.in);
		String statuses = "";
		String endStatuses = "";
		char status = scan.next().charAt(0);
		while (status != NULL) {
			statuses += status;
			char isEndStatus = scan.next().charAt(0);
			if ((isEndStatus | 32) == 'y') {
				endStatuses += status;
			}
			status = scan.next().charAt(0);
		}

		// 输入字符集
		System.out.print("input characters: ");
		String chars = "";
		char c = scan.next().charAt(0);
		while (c != NULL) {
			chars += c;
			c = scan.next().charAt(0);
		}

		// 输入状态转换表
		System.out.println("input table: ");
		String tables = scan.next();
		while (tables.length() < (statuses.length() * chars.length())) {
			tables += scan.next();
		}

		// 最后一起保存数据
		this.statuses = statuses.toCharArray();
		this.endStatuses = endStatuses.toCharArray();
		this.chars = chars.toCharArray();
		statusTable = new char[statuses.length()][chars.length()];
		for (int i = 0; i < statuses.length(); i++) {
			for (int j = 0; j < chars.length(); j++) {
				statusTable[i][j] = tables.charAt((i * chars.length()) + j);
			}
		}
	}

	/**
	 * output information to console
	 */
	public void print() {
		System.out.print("状态集：");
		for (char c : statuses) {
			System.out.print(c + " ");
		}
		System.out.println();

		System.out.print("终止状态集：");
		for (char c : endStatuses) {
			System.out.print(c + " ");
		}
		System.out.println();

		System.out.print("字符集");
		for (char c : chars) {
			System.out.print(c + " ");
		}
		System.out.println();

		System.out.println("状态转换表");
		for (int i = 0; i < statuses.length; i++) {
			for (int j = 0; j < chars.length; j++) {
				System.out.print(statusTable[i][j] + " ");
			}
			System.out.println();
		}
	}

	/**
	 * 判断输入字符集str能否被有穷自动机接收
	 *
	 * @param str
	 * @return
	 */
	public boolean isAccept(String str) {
		char status = getStartStatus();  // 当前状态
		for (char c : str.toCharArray()) {
			status = changeStatus(status, c);
		}
		return isEndStatus(status);
	}

	/**
	 * 根据当前状态（status）和读入字符（c）来确定下一状态
	 *
	 * @param status
	 * @param c
	 * @return 改变后的状态
	 */
	private char changeStatus(char status, char c) {
		for (int i = 0; i < statuses.length; i++) {
			if (status == statuses[i]) {
				for (int j = 0; j < chars.length; j++) {
					if (c == chars[j]) {
						char s = statusTable[i][j];
						if (s != NULL) {
							return s;
						}
					}

				}
			}

		}
		System.out.println("Error");
		return NULL;
	}

	/**
	 * 判断是否为终止状态
	 *
	 * @param status
	 * @return
	 */
	private boolean isEndStatus(char status) {
		for (char s : endStatuses) {
			if (s == status) {
				return true;
			}
		}
		return false;
	}

	public char[] getStatuses() {
		return statuses;
	}

	public void setStatuses(char[] statuses) {
		this.statuses = statuses;
	}

	public char getStartStatus() {
		return this.statuses[0];
	}

	public char[] getEndStatuses() {
		return endStatuses;
	}

	public void setEndStatuses(char[] endStatuses) {
		this.endStatuses = endStatuses;
	}

	public char[] getChars() {
		return chars;
	}

	public void setChars(char[] chars) {
		this.chars = chars;
	}

	public char[][] getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(char[][] statusTable) {
		this.statusTable = statusTable;
	}

}
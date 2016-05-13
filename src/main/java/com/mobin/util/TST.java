package com.mobin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class TST<Value> {
	private Node root;

	private class Node {
		char c;
		Node left, mid, right;
		Value val;
	}

	public Value get(String key) {
		Node x = get(root, key, 0);
		if (x == null) return null;
		return (Value) x.val;
	}

	private Node get(Node x, String key, int d) {
		if (x == null) return null;
		char c = key.charAt(d);

		if (c < x.c) {
			return get(x.left, key, d);
		} else if (c > x.c) {
			return get(x.right, key, d);
		} else if (d < key.length() - 1) {
			return get(x.mid, key, d+1);
		} else {
			return x;
		}
	}

	public void put(String key, Value val) {
		root = put(root, key, val, 0);
	}

	private Node put(Node x, String key, Value val, int d) {
		char c = key.charAt(d);
		if (x == null) {
			x = new Node();
			x.c = c;
		}

		if (c < x.c) {
			x.left = put(x.left, key, val, d);
		} else if (c > x.c) {
			x.right = put(x.right, key, val, d);
		} else if (d < key.length()  - 1) {
			x.mid = put(x.mid, key, val, d+1);
		} else {
			x.val = val;
		}

		return x;
	}
	/**
	 *  北京市奥林匹克公园北京5A2012
		恭王府景区北京5A2012
		北京市明十三陵景区北京5A2011
		八达岭长城北京5A2007
		颐和园北京5A2007
		天坛公园北京5A2007
		故宫博物院
	 * @param args
	 * @throws IOException 
	 */
	/*public static void main(String[] args) throws IOException {
		
		TST<String> tst=new TST<String>();
		InputStream inputStream=TST.class.getResourceAsStream("/file.properties");
		Properties properties=new Properties();
		properties.load(inputStream);
		Set<String> stringPropertyNames = properties.stringPropertyNames();
		for (String string : stringPropertyNames) {
			tst.put(string,"1");
		}
		
		System.out.println(tst.get("changlulvyouxiuboyuan"));
		System.out.println(tst.get("zhon"));

	}*/
}

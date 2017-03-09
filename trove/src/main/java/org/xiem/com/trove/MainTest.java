package org.xiem.com.trove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.list.TDoubleList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TObjectLongMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TObjectLongHashMap;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TObjectLongProcedure;
import gnu.trove.procedure.TObjectProcedure;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

public class MainTest {// http://trove.starlight-systems.com/overview

	static class QualificationFile {
		public QualificationFile(String file_name, String file_url) {
			super();
			this.file_name = file_name;
			this.file_url = file_url;
		}

		public String file_name;
		public String file_url;
	}

	public static void main(String[] args) {

		TLongObjectMap<QualificationFile> tests = new TLongObjectHashMap<>();
		tests.put(1, new QualificationFile("aaa", "aaaaaaaaaaaaaaaaa"));
		tests.put(2, new QualificationFile("bbb", "bbbbbbbbbbbbbbbbb"));
		tests.put(3, new QualificationFile("ccc", "ccccccccccccccccc"));
		tests.put(4, new QualificationFile("ddd", "ddddddddddddddddd"));
		tests.put(5, new QualificationFile("eee", "eeeeeeeeeeeeeeeee"));
		tests.forEachValue(new TObjectProcedure<QualificationFile>() {

			@Override
			public boolean execute(QualificationFile object) {

				return true;
			}
		});

		TLongObjectIterator<QualificationFile> iterator = tests.iterator();
		while (iterator.hasNext()) {
			iterator.advance();
			System.out.println("锟斤拷值锟皆硷拷为: " + iterator.key());
			System.out.println("锟斤拷值锟斤拷值为: " + iterator.value().file_name);
			System.out.println("锟斤拷值锟斤拷值为: " + iterator.value().file_url);
		}
	}
	//
	// import gnu.trove.list.TIntList;
	// import gnu.trove.list.TLongList;
	// list.binarySearch(val);
	// list.binarySearch(val);
	//
	// ActionsProcedure proc = new ActionsProcedure();
	// actions.forEach(proc);//会用PROC逐个处理ACTIONS中的值
	// Boolean ret = proc.getReturn();//这是最终的结果
	// if (ret != null) {
	// return ret ? null : this;
	// }
	// private static class ActionsProcedure implements TIntProcedure {
	// private Boolean ret = null;//最终的结果
	// public Boolean getReturn() {
	// return ret;
	// }
	// @Override
	// public boolean execute(int value) {
	// if (INCLUDE_BEACON_ID == value) {
	// ret = true;
	// return false;
	// } else if (EXCLUDE_BEACON_ID == value) {
	// ret = false;
	// return false;
	// } else {
	// return true;
	// }
	// }
	// }

	// TROVE是轻量级实现集合API的第三方开源项目:java.util.Collections
	// TROVE相比JDK原生的集合类有三个优势:1、更高的性能;2、更底的内存消耗;3、除了实现原生集合API并额外提供更强大的功能;
	// 集合是JAVA编程最常用的API之一,把项目的集合对象改用TROVE替换就能获得性能提升和内存的节省.
	// 这是对一个项目底成本的优化方案.下面两段代码分别测试TROVE和原生的集合的性能
	// http://www.thinksaas.cn/topics/0/102/102057.html
	// 1.TROVE
	public void TestIterator() {

		TIntObjectMap<Game> map = new TIntObjectHashMap<Game>();

		for (int i = 0; i < 1000; i++) {

			Game g = new Game();

			g.setName("���ջ���" + i);
			g.setSize(15000 + (i << 3));
			g.setCtDate(new Date());

			map.put(i, g);
		}

		int size = map.size();

		TIntObjectIterator<Game> it = map.iterator();

		for (int i = size; i > 0; i--) {

			it.advance();

			System.out.println(it.key() + "=" + it.value());

			if (it.key() == 3) {

				Game g = new Game();

				g.setName("���ջ���13");
				g.setSize(15000 + (i << 3));
				g.setCtDate(new Date());

				it.setValue(g);
			}
		}

		System.out.println("=======================================");

		System.out.println(map.get(3));
	}

	// 2.HASHMAP
	public void testIterator() {

		Map<Integer, Game> map = new HashMap<Integer, Game>();

		for (int i = 0; i < 1000; i++) {

			Game g = new Game();

			g.setName("���ջ���" + i);
			g.setSize(15000 + (i << 3));
			g.setCtDate(new Date());

			map.put(i, g);
		}

		Set<?> set = map.entrySet();

		for (Iterator<?> it = set.iterator(); it.hasNext();) {

			@SuppressWarnings("unchecked")
			Entry<?, Game> e = (Entry<?, Game>) it.next();

			System.out.println(e.getKey() + "=" + e.getValue());

			if (((Integer) e.getKey()).intValue() == 3) {
				
				Game g = new Game();

				g.setName("���ջ���13");
				g.setSize(18000);
				g.setCtDate(new Date());
				
				e.setValue(g);
			}
		}

		System.out.println("=======================================");
		System.out.println(map.get(3));
	}
	// *************************************************************************************
	// TIntSet-->TIntProcedure

	private static final int INCLUDE_BEACON_ID = 90; // һ��BEACON
	private static final int EXCLUDE_BEACON_ID = 91; // һ��BEACON

	private static class ActionsProcedure implements TIntProcedure {

		private Boolean ret = null;

		public Boolean getReturn() {
			return ret;
		}

		@Override
		public boolean execute(int value) {// �Լ����е�ÿ��Ԫ��ִ��һ�θú���
			if (INCLUDE_BEACON_ID == value) {
				ret = true;
				return false;
			} else if (EXCLUDE_BEACON_ID == value) {
				ret = false;
				return false;// ��ʾ���ټ���ִ����ȥ��
			} else {
				return true;// ��ʾ����ִ��
			}
		}
	}

	private static void TIntProcedureTest() {

		final TIntSet actions = new TIntHashSet();// BEACON����

		actions.add(92);
		actions.add(93);
		actions.add(9);
		actions.add(192);
		actions.add(929);
		actions.add(942);
		actions.add(7892);
		actions.add(992);
		actions.add(EXCLUDE_BEACON_ID);
		actions.add(8239);

		ActionsProcedure proc = new ActionsProcedure();
		actions.forEach(proc);// ʹ��PROC������ÿһ��ACTIONS

		Boolean ret = proc.getReturn();

		System.out.println("����: " + ret);
	}
	// *************************************************************************************

	@SuppressWarnings("unused")
	public static void main001(String[] args) {

		TObjectLongMap<String> resultMap = new TObjectLongHashMap<>(3, 1, 3);
		resultMap.forEachEntry(new TObjectLongProcedure<String>() {
			@Override
			public boolean execute(String a, long b) {
				System.out.println("键值对为: " + a + "------->" + b);
				return false;
			}
		});

		TIntIntHashMap result = new TIntIntHashMap(3, 1, 3, 6);

		String test = "ANDROID";
		String[] osInfo = test.split(" ", 2);
		System.out.println(osInfo[0] != null ? osInfo[0] : "");
		System.out.println(osInfo[1] != null ? osInfo[1] : "");

		List<Integer> exchanges = new ArrayList<Integer>();

		exchanges.add(12);
		exchanges.add(34);
		exchanges.add(56);
		exchanges.add(67);
		exchanges.add(89);
		exchanges.add(675);
		exchanges.add(423);

		System.out.println(exchanges);

		Collections.shuffle(exchanges, ThreadLocalRandom.current());

		System.out.println(exchanges);

		System.out.println(Integer.SIZE);
		System.out.println(Long.SIZE);
		System.out.println(Short.SIZE);
		System.out.println(Double.SIZE);

		BinarySearchTest();

	}

	private static void BinarySearchTest() {

		TDoubleList rightScores = new TDoubleArrayList();
		rightScores.add(1300.013);
		rightScores.add(1050.013);
		rightScores.add(100.013);
		rightScores.add(13300.013);
		rightScores.add(1700.013);
		rightScores.add(10.013);
		rightScores.add(500.013);
		rightScores.add(70.013);
		rightScores.sort();
		System.out.println(rightScores.toString());
		System.out.println(rightScores.binarySearch(99.0));

		TIntProcedureTest();
	}

}

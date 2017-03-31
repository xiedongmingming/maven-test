package org.xiem.com.guava;

import java.util.List;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;

public class GuavaCollectTest {// http://ifeve.com/google-guava-collectionhelpersexplained/

	public static void main(String[] args) {

		String str1 = "yyyyyyyyyyyyyyyyyyyxyyyyyyyyyyyyyyyyyyy";
		str1 = str1.replace("x", "z");
		System.out.println(str1);

		ImmutableList.Builder<String> cat = ImmutableList.builder();
		for (String str : new String[] { "ssss", "ddddd" }) {
			cat.add(str);
		}
		System.out.println(cat.build());
	}

	public static class ForwardingListImp extends ForwardingList<String> {

		@Override
		public void add(int index, String element) {
			super.add(index, element);
		}

		@Override
		public int size() {
			return super.size();
		}

		@Override
		protected List<String> delegate() {

			return null;
		}

	}
}

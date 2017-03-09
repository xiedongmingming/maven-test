package org.xiem.com.guava;

import java.util.List;

import com.google.common.collect.ForwardingList;

public class GuavaCollectTest {// http://ifeve.com/google-guava-collectionhelpersexplained/

	public static void main(String[] args) {

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

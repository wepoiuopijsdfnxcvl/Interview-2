package cn.collabtech.linkedlist;

import java.util.LinkedList;

public class TestLinkList {
	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<>();
		list.add("A");
		list.addFirst("B");
		list.addLast("C");
		System.out.println(list); 
	}
}

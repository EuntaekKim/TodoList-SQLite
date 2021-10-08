package com.todo.service;

import java.util.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedReader;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		int count;
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		
		System.out.print("[�׸� �߰�]\n"
				+ "���� > ");
		
		title = sc1.next();
		if (list.isDuplicate(title)) {
			System.out.print("������ �ߺ� �˴ϴ�!");
			return;
		}	
		System.out.print("ī�װ� > ");
		category = sc1.next().trim();
		
		System.out.print("���� > ");
		desc = sc2.nextLine().trim();
		
		System.out.print("�������� > ");
		due_date = sc1.next().trim();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if (list.addItem(t) > 0) {
			System.out.print("�߰��Ǿ����ϴ�.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
				
		System.out.print("[�׸����]\n"	+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int index = sc.nextInt();
		if(l.deleteItem(index) > 0) {
			System.out.println("�����Ǿ����ϴ�.");
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);

		System.out.print("[�׸����]\n"
				+ "������ �׸��� ��ȣ�� �Է��Ͻʽÿ� > "
			);
		int count = sc1.nextInt();
		
		System.out.print("�� ���� > ");
		String new_title = sc1.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ� �˴ϴ�!");
			return;
		}
		System.out.print("�� ī�װ� > ");
		String new_category = sc1.next().trim();
		
		System.out.print("�� ���� > ");
		String new_description = sc2.nextLine().trim();
		
		System.out.print("�� �������� > ");
		String new_due_date = sc1.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setId(count);
		if(l.updateItem(t) > 0) {
			System.out.println("�����Ǿ����ϴ�.");
		}
		

	}

	public static void listAll(TodoList l) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getList(keyword) ) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("\n�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void find_cate(TodoList l, String category) {
		int count = 0;
		for(TodoItem item : l.getListCategory(category) ) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("\n�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories() ) {
			System.out.print(item + " ");
			count++;
		}
		System.out.println("\n�� " + count + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter(filename);
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String oneline;
			while ((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String dateString = st.nextToken();
				TodoItem t = new TodoItem(title, desc, dateString, category, due_date);
				l.addItem(t);
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("todolist.txt ������ �����ϴ�.");
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

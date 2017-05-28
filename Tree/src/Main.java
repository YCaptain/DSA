import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dsa.iface.IBinarySearchTree;
import dsa.iface.ITree;
import dsa.impl.BinarySearchTree;
import dsa.impl.TreePrinter;

public class Main {
	public static void main(String[] args) {
		/*
		 * Write your code here to:
		 * 1. Create a splay tree
		 * 2. Read operations from splay_operations.txt and perform these on the splay tree.
		 * 3. Create a binary search tree
		 * 4. Read operations from bst_operations.txt and perform these on the binary search tree.
		 * 5. Compare the trees to see if they are equal.
		 */
		SplayTree<Integer> st = new SplayTree<>();
		readOperation("data/splay_operations.txt", st);
		
	}
	
	public static void readOperation(String file, BinarySearchTree<Integer> tree) {
		String str;
		String operation;
		int num;
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			while(br.ready()) {
				str = br.readLine();
				operation = str.substring(0, 1);
				num = Integer.parseInt(str.substring(1));
				if(operation.equals("I")) {
					tree.insert(num);
				} else if(operation.equals("R")) {
					tree.remove(num);
				} else if(operation.equals("C")) {
					tree.contains(num);
				}
				TreePrinter.printTree(tree);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

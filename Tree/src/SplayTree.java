

import static org.junit.Assert.assertEquals;

import dsa.iface.INode;
import dsa.impl.BinarySearchTree;
import dsa.impl.ProperLinkedBinaryTree;
import dsa.impl.TreePrinter;

public class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T> {

	/**
	 * Insert a value into the tree.
	 * 
	 * The node that is expanded to add the new value should be splayed.
	 */
	
	public void insert(T value) {
		super.insert(value);
		BTNode find = (BTNode) super.find(root, value);
		splay(find);
	}

	/**
	 * Check if a value is found in the tree.
	 * 
	 * If it is found, the node that contains it should be splayed.
	 * If it is not found, the parent of the external node that the search reached should be splayed.
	 */
	public boolean contains(T value) {
		BTNode find = (BTNode) super.find(root, value);
		if(find.element == value) {
			return true;
		}
		return false;
		// <-- this is just a placeholder: remove it from your final code.
	}

	/**
	 * Remove a value from the tree.
	 * 
	 * If it is removed, the parent of the node that was actually removed should be splayed.
	 */
	public void remove(T value) {
		if(contains(value)) {
			BTNode find = (BTNode) super.find(root, value);	
			if( isExternal(find.left) || isExternal(find.right) ) {
				if(isRoot(find)) {
					super.remove(find);
				} else {
					BTNode parent = find.parent;
					super.remove(find);
					splay(parent);
				}
			} else if( isInternal(find.left) && isInternal(find.right) ) {
				BTNode next = (BTNode) parent(find(find.right, value));
				if(isRoot(find)) {
					if(next.parent == find) {
						super.replace(find, next.element);
						super.remove(next);
					} else {
						BTNode parent = next.parent;
						super.remove(find);
						splay(parent);
					}
				} else {
					if(next.parent == find) {
						BTNode parent = find.parent;
						super.remove(find);
						splay(parent);
					} else {
						BTNode parent = next.parent;
						super.remove(find);
						splay(parent);
					}
				}
			}
		}
	}

	/**
	 * Splay a node.
	 * @param n The node to be splayed.
	 */
	private void splay(INode<T> n) {
		while(!isRoot(n)) {
			restruct(n);
			if(((BTNode)n).parent == null)
				root = (BTNode)n;
		}
	}
	
	private void restruct(INode<T> n) {
		BTNode x = (BTNode) n;
		
		if(isRoot(x.parent)) {
			if(x.equals(x.parent.left)) {
				System.out.println("zig");
				zig(x);
			} else if(x.equals(x.parent.right)) {
				System.out.println("zag");
				zag(x);
			}
			x.parent = null;
		} else {
			if(x.parent.equals(x.parent.parent.left)) {
				if(x.equals(x.parent.left)) {
					System.out.println("zigzig");
					zigZig(x);
				} else if(x.equals(x.parent.right)) {
					System.out.println("zagzig");
					zagZig(x);
				}
			} else if(x.parent.equals(x.parent.parent.right)) {
				if(x.equals(x.parent.left)) {
					System.out.println("zigzag");
					zigZag(x);
				} else if(x.equals(x.parent.right)) {
					System.out.println("zagzag");
					zagZag(x);
				}
			}
		}
	}

	private void zig(BTNode x) {
	/*	BTNode y = x.parent;
		BTNode xRight = x.right;
		
		
		y.left = xRight;
		x.right = y;
		xRight.parent = y;
		x.parent = y.parent;
		y.parent = x;  */
		
		x.right.parent = x.parent;
		x.parent.left = x.right;
		x.parent = x.parent.parent;
		x.right = x.right.parent;
		x.right.parent = x;
		if(x.parent != null)
			x.parent.left = x;
	}
	
	private void zag(BTNode x) {
	/*	BTNode y = x.parent;
		BTNode xLeft = x.left;

		x.left = y;
		y.right = xLeft;
		x.parent = y.parent;
		y.parent = x;
		xLeft.parent = y;
	*/

		x.left.parent = x.parent;
		x.parent.right = x.left;
		x.parent = x.parent.parent;
		x.left = x.left.parent;
		x.left.parent = x;
		if(x.parent != null) {
			x.parent.right = x;
		}
	}
	
	private void zigZig(BTNode x) {
		zig(x.parent);
		zig(x);
	}
	
	private void zagZag(BTNode x) {
		zag(x.parent);
		zag(x);
	}
	
	private void zigZag(BTNode x) {
		zig(x);
		zag(x);
	}
	
	private void zagZig(BTNode x) {
		zag(x);
		zig(x);
	}

	
}

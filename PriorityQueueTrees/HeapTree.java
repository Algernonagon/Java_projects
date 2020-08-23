/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Nathan Yang
*/

import java.util.*;

public class HeapTree<E> extends LinkedBinaryTree<E> {

	public HeapTree(){}

	public void swap(Position<E> p, Position<E> q) {
		Node<E> temp = validate(p);
		set(p, set(q, temp.getElement()));
	}

	public Position<E> replaceRoot(Position<E> p) {
		if(size() > 1) {
			swap(root, p);
			remove(p);
		}
		return root;
	}

	public Position<E> getRoot() {
		return root;
	}

	private Position<E> addLeaf(Position<E> p, E e) {
		String directions = Integer.toBinaryString(size()+1);
		Position<E> q = p;
		int n = directions.length()-1;
		char c;
		for(int i=1; i<n; i++) {
			c = directions.charAt(i);
			if(c == '0') p = left(p);
			if(c == '1') p = right(p);
		}
		c = directions.charAt(n);
		if(c == '0') q = addLeft(p, e);
		if(c == '1') q = addRight(p, e);
		return q;
	}

	public Position<E> addLeaf(E e) {
		return addLeaf(root, e);
	}	

	private Position<E> lastLeaf(Position<E> p) {
		int size = size();
		if(size == 1) return root;
		if(size % 2 == 0) size = size+1;
		else size = size-1;
		String directions = Integer.toBinaryString(size);
		int n = directions.length()-1;
		char c;
		for(int i=1; i<n; i++) {
			c = directions.charAt(i);
			if(c == '0') p = left(p);
			if(c == '1') p = right(p);
		}
		c = directions.charAt(n);
		if(c == '0') p = right(p);
		if(c == '1') p = left(p);
		return p;
	}

	public Position<E> lastLeaf() {
		return lastLeaf(root);
	}

	public E getE(Position<E> p) {
		Node<E> node = validate(p);
		return node.getElement();
	}

}	
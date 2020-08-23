/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Nathan Yang
*/

public class TreePQ<Key,Value> extends AbstractPriorityQueue<Key,Value> {
	
	protected HeapTree<PQEntry<Key,Value>> heap = new HeapTree<>();

	public TreePQ() { super(); }
	
	protected void upheap(Position<PQEntry<Key,Value>> p) {
		while(heap.parent(p) != null) {
			Position<PQEntry<Key,Value>> q = heap.parent(p);
			if(compare(heap.getE(p),heap.getE(q)) >= 0) break;
			heap.swap(p,q);
			p = heap.parent(p);
		}
	}

	protected Position<PQEntry<Key,Value>> downheap(Position<PQEntry<Key,Value>> p) {
		if(heap.numChildren(p) == 2) {
			if(compare(heap.getE(heap.left(p)),heap.getE(heap.right(p))) > 0) {
				if(compare(heap.getE(heap.right(p)),heap.getE(p)) < 0) {
					heap.swap(p,heap.right(p));
					return downheap(heap.right(p));
				}
				else return p;
			}
			else if(compare(heap.getE(heap.left(p)),heap.getE(p)) < 0){
				heap.swap(p,heap.left(p));
				return downheap(heap.left(p));
			}
			else return p;
		}
		else if(heap.numChildren(p) == 1) {
			if(compare(heap.getE(heap.left(p)),heap.getE(p)) < 0){
				heap.swap(p,heap.left(p));
				return downheap(heap.left(p));
			}
			else return p;
		}
		else return p;
	}

	public PQEntry<Key,Value> insert(Key k, Value v) {
		checkKey(k);
		PQEntry<Key,Value> entry = new PQEntry(k, v);
		if(heap.isEmpty()) heap.addRoot(entry);
		else {
			Position<PQEntry<Key,Value>> p = heap.addLeaf(entry);
			upheap(p);
		}
		return entry;
	}

	public int size() {
		return heap.size();
	}

	public PQEntry<Key,Value> min() {
		PQEntry<Key, Value> min = null;
		if(heap.root() != null) min = heap.getE(heap.root());
		return min;
	}

	public PQEntry<Key,Value> removeMin() {
		PQEntry<Key,Value> min = min();
		if(size() == 1) heap.remove(heap.root());
		if(size() > 1) downheap(heap.replaceRoot(heap.lastLeaf()));
		return min;
	}

}
package de.dualuse.util;

import static java.util.Collections.*;

import java.util.*;

public class TreeList<E> extends TreeSet<E> implements List<E> {
	private static final long serialVersionUID = 1L;
	private ArrayList<E> list = new ArrayList<E>();
	
	public TreeList() { super(); }
	public TreeList(Collection<? extends E> c) { super(c); }
	public TreeList(Comparator<? super E> comparator) { super(comparator); }
	public TreeList(SortedSet<E> s) { super(s); }

	
	//////////////////////////
	
	private boolean relist() {
		list.clear();
		for (Iterator<E> it = super.iterator();it.hasNext();)
			list.add(it.next());
		
		return true;
	}
	
	@Override
	public boolean add(E e) {
		super.add(e);
		relist();
		return true;
	}

	@Override
	public boolean remove(Object o) {
		super.remove(o);
		list.remove(o);
		return super.remove(o);
	}

	@Override
	public void clear() {
		list.clear();
		super.clear();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (!super.addAll(c))
			return false;
		
		relist();
		return true;
	}


	@Override
	public E pollFirst() {
		list.remove(0);
		return super.pollFirst();
	}

	@Override
	public E pollLast() {
		list.remove(size()-1);
		return super.pollLast();
	}

	@Override
	public TreeList<E> clone() {
		TreeList<E> l = new TreeList<E>();
		l.addAll(this);
		return l;
	}

	
	////////////////
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (!super.addAll(c)) return false;
		return relist();
	}

	@Override
	public E get(int index) {
		return list.get(index);
	}

	@Override
	public E set(int index, E element) {
		E before = list.remove(index);
		super.remove( before );
		add(element);
		return before;
	}

	@Override
	public void add(int index, E element) {
		add(element);
	}

	@Override
	public E remove(int index) {
		E before = list.remove(index);
		super.remove( before );
		return before;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int indexOf(Object o) {
		int index = binarySearch(list, (E)o, comparator());
		return index<0?-1:index;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int lastIndexOf(Object o) {
		int index = binarySearch(list, (E)o, comparator());
		if (index<0) return -1;
		while (list.get(index++).equals(o));
		return index-1;
	}


	

	/////////////////////
	
	@Override public Iterator<E> descendingIterator() { return descendingSet().iterator(); }
	@Override public Iterator<E> iterator() { return unmodifiableCollection(this).iterator(); }
	@Override public ListIterator<E> listIterator() { return unmodifiableList(list).listIterator(); }
	@Override public NavigableSet<E> descendingSet() { return unmodifiableNavigableSet(super.descendingSet()); }
	@Override public ListIterator<E> listIterator(int index) { return unmodifiableList(list).listIterator(index); }
	@Override public SortedSet<E> headSet(E toElement) { return unmodifiableSortedSet(super.headSet(toElement)); }
	@Override public SortedSet<E> tailSet(E fromElement) { return unmodifiableSortedSet(super.tailSet(fromElement)); }

	@Override public List<E> subList(int fromIndex, int toIndex) { 
		return unmodifiableList(list).subList(fromIndex, toIndex); 
	}

	@Override
	public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
		return unmodifiableNavigableSet(super.subSet(fromElement, fromInclusive, toElement, toInclusive));
	}

	@Override
	public NavigableSet<E> headSet(E toElement, boolean inclusive) {
		return unmodifiableNavigableSet(super.headSet(toElement, inclusive));
	}

	@Override
	public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
		return unmodifiableNavigableSet(super.tailSet(fromElement, inclusive));
	}

	@Override
	public SortedSet<E> subSet(E fromElement, E toElement) {
		return unmodifiableSortedSet(super.subSet(fromElement, toElement));
	}



	
}

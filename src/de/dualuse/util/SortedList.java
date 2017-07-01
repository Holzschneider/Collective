package de.dualuse.util;

import static java.lang.Math.*;
import static java.util.Collections.*;

import java.io.Serializable;
import java.util.*;

//XXX new attempt getting sorted list actually right

public class SortedList<ComparableType extends Comparable<? super ComparableType>> implements 
	List<ComparableType>, NavigableSet<ComparableType>, Comparator<ComparableType>, RandomAccess, Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<ComparableType> elements = new ArrayList<ComparableType>();
	private Comparator<ComparableType> comparator = this;
//	private int sign = 1, start = 0, end = 0;
	
	public SortedList() { }
	public SortedList(Comparator<ComparableType> comparator) { this.comparator = comparator;  }
//	
//	private SortedList(ArrayList<ComparableType> elements, Comparator<ComparableType> comparator) {
//		this.elements = elements;
//		this.comparator = comparator;
//	}
	
	@Override
	public int compare(ComparableType o1, ComparableType o2) {
		int comparison = o1.compareTo(o2);
		if (comparison==0)
			comparison = Integer.compare(System.identityHashCode(o1), System.identityHashCode(o2));
		return comparison;
	}
	
	@Override
	public Comparator<? super ComparableType> comparator() { return comparator; }
	
	@Override
	public ComparableType first() {
		return elements.get(0);
	}
	
	@Override
	public ComparableType last() {
		return elements.get(0);
	}
	
	@Override
	public ComparableType lower(ComparableType e) {
		return null;
	}
	
	@Override
	public ComparableType floor(ComparableType e) {
		int index = binarySearch(elements,e,comparator);
		return index<0?elements.get(-index-1):elements.get(index);
	}
	
	@Override
	public ComparableType ceiling(ComparableType e) {
		return null;
	}
	
	@Override
	public ComparableType higher(ComparableType e) {
		return null;
	}
	
	///////////
	
	@Override
	public ComparableType pollFirst() {
		return remove(0);
	}
	
	@Override
	public ComparableType pollLast() {
		return remove(size()-1);
	}
	
	@Override
	public NavigableSet<ComparableType> descendingSet() {
		return null;
	}
	
	@Override
	public Iterator<ComparableType> descendingIterator() {
		return descendingSet().iterator();
	}
	
	@Override
	public NavigableSet<ComparableType> subSet(ComparableType fromElement, boolean fromInclusive, ComparableType toElement, boolean toInclusive) {
		return null;
	}
	
	@Override
	public NavigableSet<ComparableType> headSet(ComparableType toElement, boolean inclusive) {
		return null;
	}
	
	@Override
	public NavigableSet<ComparableType> tailSet(ComparableType fromElement, boolean inclusive) {
		return null;
	}
	
	@Override
	public SortedSet<ComparableType> subSet(ComparableType fromElement, ComparableType toElement) {
		return null;
	}
	
	@Override
	public SortedSet<ComparableType> headSet(ComparableType toElement) {
		return null;
	}
	
	@Override
	public SortedSet<ComparableType> tailSet(ComparableType fromElement) {
		return null;
	}
	
	@Override
	public int size() {
		return elements.size();
	}
	
	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	@Override
	public boolean contains(Object o) {
		return elements.contains(o);
	}
	
	@Override
	public Iterator<ComparableType> iterator() {
		return elements.iterator();
	}
	
	@Override
	public Object[] toArray() {
		return elements.toArray();
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		return elements.toArray(a);
	}
	
	@Override
	public boolean add(ComparableType e) {
		add(abs(binarySearch(elements,e,comparator)), e);
		return true;
	}
	
	@Override
	public boolean remove(Object o) {
		return elements.remove(o);
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}
	
	@Override
	public boolean addAll(Collection<? extends ComparableType> c) {
		elements.addAll(c);
		elements.sort(comparator);
		return true;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends ComparableType> c) {
		elements.addAll(c);
		elements.sort(comparator);
		return true;
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return elements.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return elements.retainAll(c);
	}
	
	@Override
	public void clear() {
		elements.clear();
	}
	
	@Override
	public ComparableType get(int index) {
		return elements.get(index);
	}
	
	@Override
	public ComparableType set(int index, ComparableType element) {
		ComparableType c = remove(index);
		add(element);
		return c;
	}
	
	@Override
	public void add(int index, ComparableType element) {
		add(element);
	}
	
	@Override
	public ComparableType remove(int index) { 
		return elements.remove(index);
	}
	
	@Override
	public int indexOf(Object o) {
		@SuppressWarnings("unchecked")
		int index = binarySearch(elements, (ComparableType)o, comparator);
		return index<0?-1:index;
	}
	
	public int lastIndexOf(Object o) {
		@SuppressWarnings("unchecked")
		int index = binarySearch(elements, (ComparableType)o, comparator);
		if (index<0) return -1;
		
		while (elements.get(index++).equals(o));
		
		return index-1;
	}
	
	@Override
	public ListIterator<ComparableType> listIterator() {
		return elements.listIterator();
	}
	
	@Override
	public ListIterator<ComparableType> listIterator(int index) {
		return elements.listIterator(index);
	}
	
	@Override
	public List<ComparableType> subList(int fromIndex, int toIndex) {
		return unmodifiableList(elements.subList(fromIndex, toIndex));
	}

	@Override
	public Spliterator<ComparableType> spliterator() {
		return elements.spliterator();
	}
	
	
	
	
	
	
//	public SortedList() { }
//	public<T extends ComparableType> SortedList(Collection<T> fill) { addAll(fill); }
//	
//	public ComparableType get(int i) { return elements.get(i); };
//
//	@Override public int size() { return elements.size(); }
//	@Override public boolean isEmpty() { return elements.isEmpty(); }
//	@Override public boolean contains(Object o) { return elements.contains(o); }
//	@Override public Iterator<ComparableType> iterator() { return elements.iterator(); }
//	@Override public Object[] toArray() { return elements.toArray();	}
//	@Override public <T> T[] toArray(T[] a) { return elements.toArray(a); }
//	
//	
//	@Override public boolean add(ComparableType e) {
//		int lower = 0, upper = size();
//		
//		for (int mid = upper/2; upper-lower>0; mid = (lower+upper)/2) 
//			if (elements.get(mid).compareTo(e)>0)
//				upper = mid;
//			else
//				lower = mid+1;
//		
//		elements.add(upper, e);
//		
//		return true;
//	}
//	
//	
//	@Override public boolean addAll(Collection<? extends ComparableType> c) { 
//		boolean changed = elements.addAll(c);
//		if (!changed) return false;
//		
//		elements.sort(this);
//		
//		return true;
//	}
//	
//	
//	public int indexOf(Object o) { return elements.indexOf(o); }
//	
//	@Override public boolean remove(Object o) { return elements.remove(o); }
//	@Override public boolean containsAll(Collection<?> c) { return elements.containsAll(c); }
//	@Override public boolean removeAll(Collection<?> c) { return elements.removeAll(c); }
//
//	@Override public boolean retainAll(Collection<?> c) { return elements.retainAll(c); }
//	@Override public void clear() { elements.clear(); }
//
//	@Override
//	public int compare(ComparableType o1, ComparableType o2) {
//		return o1.compareTo(o2);
//	}
//
//	
//	@Override
//	public String toString() {
//		return elements.toString();
//	}
//	
//	
//	
//	@Override
//	public Comparator<? super ComparableType> comparator() { return this; }
//	
//	@Override
//	public ComparableType first() {
//		return get(0);
//	}
//	
//	@Override
//	public ComparableType last() {
//		return get(size()-1);
//	}
//	
//	@Override
//	public ComparableType lower(ComparableType e) {
//		return null;
//	}
//	
//	@Override
//	public ComparableType floor(ComparableType e) {
//		return null;
//	}
//	
//	@Override
//	public ComparableType ceiling(ComparableType e) {
//		return null;
//	}
//	
//	@Override
//	public ComparableType higher(ComparableType e) {
//		return null;
//	}
//	
//	@Override
//	public ComparableType pollFirst() {
//		return null;
//	}
//	
//	@Override
//	public ComparableType pollLast() {
//		return null;
//	}
//	
//	@Override
//	public NavigableSet<ComparableType> descendingSet() {
//		return null;
//	}
//	
//	@Override
//	public Iterator<ComparableType> descendingIterator() {
//		return null;
//	}
//	
//	@Override
//	public NavigableSet<ComparableType> subSet(ComparableType fromElement, boolean fromInclusive,
//			ComparableType toElement, boolean toInclusive) {
//		return null;
//	}
//	
//	@Override
//	public NavigableSet<ComparableType> headSet(ComparableType toElement, boolean inclusive) {
//		return null;
//	}
//	
//	@Override
//	public NavigableSet<ComparableType> tailSet(ComparableType fromElement, boolean inclusive) {
//		return null;
//	}
//	
//	@Override
//	public SortedSet<ComparableType> subSet(ComparableType fromElement, ComparableType toElement) {
//		return null;
//	}
//	
//	@Override
//	public SortedSet<ComparableType> headSet(ComparableType toElement) {
//		return null;
//	}
//	
//	@Override
//	public SortedSet<ComparableType> tailSet(ComparableType fromElement) {
//		return null;
//	}
	
}

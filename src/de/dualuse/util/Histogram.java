package de.dualuse.util;

import java.util.Arrays;

//XXX Reimplement this using a Sparse Array-Technique along with a live-updated minmax-heap that allows to traverse it 
public class Histogram {
	
	int[] counter;
	int[] counted;
	int head = 0;
	
	public Histogram() {
		this(256);
	}
	
	public Histogram(int initialCapacity) {
		counter = new int[initialCapacity];
		counted = new int[initialCapacity];
	}
	
	
	public Histogram clear() {
		while (head>0)
			counter[counted[--head]] = 0;
		
		return this;
	}
	
	public Histogram increment(int index) {
		ensureCapacity(index);
			
		if (counter[index]++==0)
			counted[head++] = index;
		
		return this;
	}
	
	public Histogram merge(Histogram mergee) {
		ensureCapacity(mergee.counter.length);
		
		for (int i=0;i<mergee.head;i++) {
			int index = mergee.counted[i];
			
			if (counter[index]==0)
				counted[head++] = index;
			
			counter[index] += mergee.counter[index];
		}
		
		return this;		
	}
	
	
	public int size() {
		return head;
	}
	
//	public Histogram sort() {
//		quicksort(0, head-1);
//		return this;
//	}
//	
//	public int pop() {
//		return counted[--head];
//	}
//	
//	public int peek() {
//		return counted[head-1];
//	}
//	
//	
//	public int pop(int[] winners) { return pop(winners,0,winners.length); }
//	public int pop(int[] winners, int offset, int length) {
//		int counter = 0; 
//		for (int i=0,I=length<head?length:head;i<I;i++)
//			winners[offset+counter++] = counted[--head];
//		
//		return counter;
//	}

	
	public int counter(int index) {
		return counter[index];
	}
	
	
	private void ensureCapacity( int c ) {
		if (c<counter.length) 
			return;
		
		counter = Arrays.copyOf(counter, (c+10)*3/2);
		counted = Arrays.copyOf(counted, (c+10)*3/2);
	}
	
	
//	private void quicksort(int low, int high) {
//		int i = low, j = high;
//		int pivot = counted[(low + high) / 2];
//
//		while (i <= j) {
//
//			while (counter[counted[i]]-counter[pivot] < 0) i++;
//			while (counter[counted[j]]-counter[pivot] > 0) j--;
//
//			if (!(i <= j)) continue;
//			int t = counted[i];
//			counted[i] = counted[j];
//			counted[j] = t;
//			i++;
//			j--;
//		}
//		
//		if (low < j) quicksort(low, j);
//		if (i < high) quicksort(i, high);
//	}
	
	////////////////////////////////////////
	
	public static void main(String[] args) {
		
		Histogram mc = new Histogram(11), md = new Histogram(1);
		
		mc.increment(10);
		mc.increment(2);
		mc.increment(4);
		mc.increment(1);
		mc.increment(2);
		mc.increment(8);
		mc.increment(10);
		mc.increment(2);
		
		
		md.increment(1);
		md.increment(2);
		md.increment(9);
		
		mc.merge(md);
		
//		mc.sort();
//
//		System.out.println( Arrays.toString(mc.counter) );
//		System.out.println( Arrays.toString(mc.counted) );
//		
//		System.out.println( "element "+ mc.peek()+": "+mc.counter(mc.pop()) ) ;
//		System.out.println( "element "+ mc.peek()+": "+mc.counter(mc.pop()) ) ;
//		System.out.println( "element "+ mc.peek()+": "+mc.counter(mc.pop()) ) ;
//		System.out.println( "element "+ mc.peek()+": "+mc.counter(mc.pop()) ) ;
		
		
		
	}


	
}











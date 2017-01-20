package de.dualuse.commons.util;

import java.io.Serializable;


/**
 * Android compatible Pair implementation ; ) nicht geklaut!
 */
public class Pair<F, S> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final F first;
    public final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
    
    public static boolean equal(Object a, Object b) {
    	return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(Object o) {
    	return (o == null) ? 0 : o.hashCode();
    }
    

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair))
            return false;
        Pair<?, ?> p = (Pair<?, ?>) o;
        
        return equal(p.first, first) && equal(p.second, second);
    }

    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    public static <A, B> Pair <A, B> create(A a, B b) {
        return new Pair<A, B>(a, b);
    }

    final public static <A, B> Pair <A, B> of(A a, B b) {
        return create(a, b);
    }
    
    
    public String toString() { return "Pair("+first.toString()+","+second.toString()+")"; }
}

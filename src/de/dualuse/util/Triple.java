package de.dualuse.util;


public class Triple<X,Y,Z> extends Pair<X,Y>{
	private static final long serialVersionUID = 1L;
	
	public final Z third;

	public Triple(X a, Y b, Z c) {
		super(a,b);
		this.third = c;
	}
	
	@Override
	public boolean equals(Object obj) {
		Triple<?,?,?> that = (Triple<?,?,?>)obj;
		return this.first.equals(that.first) && this.second.equals(that.second) && this.third.equals(that.third);
	}
	
	@Override
	public int hashCode() {
		return first.hashCode()^second.hashCode()^third.hashCode();
	}
	

    public static <A, B, C> Triple <A, B, C> create(A a, B b, C c) {
        return new Triple<A, B, C>(a, b, c);
    }

    final public static <A, B, C> Triple <A, B, C> of(A a, B b, C c) {
        return create(a, b, c);
    }
    
}
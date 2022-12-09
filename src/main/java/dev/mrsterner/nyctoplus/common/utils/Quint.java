package dev.mrsterner.nyctoplus.common.utils;

public class Quint<S, T, U, V> {

	private final S x;
	private final T z;
	private final U u;
	private final V v;

	public Quint(S x, T z, U u, V v) {
		this.x = x;
		this.z = z;
		this.u = u;
		this.v = v;
	}

	public S getX() { return x; }
	public T getZ() { return z; }
	public U getU() { return u; }
	public V getV() { return v; }
}

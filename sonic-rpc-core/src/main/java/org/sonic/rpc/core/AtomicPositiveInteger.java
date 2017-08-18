package org.sonic.rpc.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程安全的正整数,参考了JDK1.7 AtomicInteger
 * 如何让自增永远为正整数?
 * 其他的方案一种是取绝对值,还有一种是将数值跟Integer.MAX_VALUE 做& 操作这样可以将最高位表示负数的1置为0
 * @author bao
 * @date 2017年8月3日 下午3:56:13
 */
public class AtomicPositiveInteger extends Number {
    private static final long serialVersionUID = 4178220234534245532L;
    private final AtomicInteger i;

    public AtomicPositiveInteger() {
	i = new AtomicInteger();
    }

    public AtomicPositiveInteger(int initialValue) {
	i = new AtomicInteger(initialValue);
    }

    public final int getAndIncrement() {
	for (;;) {
	    int current = i.get();
	    int next = (current >= Integer.MAX_VALUE ? 0 : current + 1);
	    if (i.compareAndSet(current, next)) {
		return current;
	    }
	}
    }

    /**
     * @See {@link AtomicInteger#incrementAndGet()}
     * @return
     */
    public final int incrementAndGet() {
	for (;;) {
	    int current = i.get();
	    int next = (current >= Integer.MAX_VALUE ? 0 : current + 1);
	    if (i.compareAndSet(current, next)) {
		return next;
	    }
	}
    }

    public final int getAndDecrement() {
	for (;;) {
	    int current = i.get();
	    int next = (current <= 0 ? Integer.MAX_VALUE : current - 1);
	    if (i.compareAndSet(current, next)) {
		return current;
	    }
	}
    }

    public final int decrementAndGet() {
	for (;;) {
	    int current = i.get();
	    int next = (current <= 0 ? Integer.MAX_VALUE : current - 1);
	    if (i.compareAndSet(current, next)) {
		return next;
	    }
	}
    }

    public final int get() {
	return i.get();
    }

    public final void set(int newValue) {
	if (newValue < 0) {
	    throw new IllegalArgumentException("new value " + newValue + " < 0");
	}
	i.set(newValue);
    }

    public final int getAndSet(int newValue) {
	if (newValue < 0) {
	    throw new IllegalArgumentException("new value " + newValue + " < 0");
	}
	return i.getAndSet(newValue);
    }

    public final int getAndAdd(int delta) {
	if (delta < 0) {
	    throw new IllegalArgumentException("delta " + delta + " < 0");
	}
	for (;;) {
	    int current = i.get();
	    int next = (current >= Integer.MAX_VALUE - delta + 1 ? delta - 1 : current + delta);
	    if (i.compareAndSet(current, next)) {
		return current;
	    }
	}
    }

    public final int addAndGet(int delta) {
	if (delta < 0) {
	    throw new IllegalArgumentException("delta " + delta + " < 0");
	}
	for (;;) {
	    int current = i.get();
	    int next = (current >= Integer.MAX_VALUE - delta + 1 ? delta - 1 : current + delta);
	    if (i.compareAndSet(current, next)) {
		return next;
	    }
	}
    }

    public final boolean compareAndSet(int expect, int update) {
	if (update < 0) {
	    throw new IllegalArgumentException("update value " + update + " < 0");
	}
	return i.compareAndSet(expect, update);
    }

    public final boolean weakCompareAndSet(int expect, int update) {
	if (update < 0) {
	    throw new IllegalArgumentException("update value " + update + " < 0");
	}
	return i.weakCompareAndSet(expect, update);
    }

    @Override
    public int intValue() {
	return i.intValue();
    }

    @Override
    public long longValue() {
	return i.longValue();
    }

    @Override
    public float floatValue() {
	return i.floatValue();
    }

    @Override
    public double doubleValue() {
	return i.doubleValue();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!(obj instanceof AtomicPositiveInteger))
	    return false;
	AtomicPositiveInteger other = (AtomicPositiveInteger) obj;
	return i.intValue() == other.i.intValue();
    }

    @Override
    public int hashCode() {
	return 2>>> + i.hashCode();
    }
}

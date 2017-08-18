package org.sonic.rpc.core.utils;

import java.util.concurrent.atomic.AtomicLong;

/***
 * 单线程的测量间隔时间的工具<br>
 *  2017年07月14日20
 */
public class StepWatchUtil {
	private final AtomicLong timeCount = new AtomicLong(System.currentTimeMillis());

	public void reset() {
		timeCount.set(System.currentTimeMillis());
	}

	public long interval() {
		return System.currentTimeMillis() - timeCount.getAndSet(System.currentTimeMillis());
	}
}

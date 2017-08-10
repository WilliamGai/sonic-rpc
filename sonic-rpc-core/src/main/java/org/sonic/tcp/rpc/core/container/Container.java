package org.sonic.tcp.rpc.core.container;

public abstract class Container {
    public static volatile boolean isStart = false;
    public static volatile Container container = null;

    public abstract void start();
}

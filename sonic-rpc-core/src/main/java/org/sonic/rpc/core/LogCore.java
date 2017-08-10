package org.sonic.rpc.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author gbn
 */
public class LogCore{
	public static Logger  BASE = LoggerFactory.getLogger(LogCore.class);
	public static Logger  USER = LoggerFactory.getLogger("USER");
	public static Logger  RPC = LoggerFactory.getLogger("RPC");
}

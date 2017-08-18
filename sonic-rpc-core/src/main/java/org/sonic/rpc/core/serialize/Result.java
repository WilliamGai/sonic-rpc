package org.sonic.rpc.core.serialize;

public class Result{
	public byte result;
	public Object data;
	
	public Result(int result) {
		super();
		this.result = (byte)result;
	}
	public Result(int result, Object data) {
		super();
		this.result = (byte)result;
		this.data = data;
	}
	public Result setData(Object desc){
		this.data = desc;
		return this;
	}
}

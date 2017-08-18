package org.sonic.rpc.core.serialize;

public class Result {
	public static final byte SUCESS_1 = 1;
	public static final byte FAIL_0 = 1;
	public byte result = SUCESS_1;
	public Object data;

	public Result() {
	}

	public Result(int result) {
		super();
		this.result = (byte) result;
	}

	public Result(int result, Object data) {
		super();
		this.result = (byte) result;
		this.data = data;
	}

	public Result setData(Object desc) {
		this.data = desc;
		return this;
	}
}

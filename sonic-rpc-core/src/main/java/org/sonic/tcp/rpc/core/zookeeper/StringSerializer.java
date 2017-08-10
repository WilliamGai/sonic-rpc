package org.sonic.tcp.rpc.core.zookeeper;

import java.nio.charset.Charset;

import org.sonic.tcp.rpc.core.exception.SerializeException;
/***
 * @author bao
 * @date 2017年7月29日 上午11:01:45
 */
public class StringSerializer implements Serializer<String> {
    private final Charset charset;

    public StringSerializer() {
	this(Charset.forName("UTF8"));
    }

    public StringSerializer(Charset charset) {
	this.charset = charset;
    }

    @Override
    public byte[] serialize(String data) throws SerializeException {
	return (data == null ? null : data.getBytes(charset));
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializeException {
	return (bytes == null ? null : new String(bytes, charset));
    }

}

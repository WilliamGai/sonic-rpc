package org.sonic.tcp.rpc.core.zookeeper;

import org.sonic.tcp.rpc.core.exception.SerializeException;

public interface Serializer<T> {

    byte[] serialize(T data) throws SerializeException;

    T deserialize(byte[] bytes) throws SerializeException;

}
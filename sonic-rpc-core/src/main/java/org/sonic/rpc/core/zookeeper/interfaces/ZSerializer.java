package org.sonic.rpc.core.zookeeper.interfaces;

import org.sonic.rpc.core.exception.SerializeException;

public interface ZSerializer<T> {

    byte[] serialize(T data) throws SerializeException;

    T deserialize(byte[] bytes) throws SerializeException;

}
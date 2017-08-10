package org.sonic.rpc.core.zookeeper;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.sonic.rpc.core.serialize.Request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class ZStringSerializer implements ZkSerializer {

    private static final StringSerializer serl = new StringSerializer();

    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {
	return serl.serialize((String) data);
    }

    @Override
    public String deserialize(byte[] bytes) throws ZkMarshallingError {
	return serl.deserialize(bytes);
    }

    public static void main(String args[]) throws Exception {
//	DataSimpleVO r = new DataSimpleVO("a", 1);
//	r.value1 = DataSimpleVO.class;
	Request r = new Request();
	r.setClazz(Integer.class);
	String s = JSON.toJSONString(r, SerializerFeature.WriteClassName);
	System.out.println(s);
//	String test = FileTool.readLine("a.txt");
//	System.out.println(test);
	JSON.parseObject(s);
//	System.out.println(obj.value1);
    }
}

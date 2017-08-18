package org.sonic.rpc.provider;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.utils.JSONUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TestJson {
	public static class Point {
		public int x;
		public int y;

		public Point() {
		};

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

	}

	public static void main(String[] args) throws Exception {
		String jsonStr = readLine("a.txt");
		LogCore.BASE.info("file json is {}", jsonStr);
		Object o = JSONUtil.deserialize(jsonStr);
		LogCore.BASE.info("o is {}", o);
	}

	public static String readLine(String fileName) throws Exception {
		return new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
	}

	public static void main() {
		JSONObject json = new JSONObject();
		json.put("result", 1);
		json.put("result", 3);
		json.put("award_id", 2);
//		String rt = json.toJSONString();

		System.out.println(json.toJSONString());
		System.out.println(json.toString());
//		List<String> list = Arrays.asList("a", "c", "c");
		JSONArray ja1 = new JSONArray();
		ja1.add(1);
		ja1.add(2);
		JSONArray ja2 = new JSONArray();
		ja2.add(2);
		ja2.add(3);
		JSONArray ja3 = new JSONArray();
		ja3.add(4);
		ja3.add(5);
		JSONArray ja = new JSONArray();
		ja.add(ja1);
		ja.add(ja2);
		ja.add(ja3);
		// BeanFactoryUtils.transformedBeanName(name)
		System.out.println(ja.toJSONString());
	}
}

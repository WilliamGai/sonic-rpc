package org.sonic.rpc.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ReflectUtil {
	/**
	 * 获得某个对象的某个字段
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getField(Object r, String filedName) {
		try {
			Field f = r.getClass().getDeclaredField(filedName);
			f.setAccessible(true);
			return (T) f.get(r);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Map<String, Object> getFields(Object r) {
		Map<String, Object> map = new HashMap<>();
		Stream.of(r.getClass().getDeclaredFields()).forEach(field -> {
			field.setAccessible(true);
			try {
				map.put(field.getName(), field.get(r));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return map;
	}

	public static Map<String, Object> getFields(Object r, Predicate<Field> p) {
		Map<String, Object> map = new HashMap<>();
		Stream.of(r.getClass().getDeclaredFields()).forEach(field -> {
			if (!p.test(field))
				return;
			field.setAccessible(true);
			try {
				map.put(field.getName(), field.get(r));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return map;
	}

	/* 如果无返回值则返回null */
	public static Object invokeMethod(Method mtd, Object obj, Object... args) {
		mtd.setAccessible(true);
		try {
			return mtd.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
		Method mtd = null;
		try {
			mtd = clazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (Exception e) {
			e.printStackTrace();
			return mtd;
		}
		return mtd;
	}

	/**
	 * ()通过反射获得某个对象的某个方法，并返回方法的结果
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getReflectObject(Class<?> clazz, Object r, String methodName, Object[] params,
	            Class<?>... parameterTypes) {
		try {
			Method mtd = clazz.getDeclaredMethod(methodName, parameterTypes);
			mtd.setAccessible(true);
			return (T) mtd.invoke(r, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

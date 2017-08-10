package org.sonic.rpc.core;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sonic.rpc.core.exception.SysException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * TODO 因为Java会擦除泛型,所以要去掉因为参数列表相同而类型不同的重载,以免出错
 * 
 * @author bao
 * @date 2017年8月5日 下午11:25:41
 */
public class Util {
    public static boolean isLinux() {
	String os = System.getProperty("os.name").toLowerCase();
	if (!isEmpty(os) && os.contains("windows")) {
	    return false;
	}
	return true;
    }

    public static String getM(long availmem) {
	double result = availmem / 1024f / 1024f;
	DecimalFormat df = new DecimalFormat("#.##");
	return df.format(result) + " MB";
    }


    public static <K, V> int size(Map<K, V> map) {
	if (null == map) {
	    return 0;
	}
	return map.size();
    }

    public static int size(String s) {
	if (null == s) {
	    return 0;
	}
	return s.length();
    }

    public static int size(Collection<?> c) {
	if (null == c) {
	    return 0;
	}
	return c.size();
    }

    public static boolean isContained(String src, String tar) {
	if (Util.isEmpty(src) || Util.isEmpty(tar)) {
	    return false;
	}
	return src.contains(tar);
    }

    public static String replace(String src, String s0, String s) {
	if (Util.isEmpty(src)) {
	    return null;
	}
	return src.replace(s0, s);
    }

    public static String empty2Null(String s) {
	if (null == s || s.trim().length() == 0) {
	    return null;
	}
	return s;
    }

    public static String toEmpyDefalut(String s, String emptyDefalut) {
	if (isEmpty(s)) {
	    return emptyDefalut;
	}
	return s;
    }

    /**
     * @param t
     * @param func s如果不为empty,则执行此表达式
     * @param emptyDefalut 如果t为empty直接返回这个值
     */
    public static <R> R toEmpyDefalut(String t, Function<String, R> func, R emptyDefalut) {
	if (Util.isEmpty(t)) {
	    return emptyDefalut;
	}
	return func.apply(t);
    }

    /**
     * @see MoreObjects#firstNonNull
     * @see Objects#toString(Object, String)
     */
    public static <T> T toNullDefalut(T s, T nullDefalut) {
	if (null == s) {
	    return nullDefalut;
	}
	return s;
    }

    /**
     * @param s
     * @param func s如果不为null,则执行此表达式
     * @param nullDefalut 如果s为null直接返回这个值
     */
    public static <T, R> R toNullDefalut(T t, Function<T, R> func, R nullDefalut) {
	if (null == t) {
	    return nullDefalut;
	}
	return func.apply(t);
    }

    public static <T> void ifNotEmpty(T t, Consumer<? super T> consumer) {
	if (!isEmpty(t)) {
	    consumer.accept(t);
	}
    }

    public static boolean isEmpty(Object obj) {
	if (obj == null)
	    return true;
	Class<?> clazz = obj.getClass();
	if (clazz.isArray()) {
	    if (clazz == byte[].class) // eClass.getComponentType() == byte.class
		return ((byte[]) obj).length == 0;
	    if (clazz == short[].class)
		return ((short[]) obj).length == 0;
	    if (clazz == int[].class)
		return ((int[]) obj).length == 0;
	    if (clazz == long[].class)
		return ((long[]) obj).length == 0;
	    if (clazz == char[].class)
		return ((char[]) obj).length == 0;
	    if (clazz == float[].class)
		return ((float[]) obj).length == 0;
	    if (clazz == double[].class)
		return ((double[]) obj).length == 0;
	    if (clazz == boolean[].class)
		return ((boolean[]) obj).length == 0;
	    Object[] objArr = (Object[]) obj;
	    return objArr.length == 0;
	}
	if (String.class.isAssignableFrom(clazz)) {
	    return ((String) obj).length() == 0;
	}
	if (Map.class.isAssignableFrom(clazz)) {
	    return ((Map<?, ?>) obj).size() == 0;
	}
	if (Collection.class.isAssignableFrom(clazz)) {
	    return ((Collection<?>) obj).size() == 0;
	}
	throw new SysException("unkown classType {}", clazz.getCanonicalName());
    }

    public static boolean isEmpty(Object os[]) {
	return null == os || 0 == os.length;
    }

    public static <E extends CharSequence> boolean isEmpty(E str) {
	return null == str || 0 == str.length();
    }

    public static boolean notEmpty(String str) {
	return !isEmpty(str);
    }

    public static <T extends Map<?, ?>> boolean isEmpty(T t) {
	return null == t || 0 == t.size();
    }

    /* public static boolean isEmpty(Collection<?> t)跟这个是一样的 */
    public static <T extends Collection<?>> boolean isEmpty(T t) {
	return null == t || 0 == t.size();
    }

    public <E> int getSize(Collection<E> collection) {
	if (null == collection) {
	    return -1;
	}
	return collection.size();
    }

    public <E> E pollFromQueue(Queue<E> queue) {
	if (null == queue) {
	    return null;
	}
	return queue.poll();
    }

    /** TU **/
    public static <K, V> String joinMap(Map<K, V> map) {
	StringBuilder sb = new StringBuilder();
	for (Iterator<Map.Entry<K, V>> it = map.entrySet().iterator(); it.hasNext();) {
	    Map.Entry<K, V> entry = it.next();
	    K k = entry.getKey();
	    V v = entry.getValue();
	    if (v.getClass().isArray()) {
		Object[] os = (Object[]) v;
		sb.append(k).append("=").append(Arrays.toString(os));
	    } else {
		sb.append(k).append("=").append(v);
	    }
	}
	return sb.toString();
    }

    /** to collect */
    public static String join(Enumeration<String> names, String separator) {
	StringBuilder sb = new StringBuilder();
	boolean first = true;
	while (names.hasMoreElements()) {
	    if (first)
		first = false;
	    else
		sb.append(separator);
	    sb.append(names.nextElement());
	}
	return sb.toString();
    }

    public static String join(Collection<String> names, String separator) {
	return names.stream().collect(Collectors.joining(separator));
    }

    /**
     * 格式化字符串 ,按照logback的方式,{}中没有数字 tips,可以映射的函数:BiFunction
     */
    public static String format(String messagePattern, Object... arguments) {
	if ((messagePattern == null) || (arguments == null) || (arguments.length == 0)) {
	    return messagePattern;
	}

	StringBuilder result = new StringBuilder();
	int escapeCounter = 0;
	int currentArgument = 0;
	for (int i = 0; i < messagePattern.length(); i++) {
	    char curChar = messagePattern.charAt(i);
	    if (curChar == '\\') {
		escapeCounter++;
	    } else if ((curChar == '{') && (i < messagePattern.length() - 1) && (messagePattern.charAt(i + 1) == '}')) {
		int escapedEscapes = escapeCounter / 2;
		for (int j = 0; j < escapedEscapes; j++) {
		    result.append('\\');
		}

		if (escapeCounter % 2 == 1) {
		    result.append('{');
		    result.append('}');
		} else {
		    if (currentArgument < arguments.length)
			result.append(arguments[currentArgument]);
		    else {
			result.append('{').append('}');
		    }
		    currentArgument++;
		}
		i++;
		escapeCounter = 0;
	    } else {
		if (escapeCounter > 0) {
		    for (int j = 0; j < escapeCounter; j++) {
			result.append('\\');
		    }
		    escapeCounter = 0;
		}
		result.append(curChar);
	    }
	}
	return result.toString();
    }

    /** 全部不为空时返回真,它的非是有至少有一个为空 */
    public static boolean nonNull(Object... objs) {
	if (null == objs || 0 == objs.length)
	    return false;
	for (Object o : objs) {
	    if (null == o)
		return false;
	}
	return true;
    }

    /** 全部不为empty就返回真，它的非是有至少一个null或者空 */
    public static boolean nonEmpty(String... ss) {
	if (null == ss || 0 == ss.length)
	    return false;
	return Stream.of(ss).map(Util::notEmpty).reduce((x, y) -> x && y).get();
    }

    /** 只要有一个不为empty就返回真，它的非是全部为null或者空 */
    public static boolean anyNonEmpty(String... ss) {
	if (null == ss || 0 == ss.length)
	    return false;
	return Stream.of(ss).map(Util::notEmpty).reduce((x, y) -> x || y).get();
    }

    public static String tranString(String s, String srcCharset, String tarCharset) {
	if (Util.isEmpty(s)) {
	    return s;
	}
	try {
	    return new String(s.getBytes(srcCharset), tarCharset);
	} catch (UnsupportedEncodingException e) {
	    LogCore.BASE.error("tranString err:s={},srccharset = {}, tarCharset={}", s, srcCharset, tarCharset, e);
	    return null;
	}
    }

    /* 序列化 */
    public static String getUtf(byte[] data) {
	if (Util.isEmpty(data)) {
	    return null;
	}
	try {
	    return new String(data, "utf-8");
	} catch (Exception e) {
	    LogCore.BASE.error("parse bytes err:{}", e);
	    return null;
	}
    }

    public static byte[] getUtfBytes(String s) {
	try {
	    return s.getBytes("utf-8");
	} catch (Exception e) {
	    LogCore.BASE.error("get bytes err:{}", e);
	    return null;
	}
    }

    public static boolean isNumeric(String str) {
	if (str == null) {
	    return false;
	}
	int sz = str.length();
	for (int i = 0; i < sz; i++) {
	    if (Character.isDigit(str.charAt(i)) == false) {
		return false;
	    }
	}
	return true;
    }

    public static String prettyJsonStr(Object obj) {
	return JSON.toJSONString(obj, SerializerFeature.PrettyFormat, SerializerFeature.WriteClassName,
		SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
    }

    public static void debugMap(Map<String, Object> map) {
	if (Util.isEmpty(map)) {
	    LogCore.BASE.debug("map is empty", map);
	}
	map.forEach((k, v) -> {
	    if (v == null) {
		LogCore.BASE.debug("k is {}, v is null", k.getClass().getSimpleName(), k);
	    } else {
		LogCore.BASE.debug("k is {}, v is {}, k={},v={}", k.getClass().getSimpleName(),
			v.getClass().getSimpleName(), k, v);
	    }
	});
    }

    /**
     * 合并两个byte[]数组
     * 
     * @see Arrays#copyOf(byte[], int)
     */
    public static byte[] mergeBytes(byte[] data, byte[] append) {
	byte[] copy = new byte[data.length + append.length];
	System.arraycopy(data, 0, copy, 0, data.length);
	System.arraycopy(append, 0, copy, data.length, append.length);
	return copy;
    }

    /** if not */
    public <T> void ifNot(boolean flag, Supplier<T> trueSup, Supplier<T> falseSup) {

    }

    public static void main(String args[]) {
	Map<String, Object> map = new HashMap<>();
	map.put("a", 1);
	Object obj = map;

	Util.isEmpty(obj);
	// JSONUtil.serialize(serializable)
	byte[] data = new byte[] {};
	System.out.println(data.getClass() == byte[].class);
	System.out.println(data.getClass().isArray());
	System.out.println(data.getClass().getComponentType() == byte.class);
	System.out.println(map.getClass().isAssignableFrom(Map.class));
	System.out.println(Map.class.isAssignableFrom(HashMap.class));
	System.out.println(null instanceof String);
	System.out.println(String.class.isInstance("a"));

	// System.out.println(Util.isEmpty(data));

	// Integer[] idata= new Integer[]{1,2,3};
	// Object o = data;
	// System.out.println(arr.length);
	Util.isEmpty(data);

    }

}

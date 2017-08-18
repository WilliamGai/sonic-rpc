package org.sonic.tcp.rpc.provider;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.TimeUtil;
import org.sonic.rpc.core.zookeeper.ZStringSerializer;


/**
 * Hello world!
 */
public class ZookeeperTest {
    public static void main(String[] args) throws Exception {

	// LogCore.BASE.info("zookeeper test");
	// ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
	// context.start();
	// PeopleController peopleController = context.getBean(PeopleController.class);
	//
	// context.close();
	String url = "123.56.13.70:2181,123.56.13.70:2182,123.56.13.70:2183";
	ZkClient zk = new ZkClient(url);// 连接
	zk.setZkSerializer(new ZStringSerializer());
	long time = zk.getCreationTime("/bao");// 获取某个节点的创建时间
	int count = zk.countChildren("/");// 节点数目
	boolean exist = zk.exists("/bao");// 节点是否存在
	// List<String> list = zk.getChildren("/");// 获取节点名称
//	List<String> list = zk.getChildren("/rpc/com/sonic/http/rpc/api/SpeakInterface");// 获取节点名称

//	 zk.createPersistent("/hello", "world");//创建节点,如果已经存在会报错
//	 zk.createPersistent("/hello1", "world1");//创建节点,如果已经存在会报错
//	 zk.createPersistent("/hullo1");//创建节点
	// zk.delete("/hullo");//删除节点 删第二次不会报错
	// zk.deleteRecursive("/hello");//递归删除

//	String value = zk.readData("/hello1");
//	String bao = zk.readData("/bao");
//	LogCore.BASE.info("children num={},childrens={}", count, list);
	LogCore.BASE.info("createTime={}", TimeUtil.SDF.get().format(new Date(time)));
//	LogCore.BASE.info("/hello1={},", value);
//	LogCore.BASE.info("/bao={}", bao);
	LogCore.BASE.info("/bao exist={}", exist);
	LogCore.BASE.info("/count={}", count);
	/* 监听子节点的创建删除 */
	zk.subscribeDataChanges("/bao", new IZkDataListener() {

	    @Override
	    public void handleDataDeleted(String dataPath) throws Exception {
		LogCore.BASE.info("{}   deleted", dataPath);
	    }

	    @Override
	    public void handleDataChange(String dataPath, Object data) throws Exception {
		LogCore.BASE.info("{} /{}  changed", dataPath, data);

	    }
	});
	/* 监听节点的值的改变 */
	zk.subscribeChildChanges("/bao", (String parentPath, List<String> currentChilds) -> {
	    LogCore.BASE.info("{} /{}  children changed", parentPath, currentChilds);

	});

	ConcurrentHashMap<Class<?>, Integer> INVOKE_COUNT_MAP = new ConcurrentHashMap<>();
	// INVOKE_COUNT_MAP.merge(Integer.class,Integer.MAX_VALUE, Integer::sum);
	int i = INVOKE_COUNT_MAP.merge(Integer.class, Integer.MAX_VALUE, Integer::sum);
	LogCore.BASE.info("i={}", i);
	LogCore.BASE.info("%={}", -4 % 2);
	LogCore.BASE.info("2<<<3={}", 1 << 5);
	LogCore.BASE.info(" {} MAX_VALUE= {}", Integer.MAX_VALUE, Integer.toBinaryString(Integer.MAX_VALUE));
	LogCore.BASE.info("{} MIN_VALUE={}", Integer.MIN_VALUE, Integer.toBinaryString(Integer.MIN_VALUE));
	LogCore.BASE.info("-1=                   {}", Integer.toBinaryString(-1));
	LogCore.BASE.info("1={}", Integer.toBinaryString(1));
	LogCore.BASE.info("");
	LogCore.BASE.info("");
	LogCore.BASE.info("-2={}", Integer.toBinaryString(-2));
	LogCore.BASE.info("2={}", Integer.toBinaryString(2));
	LogCore.BASE.info("2={}", Integer.toBinaryString(2));
	LogCore.BASE.info("OR 2={}", 2&Integer.MAX_VALUE);
	LogCore.BASE.info("OR -2={}", -2&Integer.MAX_VALUE);
	LogCore.BASE.info("OR -3={}", -3&Integer.MAX_VALUE);
	// ThreadTool.sleep(100000);

    }
}

package org.csdc.storage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
/**
 * Zookeeper集群工具类 （暂时未在DMSS中使用）
 * @author jintf
 * @date 2014-6-16
 */
public class ZkTool implements Watcher {
	
	private ZooKeeper zooKeeper;
	
	private static final int SESSION_TIEMOUT = 10000;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	public void connect(String host) throws IOException, InterruptedException{
		zooKeeper = new ZooKeeper(host, SESSION_TIEMOUT, this) ;
		latch.await();
	}
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getState() == KeeperState.SyncConnected){
			latch.countDown();
		}
	}
	
	public void close() throws InterruptedException{
		zooKeeper.close();
	}
	
	public void list(String path) throws KeeperException, InterruptedException{
		List<String> children = zooKeeper.getChildren(path, false);
		for(String child:children){
			System.out.println(child);
		}
	}
	
	public void delete(String path) throws KeeperException, InterruptedException{
		List<String> children = zooKeeper.getChildren(path, false);
		for (String child:children) {
			delete(path+"/"+child);
		}
		zooKeeper.delete(path, -1);
	}

}

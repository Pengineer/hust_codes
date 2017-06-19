package org.cdsc.storage;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.csdc.storage.ZkTool;
import org.junit.Test;

import junit.framework.TestListener;

public class ZkToolTest {
	
	public void testList() throws IOException, InterruptedException, KeeperException{
		ZkTool zkTool = new ZkTool();
		zkTool.connect("master:2180");
		zkTool.list("/configs/myconf");
		zkTool.close();
	}
	
	@Test
	public void testDelete() throws IOException, InterruptedException, KeeperException{
		ZkTool zkTool = new ZkTool();
		zkTool.connect("master:2180");
		zkTool.delete("/configs");
		zkTool.close();
	}
}

package worker;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;
import worker.base.ZKHandle;
import worker.bean.ControlledTrigger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linghang.kong on 2016/7/22.
 */
public class ZookeeperTest {
    @Test
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException, NoSuchAlgorithmException {
        String host = "10.11.58.89";
        String port = "2181";
        // 节点名称
        String znode = "/mytest/acltest2";
        ZooKeeper zooKeeper = new ZKHandle(host, port, 10000).getZooKeeper();
        // 权限参数
        String auth = "kong:secret";
        String scheme = "digest";
        // 产生摘要,userid:passwd_digest
        String digest = DigestAuthenticationProvider.generateDigest(auth);
        // 添加ACL权限
        // 授权
        zooKeeper.addAuthInfo(scheme, auth.getBytes());
        // IP地址来限制访问权限
        ACL acl1 = new ACL(ZooDefs.Perms.ALL, new Id("ipaddress", "10.11.58.0/24"));
        // 用户名密码限制访问权限
        ACL acl2 = new ACL(ZooDefs.Perms.ALL, new Id(scheme, digest));
        List aclList = new ArrayList<ACL>();
        aclList.add(acl2);
        zooKeeper.create(znode, "acldata".getBytes(), aclList, CreateMode.EPHEMERAL);
        System.out.println(new String(zooKeeper.getData(znode, false, null)));
        zooKeeper.close();
        System.exit(0);


//        List b = new ArrayList();
//        String[]a = (String[]) b.toArray(new String[b.size()]);


        AsynchController asynchController = new AsynchController(zooKeeper);
        // 事件对象 Controlled Trigger
        ControlledTrigger controlledTrigger = new ControlledTrigger();
        System.out.println(controlledTrigger.isChangedBit());
        asynchController.getDataFromZk(znode, controlledTrigger);
        //while (true) {
        Thread.sleep(5000);
        if (controlledTrigger.isChangedBit()) {
            System.out.println(new String((byte[]) controlledTrigger.getData()));
            System.out.println(controlledTrigger.getStat().toString());
            controlledTrigger.setChangedBit(false);
        } else
            System.out.println("no change.");
        //}
    }
}

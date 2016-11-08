package curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by linghang.kong on 2016/10/28.
 */
public class test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) throws Exception {
        String connectString = null;
        int sleepMsBetweenRetry = 1000;
        RetryPolicy retryPolicy = new RetryOneTime(sleepMsBetweenRetry);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectString, retryPolicy);

        // 与Zookeeper client直接相连，在断连的情况下，保证操作被执行。
//        CuratorZookeeperClient curatorZookeeperClient = new CuratorZookeeperClient();
        // 注册监听器
        curatorFramework.getCuratorListenable().addListener(new Listener());

        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/mytest", new byte[0]);
        curatorFramework.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).inBackground().forPath("/mytest", new byte[0]);

        // 使用监听
        curatorFramework.getData().watched().inBackground().forPath("/mytest");
//        // 使用重选Watcher
//        curatorFramework.getData().usingWatcher(new CuratorWatcher()).inBackground().forPath("/mytest");

        UnhandledErrorListener unhandledErrorListener = new UnhandledErrorListener() {
            @Override
            public void unhandledError(String s, Throwable throwable) {
                try {
                    logger.error("Message: {}, exception: {}.", s, throwable);
                } catch (Exception e) {

                }
            }
        };

        // 注册异常处理监听器
        curatorFramework.getUnhandledErrorListenable().addListener(unhandledErrorListener);

        curatorFramework.delete().guaranteed().inBackground().forPath("/mytest");

        // 群首

//        curatorFramework.getCuratorListenable().addListener();

//        MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);

    }
}

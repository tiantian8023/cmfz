package com.mp.cmfz.lock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZookeeperDistributeLock extends AbstractZookeeperLock {

    public ZookeeperDistributeLock(String lockName) {
        lock = lockName;
    }

    @Override
    protected boolean tryLock() {
        try {
            // 创建一个临时节点
            zkClient.createEphemeral(lock);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    protected void waitLock() {

        IZkDataListener listener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }

            }
        };
        // 1. 订阅数据变化
        zkClient.subscribeDataChanges(lock, listener);

        // 2. 判断锁节点是否存在
        if (zkClient.exists(lock)) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 3. 订阅取消
        zkClient.unsubscribeDataChanges(lock, listener);
    }
}

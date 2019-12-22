package com.mp.cmfz.lock;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

public abstract class AbstractZookeeperLock implements ZookeeperLock {

    protected String lock;

    protected String zk_address = "127.0.0.1:2181";

    protected ZkClient zkClient = new ZkClient(zk_address);

    //倒计数器
    protected CountDownLatch countDownLatch;

    @Override
    //加锁
    public final void lock() {
        // 尝试拿到锁
        if (tryLock()) {
            System.out.println("获取锁成功");
        } else {
            // 如果获取锁未成功,阻塞,等待获取锁
            waitLock();

            lock();
        }
    }

    @Override
    public final void unlock() {

        if (zkClient != null) {
            zkClient.close();
            System.out.println("解锁成功");
        }

    }

    protected abstract boolean tryLock();

    protected abstract void waitLock();
}

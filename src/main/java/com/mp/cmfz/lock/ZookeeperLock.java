package com.mp.cmfz.lock;

public interface ZookeeperLock {

    // 加锁
    public void lock();

    // 解锁
    public void unlock();
}

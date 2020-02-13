<!-- title: 分布式锁组件 -->
<!-- type: app -->
<!-- author: shuijing -->
<!-- date: 2019-11-17 -->
## 1. 组件介绍

此组件提供分布式锁能力，可以保证在分布式部署的应用集群中，同一个方法在同一操作只能被一台机器上的一个线程执行。
            
###  1.1 实现方式

本组件提供两种模式的分布式锁， redis 、zookeeper，项目基本都依赖redis，默认实现为redis，在引用`acooly-component-dubbo`的情况下可改用zookeeper模式

### 1.2 分布式锁提供具体能力

1. 锁是一把可重入锁,可以理解为分布式的 `ReentrantLock`
2. 锁是阻塞锁，同一时间只有一台机器的一个线程可以拿到锁
3. 保证在一个线程拿到锁后，突然宕机情况不会出现死锁现象

## 2. 使用说明

maven坐标：

     <dependency>
        <groupId>com.acooly</groupId>
        <artifactId>acooly-component-distributedlock</artifactId>
        <version>${acooly-latest-version}</version>
      </dependency>

`${acooly-latest-version}`为框架最新版本或者购买的版本。

### 2.1 什么情况下需要分布式锁

1. 举个栗子，手机用户可以在手机App端、网上营业厅、wap手厅进行流量业务的操作，如果发现该用户没有流量账户的时候，会首先给该用户创建一个专门的流量账户，如果用户在app端、网上营业厅端同时操作的时候，可能会给该用户创建2个账户;
2. 再举一个栗子，一个公共集团账户，下面包含很多账户，给下面账户充值的时候，会对该集团账本进行资金扣减，高并发多请求的时候会到导致并发失败，这时候为了减少失败率，我们目前的业务场景常用的是数据库排它锁(行锁select..for update)，分布式锁在此类场景同样适用.

### 2.2 配置说明

redis

* `acooly.distributedlock.mode=redis` 可选参数，分布式锁模式，默认为redis，可选值 zookeeper
* `acooly.distributedlock.redis.lockWatchdogTimeout=30000` 可选参数,单位ms，redis分布式锁模式，客户端宕机后，防止死锁，自动解锁超时时间，默认30000ms

zookeeper

* `acooly.distributedlock.mode=zookeeper` 可选参数，分布式锁模式，默认为redis，可选值 zookeeper
* `acooly.distributedlock.zookeeper.retryTimes=1000` 可选参数， zk连接故障时重试次数 默认1000次
* `acooly.distributedlock.zookeeper.sessionTimeoutMs=30000` 可选参数，zk session timeout ，单位ms，默认30000ms
* `acooly.distributedlock.zookeeper.connectionTimeoutMs=10000` 可选参数，zk connection timeout ，单位ms，默认10000ms
* `acooly.distributedlock.zookeeper.sleepMsBetweenRetries=1000` 可选参数，zk连接异常时，重试时间间隔,单位ms，默认1000ms

### 2.3 使用方法

```
    @Autowired
    private DistributedLockProperties properties;
    
    
    Lock lock = factory.newLock("lock4");
            lock.lock();
            try {
                // 执行一些需要加分布式锁的操作
            } finally {
                lock.unlock();
            }
            
    
    
    Lock lock = factory.newLock("lock5");
            if (lock.tryLock()) {
                try {
                    //拿到锁，执行一些需要加分布式锁的操作
                } finally {
                    lock.unlock();
                }
            } else {
                //执行另外操作
            }        
    
    
    
     Lock lock = factory.newLock("lock6");
            //在等待时间内得到锁
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    //拿到锁，执行一些需要加分布式锁的操作
                } finally {
                    lock.unlock();
                }
            } else {
                //执行另外操作
            }
    
    
    //重入锁
    Lock lock = factory.newLock("lock12");
    
            lock.lock();
            //拿到锁，执行一些需要加分布式锁的操作
            lock.lock();
            //重入，执行一些需要加分布式锁的操作
            
            //do something
            lock.unlock();
            
            //do something
            lock.unlock();//加锁几次，需要同一线程解锁几次

    //注意,只有加锁的线程才能解锁操作，所谓解铃还须系铃人

```

### 2.4 测试用例
 
 `com.acooly.core.test.distributedlock.RedisdtLockTest`
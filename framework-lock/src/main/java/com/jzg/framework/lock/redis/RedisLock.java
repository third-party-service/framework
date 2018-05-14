package com.jzg.framework.lock.redis;

import com.jzg.framework.cache.redis.RedisCache;
import com.jzg.framework.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Redis 分布式锁
 */
@Component("redisLock")
public class RedisLock {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);
    /**
     * 锁名称分隔符
     */
    private static final String LOCK_NAME_SPLIT = ":";
    /**
     * 心跳时间ms
     */
    private static final int HEARTBEET_MILLIS = 100;

    /*@Resource
    private ShardedJedisPool shardedJedisPool;*/

    /**
     * Redis缓存
     */
    @Resource
    private RedisCache redisCache;


    /**
     * 锁的前缀
     **/
    private String moduleName;
    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expire = 60 * 1000;
    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeout = 10 * 1000;

    /**
     * 加锁
     * @param lockKey 锁键值
     * @return 成功或失败
     * @throws Exception 异常
     */
    public boolean lock(String lockKey) throws Exception {
        String lockTime;
        long expireTime;
        int timeoutTime = timeout;
        long threadId = Thread.currentThread().getId();

        String lockValue;
        String lockName = moduleName + LOCK_NAME_SPLIT + lockKey;
        while (timeoutTime >= 0) {
            expireTime = System.currentTimeMillis() + expire + 1;
            lockValue = String.valueOf(expireTime) + LOCK_NAME_SPLIT + threadId;
            if (setLock(lockName, lockValue)) {
                return true;
            }
            lockTime = getLockTime(getLockValue(lockName));
            if (lockTime != null && Long.parseLong(lockTime) < System.currentTimeMillis()) {
                String lastTime = getAndSetLockValue(lockName, lockValue);
                if (lastTime != null && lastTime.equals(lockTime)) {
                    return true;
                }
            }
            timeoutTime -= HEARTBEET_MILLIS;
            Thread.sleep(HEARTBEET_MILLIS);
        }
        return false;
    }

    /**
     * 解锁
     * @param lockKey 锁键
     */
    public void unLock(String lockKey) {
        String lockName = moduleName + LOCK_NAME_SPLIT + lockKey;
        Long threadId = getLockThreadId(getLockValue(lockName));
        if (threadId == Thread.currentThread().getId()) {
            delLock(lockName);
        }
    }

    /**
     *
     * @param lockValue 锁值
     * @return
     */
    private String getLockTime(String lockValue) {
        if (StringUtils.isNotNull(lockValue)) {
            String[] lockValues = lockValue.split(LOCK_NAME_SPLIT);
            return lockValues[0];
        }
        return "0";
    }

    private Long getLockThreadId(String lockValue) {
        if (StringUtils.isNotNull(lockValue)) {
            String[] lockValues = lockValue.split(LOCK_NAME_SPLIT);
            if (lockValues.length >= 2) {
                return Long.parseLong(lockValues[1]);
            }
        }
        return 0L;
    }


    /*private ShardedJedis getRedisClient() {
        ShardedJedis shardJedis = this.shardedJedisPool.getResource();
        return shardJedis;
    }
    private void returnResource(ShardedJedis shardedJedis, boolean broken) {
        shardedJedis.close();
    }
    private void returnResource(ShardedJedis shardedJedis) {
        shardedJedis.close();
    }*/

    /**
     * 安全地设置分布式锁
     *
     * @param lockName
     * @param lockValue
     * @return
     */
    private boolean setLock(String lockName, String lockValue) {
        boolean success = false;
        try {
            success = redisCache.setnx(lockName, lockValue);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return success;

        /*boolean success = false;
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long rtnValue = shardedJedis.setnx(lockName, (String) lockValue);
            success = (rtnValue == 1 ? true : false);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
        }
        return success;*/
    }

    /**
     * 获取分布式锁对应的值
     *
     * @param lockName
     * @return
     */
    private String getLockValue(String lockName) {
        String result = "";
        try {
            result = redisCache.get(lockName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
        /*String result = null;
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.get(lockName);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
        }

        return result;*/
    }

    /**
     * 获取并设置分布式锁的值
     *
     * @param lockName
     * @param lockValue
     * @return 旧有存储的值
     */
    private String getAndSetLockValue(String lockName, String lockValue) {
        String result = "";
        try {
            result = redisCache.getSet(lockName, lockValue);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
        /*String result = null;
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.getSet(lockName, lockValue);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
        }

        return result;*/
    }

    /**
     * 删除分布式锁
     *
     * @param lockName
     */
    private boolean delLock(String lockName) {
        boolean success = false;
        try {
            success = redisCache.delete(lockName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return success;

        /*boolean success = false;
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long rtnValue = shardedJedis.del(lockName);
            success = rtnValue > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
        }

        return success;*/
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}

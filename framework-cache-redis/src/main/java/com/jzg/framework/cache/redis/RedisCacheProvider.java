package com.jzg.framework.cache.redis;

import com.jzg.framework.cache.annotation.ArgName;
import com.jzg.framework.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.io.Serializable;
import java.util.*;

/**
 * Redis缓存
 */
public class RedisCacheProvider implements RedisCache {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(RedisCacheProvider.class);

    /**
     * 哨兵连接池
     */
    private static JedisSentinelPool jedisSentinelPool;


    /**
     * 缓存名称
     */
    private String cacheName;

    public RedisCacheProvider() {
        this.cacheName = RedisCacheConstant.DEFAULT_CACHE_NAME;
        initSentinelPool();
    }

    /**
     * 构造函数
     *
     * @param cacheName 缓存名称
     */
    public RedisCacheProvider(String cacheName) {
        if (StringUtils.isEmpty(cacheName)) {
            this.cacheName = RedisCacheConstant.DEFAULT_CACHE_NAME;
        } else {
            this.cacheName = cacheName;
        }
        initSentinelPool();
    }

    /**
     * 构造函数
     *
     * @param cacheName       缓存名称
     * @param redisConfigPath Redis配置文件路径
     */
    public RedisCacheProvider(String cacheName, String redisConfigPath) {
        if (StringUtils.isEmpty(cacheName)) {
            this.cacheName = RedisCacheConstant.DEFAULT_CACHE_NAME;
        } else {
            this.cacheName = cacheName;
        }
        initSentinelPool(redisConfigPath);
    }

    /**
     * 初始化Redis连接池.
     */
    private static void initSentinelPool() {
        initSentinelPool("");
    }

    /**
     * 初始化Redis连接池.
     *
     * @param redisFilePath Redis配置文件路径
     */
    private static void initSentinelPool(String redisFilePath) {
        synchronized (RedisCacheProvider.class) {
            JedisPoolConfig config = new JedisPoolConfig();
            PropertiesUtil propertiesUtil = null;
            if (com.jzg.framework.utils.string.StringUtils.isEmpty(redisFilePath)) {
                propertiesUtil = new PropertiesUtil(RedisCacheConstant.REDIS_CONFIG_LOCATION_ROOT);
                if (propertiesUtil.getAllProperty().size() <= 0) {
                    logger.info("classPath下不存在" + RedisCacheConstant.REDIS_CONFIG_LOCATION_ROOT + "，尝试读取" + RedisCacheConstant.REDIS_CONFIG_LOCATION + "文件");
                    propertiesUtil = new PropertiesUtil(RedisCacheConstant.REDIS_CONFIG_LOCATION);
                }
            } else {
                propertiesUtil = new PropertiesUtil(redisFilePath);
            }

            if (propertiesUtil.getAllProperty().size() <= 0) {
                logger.error("没有获取到Redis配置文件，请查看classPath下redis.properites或conf/redis.properties是否存在");
                return;
            }

            //设置最大连接总数
            config.setMaxTotal(Integer.parseInt(propertiesUtil.getProperty("redis.maxTotal")));
            //设置最大空闲数
            config.setMaxIdle(Integer.parseInt(propertiesUtil.getProperty("redis.maxIdle")));
            //设置最小空闲数
            config.setMinIdle(Integer.parseInt(propertiesUtil.getProperty("redis.minIdle")));
            config.setMaxWaitMillis(Long.parseLong(propertiesUtil.getProperty("redis.maxWaitMillis")));
            //在获取连接的时候检查有效性, 默认false
            config.setTestOnBorrow(Boolean.parseBoolean(propertiesUtil.getProperty("redis.testOnBorrow")));
            //在空闲时检查有效性, 默认false
            config.setTestOnReturn(Boolean.parseBoolean(propertiesUtil.getProperty("redis.testOnReturn")));
            //是否启用pool的jmx管理功能, 默认true
            config.setJmxEnabled(true);
            //Idle时进行连接扫描
            config.setTestWhileIdle(Boolean.parseBoolean(propertiesUtil.getProperty("redis.testWhileIdle")));
            //是否启用后进先出, 默认true
            config.setLifo(true);
            //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
            config.setTimeBetweenEvictionRunsMillis(Long.parseLong(propertiesUtil.getProperty("redis.timeBetweenEvictionRunsMillis")));
            //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
            config.setNumTestsPerEvictionRun(Integer.parseInt(propertiesUtil.getProperty("redis.numTestsPerEvictionRun")));
            //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
            config.setMinEvictableIdleTimeMillis(Long.parseLong(propertiesUtil.getProperty("redis.minEvictableIdleTimeMillis")));
            //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
            config.setBlockWhenExhausted(true);
            //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
            config.setSoftMinEvictableIdleTimeMillis(Long.parseLong(propertiesUtil.getProperty("redis.softMinEvictableIdleTimeMillis")));

            //连接地址以及端口号,有多个就一次增加，Sentinel的地址
            Set<String> sentinelSet = new HashSet<>();
            //setinelSet.add("192.168.0.126:26379");
            Map<String, String> properties = propertiesUtil.getAllProperty();
            Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry.getKey().startsWith("redis.sentinel.host")) {
                    if (!sentinelSet.contains(entry.getValue())) {
                        String port = properties.get(entry.getKey().replace("host", "port"));
                        if (!StringUtils.isEmpty(port)) {
                            sentinelSet.add(entry.getValue() + ":" + port);
                            logger.info("Redis哨兵地址：" + entry.getValue() + ":" + port);
                        } else {
                            logger.error("请检查redis.sentinel.host与redis.sentinel.port是否成对存在");
                            return;
                        }
                    }
                }
            }

            if (sentinelSet.size() <= 0) {
                logger.error("请检查redis.sentinel.host与redis.sentinel.port是否存在");
                return;
            }

            try {
                jedisSentinelPool = new JedisSentinelPool(propertiesUtil.getProperty("redis.sentinel.mastername"), sentinelSet, config, RedisCacheConstant.DEFAULT_POOL_CONNECT_TIMEOUT);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }

            if (jedisSentinelPool != null) {
                logger.info("Redis连接池已创建完毕");
            } else {
                logger.error("Redis连接池初始化失败");
            }
        }
    }

    /**
     * 获取Jedis连接
     *
     * @return Jedis连接
     */
    private Jedis getRedisClient() {
        Jedis jedis = jedisSentinelPool.getResource();
        return jedis;
    }

    /**
     * 关闭连接
     *
     * @param jedis jedis
     */
    private void returnResource(Jedis jedis) {
        //jedisSentinelPool.returnResource(jedis);
        jedis.close();
    }

    /**
     * 关闭连接
     *
     * @param jedis  jedis
     * @param broken 是否强制关闭
     */
    private void returnResource(Jedis jedis, boolean broken) {
        jedis.close();
        /*if (broken) {
            jedisSentinelPool.returnBrokenResource(jedis);
        } else {
            jedisSentinelPool.returnResource(jedis);
        }*/
    }


    /**
     * 通过Key获取缓存
     *
     * @param key
     * @return 字符串
     */
    @Override
    public <T extends Serializable> T get(String key) throws Exception {
        String result = null;
        Jedis jedis = this.getRedisClient();
        try {
            result = jedis.get(key);
        } finally {
            returnResource(jedis);
        }

        return (T) result;
    }


    /**
     * 通过keys获取列表
     *
     * @param keys
     * @return
     */
    @Override
    public <T extends Serializable> Map<String, T> get(List<String> keys) throws Exception {
        Map<String, T> map = new HashMap<>();
        Jedis jedis = this.getRedisClient();
        try {
            for (int i = 0; i < keys.size(); i++) {
                T tmp = get(keys.get(i));
                map.put(keys.get(i), tmp);
            }
        } finally {
            returnResource(jedis);
        }

        return map;

        //throw new Exception("not implement");
    }

    /**
     * 设置缓存 对象
     *
     * @param key
     * @param t
     */
    @Override
    public <T extends Serializable> boolean set(String key, T t) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            if (t instanceof String) {
                String str = jedis.set(key, (String) t);
                bRet = "OK".equals(str) ? true : false;
            } else {
                throw new Exception("t is not string. if t is object, please use setObject");
            }
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 设置缓存
     *
     * @param key    键
     * @param expire 缓存时间(s)
     * @param t      缓存值
     * @return
     * @throws Exception
     */
    @Override
    public <T extends Serializable> boolean set(String key, int expire, T t) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            if (t instanceof String) {
                String str = jedis.setex(key, expire, (String) t);
                bRet = "OK".equals(str) ? true : false;
            } else {
                throw new Exception("t is not string. if t is object, please use setObject");
            }
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 设置缓存
     *
     * @param key    键
     * @param t      缓存值
     * @param expire 缓存时间(s)
     * @return 缓存对象
     * @throws Exception 异常
     */
    @Override
    public <T extends Serializable> boolean set(@ArgName("key") String key, T t, int expire) throws Exception {
        return set(key, expire, t);
    }

    /**
     * 增加数量
     *
     * @param key
     * @param val
     * @return
     * @throws Exception
     */
    @Override
    public long incr(String key, long val) throws Exception {
        long nRet = 0L;
        Jedis jedis = this.getRedisClient();

        try {
            nRet = jedis.incrBy(key, val);
        } finally {
            returnResource(jedis);
        }

        return nRet;
    }

    /**
     * 增加数量 +1
     *
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public long incr(String key) throws Exception {
        return incr(key, 1);
    }

    /**
     * 减少数量
     *
     * @param key
     * @param val
     * @return
     * @throws Exception
     */
    @Override
    public long decr(String key, long val) throws Exception {
        long nRet = 0L;
        Jedis jedis = this.getRedisClient();

        try {
            nRet = jedis.decrBy(key, val);
        } finally {
            returnResource(jedis);
        }

        return nRet;
    }

    /**
     * 减少数量  -1
     *
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public long decr(String key) throws Exception {
        return decr(key, 1);
    }

    /**
     * 移除（删除）缓存
     *
     * @param key
     */
    @Override
    public boolean delete(String key) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            Long nRet = jedis.del(key);
            bRet = nRet > 0 ? true : false;
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 批量删除键值
     *
     * @param keys 多个键值
     * @return 成功或失败
     * @throws Exception 异常
     */
    @Override
    public boolean delete(@ArgName("keys") String... keys) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            if (jedis.del(keys) > 0) {
                bRet = true;
            }
        } finally {
            returnResource(jedis);
        }
        return bRet;
    }

    /**
     * 清空缓存
     */
    @Deprecated
    @Override
    public void clear() throws Exception {
        Jedis jedis = this.getRedisClient();
        try {
            String result = jedis.flushAll();
            boolean bRet = "OK".equals(result) ? true : false;
            if (bRet) {
                logger.info("清除redis所有键值成功");
            } else {
                logger.info("清除redis所有键值失败");
            }
        } finally {
            returnResource(jedis);
        }
    }


    /**
     * 更新缓存超时时间
     *
     * @param key       键
     * @param newExpire 超时时间
     * @return
     * @throws Exception
     */
    @Override
    public boolean touch(String key, int newExpire) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();

        try {
            Long nRet = jedis.expire(key, newExpire);
            bRet = nRet > 0 ? true : false;
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 获取缓存数据并更新超时时间
     *
     * @param key       键
     * @param newExpire 新缓存时间
     * @return
     * @throws Exception
     */
    @Override
    public <T extends Serializable> T getAndTouch(String key, int newExpire) throws Exception {
        String result = null;
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();

        try {
            Long nRet = jedis.expire(key, newExpire);
            bRet = nRet > 0 ? true : false;
            if (bRet) {
                result = get(key);
            }
        } finally {
            returnResource(jedis);
        }

        return (T) result;
    }

    /**
     * 设置序列化对象
     *
     * @param key
     * @param t
     * @return
     * @throws Exception
     */
    @Override
    public <T extends Serializable, Object> boolean setObject(String key, T t) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            ObjectsTranscoder<Serializable> transcoder = new ObjectsTranscoder<>();
            byte[] bytes = transcoder.serialize(t);
            String str = jedis.set(key.getBytes(), bytes);
            bRet = "OK".equals(str) ? true : false;
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 缓存List对象
     *
     * @param key
     * @param list
     * @return
     * @throws Exception
     */
    @Override
    public <T extends Serializable, Object> boolean setObjectList(String key, List<T> list) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            ListTranscoder<T> transcoder = new ListTranscoder<>();
            byte[] bytes = transcoder.serialize(list);
            String str = jedis.set(key.getBytes(), bytes);
            bRet = "OK".equals(str) ? true : false;
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }


    /**
     * 获取缓存值
     *
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public <T extends Serializable, Object> T getObject(String key) throws Exception {
        T result = null;

        Jedis jedis = this.getRedisClient();
        try {
            byte[] bytes = jedis.get(key.getBytes());
            ObjectsTranscoder<T> transcoder = new ObjectsTranscoder<>();
            result = transcoder.deserialize(bytes);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 获取List对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    @Override
    public <T extends Serializable, Object> List<T> getObjectList(String key) throws Exception {
        List<T> result = new ArrayList<>();

        Jedis jedis = this.getRedisClient();
        try {
            byte[] bytes = jedis.get(key.getBytes());
            ListTranscoder<T> transcoder = new ListTranscoder<>();
            result = transcoder.deserialize(bytes);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 设置指定 key 的值，并返回 key 的旧值
     *
     * @param key 键
     * @param val 值
     * @return 老的键值
     */
    @Override
    public String getSet(String key, String val) throws Exception {
        String result = null;
        Jedis jedis = this.getRedisClient();
        try {
            result = jedis.getSet(key, val);
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 也就是只有键不存在的时候才设置；如果 key 已经存在，返回 0， nx 是 not exist 的意思。
     *
     * @param key 键
     * @param val 值
     * @return 是否成功
     */
    @Override
    public boolean setnx(@ArgName("key") String key, @ArgName("val") String val) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            long nRet = jedis.setnx(key, val);
            bRet = nRet > 0 ? true : false;
        } finally {
            returnResource(jedis);
        }
        return bRet;
    }

    /**
     * 批量设置键值对  jedis.mset("key01","value01","key02","value02","key03","value03"));
     *
     * @param keysvals 键值对
     * @return 成功或失败
     * @throws Exception 异常
     */
    @Override
    public boolean mset(String... keysvals) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            String str = jedis.mset(keysvals);
            bRet = "OK".equals(str) ? true : false;
        } finally {
            returnResource(jedis);
        }
        return bRet;
    }

    /**
     * 批量设置NX
     *
     * @param keysvals 键值对
     * @return 成功或失败
     * @throws Exception 异常
     */
    @Override
    public boolean msetnx(String... keysvals) throws Exception {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            long nRet = jedis.msetnx(keysvals);
            bRet = nRet > 0 ? true : false;
        } finally {
            returnResource(jedis);
        }
        return bRet;
    }

    /**
     * 获取键集合，正则匹配
     *
     * @param pattern 正则表达式
     * @return 键集合
     */
    @Override
    public Set<String> keys(String pattern) throws Exception {
        Set<String> set = new HashSet<>();
        Jedis jedis = this.getRedisClient();
        try {
            set = jedis.keys(pattern);
        } finally {
            returnResource(jedis);
        }
        return set;
    }

    /**
     * 同时将多个 field-val (域-值)对设置到哈希表 key 中。
     *
     * @param key
     * @param map
     * @return
     */
    @Override
    public boolean hmset(String key, Map<String, String> map) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            String str = jedis.hmset(key, map);
            logger.debug("hmset: " + str);
            bRet = "OK".equals(str) ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 val 。
     *
     * @param key
     * @param filed
     * @param val
     * @return
     */
    @Override
    public boolean hset(String key, String filed, String val) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            long nRet = jedis.hset(key, filed, val);
            logger.debug("hset: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key    键
     * @param fields hash字段
     * @return
     */
    @Override
    public List<String> hmget(String key, String... fields) {
        List<String> list = new ArrayList<>();
        Jedis jedis = this.getRedisClient();
        try {
            list = jedis.hmget(key, fields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return list;
    }

    /**
     * 获取给定字段的值
     *
     * @param key   键
     * @param field hash字段
     * @return
     */
    @Override
    public String hget(String key, String field) {
        String result = null;
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();

        try {
            result = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    @Override
    public long hlen(String key) {
        long nRet = 0L;
        Jedis jedis = this.getRedisClient();
        try {
            nRet = jedis.hlen(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return nRet;
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在。
     *
     * @param key
     * @param feild
     * @return
     */
    @Override
    public boolean hexists(String key, String feild) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            bRet = jedis.hexists(key, feild);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key
     * @return
     */
    @Override
    public Set<String> hkeys(String key) {
        Set<String> set = null;
        Jedis jedis = this.getRedisClient();
        try {
            set = jedis.hkeys(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return set;
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key
     * @return
     */
    @Override
    public List<String> hvals(String key) {
        List<String> list = null;
        Jedis jedis = this.getRedisClient();
        try {
            list = jedis.hvals(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return list;
    }

    /**
     * 删除给定字段
     *
     * @param key    键
     * @param fields hash字段
     * @return
     */
    @Override
    public boolean hdel(String key, String... fields) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            long nRet = jedis.hdel(key, fields);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 在List头部添加一个或多个值
     *
     * @param key
     * @param vals
     * @return
     */
    @Override
    public boolean lpush(String key, String... vals) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            Long nRet = jedis.lpush(key, vals);
            logger.debug("lpush: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 在List尾部添加一个或多个值
     *
     * @param key
     * @param vals
     * @return
     */
    @Override
    public boolean rpush(String key, String... vals) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            Long nRet = jedis.rpush(key, vals);
            logger.debug("rpush: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    @Override
    public boolean lset(String key, long index, String value) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            String str = jedis.lset(key, index, value);
            logger.debug("lset: " + str);
            bRet = "OK".equals(str) ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 按范围取出list数据
     *
     * @param key   键
     * @param start 起始位置
     * @param end   -1 表示取出所有
     * @return
     */
    @Override
    public List<String> lrange(String key, int start, int end) {
        List<String> list = new ArrayList<>();
        Jedis jedis = this.getRedisClient();
        try {
            list = jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return list;
    }

    /**
     * 通过索引获取列表中的元素。你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key
     * @param index
     * @return 列表中下标为指定索引值的元素。 如果指定索引值不在列表的区间范围内，返回 nil
     */
    @Override
    public String lindex(String key, int index) {
        String result = null;
        Jedis jedis = this.getRedisClient();
        try {
            result = jedis.lindex(key, index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 从list头部取值
     *
     * @param key
     * @return
     */
    @Override
    public String lpop(String key) {
        String result = null;
        Jedis jedis = this.getRedisClient();
        try {
            result = jedis.lpop(key);
            logger.debug("lpop: " + result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 从list尾部取值
     *
     * @param key
     * @return
     */
    @Override
    public String rpop(String key) {
        String result = null;
        Jedis jedis = this.getRedisClient();
        try {
            result = jedis.rpop(key);
            logger.debug("rpop: " + result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 从list中移除value对应元素
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    @Override
    public boolean lrem(String key, long count, String value) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            long nRet = jedis.lrem(key, count, value);
            logger.debug("lrem: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public boolean ltrim(String key, long start, long end) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            String str = jedis.ltrim(key, start, end);
            logger.debug("lset: " + str);
            bRet = "OK".equals(str) ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略。
     * 假如集合 key 不存在，则创建一个只包含添加的元素作成员的集合。
     * 当集合 key 不是集合类型时，返回一个错误。
     *
     * @param key
     * @param members
     * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。若 > 0 返回true
     */
    @Override
    public boolean sadd(String key, String... members) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            Long nRet = jedis.sadd(key, members);
            logger.debug("sadd: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 返回集合中的所有的成员。 不存在的集合 key 被视为空集合。
     *
     * @param key
     * @return 集合中的所有成员。
     */
    @Override
    public Set<String> smembers(String key) {
        Set<String> set = null;
        Jedis jedis = this.getRedisClient();
        try {
            set = jedis.smembers(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return set;
    }

    /**
     * 返回Set中元素数量
     *
     * @param key
     * @return 集合的数量。 当集合 key 不存在时，返回 0 。
     */
    @Override
    public long scard(String key) {
        long nRet = 0;
        Jedis jedis = this.getRedisClient();
        try {
            nRet = jedis.scard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return nRet;
    }

    /**
     * 从set头部取值
     *
     * @param key
     * @return
     */
    @Override
    public String spop(String key) {
        String result = null;
        Jedis jedis = this.getRedisClient();
        try {
            result = jedis.spop(key);
            logger.debug("spop: " + result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 从set头部取值
     *
     * @param key   键
     * @param count 取值数量
     * @return
     */
    @Override
    public Set<String> spop(String key, long count) {
        Set<String> result = null;
        Jedis jedis = this.getRedisClient();
        try {
            result = jedis.spop(key, count);
            logger.debug("spop: " + result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 从set中移除members对应元素
     *
     * @param key
     * @param members
     * @return
     */
    @Override
    public boolean srem(String key, String... members) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            Long nRet = jedis.srem(key, members);
            logger.debug("srem: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 用于将一个或多个成员元素及其分数值加入到有序集当中。
     * 如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。
     * 分数值可以是整数值或双精度浮点数。
     * 如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key
     * @param score
     * @param member
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。若 > 0 返回true
     */
    @Override
    public boolean zadd(String key, double score, String member) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            Long nRet = jedis.zadd(key, score, member);
            logger.debug("zadd count: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 用于将一个或多个成员元素及其分数值加入到有序集当中。
     *
     * @param key
     * @param scoreMembers
     * @return
     */
    @Override
    public boolean zadd(String key, Map<String, Double> scoreMembers) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            Long nRet = jedis.zadd(key, scoreMembers);
            logger.debug("zadd count: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    /**
     * 取set中start至end之间的值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<String> zrange(String key, long start, long end) {
        Set<String> result = null;
        Jedis jedis = this.getRedisClient();
        try {
            result = jedis.zrange(key, start, end);
            logger.debug("spop: " + result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return result;
    }

    /**
     * 计算集合中元素的数量
     *
     * @param key
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。 当 key 不存在时，返回 0 。
     */
    @Override
    public long zcard(String key) {
        long nRet = 0;
        Jedis jedis = this.getRedisClient();
        try {
            nRet = jedis.zcard(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return nRet;
    }

    /**
     * 从set中移除members对应元素
     *
     * @param key
     * @param members
     * @return
     */
    @Override
    public boolean zrem(String key, String... members) {
        boolean bRet = false;
        Jedis jedis = this.getRedisClient();
        try {
            Long nRet = jedis.zrem(key, members);
            logger.debug("zadd count: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }

        return bRet;
    }

    @Override
    public long exprie(String key, int seconds) {
        long expireLong = 0;
        Jedis jedis = this.getRedisClient();
        try {
            expireLong = jedis.expire(key, seconds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return expireLong;
    }

    /**
     * 查看键的剩余生存时间
     *
     * @param key 键
     * @return 键的剩余生存时间
     */
    @Override
    public long ttl(@ArgName("key") String key) {
        long ttlLong = 0;
        Jedis jedis = this.getRedisClient();
        try {
            ttlLong = jedis.ttl(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return ttlLong;
    }

    /**
     * 移除键的生存时间
     *
     * @param key 键
     * @return 键的生存时间
     */
    @Override
    public long persist(@ArgName("key") String key) {
        long persistLong = 0;
        Jedis jedis = this.getRedisClient();
        try {
            persistLong = jedis.persist(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(jedis);
        }
        return persistLong;
    }
}






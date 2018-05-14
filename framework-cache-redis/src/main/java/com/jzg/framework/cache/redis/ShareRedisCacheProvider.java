


package com.jzg.framework.cache.redis;

import com.jzg.framework.cache.annotation.ArgName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * Redis缓存
 */
//@Component("shareRedisCache")
public class ShareRedisCacheProvider implements RedisCache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheProvider.class);

    @Resource
    private ShardedJedisPool shardedJedisPool;

    private String cacheName;

    public ShareRedisCacheProvider() {
        this.cacheName = "shareRedisCache";
    }

    public ShareRedisCacheProvider(String cacheName) {
        if (StringUtils.isEmpty(cacheName)) {
            this.cacheName = "shareRedisCache";
        } else {
            this.cacheName = cacheName;
        }
    }

    private ShardedJedis getRedisClient() {
        ShardedJedis shardJedis = shardedJedisPool.getResource();
        return shardJedis;
    }

    private void returnResource(ShardedJedis shardedJedis) {
        //shardedJedisPool.returnResource(shardedJedis);
        shardedJedis.close();
    }

    private void returnResource(ShardedJedis shardedJedis, boolean broken) {
        shardedJedis.close();
        /*if (broken) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.get(key);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            for (int i = 0; i < keys.size(); i++) {
                T tmp = get(keys.get(i));
                map.put(keys.get(i), tmp);
            }
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            if (t instanceof String) {
                String str = shardedJedis.set(key, (String) t);
                bRet = "OK".equals(str) ? true : false;
            } else {
                throw new Exception("t is not string. if t is object, please use setObject");
            }
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            if (t instanceof String) {
                String str = shardedJedis.setex(key, expire, (String) t);
                bRet = "OK".equals(str) ? true : false;
            } else {
                throw new Exception("t is not string. if t is object, please use setObject");
            }
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();

        try {
            nRet = shardedJedis.incrBy(key, val);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();

        try {
            nRet = shardedJedis.decrBy(key, val);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long nRet = shardedJedis.del(key);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        throw new Exception("此方法无法实现");
    }

    /**
     * 清空缓存
     */
    @Deprecated
    @Override
    public void clear() throws Exception {
        throw new Exception("此方法无法实现");
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
        ShardedJedis shardedJedis = this.getRedisClient();

        try {
            Long nRet = shardedJedis.expire(key, newExpire);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();

        try {
            Long nRet = shardedJedis.expire(key, newExpire);
            bRet = nRet > 0 ? true : false;
            if (bRet) {
                result = get(key);
            }
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            ObjectsTranscoder<Serializable> transcoder = new ObjectsTranscoder<>();
            byte[] bytes = transcoder.serialize(t);
            String str = shardedJedis.set(key.getBytes(), bytes);
            bRet = "OK".equals(str) ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            ListTranscoder<T> transcoder = new ListTranscoder<>();
            byte[] bytes = transcoder.serialize(list);
            String str = shardedJedis.set(key.getBytes(), bytes);
            bRet = "OK".equals(str) ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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

        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            byte[] bytes = shardedJedis.get(key.getBytes());
            ObjectsTranscoder<T> transcoder = new ObjectsTranscoder<>();
            result = transcoder.deserialize(bytes);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
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

        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            byte[] bytes = shardedJedis.get(key.getBytes());
            ListTranscoder<T> transcoder = new ListTranscoder<>();
            result = transcoder.deserialize(bytes);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.getSet(key, val);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            long nRet = shardedJedis.setnx(key, val);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
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
        throw new Exception("此方法无法实现");
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
        throw new Exception("此方法无法实现");
    }


    /**
     * 获取键集合，正则匹配
     *
     * @param pattern 正则表达式
     * @return 键集合
     */
    @Deprecated
    @Override
    public Set<String> keys(String pattern) throws Exception {
        throw new Exception("此方法无法实现");
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     *
     * @param key
     * @param map
     * @return
     */
    @Override
    public boolean hmset(String key, Map<String, String> map) {
        boolean bRet = false;
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            String str = shardedJedis.hmset(key, map);
            System.out.println("hmset: " + str);
            bRet = "OK".equals(str) ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
        }

        return bRet;
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 value 。
     *
     * @param key
     * @param filed
     * @param val
     * @return
     */
    @Override
    public boolean hset(String key, String filed, String val) {
        boolean bRet = false;
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            long nRet = shardedJedis.hset(key, filed, val);
            System.out.println("hset: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            list = shardedJedis.hmget(key, fields);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();

        try {
            result = shardedJedis.hget(key, field);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            nRet = shardedJedis.hlen(key);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            bRet = shardedJedis.hexists(key, feild);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            set = shardedJedis.hkeys(key);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            list = shardedJedis.hvals(key);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            long nRet = shardedJedis.hdel(key, fields);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long nRet = shardedJedis.lpush(key, vals);
            System.out.println("lpush: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long nRet = shardedJedis.rpush(key, vals);
            System.out.println("rpush: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            String str = shardedJedis.lset(key, index, value);
            System.out.println("lset: " + str);
            bRet = "OK".equals(str) ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            list = shardedJedis.lrange(key, start, end);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.lindex(key, index);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
            throw e;
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.lpop(key);
            System.out.println("lpop: " + result);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.rpop(key);
            System.out.println("rpop: " + result);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            long nRet = shardedJedis.lrem(key, count, value);
            System.out.println("lrem: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            String str = shardedJedis.ltrim(key, start, end);
            System.out.println("lset: " + str);
            bRet = "OK".equals(str) ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long nRet = shardedJedis.sadd(key, members);
            System.out.println("sadd: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            set = shardedJedis.smembers(key);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            nRet = shardedJedis.scard(key);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.spop(key);
            System.out.println("spop: " + result);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.spop(key, count);
            System.out.println("spop: " + result);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long nRet = shardedJedis.srem(key, members);
            System.out.println("srem: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long nRet = shardedJedis.zadd(key, score, member);
            System.out.println("zadd count: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long nRet = shardedJedis.zadd(key, scoreMembers);
            System.out.println("zadd count: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            result = shardedJedis.zrange(key, start, end);
            System.out.println("spop: " + result);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            nRet = shardedJedis.zcard(key);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            Long nRet = shardedJedis.zrem(key, members);
            System.out.println("zadd count: " + nRet);
            bRet = nRet > 0 ? true : false;
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
        }

        return bRet;
    }

    @Override
    public long exprie(String key, int seconds) {
        long expireLong = 0;
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            expireLong = shardedJedis.expire(key, seconds);
        } catch (Exception e) {
            returnResource(shardedJedis, true);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            ttlLong = shardedJedis.ttl(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(shardedJedis);
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
        ShardedJedis shardedJedis = this.getRedisClient();
        try {
            persistLong = shardedJedis.persist(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            returnResource(shardedJedis);
        }
        return persistLong;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}

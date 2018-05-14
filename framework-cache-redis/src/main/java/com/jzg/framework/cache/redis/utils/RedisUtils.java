package com.jzg.framework.cache.redis.utils;

import com.jzg.framework.cache.CacheFactory;
import com.jzg.framework.cache.redis.DefaultRedisCacheFactory;
import com.jzg.framework.cache.redis.RedisCache;
import com.jzg.framework.cache.redis.RedisCacheConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: Create By IDEA
 * @author: JZG
 * @date: 2017/12/12 15:15
 */
public final class RedisUtils {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * Redis缓存
     */
    private static RedisCache redisCache;

    private RedisUtils() {
    }

    static {
        synchronized (RedisUtils.class) {
            CacheFactory factory = new DefaultRedisCacheFactory();
            redisCache = (RedisCache) factory.getCache(RedisCacheConstant.DEFAULT_CACHE_NAME);
        }
    }

    /**
     * 通过Key获取缓存
     *
     * @param key key
     * @return 字符串
     */
    public static String get(String key) {
        String result = null;
        try {
            result = redisCache.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * 通过keys获取列表
     *
     * @param keys keys
     * @param <T>  t
     * @return map
     */
    public static <T extends Serializable> Map<String, T> get(List<String> keys) {
        Map<String, T> map = new HashMap<>();
        try {
            map = redisCache.get(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 设置缓存 对象
     *
     * @param key key
     * @param val val
     * @return boolean
     */
    public static boolean set(String key, String val) {
        boolean bRet = false;
        try {
            bRet = redisCache.set(key, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }

    /**
     * 设置缓存
     *
     * @param key    键
     * @param expire 缓存时间(s)
     * @param val    缓存值
     * @return boolean
     */
    public static boolean set(String key, int expire, String val) {
        boolean bRet = false;
        try {
            bRet = redisCache.set(key, expire, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }

    /**
     * 设置缓存
     *
     * @param key    键
     * @param expire 缓存时间(s)
     * @param val    缓存值
     * @return boolean
     */
    public static boolean set(String key, String val, int expire) {
        return set(key, expire, val);
    }

    /**
     * 增加数量
     *
     * @param key key
     * @param val val
     * @return 增加后数值
     */
    public static long incr(String key, long val) {
        long nRet = 0L;

        try {
            nRet = redisCache.incr(key, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return nRet;
    }

    /**
     * 增加数量 +1
     *
     * @param key key
     * @return 增加后数值
     */
    public static long incr(String key) {
        return incr(key, 1);
    }

    /**
     * 减少数量
     *
     * @param key key
     * @param val val
     * @return 减少后数值
     */
    public static long decr(String key, long val) {
        long nRet = 0L;
        try {
            nRet = redisCache.decr(key, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return nRet;
    }

    /**
     * 减少数量  -1
     *
     * @param key key
     * @return 减少后数值
     */
    public static long decr(String key) {
        return decr(key, 1);
    }

    /**
     * 移除（删除）缓存
     *
     * @param key key
     * @return boolean
     */
    public static boolean delete(String key) {
        boolean bRet = false;
        try {
            bRet = redisCache.delete(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }

    /**
     * 批量删除键值
     *
     * @param keys 多个键值
     * @return 成功或失败
     */
    public static boolean delete(String... keys) {
        boolean bRet = false;
        try {
            bRet = redisCache.delete(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }


    /**
     * 更新缓存超时时间
     *
     * @param key       键
     * @param newExpire 超时时间
     * @return boolean
     */
    public static boolean touch(String key, int newExpire) {
        boolean bRet = false;
        try {
            bRet = redisCache.touch(key, newExpire);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }

    /**
     * 获取缓存数据并更新超时时间
     *
     * @param key       键
     * @param newExpire 新缓存时间
     * @param <T>       t
     * @return t
     */
    public static <T extends Serializable> T getAndTouch(String key, int newExpire) {
        String result = null;

        try {
            result = redisCache.getAndTouch(key, newExpire);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return (T) result;
    }

    /**
     * 设置序列化对象
     *
     * @param key      key
     * @param t        t
     * @param <T>      t
     * @param <Object> object
     * @return boolean
     */
    public static <T extends Serializable, Object> boolean setObject(String key, T t) {
        boolean bRet = false;
        try {
            bRet = redisCache.setObject(key, t);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }


    /**
     * 获取缓存值
     *
     * @param key      key
     * @param <T>      t
     * @param <Object> object
     * @return t
     */
    public static <T extends Serializable, Object> T getObject(String key) {
        T result = null;

        try {
            result = redisCache.getObject(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
    public static String getSet(String key, String val) {
        String result = null;
        try {
            result = redisCache.getSet(key, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
    public boolean setnx(String key, String val) {
        boolean bRet = false;
        try {
            bRet = redisCache.setnx(key, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }

    /**
     * 批量设置键值对  jedis.mset("key01","value01","key02","value02","key03","value03"));
     *
     * @param keysvals 键值对
     * @return 成功或失败
     */
    public static boolean mset(String... keysvals) {
        boolean bRet = false;
        try {
            bRet = redisCache.mset(keysvals);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }

    /**
     * 批量设置NX
     *
     * @param keysvals 键值对
     * @return 成功或失败
     */
    public static boolean msetnx(String... keysvals) {
        boolean bRet = false;
        try {
            bRet = redisCache.msetnx(keysvals);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bRet;
    }

    /**
     * 获取键集合，正则匹配
     *
     * @param pattern 正则表达式
     * @return 键集合
     */
    public static Set<String> keys(String pattern) {
        Set<String> set = null;
        try {
            set = redisCache.keys(pattern);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return set;
    }

    /**
     * 同时将多个 field-val (域-值)对设置到哈希表 key 中。
     *
     * @param key key
     * @param map map
     * @return boolean
     */
    public static boolean hmset(String key, Map<String, String> map) {
        return redisCache.hmset(key, map);
    }

    /**
     * 将哈希表 key 中的字段 field 的值设为 val 。
     *
     * @param key   key
     * @param filed filed
     * @param val   val
     * @return boolean
     */
    public static boolean hset(String key, String filed, String val) {
        return redisCache.hset(key, filed, val);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key    键
     * @param fields hash字段
     * @return list
     */
    public static List<String> hmget(String key, String... fields) {
        return redisCache.hmget(key, fields);
    }

    /**
     * 获取给定字段的值
     *
     * @param key   键
     * @param field hash字段
     * @return string
     */
    public static String hget(String key, String field) {
        return redisCache.hget(key, field);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key key
     * @return long
     */
    public static long hlen(String key) {
        return redisCache.hlen(key);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在。
     *
     * @param key   key
     * @param feild feild
     * @return boolean
     */
    public static boolean hexists(String key, String feild) {
        return redisCache.hexists(key, feild);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key key
     * @return set
     */
    public static Set<String> hkeys(String key) {
        return redisCache.hkeys(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key key
     * @return list
     */
    public static List<String> hvals(String key) {
        return redisCache.hvals(key);
    }

    /**
     * 删除给定字段
     *
     * @param key    键
     * @param fields hash字段
     * @return boolean
     */
    public static boolean hdel(String key, String... fields) {
        return redisCache.hdel(key, fields);
    }

    /**
     * 在List头部添加一个或多个值
     *
     * @param key key
     * @param vals vals
     * @return boolean
     */
    public static boolean lpush(String key, String... vals) {
        return redisCache.lpush(key, vals);
    }

    /**
     * 在List尾部添加一个或多个值
     *
     * @param key key
     * @param vals vals
     * @return boolean
     */
    public static boolean rpush(String key, String... vals) {
        return redisCache.rpush(key, vals);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   key
     * @param index index
     * @param value value
     * @return boolean
     */
    public static boolean lset(String key, long index, String value) {
        return redisCache.lset(key, index, value);
    }

    /**
     * 按范围取出list数据
     *
     * @param key   键
     * @param start 起始位置
     * @param end   -1 表示取出所有
     * @return list
     */
    public static List<String> lrange(String key, int start, int end) {
        return redisCache.lrange(key, start, end);
    }

    /**
     * 通过索引获取列表中的元素。你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key   key
     * @param index index
     * @return 列表中下标为指定索引值的元素。 如果指定索引值不在列表的区间范围内，返回 nil
     */
    public static String lindex(String key, int index) {
        return redisCache.lindex(key, index);
    }

    /**
     * 从list头部取值
     *
     * @param key key
     * @return string
     */
    public static String lpop(String key) {
        return redisCache.lpop(key);
    }

    /**
     * 从list尾部取值
     *
     * @param key key
     * @return string
     */
    public static String rpop(String key) {
        return redisCache.rpop(key);
    }

    /**
     * 从list中移除value对应元素
     *
     * @param key   key
     * @param count count
     * @param value value
     * @return boolean
     */
    public static boolean lrem(String key, long count, String value) {
        return redisCache.lrem(key, count, value);
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return boolean
     */
    public static boolean ltrim(String key, long start, long end) {
        return redisCache.ltrim(key, start, end);
    }

    /**
     * 将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略。
     * 假如集合 key 不存在，则创建一个只包含添加的元素作成员的集合。
     * 当集合 key 不是集合类型时，返回一个错误。
     *
     * @param key     key
     * @param members members
     * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。若 > 0 返回true
     */
    public static boolean sadd(String key, String... members) {
        return redisCache.sadd(key, members);
    }

    /**
     * 返回集合中的所有的成员。 不存在的集合 key 被视为空集合。
     *
     * @param key key
     * @return 集合中的所有成员。
     */
    public static Set<String> smembers(String key) {
        return redisCache.smembers(key);
    }

    /**
     * 返回Set中元素数量
     *
     * @param key key
     * @return 集合的数量。 当集合 key 不存在时，返回 0 。
     */
    public static long scard(String key) {
        return redisCache.scard(key);
    }

    /**
     * 从set头部取值
     *
     * @param key key
     * @return string
     */
    public static String spop(String key) {
        return redisCache.spop(key);
    }

    /**
     * 从set头部取值
     *
     * @param key   键
     * @param count 取值数量
     * @return sets
     */
    public static Set<String> spop(String key, long count) {
        return redisCache.spop(key, count);
    }

    /**
     * 从set中移除members对应元素
     *
     * @param key     key
     * @param members members
     * @return boolean
     */
    public static boolean srem(String key, String... members) {
        return redisCache.srem(key, members);
    }

    /**
     * 用于将一个或多个成员元素及其分数值加入到有序集当中。
     * 如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。
     * 分数值可以是整数值或双精度浮点数。
     * 如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key    key
     * @param score  score
     * @param member member
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。若 > 0 返回true
     */
    public static boolean zadd(String key, double score, String member) {
        return redisCache.zadd(key, score, member);
    }

    /**
     * 用于将一个或多个成员元素及其分数值加入到有序集当中。
     *
     * @param key          key
     * @param scoreMembers scoreMembers
     * @return boolean
     */
    public static boolean zadd(String key, Map<String, Double> scoreMembers) {
        return redisCache.zadd(key, scoreMembers);
    }

    /**
     * 取set中start至end之间的值
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return set
     */
    public static Set<String> zrange(String key, long start, long end) {
        return redisCache.zrange(key, start, end);
    }

    /**
     * 计算集合中元素的数量
     *
     * @param key key
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。 当 key 不存在时，返回 0 。
     */
    public static long zcard(String key) {
        return redisCache.zcard(key);
    }

    /**
     * 从set中移除members对应元素
     *
     * @param key     key
     * @param members members
     * @return boolean
     */
    public static boolean zrem(String key, String... members) {
        return redisCache.zrem(key, members);
    }

    /**
     * 设置key的过期时间
     *
     * @param key     键
     * @param seconds 秒
     * @return 1 已设置成功，0 不成功或键不存在
     */
    public static long exprie(String key, int seconds) {
        return redisCache.exprie(key, seconds);
    }

    /**
     * 查看键的剩余生存时间
     *
     * @param key 键
     * @return 键的剩余生存时间
     */
    public static long ttl(String key) {
        return redisCache.ttl(key);
    }

    /**
     * 移除键的生存时间
     *
     * @param key 键
     * @return 键的生存时间
     */
    public static long persist(String key) {
        return redisCache.persist(key);
    }
}

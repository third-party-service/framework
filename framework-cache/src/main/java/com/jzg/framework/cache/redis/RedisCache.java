package com.jzg.framework.cache.redis;


import com.jzg.framework.cache.Cache;
import com.jzg.framework.cache.annotation.ArgName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis缓存
 */
public interface RedisCache extends Cache {

    /**
     * 更新缓存超时时间
     *
     * @param key       键
     * @param newExpire 超时时间
     * @return 更新成功或失败
     * @throws Exception 异常
     */
    boolean touch(@ArgName("key") String key, int newExpire) throws Exception;

    /**
     * 获取缓存数据并更新超时时间
     *
     * @param key       键
     * @param newExpire 新缓存时间
     * @param <T>       缓存对象类型
     * @return 更新时间并返回对象
     * @throws Exception 异常
     */
    <T extends Serializable> T getAndTouch(@ArgName("key") String key, int newExpire) throws Exception;


    /**
     * 缓存序列化对象
     *
     * @param key      键
     * @param t        缓存对象
     * @param <T>      缓存对象类型
     * @param <Object> 泛型
     * @return 成功或失败
     * @throws Exception 异常
     */
    <T extends Serializable, Object> boolean setObject(@ArgName("key") String key, T t) throws Exception;

    /**
     * 缓存List对象
     *
     * @param key      键
     * @param list     缓存对象列表
     * @param <T>      缓存对象类型
     * @param <Object> 泛型
     * @return 成功或失败
     * @throws Exception 异常
     */
    <T extends Serializable, Object> boolean setObjectList(@ArgName("key") String key, List<T> list) throws Exception;

    /**
     * 获取缓存值
     *
     * @param key      键
     * @param <T>      缓存对象类型
     * @param <Object> 泛型
     * @return 对象
     * @throws Exception 异常
     */
    <T extends Serializable, Object> T getObject(@ArgName("key") String key) throws Exception;

    /**
     * 获取List对象
     *
     * @param key      键
     * @param <T>      缓存对象类型
     * @param <Object> 泛型
     * @return 对象
     * @throws Exception 异常
     */
    <T extends Serializable, Object> List<T> getObjectList(@ArgName("key") String key) throws Exception;

    /**
     * 设置指定 key 的值，并返回 key 的旧值
     *
     * @param key 键
     * @param val 值
     * @return 老的键值
     * @throws Exception 异常
     */
    String getSet(String key, String val) throws Exception;

    /**
     * 也就是只有键不存在的时候才设置；如果 key 已经存在，返回 0， nx 是 not exist 的意思。
     *
     * @param key 键
     * @param val 值
     * @return 是否成功
     * @throws Exception 异常
     */
    boolean setnx(@ArgName("key") String key, @ArgName("val") String val) throws Exception;


    /**
     * 批量设置键值对  jedis.mset("key01","value01","key02","value02","key03","value03"));
     *
     * @param keysvals 键值对
     * @return 成功或失败
     * @throws Exception 异常
     */
    boolean mset(@ArgName("keysvals") String... keysvals) throws Exception;

    /**
     * 批量设置NX
     *
     * @param keysvals 键值对
     * @return 成功或失败
     * @throws Exception 异常
     */
    boolean msetnx(@ArgName("keysvals") String... keysvals) throws Exception;

    //keys操作

    /**
     * 获取键集合，正则匹配
     *
     * @param pattern 正则表达式
     * @return 键集合
     * @throws Exception 异常
     */
    Set<String> keys(@ArgName("pattern") String pattern) throws Exception;

    //hash操作

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     *
     * @param key 键
     * @param map hash
     * @return 成功或失败
     */
    boolean hmset(@ArgName("key") String key, Map<String, String> map);

    /**
     * 将哈希表 key 中的字段 field 的值设为 value 。
     *
     * @param key   键
     * @param filed hash中key
     * @param val   hash中value
     * @return 成功或失败
     */
    boolean hset(@ArgName("key") String key, String filed, String val);

    /**
     * 获取所有给定字段的值
     *
     * @param key    键
     * @param fields hash字段
     * @return LIST列表
     */
    List<String> hmget(@ArgName("key") String key, String... fields);

    /**
     * 获取给定字段的值
     *
     * @param key   键
     * @param field hash字段
     * @return field对应的值
     */
    String hget(@ArgName("key") String key, String field);

    /**
     * 获取哈希表中字段的数量
     *
     * @param key 键
     * @return 数量
     */
    long hlen(@ArgName("key") String key);

    /**
     * 查看哈希表 key 中，指定的字段是否存在。
     *
     * @param key   键
     * @param feild 字段
     * @return 成功或失败
     */
    boolean hexists(@ArgName("key") String key, String feild);

    /**
     * 获取所有哈希表中的字段
     *
     * @param key 键
     * @return 字段集合
     */
    Set<String> hkeys(@ArgName("key") String key);

    /**
     * 获取哈希表中所有值
     *
     * @param key 键
     * @return 值列表
     */
    List<String> hvals(@ArgName("key") String key);

    /**
     * 删除给定字段
     *
     * @param key    键
     * @param fields hash字段
     * @return 成功或失败
     */
    boolean hdel(@ArgName("key") String key, String... fields);


    //list 操作

    /**
     * 在List头部添加一个或多个值
     *
     * @param key  键
     * @param vals 值
     * @return 成功或失败
     */
    boolean lpush(@ArgName("key") String key, String... vals);

    /**
     * 在List尾部添加一个或多个值
     *
     * @param key  键
     * @param vals 值
     * @return 成功或失败
     */
    boolean rpush(@ArgName("key") String key, String... vals);

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return 成功或失败
     */
    boolean lset(@ArgName("key") String key, long index, String value);


    /**
     * 按范围取出list数据
     *
     * @param key   键
     * @param start 起始位置
     * @param end   -1 表示取出所有
     * @return 列表
     */
    List<String> lrange(@ArgName("key") String key, int start, int end);

    /**
     * 通过索引获取列表中的元素。你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     *
     * @param key   键
     * @param index 索引
     * @return 列表中下标为指定索引值的元素。 如果指定索引值不在列表的区间范围内，返回 nil
     */
    String lindex(@ArgName("key") String key, int index);

    /**
     * 从list头部取值
     *
     * @param key 键
     * @return 头部的值
     */
    String lpop(@ArgName("key") String key);

    /**
     * 从list尾部取值
     *
     * @param key 键
     * @return 尾部的值
     */
    String rpop(@ArgName("key") String key);


    /**
     * 从list中移除value对应元素
     *
     * @param key   键
     * @param count 数量
     * @param value 值
     * @return 成功或失败
     */
    boolean lrem(@ArgName("key") String key, long count, String value);

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     *
     * @param key   键
     * @param start 起始位置
     * @param end   截止位置
     * @return 成功或失败
     */
    boolean ltrim(@ArgName("key") String key, long start, long end);


    //set 操作

    /**
     * 将一个或多个成员元素加入到集合中，已经存在于集合的成员元素将被忽略。
     * 假如集合 key 不存在，则创建一个只包含添加的元素作成员的集合。
     * 当集合 key 不是集合类型时，返回一个错误。
     *
     * @param key     键
     * @param members 值
     * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。若 > 0 返回true
     */
    boolean sadd(@ArgName("key") String key, String... members);

    /**
     * 返回集合中的所有的成员。 不存在的集合 key 被视为空集合。
     *
     * @param key 键
     * @return 集合中的所有成员。
     */
    Set<String> smembers(@ArgName("key") String key);

    /**
     * 返回Set中元素数量
     *
     * @param key 键
     * @return 集合的数量。 当集合 key 不存在时，返回 0 。
     */
    long scard(@ArgName("key") String key);

    /**
     * 从set头部取值
     *
     * @param key 键
     * @return 集合头部的值
     */
    String spop(@ArgName("key") String key);

    /**
     * 从set头部取值
     *
     * @param key   键
     * @param count 取值数量
     * @return 头部集合
     */
    Set<String> spop(@ArgName("key") String key, long count);

    /**
     * 从set中移除members对应元素
     *
     * @param key     键
     * @param members members
     * @return 成功或失败
     */
    boolean srem(@ArgName("key") String key, String... members);


    //sorted set

    /**
     * 用于将一个或多个成员元素及其分数值加入到有序集当中。
     * 如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。
     * 分数值可以是整数值或双精度浮点数。
     * 如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     *
     * @param key    键
     * @param score  分数值
     * @param member 成员元素
     * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。若 > 0 返回true
     */
    boolean zadd(@ArgName("key") String key, double score, String member);

    /**
     * 用于将一个或多个成员元素及其分数值加入到有序集当中。
     *
     * @param key          键
     * @param scoreMembers 成员元素及其分数值
     * @return 成功或失败
     */
    boolean zadd(@ArgName("key") String key, Map<String, Double> scoreMembers);

    /**
     * 取set中start至end之间的值
     *
     * @param key   键
     * @param start 起始位置
     * @param end   截止位置
     * @return 集合
     */
    Set<String> zrange(@ArgName("key") String key, long start, long end);

    /**
     * 计算集合中元素的数量
     *
     * @param key 键
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。 当 key 不存在时，返回 0 。
     */
    long zcard(@ArgName("key") String key);

    /**
     * 从set中移除members对应元素
     *
     * @param key     键
     * @param members 成员元素
     * @return 成功或失败
     */
    boolean zrem(@ArgName("key") String key, String... members);

    /**
     * 设置key的过期时间
     *
     * @param key     键
     * @param seconds 秒
     * @return 1 已设置成功，0 不成功或键不存在
     */
    long exprie(@ArgName("key") String key, int seconds);

    /**
     * 查看键的剩余生存时间
     *
     * @param key 键
     * @return
     */
    long ttl(@ArgName("key") String key);

    /**
     * 移除键的生存时间
     *
     * @param key 键
     * @return
     */
    long persist(@ArgName("key") String key);
}

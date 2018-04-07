/**
 * 
 */
package com.bsco.framework.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author jack.li
 *
 */
public class RedisUtil {

	private static JedisPool pool = null;
	private static Properties properties = new Properties();
	private static int maxActive;
	private static int maxWait;
	private static int maxIdle;
	private static boolean testOnBorrow;
	private static String host;
	private static int port;
	static {
		try {
			properties.load(RedisUtil.class.getResourceAsStream("/redis.properties"));
			maxActive = Integer.parseInt(properties.getProperty("redis.maxActive", "5"));
			maxWait = Integer.parseInt(properties.getProperty("redis.maxWait", "10000"));
			maxIdle = Integer.parseInt(properties.getProperty("redis.maxIdle", "5"));
			testOnBorrow = Boolean.getBoolean(properties.getProperty("redis.testOnBorrow", "true"));
			host = properties.getProperty("redis.host", "localhost");
			port = Integer.parseInt(properties.getProperty("redis.port", "6379"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 构建redis连接池
     * 
     * @param ip
     * @param port
     * @return JedisPool
     */
    public static JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxActive(maxActive);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(maxIdle);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWait(maxWait);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(testOnBorrow);
            pool = new JedisPool(config, host, port, 60000);
        }
        return pool;
    }
    
    /**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }
    
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public static byte[] get(byte[] key){
    	byte[] value = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }
    
    public static String flushDB(){
    	String value = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.flushDB();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }
    
    /**
     * 保存数据
     * 
     * @param key
     * @param value
     * @return
     */
    public static byte[] set(byte[] key, byte[] value){
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, value);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return value;
    }
    
    public static Long incr(byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.incr(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long decr(byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.decr(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Boolean exists(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.exists(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static byte[] lpop(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.lpop(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static byte[] rpop(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.rpop(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long lpush(final byte[] key, final byte[] d) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.lpush(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long rpush(final byte[] key, final byte[] d) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.rpush(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long hlen(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hlen(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static List<byte[]> hmget(final byte[] key, final byte[]... fields) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hmget(key, fields);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static List<byte[]> hvals(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hvals(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Map<byte[], byte[]> hgetAll(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hgetAll(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static byte[] hget(final byte[] key, final byte[] field) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hget(key, field);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static String type(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.type(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static <T> Long hset(final byte[] key, final byte[] field, final byte[] value) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hset(key, field, value);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long hdel(final byte[] key, final byte[]... fields) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hdel(key, fields);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static String hmset(final byte[] key, final Map<byte[], byte[]> hash) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hmset(key, hash);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static <T> Long hsetnx(final byte[] key, final byte[] field, final byte[] value) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hsetnx(key, field, value);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long sadd(final byte[] key, final byte[]... members) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.sadd(key, members);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long sadd(final String key, final String... members) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.sadd(key, members);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Set<byte[]> smembers(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.smembers(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Set<String> smembers(final String key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.smembers((key));
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
        return null;
    }
    
    public static Set<byte[]> sinter(final byte[]... keys) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.sinter(keys);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Set<String> sinter(final String... keys) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.sinter(keys);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static long srem(final String key,final String... members) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.srem(key, members);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Boolean sismember(final byte[] key, final byte[] member) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.sismember(key, member);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Set<byte[]> hkeys(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.hkeys(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long scard(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.scard(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long setnx(final byte[] key, final byte[] value) throws Exception {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.setnx(key, value);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static String watch(byte[]... keys) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.watch(keys);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long delete(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.del(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long expired(final byte[] key, final int seconds) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.expire(key, seconds);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long zadd(final byte[] key, final double score, final byte[] member) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.zadd(key, score, member);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long zcard(final byte[] key) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.zcard(key);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long zcount(final byte[] key, final double min, final double max) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.zcount(key, min, max);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long zrem(final byte[] key,final byte[]...members) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.zrem(key, members);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Set<byte[]> zrevrange(final byte[] key, final int start, final int end) {
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, end);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Long zremrangeByRank(final byte[] key, final int start, final int end){
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.zremrangeByRank(key, start, end);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static Double zscore(final byte[] key, final byte[] member){
    	JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            return jedis.zscore(key, member);
        } finally {
            //返还到连接池
            returnResource(pool, jedis);
        }
    }
    
    public static synchronized Long genIdentify(byte[] key) {
    	Long id = null;
    	byte[] oldIdByte = get(key);
    	if(oldIdByte == null) {
    		id = 0l;
    	} else {
    		id = new Long(new String(get(key)));
    	}
    	id = id + 1;
    	set(key, id.toString().getBytes());
    	return id;
    }
    
    public static void main(String[] args) {
//    	long start = System.currentTimeMillis();
//    	String key = "var";
//    	for(int i = 0; i < 10000; i++) {
//    		RedisUtil.set((key+i).getBytes(), ("abcdefg"+i).getBytes());
//    	}
//    	for(int i = 0; i < 10000; i++) {
//    		String value = new String(RedisUtil.get((key+i).getBytes()));
//    		System.out.println(value);
//    	}
//    	long end = System.currentTimeMillis();
//    	System.out.println((end - start) / (1000));
//    	Long id = RedisUtil.genIdentify("CLASS_TEAM".getBytes());
//    	System.out.println(id);
    	RedisUtil.set("CLASS_TEAM".getBytes(), "8".getBytes());
	}
    
}

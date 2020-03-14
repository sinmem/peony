package com.sinmem.peony.common.utils;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * redis工具类
 * @author xingm
 * 参考于https://www.cnblogs.com/zeng1994/p/03303c805731afc9aa9c60dbbd32a323.html
 */
@Component
public class RedisUtils {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 设置缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key, long time){
		try {
			if(time > 0){
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据key 获取失效时间
	 * @param key 键
	 * @return 时间(秒) 0表示无限制
	 */
	public long getExpire(String key){
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}
	
	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return
	 */
	public boolean hasKey(String key){
		try{
			return redisTemplate.hasKey(key);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除缓存
	 * @param key 一个或多个
	 */
	@SuppressWarnings("unchecked")
	public void del(String...key){
		if(key!=null && key.length >0){
			if(key.length == 1){
				redisTemplate.delete(key[0]);
			}else{
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}

	/**
	 * 获取缓存
	 * @param key 键
	 * @return object值
	 */
	public String  getStr(String key){
		return key == null ? null : String.valueOf(redisTemplate.opsForValue().get(key));
	}
	
	/**
	 * 获取缓存
	 * @param key 键
	 * @return object值
	 */
	public Object get(String key){
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 放入缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean set(String key, Object value){
		try {
			redisTemplate.opsForValue().getAndSet(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 放入缓存并设置失效时间
	 * @param key 键
	 * @param value 值
	 * @param time 失效时间(秒)
	 * @return
	 */
	public boolean set(String key, Object value, long time){
		try {
			if(time > 0){
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			}else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/** 没看懂....
	 * 递增
	 * @param key 键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public long incr(String key, long delta){
		if(delta < 0){
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}
	
	/** 没看懂....
	 * 递减
	 * @param key 键
	 * @param delta 要减少几(大于0)
	 * @return
	 */
	public long decr(String key, long delta){
		if(delta < 0){
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}
}





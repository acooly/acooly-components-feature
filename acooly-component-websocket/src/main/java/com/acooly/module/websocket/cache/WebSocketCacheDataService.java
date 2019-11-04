package com.acooly.module.websocket.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.acooly.module.distributedlock.DistributedLockFactory;
import com.acooly.module.websocket.WebSocketProperties;
import com.beust.jcommander.internal.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("webSocketCacheDataService")
public class WebSocketCacheDataService {

	/** redis尝试锁，时间设置 **/
	public static Integer WEB_SOCKET_TRY_LOCK_TIME = 1;
	/**
	 * websocket 缓存key
	 */
	private static String REDIS_KEY = "websocket_";

	/**
	 * websocket 缓存key
	 */
	private static String LOCK_REDIS_KEY = "websocket_lock_";

	@Autowired
	private WebSocketProperties webSocketProperties;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private DistributedLockFactory factory;

	/**
	 * 设置 session key缓存
	 * 
	 * @param session
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setWebSocketSession(Session session) {
		// 过期时间
		Long timeOut = webSocketProperties.getTimeOut();

		HashOperations redisDataHash = redisTemplate.opsForHash();
		String rediskey = getRedisKey(session);
		String redisHashKey = getRedisHashKey(session);
		String redisValue = session.getId();

		Lock lock = factory.newLock(getLockRedisKey(session));
		try {
			try {
				if (lock.tryLock(WEB_SOCKET_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
					// 获取当前 redis中缓存值
					Map<String, Object> valueMap = (Map<String, Object>) redisDataHash.get(rediskey, redisHashKey);
					if (valueMap == null) {
						valueMap = Maps.newHashMap();
					}

					// 如果不是 不是群发，新建map存储结构
					if (!webSocketProperties.getGroupMessage()) {
						valueMap = Maps.newHashMap();
					}

					valueMap.put(redisValue, redisValue);
					redisDataHash.put(rediskey, redisHashKey, valueMap);
					redisTemplate.expire(rediskey, timeOut, TimeUnit.HOURS);
				}
			} finally {
				lock.unlock();
			}
		} catch (Exception e) {
			log.error("-webSocket-设置 session key缓存 失败{}", e);
		}

	}

	/**
	 * session key缓存 删除
	 * 
	 * @param session
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void deleteWebSocketSession(Session session) {
		HashOperations redisDataHash = redisTemplate.opsForHash();
		String rediskey = getRedisKey(session);
		String redisHashKey = getRedisHashKey(session);
		String redisValue = session.getId();

		Lock lock = factory.newLock(getLockRedisKey(session));
		try {
			if (lock.tryLock(WEB_SOCKET_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
				try {
					Map<String, Object> valueMap = (Map<String, Object>) redisDataHash.get(rediskey, redisHashKey);
					if (valueMap != null) {
						int size = valueMap.size();
						if (size == 1L) {
							redisDataHash.delete(rediskey, redisHashKey);
						} else {
							valueMap.remove(redisValue);
							// 过期时间
							Long timeOut = webSocketProperties.getTimeOut();
							redisDataHash.put(rediskey, redisHashKey, valueMap);
							redisTemplate.expire(rediskey, timeOut, TimeUnit.HOURS);
						}
					}
				} finally {
					lock.unlock();
				}
			}
		} catch (Exception e) {
			log.error("-webSocket-删除 session key缓存 失败{}", e);
		}
	}

	/**
	 * 获取 session key缓存
	 * 
	 * @param businessKey
	 * @param businessType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Set getWebSocketSessionId(String businessKey, String businessType) {
		HashOperations redisDataHash = redisTemplate.opsForHash();
		String rediskey = getRedisKey(businessKey);
		String redisHashKey = getRedisHashKey(businessKey, businessType);
		Map<String, Object> valueMap = (Map<String, Object>) redisDataHash.get(rediskey, redisHashKey);
		return valueMap.keySet();
	}

	/**
	 * 获取HashKey
	 * 
	 * @param session
	 * @return
	 */
	private String getRedisHashKey(Session session) {
		Map<String, String> map = session.getPathParameters();
		String businessKey = map.get("businessKey");
		String businessType = map.get("businessType");
		return getRedisHashKey(businessKey, businessType);
	}

	/**
	 * 获取HashKey
	 * 
	 * @param businessKey
	 * @param businessType
	 * @return
	 */
	private String getRedisHashKey(String businessKey, String businessType) {
		String rediskey = REDIS_KEY + businessKey + "_" + businessType;
		return rediskey;
	}

	/**
	 * 获取
	 * 
	 * @param session
	 * @return
	 */
	private String getRedisKey(Session session) {
		Map<String, String> map = session.getPathParameters();
		String businessKey = map.get("businessKey");
		return getRedisKey(businessKey);
	}

	/**
	 * 获取
	 * 
	 * @param businessKey
	 * @return
	 */
	private String getRedisKey(String businessKey) {
		String rediskey = REDIS_KEY + businessKey;
		return rediskey;
	}

	/**
	 * 获取
	 * 
	 * @param session
	 * @return
	 */
	private String getLockRedisKey(Session session) {
		Map<String, String> map = session.getPathParameters();
		String businessKey = map.get("businessKey");
		return LOCK_REDIS_KEY + businessKey;
	}

}

package com.acooly.module.websocket.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
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
		String rediskey = getRedisKey(session);
		String redisValue = session.getId();
		String lockRedisKey = getLockRedisKey(session);

		Lock lock = factory.newLock(lockRedisKey);
		try {
			if (lock.tryLock(WEB_SOCKET_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
				try {
					ValueOperations redisData = redisTemplate.opsForValue();
					// 获取当前 redis中缓存值
					Map<String, Object> valueMap = (Map<String, Object>) redisData.get(rediskey);
					if (valueMap == null) {
						valueMap = Maps.newHashMap();
					}

					// 如果不是 不是群发，新建map存储结构
					if (!webSocketProperties.getGroupMessage()) {
						valueMap = Maps.newHashMap();
					}

					valueMap.put(redisValue, redisValue);
					redisData.set(rediskey, valueMap, webSocketProperties.getTimeOut(), TimeUnit.HOURS);
				} catch (Exception e) {
					log.error("-webSocket-设置 session key 缓存 失败{}", e);
					throw new BusinessException("设置 session key 缓存 失败", e);
				} finally {
					lock.unlock();
				}
			} else {
				if (redisTemplate.hasKey(lockRedisKey)) {
					log.info("-webSocket组件,当前key未释放,系统删除:lockKey:{}", lockRedisKey);
					redisTemplate.delete(lockRedisKey);
				}
				log.info("-webSocket-设置 session key缓存 失败,获取redis锁失败rediskey:{},redisValue:{}", rediskey, redisValue);
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
		String rediskey = getRedisKey(session);
		String redisValue = session.getId();
		String lockRedisKey = getLockRedisKey(session);
		Lock lock = factory.newLock(lockRedisKey);
		try {
			if (lock.tryLock(WEB_SOCKET_TRY_LOCK_TIME, TimeUnit.SECONDS)) {
				try {
					ValueOperations redisData = redisTemplate.opsForValue();
					Map<String, Object> valueMap = (Map<String, Object>) redisData.get(rediskey);
					if (valueMap != null) {
						valueMap.remove(redisValue);
						redisData.set(rediskey, valueMap, webSocketProperties.getTimeOut(), TimeUnit.HOURS);
					}
				} catch (Exception e) {
					log.error("-webSocket-删除 session key失败{}", e);
					throw new BusinessException("-webSocket-删除   session key失败", e);
				} finally {
					lock.unlock();
				}
			} else {
				if (redisTemplate.hasKey(lockRedisKey)) {
					log.info("-webSocket组件,当前key未释放,系统删除:lockKey:{}", lockRedisKey);
					redisTemplate.delete(lockRedisKey);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set getWebSocketSessionId(String businessType, String businessKey) {
		String rediskey = getRedisKey(REDIS_KEY, businessType, businessKey);
		ValueOperations redisData = redisTemplate.opsForValue();
		Map<String, Object> valueMap = (Map<String, Object>) redisData.get(rediskey);
		if (valueMap != null) {
			return valueMap.keySet();
		}
		return null;
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
		String businessType = map.get("businessType");
		return getRedisKey(REDIS_KEY, businessType, businessKey);
	}

	/**
	 * 获取锁， 单节点key
	 * 
	 * @param session
	 * @return
	 */
	private String getLockRedisKey(Session session) {
		Map<String, String> map = session.getPathParameters();
		String businessKey = map.get("businessKey");
		String businessType = map.get("businessType");
		return getRedisKey(LOCK_REDIS_KEY, businessType, businessKey);
	}

	/**
	 * 获取
	 * 
	 * @param businessKey
	 * @return
	 */
	private String getRedisKey(String key, String businessType, String businessKey) {
		String rediskey = key + businessType + "_" + businessKey;
		return rediskey;
	}

}

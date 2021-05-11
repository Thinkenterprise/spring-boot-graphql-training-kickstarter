package com.thinkenterprise.graphql.cache;

import java.util.HashMap;
import java.util.Map;

import org.dataloader.CacheMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.thinkenterprise.domain.route.graphql.resolver.query.RootQueryResolver;

@Component
public class RedisCache implements CacheMap<Long, Object>{

	protected static Logger log = LoggerFactory.getLogger(RootQueryResolver.class);
	
	private Map<Long, Object> map = new HashMap<>();
	
	@Override
	public boolean containsKey(Long key) {
		log.info("Redis Cache: " + "containsKey" + key);
		return map.containsKey(key);
	}

	@Override
	public Object get(Long key) {
		log.info("Redis Cache: " + "get "+ key);
		return map.get(key);
	}

	@Override
	public CacheMap<Long, Object> set(Long key, Object value) {
		log.info("Redis Cache: "  + "set " + key + value);
		map.put(key, value);
		return this;
	}

	@Override
	public CacheMap<Long, Object> delete(Long key) {
		log.info("Redis Cache: " + "delete " + key);
		map.remove(key);
		return this;
	}

	@Override
	public CacheMap<Long, Object> clear() {
		log.info("Redis Cache: " + "clear ");
		map.clear();
		return this;
	}

}

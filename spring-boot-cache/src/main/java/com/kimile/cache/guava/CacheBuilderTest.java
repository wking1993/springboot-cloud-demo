package com.kimile.cache.guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CacheBuilderTest {
	
	LoadingCache<String, Object> cacheBuilder = 
			CacheBuilder.newBuilder()
					.expireAfterWrite(1, TimeUnit.MINUTES)
					.build(new CacheLoader<String, Object>() {
						@Override
						public Object load(String key) throws Exception {
							//可以在此处加载数据库数据，缓存到cache中，留待进行选用
							//return dao.findById(id);
							return key;
						}
					});
	
	public Object get(String id) throws ExecutionException {
		return cacheBuilder.get(id);
	}
	
}

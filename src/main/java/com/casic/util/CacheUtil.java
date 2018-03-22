package com.casic.util;

import com.hotent.core.cache.impl.MemoryCache;

public class CacheUtil {
	
	private static MemoryCache cache =null;
	static{
		cache = new MemoryCache();
	}
	public static MemoryCache getUserCache() {
		return cache;
	}
}

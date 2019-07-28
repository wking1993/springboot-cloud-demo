package com.kimile.cache.bloom;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterTest {
	
	/**
	 * 通过BloomFilter创建一个布隆过滤器，是存在一定错误率的，我们可以在create的时候制定第3个参数来调节错误率，
	 * 错误率单位为%，默认为0.03，即错误率为0.03%=0.0003
	 * @param args
	 */
	public static void main(String[] args) {
		//总数量
		int total = 1000000;
		BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total);
		//初始化1000000条数据到过滤器中
		for (int i = 0; i < total; i++) {
			bf.put("" + i);
		}
		
		//判断值是否存在过滤器中
		int count = 0;
		for (int i = 0; i < total + 10000; i++) {
			if (bf.mightContain("" + i)) {
				count++;
			}
		}
		System.out.println(count);
		BloomFilter<String> bf1 = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total, 0.003);
		//初始化1000000条数据到过滤器中
		for (int i = 0; i < total; i++) {
			bf1.put("" + i);
		}
		
		//判断值是否存在过滤器中
		int count1 = 0;
		for (int i = 0; i < total + 10000; i++) {
			if (bf1.mightContain("" + i)) {
				count1++;
			}
		}
		System.out.println(count1);
	}
	
}

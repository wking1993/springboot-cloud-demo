package com.kimile.db.mongodb.bean;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
//@CompoundIndexes创建组合索引，参数是@CompoundIndex的注解数组
@CompoundIndexes({
	//@CompoundIndex组合索引，name表示索引名称，def表示组合索引的字段和索引存储升序(1)或者降序(-1)
	@CompoundIndex(name = "city_region_idx", def = "{'city':1, 'region':1}")
})
public class Person {
	
	private String id;
	
	//@Indexed可以给当前字段添加索引，唯一索引的标识方式为unique=true,以后台方式创建索引的参数是background=true
	@Indexed(unique = true)
	private String name;
	
	@Indexed(background = true)
	private int age;
	
	private String city;
	
	private String region;
	
}

package com.kimile.db.mongodb.listener;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

import com.kimile.db.mongodb.annotation.GeneratedValue;
import com.kimile.db.mongodb.bean.SequenceId;

public class SaveMongoEventListener extends AbstractMongoEventListener<Object> {

	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public void onBeforeConvert(BeforeConvertEvent<Object> event) {
		Object source = event.getSource();
		if (source != null) {
			ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
				@Override
				public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
					ReflectionUtils.makeAccessible(field);
					if (field.isAnnotationPresent(GeneratedValue.class)) {
						//设置自增id
						field.set(source, getNextId(source.getClass().getSimpleName()));
					}
				}
			});
		}
	}
	
	private Long getNextId(String collName) {
		Query query = Query.query(Criteria.where("collName").is(collName));
		Update update = new Update();
		update.inc("seqId", 1);
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.upsert(true);
		options.returnNew(true);
		SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);
		return seqId.getSeqId();
	}
	
}

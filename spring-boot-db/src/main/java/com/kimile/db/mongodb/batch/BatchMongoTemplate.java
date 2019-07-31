package com.kimile.db.mongodb.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class BatchMongoTemplate {
	
	public static int batchUpdate(MongoTemplate mongoTemplate, 
			String collName, List<BatchUpdateOptions> options, boolean ordered) {
		return doBatchUpdate(mongoTemplate.getCollection(collName), collName, options, ordered);
	}
	
	public static int batchUpdate(DBCollection dbCollection, 
			String collName, List<BatchUpdateOptions> options, boolean ordered) {
		return doBatchUpdate(dbCollection, collName, options, ordered);
	}
	
	public static int batchUpdate(MongoTemplate mongoTemplate, 
			Class<?> entityClass, List<BatchUpdateOptions> options, boolean ordered) {
		String collName = determineCollectionName(entityClass);
		return doBatchUpdate(mongoTemplate.getCollection(collName), collName, options, ordered);
	}
	
	public static int batchUpdate(DBCollection dbCollection, 
			Class<?> entityClass, List<BatchUpdateOptions> options, boolean ordered) {
		String collName = determineCollectionName(entityClass);
		return doBatchUpdate(dbCollection, collName, options, ordered);
	}
	
	public static int batchUpdate(MongoTemplate mongoTemplate, 
			String collName, List<BatchUpdateOptions> options) {
		return doBatchUpdate(mongoTemplate.getCollection(collName), collName, options, true);
	}
	
	public static int batchUpdate(DBCollection dbCollection, 
			String collName, List<BatchUpdateOptions> options) {
		return doBatchUpdate(dbCollection, collName, options, true);
	}
	
	public static int batchUpdate(MongoTemplate mongoTemplate, 
			Class<?> entityClass, List<BatchUpdateOptions> options) {
		String collName = determineCollectionName(entityClass);
		return doBatchUpdate(mongoTemplate.getCollection(collName), collName, options, true);
	}
	
	public static int batchUpdate(DBCollection dbCollection, 
			Class<?> entityClass, List<BatchUpdateOptions> options) {
		String collName = determineCollectionName(entityClass);
		return doBatchUpdate(dbCollection, collName, options, true);
	}
	
	private static int doBatchUpdate(DBCollection dbCollection, 
			String collName, List<BatchUpdateOptions> options, boolean ordered) {
		DBObject command = new BasicDBObject();
		command.put("update", collName);
		List<BasicDBObject> updateList = new ArrayList<BasicDBObject>();
		for (BatchUpdateOptions option : options) {
			BasicDBObject update = new BasicDBObject();
			update.put("q", option.getQuery());
			update.put("u", option.getUpdate());
			update.put("upsert", option.isUpsert());
			update.put("multi", option.isMulti());
			updateList.add(update);
		}
		command.put("updates", updateList);
		command.put("ordered", ordered);
		CommandResult result = dbCollection.getDB().command(command);
		return result.getInt("n");
	}
	
	private static String determineCollectionName(Class<?> entityClass) {
		if (entityClass == null) {
			throw new InvalidDataAccessApiUsageException("No class parameter provided, entity collection can't be determined!");
		}
		String collName = entityClass.getSimpleName();
		if (entityClass.isAnnotationPresent(Document.class)) {
			Document document = entityClass.getAnnotation(Document.class);
			collName = document.collection();
		} else {
			collName = collName.replaceFirst(collName.substring(0, 1), collName.substring(0, 1).toLowerCase());
		}
		return collName;
	}
	
}

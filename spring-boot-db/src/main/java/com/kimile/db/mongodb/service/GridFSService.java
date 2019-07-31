package com.kimile.db.mongodb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Service
public class GridFSService {
	
	private static GridFsTemplate gridFsTemplate;
	
	@Autowired
	public GridFSService(GridFsTemplate gridFsTemplate) {
		this.gridFsTemplate = gridFsTemplate;
	}
	
	//上传文件
	public static void uploadFile() throws FileNotFoundException {
		File file = new File("C:/Users/a/Desktop/test.jpg");
		InputStream content = new FileInputStream(file);
		//存储文件的额外信息，比如用户ID，后面要查询某个用户的所有文件时，就可以直接查询
		DBObject metadata = new BasicDBObject("userId", "1001");
		String fileName = file.getName();
		GridFSFile gridFSFile = gridFsTemplate.store(content, fileName, "image/jpg", metadata);
		String fileId = gridFSFile.getId().toString();
		System.out.println(fileId);
	}
	
	//根据文件ID查询文件
	public static GridFSDBFile getFile(String fileId) {
		return gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
	}
	
	//根据文件ID删除文件
	public static void removeFile(String fileId) {
		gridFsTemplate.delete(Query.query(Criteria.where("_id").is(fileId)));
	}
	
}

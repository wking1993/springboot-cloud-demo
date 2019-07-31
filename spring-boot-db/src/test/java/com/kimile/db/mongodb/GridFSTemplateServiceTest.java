package com.kimile.db.mongodb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kimile.db.mongodb.service.GridFSService;
import com.mongodb.gridfs.GridFSDBFile;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GridFSTemplateServiceTest {
	
	@Test
	public void testUploadFile() throws FileNotFoundException {
		GridFSService.uploadFile();
	}
	
	@Test
	public void testGetFile() throws IOException {
		GridFSDBFile file = GridFSService.getFile("5d400069fc0ea2231c1b2f79");
		if (file == null) {
			System.out.println("无对应文件");
			return;
		}
		file.writeTo("C:/Users/a/Desktop/newTest.jpg");
		System.out.println("生成新文件成功");
	}
	
	@Test
	public void testRemoveFile() throws IOException {
		GridFSService.removeFile("5d400069fc0ea2231c1b2f79");
	}
	
}

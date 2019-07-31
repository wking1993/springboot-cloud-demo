package com.kimile.db.mongodb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.kimile.db.mongodb.bean.Student;

@Service
public class GenerateValueService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insertStudent() {
		List<Student> students = new ArrayList<Student>();
		for (int i = 0; i < 5; i++) {
			Student student = new Student();
			student.setName("student" + i);
			students.add(student);
		}
		mongoTemplate.insert(students, Student.class);
	}
	
}

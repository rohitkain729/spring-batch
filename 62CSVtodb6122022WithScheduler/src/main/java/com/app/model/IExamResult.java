package com.app.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

//@Document
@Data
public class IExamResult {

//	input exam result

	private Integer id;
	private String dob;
	private Float percentage;
	private Integer semester;

}

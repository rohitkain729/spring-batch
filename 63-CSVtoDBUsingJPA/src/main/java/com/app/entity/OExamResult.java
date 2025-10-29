package com.app.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JPA_EXAM_RESULT")
public class OExamResult {

//	output exam result
	@Id
	private Integer id;
	private LocalDateTime dob;
	private Float percentage;
	private Integer semester;

}

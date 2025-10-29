package com.app.processor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.app.entity.OExamResult;
import com.app.model.IExamResult;

@Component
public class ExamResultProcessor implements ItemProcessor<IExamResult, OExamResult> {

	@Override
	public OExamResult process(IExamResult item) throws Exception {

		if (item.getPercentage() >= 40.0f) {

			OExamResult result = new OExamResult();

			result.setId(item.getId());
			result.setPercentage(item.getPercentage());
			result.setSemester(item.getSemester());

			// Flexible date formatter (accepts optional fractions)
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]");

			// Parse the input string into LocalDateTime
			LocalDateTime localDateTime = LocalDateTime.parse(item.getDob(), formatter);

			// Convert LocalDateTime → Date for MongoDB ISODate storage
			Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

			// Store the date (if OExamResult.dob is LocalDateTime)
			result.setDob(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

			return result;
		}

		return null;
	}
}

//package com.app.processor;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;

//import java.util.Date;
//
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.stereotype.Component;
//
//import com.app.document.OExamResult;
//import com.app.model.IExamResult;
//
//
//@Component
//public class ExamResultProcessor implements ItemProcessor<IExamResult, OExamResult>  {
//	
//
//@Override
//	public OExamResult process(IExamResult item) throws Exception {
//
//		if (item.getPercentage() >= 40.0f) {
//			OExamResult result = new OExamResult() {
//				{
//					setId(item.getId());
//					setPercentage(item.getPercentage());
//					setSemester(item.getSemester());
//					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");	
//					
////					String date = LocalDateTime.parse(item.getDob()).format(formatter);
//					
//					 LocalDateTime localDateTime = LocalDateTime.parse(item.getDob(), formatter);
//					 Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); 
//					 
////					LocalDateTime upatedDate = LocalDateTime.parse(date);
//					
//					 setDob(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
//
////					setDob(upatedDate);
//				}
//			};
//			return result;
//		}
//		return null;
//	}
//
//}

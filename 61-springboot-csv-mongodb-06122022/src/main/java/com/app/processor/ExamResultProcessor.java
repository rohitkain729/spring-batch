package com.app.processor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.app.document.OExamResult;
import com.app.model.IExamResult;


@Component
public class ExamResultProcessor implements ItemProcessor<IExamResult, OExamResult>  {
	

@Override
	public OExamResult process(IExamResult item) throws Exception {

		if (item.getPercentage() >= 40.0f) {
			OExamResult result = new OExamResult() {
				{
					setId(item.getId());
					setPercentage(item.getPercentage());
					setSemester(item.getSemester());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");	
					String date = LocalDateTime.parse(item.getDob()).format(formatter);
					LocalDateTime upatedDate = LocalDateTime.parse(date);
					setDob(upatedDate);
				}
			};
			return result;
		}
		return null;
	}

}

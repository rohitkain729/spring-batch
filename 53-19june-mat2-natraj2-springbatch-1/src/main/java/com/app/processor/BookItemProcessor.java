package com.app.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("bookProcessor")
public class BookItemProcessor implements ItemProcessor<String, String> {

	public BookItemProcessor() {
		System.out.println("BookItemProcessor.BookItemProcessor()");
	}

	@Override
	public String process(String item) throws Exception {

		String bookWithAuthor = null;

		if (item.equalsIgnoreCase("Thinking in Java")) {
			bookWithAuthor = item + "by Bruce eckel";
		} else if (item.equalsIgnoreCase("Effective Java")) {
			bookWithAuthor = item + " by Josh Blosh";
		} else if (item.equalsIgnoreCase("Black Book of Java")) {
			bookWithAuthor = item + " by RNR";
		} else if (item.equalsIgnoreCase("Head First Java")) {

			bookWithAuthor = item + " by Kathy Sierra";
		} else if (item.equalsIgnoreCase("Complete reference")) {

			bookWithAuthor = item + " by Herbat schield";
		} else
			bookWithAuthor = null;
		return bookWithAuthor;
	}

}

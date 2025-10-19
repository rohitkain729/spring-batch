package com.app.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component("bookItemReader")
public class BookItemReader implements ItemReader<String> {

	private String[] books = new String[] { "Thinking in Java", "Head First Java", "Black Book of Java",
			"Complete reference" };

	private int count = 0;

	public BookItemReader() {

		System.out.println("BookItemReader.BookItemReader()");
	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (count < books.length) {
			return books[count++];
		}

		return null;
	}

}

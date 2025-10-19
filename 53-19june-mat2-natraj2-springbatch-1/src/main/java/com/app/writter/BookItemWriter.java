package com.app.writter;

import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("bookWriter")
public class BookItemWriter implements ItemWriter<String> {

	public BookItemWriter() {
		System.out.println("BookItemWriter.BookItemWriter()" + new Date());
	}

	@Override
	public void write(List<? extends String> chunk) throws Exception {

		System.out.println("BookItemWriter.write()");

		System.out.println("books are ::" + chunk.toString());
	}

	

}

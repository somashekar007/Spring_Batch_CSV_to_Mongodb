package com.example.spring.batch.item.write.listener;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

public class StepItemWriteListener implements ItemWriteListener {

	@Override
	public void beforeWrite(List items) {
		System.out.println("ItemWriteListener - beforeWrite");
		
	}

	@Override
	public void afterWrite(List items) {
		System.out.println("ItemWriteListener - afterWrite");
		
	}

	@Override
	public void onWriteError(Exception exception, List items) {
		System.out.println("ItemWriteListener - onWriteError");
		
	}

}

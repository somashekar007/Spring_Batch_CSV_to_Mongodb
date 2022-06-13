package com.example.spring.batch.item.read.listener;

import org.springframework.batch.core.ItemReadListener;

public class StepItemReadListener implements ItemReadListener {

	@Override
	public void beforeRead() {
		System.out.println("ItemReadListener - beforeRead");
		
	}

	@Override
	public void afterRead(Object item) {
		System.out.println("ItemReadListener - afterRead");
		
	}

	@Override
	public void onReadError(Exception ex) {
		System.out.println("ItemReadListener - onReadError");
		
	}

}

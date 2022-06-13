package com.example.spring.batch.step.execution.listener;


import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class StepResultListener  implements StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("beforeStep() called");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("Called afterStep()");
		return stepExecution.getExitStatus();
	}

}

package com.example.spring.batch.jobexecutionlistener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobResultListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("beforeJob() method called");
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED ) {
	        System.out.println("afterJob() method called");
	        System.out.println("Job is completed");
	    }
	    else if (jobExecution.getStatus() == BatchStatus.FAILED) {
	    	 System.out.println("afterJob() method called");
		        System.out.println("Job is failed");
	    }
		
	}

}

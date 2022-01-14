package za.co.africanbank.datascience.abdocs.job;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.africanbank.datascience.abdocs.service.ABDocsService;

@DisallowConcurrentExecution
public class ABDocsJob implements Job {

	@Autowired
	private ABDocsService service;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) {

		service.allocations();

	}
}

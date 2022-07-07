package za.co.africanbank.datascience.abdocs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//exclude = { SecurityAutoConfiguration.class } ,
@Import({SchedulerConfig.class})
@SpringBootApplication(scanBasePackages = {"za.co.africanbank.datascience.abdocs.controllers","za.co.africanbank.datascience.abdocs.job","za.co.africanbank.datascience.abdocs.service","za.co.africanbank.datascience.abdocs.dao","za.co.africanbank.datascience.abdocs.beans","za.co.africanbank.datascience.abdocs.authentication"})
@EnableJpaRepositories(basePackages = "za.co.africanbank.datascience.abdocs.repositories")
@EntityScan(basePackages = "za.co.africanbank.datascience.abdocs.entities")
public class AbdocsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbdocsApplication.class, args);
	}

}

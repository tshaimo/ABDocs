package za.co.africanbank.datascience.abdocs;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import za.co.africanbank.datascience.abdocs.job.ABDocsJob;


@Configuration
public class SchedulerConfig {
 
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfig.class);
 
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
  
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
 
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, Trigger[] traceJobTrigger)throws IOException {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(traceJobTrigger);
        LOG.info("starting jobs....");
        return factory;
    }
    
    @Bean(name = "ABDocsJob")
    public static CronTriggerFactoryBean  simpleJobTrigger(@Qualifier("simpleJobDetail")JobDetail readerJob,@Value("${abdocsjob.cronExpression}") String cronExpression) {
    	 CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
    	 factoryBean.setJobDetail(readerJob);
    	 factoryBean.setCronExpression(cronExpression);
    	 factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
    	 return factoryBean;
    	 }
 
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
 
    @Bean
    public JobDetailFactoryBean simpleJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(ABDocsJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

   /* @Bean
    public DataSource getDataSource(){
    	DataSourceBuilder source = DataSourceBuilder.create();
    	source.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("jdbc:sqlserver://"+System.getenv("db_name")+";databaseName=ABDocs");
        source.url((System.getenv("db_name") != null)
    			?"jdbc:sqlserver://"+System.getProperty("db_name")+";databaseName=ABDocs"
    				:"jdbc:sqlserver://10.30.121.215;databaseName=ABDocs");
    	source.username("Blaze");
    	source.password("jZ&5^$L3*kj");
    	return source.build();
    }*/
}

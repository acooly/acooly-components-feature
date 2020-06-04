package com.acooly.module.scheduler;

import com.acooly.core.common.boot.ApplicationContextHolder;
import com.acooly.core.common.dao.support.StandardDatabaseScriptIniter;
import com.acooly.module.scheduler.job.AutowiringSpringBeanJobFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

import static com.acooly.module.scheduler.SchedulerProperties.PREFIX;

/**
 * @author shuijing
 */
@Configuration
@EnableConfigurationProperties({SchedulerProperties.class})
@ConditionalOnProperty(value = PREFIX + ".enable", matchIfMissing = true)
@ComponentScan(basePackages = "com.acooly.module.scheduler")
public class SchedulerAutoConfig {

    @Autowired
    private SchedulerProperties schedulerProperties;

    @Bean
    public StandardDatabaseScriptIniter schedulerScriptIniter() {
        return new DefaultStandardDatabaseScriptIniter();
    }


    @ConditionalOnProperty(value = PREFIX + ".enablejob", matchIfMissing = true)
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory)
            throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //当配置文件修改后，启动的时候更新triggers
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        Resource quartzResource =
                ApplicationContextHolder.get().getResource("classpath:META-INF/quartz.properties");
        propertiesFactoryBean.setLocation(quartzResource);
        propertiesFactoryBean.afterPropertiesSet();
        Properties properties = propertiesFactoryBean.getObject();
        properties.setProperty(
                "org.quartz.threadPool.threadCount",
                String.valueOf(schedulerProperties.getThreadCount()));
        properties.setProperty(
                "org.quartz.jobStore.clusterCheckinInterval",
                String.valueOf(schedulerProperties.getClusterCheckinInterval()));
        return properties;
    }
}

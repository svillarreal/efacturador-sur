package ar.com.proevolutionit.efacturador.base.view.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Value(value = "#{systemProperties['efacturador.configuration.dir']}")
	private String configDir;

	/*
	 * @see http://stackoverflow.com/a/25158853
	 * 
	 * To disable destroy method inference for a particular @Bean, specify an
	 * empty string as the value, e.g. @Bean(destroyMethod="")
	 */
	@Bean(destroyMethod = "")
	public DataSource dataSource() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		dataSourceLookup.setResourceRef(true);
		return dataSourceLookup.getDataSource("java:comp/env/jdbc/efacturador");
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) throws FileNotFoundException, IOException {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource);
		localSessionFactoryBean.setPackagesToScan("ar.com.proevolutionit.efacturador");
		Properties properties = new Properties();
		properties.load(new FileInputStream(FilenameUtils.concat(configDir, "hibernate.properties")));
		localSessionFactoryBean.setHibernateProperties(properties);
		return localSessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}

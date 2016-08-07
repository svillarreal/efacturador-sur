package ar.com.proevolutionit.efacturador.base.view.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.fop.apps.FopFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import com.topgroup.commons.utils.spring.service.SpringConverterService;

@Configuration
@EnableCaching
@ComponentScan(basePackages = { "ar.com.proevolutionit.efacturador" })
public class GeneralConfig {

	@Bean
	public SpringConverterService converterService() {
		SpringConverterService converterService = new SpringConverterService();
		// converterService.addConverter(myConverter());
		return converterService;
	}

	@Bean
	public FactoryBean<net.sf.ehcache.CacheManager> ehcacheCacheManager() {
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		ehCacheManagerFactoryBean.setShared(true);
		return ehCacheManagerFactoryBean;
	}

	@Bean
	public CacheManager cacheManager(net.sf.ehcache.CacheManager cacheManager) throws Exception {
		return new EhCacheCacheManager(cacheManager);
	}

	// @Bean
	// public MyConverter myConverter() {
	// return new MyConverter();
	// }

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasenames("classpath:messages", "classpath:com/topgroup/commons/vaadin/messages");
		resource.setDefaultEncoding("UTF-8");
		resource.setUseCodeAsDefaultMessage(true);
		resource.setCacheSeconds(-1);
		return resource;
	}

	@Bean
	public PropertyPlaceholderConfigurer propertyConfigurer() throws IOException {
		PropertyPlaceholderConfigurer properties = new PropertyPlaceholderConfigurer();
		properties.setLocations(new Resource[] { new FileSystemResource(
				FilenameUtils.concat(System.getProperty("efacturador.configuration.dir"), "configuration.properties")) });
		return properties;
	}

	@Bean
	public FopFactory fopFactory() {
		return FopFactory.newInstance();
	}

	@Bean
	public VelocityEngine velocityEngine() throws VelocityException, IOException {
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		Properties props = new Properties();
		props.put("input.encoding", "UTF-8");
		props.put("output.encoding", "UTF-8");
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader." + "ClasspathResourceLoader");
		factory.setResourceLoaderPath(
				"C:\\dev\\git\\efacturador-sur\\fuentes\\trunk\\efacturador\\src\\main\\config\\templates");
		factory.setPreferFileSystemAccess(true);
		factory.setVelocityProperties(props);
		return factory.createVelocityEngine();
	}
}

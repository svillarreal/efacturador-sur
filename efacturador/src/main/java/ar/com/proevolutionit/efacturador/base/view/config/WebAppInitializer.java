package ar.com.proevolutionit.efacturador.base.view.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionTrackingMode;

import org.apache.commons.io.FilenameUtils;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.topgroup.commons.utils.listener.AdvancedLog4jConfigListener;
import ar.com.proevolutionit.efacturador.base.view.EFacturadorMainUI;
import com.vaadin.spring.server.SpringVaadinServlet;

import net.sf.ehcache.constructs.web.ShutdownListener;
import net.sf.ehcache.constructs.web.filter.GzipFilter;

public class WebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(GeneralConfig.class, DatabaseConfig.class, AsyncConfig.class);

		String configDir = System.getProperty("efacturador.configuration.dir");
		container.setInitParameter("log4jConfigLocation", "file:///" + FilenameUtils.concat(configDir, "log4j.properties"));
		container.setInitParameter("log4jRefreshInterval", "30000");

		container.setInitParameter("varyHeader", "true");
		container.setInitParameter("productionMode", "true");
		container.addListener(new AdvancedLog4jConfigListener());
		container.addListener(new ContextLoaderListener(rootContext));
		container.addListener(new RequestContextListener());
		container.addListener(new ShutdownListener());

		Dynamic osivFilter = container.addFilter("openSessionInViewFilter", new OpenSessionInViewFilter());
		osivFilter.setInitParameter("sessionFactoryBeanName", "sessionFactory");
		osivFilter.addMappingForUrlPatterns(getDispatcherTypes(), true, "/*");

		Dynamic characterEncodingFilter = container.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(getDispatcherTypes(), false, "/*");

		Dynamic gzipFilter = container.addFilter("gzipFilter", new GzipFilter());
		gzipFilter.addMappingForUrlPatterns(getDispatcherTypes(), false, "/*");

		// Disable JSESSIONID in URL
		container.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));

		ServletRegistration.Dynamic dispatcher = container.addServlet("Vaadin-Application-Servlet", new SpringVaadinServlet());
		dispatcher.setLoadOnStartup(1);
		dispatcher.setInitParameter("ui", EFacturadorMainUI.class.getName());
		dispatcher.addMapping("/*");
	}

	private EnumSet<DispatcherType> getDispatcherTypes() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC);
	}

}

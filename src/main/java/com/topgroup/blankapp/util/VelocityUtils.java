package com.topgroup.blankapp.util;

import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VelocityUtils {

	public static final Logger logger = LoggerFactory.getLogger(VelocityUtils.class);

	public static String fillTemplate(String templateName, Map<String, Object> properties) {
		String res = null;

		try {
			// http://velocity.apache.org/engine/releases/velocity-1.7/developer-guide.html
			Properties props = new Properties();
			props.setProperty("resource.loader", "classpath");
			props.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			props.setProperty("classpath.resource.loader.cache", "true");

			Context context = new VelocityContext();
			for (Entry<String, Object> entry : properties.entrySet()) {
				context.put(entry.getKey(), entry.getValue());
			}

			VelocityEngine engine = new VelocityEngine();
			engine.init(props);
			Template template = engine.getTemplate(templateName);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			res = writer.toString();

			logger.debug("Template result: {}", res);

		} catch (Exception e) {
			res = null;
			e.printStackTrace();
		}

		return res;
	}

}

package com.topgroup.blankapp.view.base.command;

import java.util.Date;
import java.util.Properties;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.ui.MenuBar.MenuItem;

import com.topgroup.blankapp.view.base.AboutForm;
import com.topgroup.blankapp.view.base.bean.AboutBean;
import com.topgroup.commons.vaadin.util.VaadinUtil;

@SuppressWarnings("serial")
@Component("usuario/acercaDe")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class AcercaDeCommand extends BaseCommand {

	@Override
	public void menuSelected(MenuItem selectedItem) {
		final AboutBean aboutBean = new AboutBean();
		InputStream is = ((com.vaadin.terminal.gwt.server.WebApplicationContext) application.getContext()).getHttpSession().getServletContext()
				.getResourceAsStream("/META-INF/build.properties");
		Properties props = new Properties();
		try {
			if (is != null) {
				props.load(is);
				aboutBean.setVersion(props.getProperty("build.version.full"));
				aboutBean.setGroupId(props.getProperty("build.groupId"));
				aboutBean.setArtifactId(props.getProperty("build.artifactId"));
				aboutBean.setReleaseDate(new SimpleDateFormat().format(new Date(Long.parseLong(props.getProperty("build.timestamp.millis")))));
			}
		} catch (Exception e) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bos);
			e.printStackTrace(ps);
			VaadinUtil.showMessageError(application.getMainWindow(), "<h2>Problem loading build.properties:<h2><h6> "
					+ (new String(bos.toByteArray())).substring(0, 1000) + "...");
		}
		AboutForm aboutForm = new AboutForm();
		aboutForm.init(aboutBean, aboutForm);
		window = aboutForm.createWindow();
		super.menuSelected(selectedItem);
	}

}

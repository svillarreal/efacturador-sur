/**
 * 
 */
package com.topgroup.blankapp.view.base.bean;

import com.topgroup.commons.vaadin.annotation.FieldForm;
import com.topgroup.commons.vaadin.view.bean.ViewBean;

/**
 * Bean de presentación que representa la información de la primer pestaña de la pantalla "Acerca de ..."
 * 
 * @author mlopez
 *
 */
public class AboutBean implements ViewBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FieldForm(messageKey = "about.form.tabversion.version", required = false)
	private String version;
	@FieldForm(messageKey = "about.form.tabversion.groupId", required = false)
	private String groupId;
	@FieldForm(messageKey = "about.form.tabversion.artifactId", required = false)
	private String artifactId;
	@FieldForm(messageKey = "about.form.tabversion.releaseDate", required = false)
	private String releaseDate;
	
	
	/* (non-Javadoc)
	 * @see com.topgroup.commons.vaadin.view.form.BaseBean#initialize()
	 */
	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getArtifactId() {
		return artifactId;
	}


	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}


	public String getReleaseDate() {
		return releaseDate;
	}


	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
	

}

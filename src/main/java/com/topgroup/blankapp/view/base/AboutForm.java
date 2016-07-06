package com.topgroup.blankapp.view.base;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import com.topgroup.blankapp.view.base.bean.AboutBean;
import com.topgroup.commons.vaadin.callback.CallbackForm;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;
import com.topgroup.commons.vaadin.view.panel.BaseFormPanel;

/**
 * Formulario para la pantalla acerca de ...
 * 
 * @author mlopez
 * 
 */
@Component("misdatos/acercaDe")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class AboutForm extends BaseFormPanel<AboutBean> implements CallbackForm {

	private static final String TITLE_PANEL = VaadinUtil.getMessage("about.form.tabversion.title");

	private static final ThemeResource icon1 = new ThemeResource("../applayout/img/vcard.png");

	private static final ThemeResource icon2 = new ThemeResource("../applayout/img/script_code.png");

	private static final ThemeResource icon3 = new ThemeResource("../applayout/img/computer.png");

	private static final long serialVersionUID = -6241486511113847293L;

	@Override
	public void accept(Event event) {
		closeVindow();
	}

	@Override
	public void cancel(Event event) {
		accept(event);
	}

	@Override
	protected String[] getVisibleItemProperties() {
		return new String[] { "version", "groupId", "artifactId", "releaseDate" };
	}

	@Override
	protected String getTitlePanel() {
		return TITLE_PANEL;
	}

	@Override
	protected String getWindowWidth() {
		return "700px";
	}

	@Override
	protected int getModeForm() {
		return MODE_VIEW_FORM;
	}

	@Override
	protected void addFieldGenerator(PanelBeanForm<AboutBean> beanForm) {
	}

	@Override
	protected PanelBeanForm<AboutBean> createBeanForm(AboutBean parameter, int mode) {

		PanelBeanForm<AboutBean> beanForm = new PanelBeanForm<AboutBean>(parameter) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			private Panel layoutAbout;

			// Icons for the table

			@SuppressWarnings("serial")
			@Override
			protected void prepareLayout() {

				VerticalLayout verticalLayout = (VerticalLayout) getContent();
				verticalLayout.setMargin(false);
				verticalLayout.setSpacing(true);

				// Tab 1 content
				VerticalLayout l1 = new VerticalLayout();
				FormLayout formLayout = createLayoutPanel();
				layoutAbout = new Panel(VaadinUtil.getMessage("about.form.tabversion.versionpanel"), formLayout);
				l1.setMargin(true);
				l1.addComponent(layoutAbout);
				// Tab 2 content
				VerticalLayout l2 = new VerticalLayout();
				l2.setMargin(true);

				Panel p = new Panel("", new FormLayout());
				Table tabla = new Table();
				tabla.setSelectable(true);
				tabla.setWidth("100%");
				tabla.setHeight("295px");
				tabla.setData(System.getProperties().entrySet());
				tabla.setContainerDataSource(new BeanItemContainer<Map.Entry<Object, Object>>(Map.Entry.class, System.getProperties().entrySet()));
				tabla.addGeneratedColumn("key", new ColumnGenerator() {

					@Override
					public Object generateCell(Table source, Object itemId, Object columnId) {
						Map.Entry ent = (Entry) itemId;
						return ent.getKey();
					}
				});
				tabla.addGeneratedColumn("value", new ColumnGenerator() {

					@Override
					public Object generateCell(Table source, Object itemId, Object columnId) {
						Map.Entry ent = (Entry) itemId;
						return ent.getValue();
					}
				});
				p.addComponent(tabla);
				l2.addComponent(p);

				// Tab 3 content
				VerticalLayout l3 = new VerticalLayout();
				l3.setMargin(true);
				p = new Panel("", new FormLayout());
				tabla = new Table();
				tabla.setSelectable(true);
				tabla.setWidth("100%");
				tabla.setHeight("295px");
				tabla.setData(System.getProperties().entrySet());
				tabla.setContainerDataSource(new BeanItemContainer<Map.Entry<String, String>>(Map.Entry.class, System.getenv().entrySet()));
				tabla.addGeneratedColumn("key", new ColumnGenerator() {

					@Override
					public Object generateCell(Table source, Object itemId, Object columnId) {
						Map.Entry ent = (Entry) itemId;
						return ent.getKey();
					}
				});
				tabla.addGeneratedColumn("value", new ColumnGenerator() {

					@Override
					public Object generateCell(Table source, Object itemId, Object columnId) {
						Map.Entry ent = (Entry) itemId;
						return ent.getValue();
					}
				});
				p.addComponent(tabla);
				l3.addComponent(p);

				TabSheet t = new TabSheet();
				t.setHeight("400px");
				t.setWidth("660px");

				t.addTab(l1, VaadinUtil.getMessage("about.form.tabversion.title"), icon1);
				t.addTab(l2, VaadinUtil.getMessage("about.form.tabsysprops.title"), icon2);
				t.addTab(l3, VaadinUtil.getMessage("about.form.tabenvironment.title"), icon3);

				addComponent(t);

			}

			protected FormLayout createLayoutPanel() {
				FormLayout layout = new FormLayout();
				layout.setWidth(100, UNITS_PERCENTAGE);
				layout.setMargin(true, true, true, true);
				return layout;
			}

			@Override
			protected void attachField(Object propertyId, Field field) {
				String property = (String) propertyId;
				field.setWidth(100, UNITS_PERCENTAGE);
				layoutAbout.addComponent(field);
			}

		};

		if (mode == MODE_VIEW_FORM) {
			// No ejecuta las validaciones del formulario
			beanForm.setJsr303Validate(false);
			beanForm.setReadOnly(true);
		}

		setStyleName(Reindeer.PANEL_LIGHT);

		return beanForm;
	}

}

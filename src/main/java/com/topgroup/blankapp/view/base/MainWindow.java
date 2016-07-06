package com.topgroup.blankapp.view.base;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.topgroup.commons.vaadin.util.MultiWindow;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Window;

@Component("secure")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainWindow extends Window implements MultiWindow {

	private static final long serialVersionUID = 1395116844623937385L;

	@Autowired
	private HeaderPanel headerPanel;

	@Autowired
	private Menu menu;

	@Autowired
	private HomePanel homePanel;

	@Autowired
	private BodyPanel bodyPanel;

	@Autowired
	private FooterPanel footerPanel;

	@PostConstruct
	protected final void init() {
		setName("secure");
		setCaption(VaadinUtil.getMessage("blankapp.title"));
		AbsoluteLayout layout = new AbsoluteLayout();
		layout.setSizeFull();
		setContent(layout);
		
//		setTheme("applayout");
		
		layout.addComponent(headerPanel, "top: 0px;");
		layout.addComponent(menu, "top: 80px;");
		layout.addComponent(homePanel, "top: 80px; right:0px;");
		layout.addComponent(bodyPanel, "top: 100px;");
		layout.addComponent(footerPanel, "bottom: 0px;");
	}

	@Override
	public boolean isMainWindow() {
		return true;
	}

	public void setBody(com.vaadin.ui.Component component) {
		bodyPanel.setBody(component);
	}

	public void setBody(String beanName) {
		setBody(menu.getComponent(beanName));
	}

	@Override
	public boolean removeWindow(Window window) {
		return super.removeWindow(window);
	}

}

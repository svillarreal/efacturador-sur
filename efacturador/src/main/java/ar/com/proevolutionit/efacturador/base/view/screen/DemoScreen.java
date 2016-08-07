package ar.com.proevolutionit.efacturador.base.view.screen;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component("demoScreen")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class DemoScreen extends Panel {

	@Autowired
	private EFacturadorPanel eFacturadorPanel;

	@PostConstruct
	private void init() {
		VerticalLayout container = new VerticalLayout();
		container.setMargin(true);
		setContent(container);

		container.addComponent(new Label(VaadinUtil.getMessage("demo.title")));
		container.addComponent(eFacturadorPanel);
	}

}

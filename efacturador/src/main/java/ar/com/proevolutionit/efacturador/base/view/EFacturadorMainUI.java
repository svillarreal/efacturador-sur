package ar.com.proevolutionit.efacturador.base.view;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

import com.topgroup.commons.utils.lang.Errors;
import com.topgroup.commons.vaadin.converter.DefaultConverterFactory;
import com.topgroup.commons.vaadin.util.BodyPanelChangeEvent;
import com.topgroup.commons.vaadin.util.BodyPanelChangeException;
import com.topgroup.commons.vaadin.util.BodyPanelChangeListener;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.home.core.ErrorWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ar.com.proevolutionit.efacturador.base.view.presenter.EFacturadorMainUIPresenter;
import ar.com.proevolutionit.efacturador.base.view.screen.EFacturadorMainUIView;

@SuppressWarnings("serial")
@Theme("applayout")
@SpringUI
@Widgetset("Cotizador")
public class EFacturadorMainUI extends UI implements ApplicationContextAware, EFacturadorMainUIView {

	@Autowired
	private EFacturadorMainUIPresenter presenter;

	private ApplicationContext applicationContext;

	@Autowired(required = false)
	private List<BodyPanelChangeListener> bodyPanelChangeListeners;

	@Autowired
	private EFacturadorBodyPanel bodyPanel;

	private Deque<Component> screenStack = buildScreenStack();

	@Configuration
	@EnableVaadin
	public static class MyConfiguration {

	}

	@Override
	protected void init(VaadinRequest request) {
		presenter.setView(this);
		VaadinUtil.setMessageSource(applicationContext.getBean("messageSource", MessageSource.class));

		VaadinSession.getCurrent().setConverterFactory(new DefaultConverterFactory());

		setErrorHandler(new DefaultErrorHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				Window errorWindow = new ErrorWindow((Exception) event.getThrowable());
				addWindow(errorWindow);
			}
		});

		setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		layout.addComponent(bodyPanel);
		layout.setExpandRatio(bodyPanel, 1);
		setContent(layout);

		Panel panel = applicationContext.getBean(getPage().getUriFragment(), Panel.class);
		layout.addComponent(panel);
	}

	/**
	 * @param request
	 * @param parameter
	 * @return
	 */
	public String getURLParam(VaadinRequest request, String parameter) {
		return request.getParameter(parameter);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void showError(Errors errors) {
		VaadinUtil.showMessageError(getUI(), errors);
	}

	public void setBody(com.vaadin.ui.Component component, boolean clearStack) {
		try {
			notifyBodyPanelChangeListeners(component);
			if (clearStack) {
				screenStack.clear();
			}
			bodyPanel.setBody(component);
		} catch (BodyPanelChangeException e) {
			VaadinUtil.showMessage(getUI(), VaadinUtil.getMessage(e.getMessageKey(), e.getMessageArgs()));
		}
	}

	private void notifyBodyPanelChangeListeners(com.vaadin.ui.Component newBodyContent)
			throws BodyPanelChangeException {
		if (bodyPanelChangeListeners == null) {
			return;
		}
		for (BodyPanelChangeListener listener : bodyPanelChangeListeners) {
			listener.onChange(new BodyPanelChangeEvent(bodyPanel, newBodyContent));
		}
	}

	protected Deque<Component> buildScreenStack() {
		return new LinkedList<Component>();
	}

	public void clearBody() {
		com.vaadin.ui.Component bodyContent = bodyPanel.getBodyContent();
		if (bodyContent != null) {
			bodyPanel.clearContent(bodyContent);
			screenStack.remove(bodyContent);
			Component stackableScreen = screenStack.poll();
			if (stackableScreen != null && !stackableScreen.equals(bodyContent)) {
				setBody(stackableScreen, false);
			}
		}
	}

	public void stackBody(Component sourceScreen, com.vaadin.ui.Component targetScreen) {
		try {
			notifyBodyPanelChangeListeners(targetScreen);
			screenStack.push(sourceScreen);
			bodyPanel.setBody(targetScreen);
		} catch (BodyPanelChangeException e) {
			VaadinUtil.showMessage(getUI(), VaadinUtil.getMessage(e.getMessageKey(), e.getMessageArgs()));
		}
	}

	public void stackBody(com.vaadin.ui.Component targetScreen) {
		com.vaadin.ui.Component bodyContent = bodyPanel.getBodyContent();
		stackBody(bodyContent, targetScreen);
	}

}

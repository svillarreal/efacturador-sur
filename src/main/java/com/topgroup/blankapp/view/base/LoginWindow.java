package com.topgroup.blankapp.view.base;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

import com.topgroup.blankapp.util.VelocityUtils;
import com.topgroup.commons.security.exception.BusinessException;
import com.topgroup.commons.vaadin.util.MultiWindow;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Link;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

@Component("login")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginWindow extends Window implements MultiWindow {

	private static final long serialVersionUID = -4110631591217781230L;

	public LoginWindow() {
		super();
	}

	@PostConstruct
	protected final void init() {
		setName("login");
		setCaption(VaadinUtil.getMessage("login.window.title"));
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		setContent(layout);
		layout.addStyleName("login_window");		
				
		// login html
		Panel panelForm = getPanelForm();		
		this.getWindow().addWindow(this.createWindowFromPanel(panelForm));		
	}	

	@Override
	public boolean isMainWindow() {
		return false;
	}
			
	private Panel getPanelForm(){
		Panel panelForm = new Panel();
		panelForm.addStyleName(Reindeer.PANEL_LIGHT);	
					
		GridLayout gridLayout = new GridLayout(1, 1);
		gridLayout.addStyleName(Reindeer.LAYOUT_WHITE);
		gridLayout.setSizeFull();
		gridLayout.setMargin(false, true, false, true);
		panelForm.setContent(gridLayout);

		LoginForm loginForm = new CustomLoginForm();
		loginForm.setUsernameCaption(VaadinUtil.getMessage("login.username.label"));
		loginForm.setPasswordCaption(VaadinUtil.getMessage("login.password.label"));
		loginForm.setLoginButtonCaption(VaadinUtil.getMessage("login.aceptar.label"));
		gridLayout.addComponent(loginForm);
		
		return panelForm;		
	}
	
	private Window createWindowFromPanel(Panel panelForm){
		Window window = new Window(VaadinUtil.getMessage("login.panel.title"));
		window.setWidth(340, UNITS_PIXELS);
		window.setHeight(-1, UNITS_PIXELS);
		window.setModal(true);
		window.setClosable(false);
		window.setScrollable(false);
		window.setResizable(false);
		window.setStyleName("login");
		
		Link link = new Link();
		link.setDescription("Blankapp 2.0");
		link.setIcon(new ThemeResource("img/logo-site.png"));
		link.setWidth(300, UNITS_PIXELS);
		link.setHeight(70, UNITS_PIXELS);
						
		window.addComponent(link);
		window.addComponent(panelForm);
		return window;
	}
}

class CustomLoginForm extends LoginForm {

	private static final long serialVersionUID = 7607968565241184922L;

	public static final String TEMPLATE_LOGIN = "com/topgroup/templates/login.html";
	
	private static final String UsuarioNoVigenteAcegyException ="com.topgroup.commons.security.exception.UsuarioNoVigenteAcegyException";
	private static final String BadCredentialsException = "org.springframework.security.authentication.BadCredentialsException";
	private static final String UsernameNotFoundException = "org.springframework.security.core.userdetails.UsernameNotFoundException";
	private static final String LockedException = "org.springframework.security.authentication.LockedException";
	private static final String ParticipationDeletedException = "com.topgroup.commons.security.exception.ParticipationDeletedException";
	private static final String ExpiredAccountBadException = "com.topgroup.commons.security.exception.ExpiredAccountBadException";
	

	protected final Logger logger = LoggerFactory.getLogger(CustomLoginForm.class);

	@Override
	protected byte[] getLoginHTML() {
		Map<String, Object> properties = new HashMap<String, Object>();

		handlerAuthenticationException(properties);

		properties.put("APPURI", StringUtils.remove(getApplication().getURL().toString(), "/app") + "j_spring_security_check");
		properties.put("GENERATED_BODY_CLASSNAME", ApplicationConnection.GENERATED_BODY_CLASSNAME);
		properties.put("USERNAME_CAPTION", getUsernameCaption());
		properties.put("PASSWORD_CAPTION", getPasswordCaption());
		properties.put("LOGIN_BUTTON_CAPTION", getLoginButtonCaption());

		return VelocityUtils.fillTemplate(TEMPLATE_LOGIN, properties).getBytes();
	}

	public void handlerAuthenticationException(Map<String, Object> properties) {
		try {
			HttpSession session = VaadinUtil.getSession(this);
			Exception exception = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			String message = null;
			if (exception != null) {
				message = exception.getMessage();
				if (exception instanceof AuthenticationException && exception.getCause() instanceof BusinessException) {
					BusinessException e = (BusinessException)exception.getCause();
					message = VaadinUtil.getMessage(e.getErrorDescriptionKey(), e.getValues());
				}else if(exception.getClass().getName().equals(UsuarioNoVigenteAcegyException)){
					message = VaadinUtil.getMessage("security.login.usuarioNoVigente");
				}else if(exception.getClass().getName().equals(BadCredentialsException)){
					message = VaadinUtil.getMessage("security.login.badCredentials");
				}else if(exception.getClass().getName().equals(UsernameNotFoundException)){
					message = VaadinUtil.getMessage("security.login.userNotFound");
				}else if(exception.getClass().getName().equals(LockedException)){
					message = VaadinUtil.getMessage("security.login.userLocked");
				}else if(exception.getClass().getName().equals(ParticipationDeletedException)){
					message = VaadinUtil.getMessage("security.login.userNotFound");
				}else if(exception.getClass().getName().equals(ExpiredAccountBadException)){
					message = VaadinUtil.getMessage("security.login.ExpiredAccount");
				}else{
					message = VaadinUtil.getMessage("security.login.errorUnknown");
				}
				session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			}
			properties.put("ERROR_MESSAGE", message);
		} catch (Exception e) {
			logger.error("Error en el formulario de login", e);
			properties.put("ERROR_MESSAGE", e.getMessage());
		}
	}

}

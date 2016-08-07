package ar.com.proevolutionit.efacturador.base.view;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.topgroup.commons.vaadin.view.home.component.BodyPanel;

/**
 * @author svillarreal
 *
 */
@SuppressWarnings("serial")
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EFacturadorBodyPanel extends BodyPanel {

}

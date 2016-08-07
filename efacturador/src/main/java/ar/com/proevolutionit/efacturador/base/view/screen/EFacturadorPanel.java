package ar.com.proevolutionit.efacturador.base.view.screen;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class EFacturadorPanel extends HorizontalLayout {

	private TextField resultadoField;

	public EFacturadorPanel() {
		VerticalLayout container = new VerticalLayout();
		container.setSpacing(true);
		container.setMargin(true);
		addComponent(container);

		container.addComponent(new DateField("Fecha:"));
		Button calcularButton = new Button("Calcular");
		calcularButton.setDescription("Click para calcular el valor");
		calcularButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				resultadoField.setValue("$565.55");

			}
		});
		container.addComponent(calcularButton);
		resultadoField = new TextField("Resultado");
		container.addComponent(resultadoField);
	}

}

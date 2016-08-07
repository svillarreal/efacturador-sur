package ar.com.proevolutionit.efacturador.base.view.presenter;

import org.springframework.stereotype.Component;

import ar.com.proevolutionit.efacturador.base.view.screen.EFacturadorMainUIView;

/**
 * @author svillarreal
 *
 */
@Component
public class EFacturadorMainUIPresenter {

	private EFacturadorMainUIView view;

	public void setView(EFacturadorMainUIView view) {
		this.view = view;
	}

}

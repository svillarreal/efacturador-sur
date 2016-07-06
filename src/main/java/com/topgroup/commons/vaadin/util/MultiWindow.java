package com.topgroup.commons.vaadin.util;

public interface MultiWindow {

	/**
	 * Nombre que identifica a la ventana.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Indica si es la ventana principal de la aplicación.
	 * 
	 * @return
	 */
	boolean isMainWindow();

}

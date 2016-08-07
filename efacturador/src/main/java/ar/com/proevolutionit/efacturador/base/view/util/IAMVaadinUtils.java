package ar.com.proevolutionit.efacturador.base.view.util;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.ConfirmDialog.Listener;

import com.topgroup.commons.vaadin.field.DataCombobox;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

/**
 * @author svillarreal
 *
 */
public class IAMVaadinUtils {

	/**
	 * @param form
	 * 
	 */
	public static void activateBlurValidations(Form form) {

		for (Object itemId : form.getItemPropertyIds()) {
			Field field = form.getField(itemId);
			if (field != null) {
				if (field instanceof TextField) {
					final TextField tf = (TextField) field;
					tf.addBlurListener(new BlurListener() {
						@Override
						public void blur(BlurEvent event) {
							try {
								tf.isValid();
							} catch (InvalidValueException e) {
								// Está bien que no haga nada, el isValid() es quien muestra los mensajes x pantalla, pero no me sirve de nada ésta
								// excepción
							}
						}
					});
				}
				if (field instanceof DateField) {
					final DateField df = (DateField) field;
					df.addBlurListener(new BlurListener() {
						@Override
						public void blur(BlurEvent event) {
							try {
								df.isValid();
							} catch (InvalidValueException e) {
								// Está bien que no haga nada, el isValid() es quien muestra los mensajes x pantalla, pero no me sirve de nada ésta
								// excepción
							}
						}
					});
				}
				if (field instanceof DataCombobox) {
					final DataCombobox dcf = (DataCombobox) field;
					dcf.addBlurListener(new BlurListener() {
						@Override
						public void blur(BlurEvent event) {
							try {
								dcf.isValid();
							} catch (InvalidValueException e) {
								// Está bien que no haga nada, el isValid() es quien muestra los mensajes x pantalla, pero no me sirve de nada ésta
								// excepción
							}
						}
					});
				}
				if (field instanceof ComboBox) {
					final ComboBox cf = (ComboBox) field;
					cf.addBlurListener(new BlurListener() {
						@Override
						public void blur(BlurEvent event) {
							try {
								cf.isValid();
							} catch (InvalidValueException e) {
								// Está bien que no haga nada, el isValid() es quien muestra los mensajes x pantalla, pero no me sirve de nada ésta
								// excepción
							}
						}
					});
				}
				if (field instanceof CheckBox) {
					final CheckBox cb = (CheckBox) field;
					cb.addBlurListener(new BlurListener() {
						@Override
						public void blur(BlurEvent event) {
							try {
								cb.isValid();
							} catch (InvalidValueException e) {
								// Está bien que no haga nada, el isValid() es quien muestra los mensajes x pantalla, pero no me sirve de nada ésta
								// excepción
							}
						}
					});
				}
			}
		}

	}

	/**
	 * @param current
	 * @param message
	 */
	public static void toastError(UI current, String message) {
		Notification notification = new Notification(message, Notification.TYPE_ERROR_MESSAGE);
		notification.setPosition(Notification.POSITION_CENTERED);
		notification.setDelayMsec(VaadinUtil.TOAST_DELAY_NORMAL);
		current.showNotification(notification);
	}

	/**
	 * @param current
	 * @param message
	 */
	public static void toastInfo(UI current, String message) {
		Notification notification = new Notification(message, Notification.TYPE_TRAY_NOTIFICATION);
		notification.setPosition(Notification.POSITION_CENTERED);
		notification.setDelayMsec(VaadinUtil.TOAST_DELAY_NORMAL);
		current.showNotification(notification);
	}

	/**
	 * 
	 */
	public static void acceptMessage(UI ui, String message, Listener listener) {
		String okMessage = VaadinUtil.getMessage("button.accept");
		ConfirmDialog dialog = ConfirmDialog.show(ui, VaadinUtil.getMessage("acceptDialog.title"), message, okMessage, null, listener);
		dialog.setClosable(false);
		Button cancel = dialog.getCancelButton();
		HorizontalLayout hl = (HorizontalLayout) cancel.getParent();
		hl.setComponentAlignment(cancel, Alignment.MIDDLE_CENTER);
		hl.removeComponent(cancel);
	}

}

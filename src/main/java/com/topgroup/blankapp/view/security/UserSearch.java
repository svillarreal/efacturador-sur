package com.topgroup.blankapp.view.security;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.VerticalLayout;

import com.topgroup.blankapp.view.base.Constants;
import com.topgroup.blankapp.view.security.bean.BlankPassBean;
import com.topgroup.blankapp.view.security.bean.InhabilitarBean;
import com.topgroup.blankapp.view.security.bean.UserBean;
import com.topgroup.blankapp.view.security.bean.UserSearchBean;
import com.topgroup.commons.security.filter.ParticipationFilter;
import com.topgroup.commons.security.model.Participation;
import com.topgroup.commons.security.model.Profile;
import com.topgroup.commons.security.model.User;
import com.topgroup.commons.security.model.UserRepresentation;
import com.topgroup.commons.security.service.IParticipationService;
import com.topgroup.commons.security.service.IProfileService;
import com.topgroup.commons.security.service.ISystemService;
import com.topgroup.commons.security.service.IUserService;
import com.topgroup.commons.security.util.SecurityUtil;
import com.topgroup.commons.utils.lang.ConverterService;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.FieldGenerator;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;
import com.topgroup.commons.vaadin.view.panel.BaseSearchPanel;

@Component("usuarios/usuariosSearch")
@Scope(WebApplicationContext.SCOPE_REQUEST)
@SuppressWarnings("serial")
public class UserSearch extends BaseSearchPanel<UserSearchBean> {

	protected static final String[] VISIBLE_COLUMNS_TABLE = new String[] { "username", "name", "lastName", "profileName", "email", "enabled" };

	protected static final String[] FORM_FIELDS = new String[] { "username", "profile", "name", "lastName" };

	private static final String[] COLUMN_HEADERS = new String[] { VaadinUtil.getMessage("user.search.column.username"),
			VaadinUtil.getMessage("user.search.column.name"), VaadinUtil.getMessage("user.search.column.lastName"),
			VaadinUtil.getMessage("user.search.column.profile"), VaadinUtil.getMessage("user.search.column.email"),
			VaadinUtil.getMessage("user.search.column.enable") };

	@Autowired
	private IParticipationService participationService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IProfileService profileService;

	@Autowired
	private ISystemService systemService;
	
	@Autowired
	private BlankPassForm blankPassForm;

	@Autowired
	private UserDataForm userDataForm;

	@Autowired
	private UserEditDataForm userEditDataForm;

	@Autowired
	private InhabilitarUserForm inhabilitarUserForm;
	
	@Autowired
	private ConverterService converterService;
	
	@Override
	protected void addTableActions() {
		// Acción de visualización
		TableAction action = createViewAction();
		addAction(action);

		// Acción de Edición
		action = createEditAction();
		addAction(action);

		// Acción de Eliminación
		action = createDeleteAction();
		addAction(action);

		// Acción Blanqueo de Password
		action = createCleanPassAction();
		addAction(action);

		// Acción de habilitación de usuario
		action = createEnableAction();
		addAction(action);

		// Acción de Inhabilitación de usuario
		action = createDisableAction();
		addAction(action);
	}

	protected TableAction createDisableAction() {
		ClickListener listener;
		TableAction action;
		listener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UserBean userBean = (UserBean) table.getValue();
				if (userBean != null) {

					Integer idUser = userBean.getIdUser();
					if (((UserRepresentation) SecurityUtil.getUsuario()).getId().equals(idUser)) {
						VaadinUtil.showMessageError(UserSearch.this.getWindow(), VaadinUtil.getMessage("security.user.error.inhabilitatehimself"));
					} else {
						final InhabilitarBean inhabilitarBean = new InhabilitarBean(idUser, userBean.getUsername());

						inhabilitarUserForm.setSearchPanel(UserSearch.this);
						inhabilitarUserForm.init(inhabilitarBean, inhabilitarUserForm);
						UserSearch.this.getWindow().addWindow(inhabilitarUserForm.createWindow());
					}
				}
			}
		};

		action = new TableAction(VaadinUtil.getMessage("user.search.disable.action"), Constants.ICON_USER_DISABLE, listener) {

			@Override
			public boolean isVisibleAction(Object target, Object sender) {
				UserBean userBean = (UserBean) target;
				return (userBean != null ? userBean.isEnabled() : false);
			}
		};
		return action;
	}

	protected TableAction createEnableAction() {
		ClickListener listener = new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {
				final UserBean userBean = (UserBean) table.getValue();
				if (userBean != null) {

					VaadinUtil.confirmMessage(UserSearch.this.getWindow(), VaadinUtil.getMessage("user.enable.confirm.title"),
							VaadinUtil.getMessage("user.enable.confirm.message", userBean.getUsername()), new ConfirmDialog.Listener() {

								@Override
								public void onClose(ConfirmDialog confirmDialog) {

									if (confirmDialog.isConfirmed()) {
										UserRepresentation ur = (UserRepresentation) SecurityUtil.getUsuario();
										User user = userService.loadUser(userBean.getIdUser());
										userService.enableUserSystem(ur.getIdSystem(), user, ur);

										// Actualiza el resultado de la busqueda
										search(event);

										VaadinUtil.showMessage(UserSearch.this.getWindow(),
												VaadinUtil.getMessage("user.enable.success.message", userBean.getUsername()));
									}

								}

							});
				}
			}
		};

		TableAction action = new TableAction(VaadinUtil.getMessage("user.search.enable.action"), Constants.ICON_USER_ENABLE, listener) {

			@Override
			public boolean isVisibleAction(Object target, Object sender) {
				UserBean userBean = (UserBean) target;
				return (userBean != null ? !userBean.isEnabled() : false);
			}
		};

		return action;
	}

	protected TableAction createCleanPassAction() {
		ClickListener listener;
		TableAction action;
		listener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UserBean userBean = (UserBean) table.getValue();
				if (userBean != null) {

					final BlankPassBean blankPassbean = new BlankPassBean();
					Participation participation = participationService.findById(userBean.getIdUser(), systemService.getCurrentSystem().getId());
					blankPassbean.setUser(participation.getUser());

					blankPassForm.init(blankPassbean, blankPassForm);
					UserSearch.this.getWindow().addWindow(blankPassForm.createWindow());
				}
			}

		};

		action = new TableAction(VaadinUtil.getMessage("user.search.cleanpass.action"), Constants.ICON_CLEAR_PASS, listener) {

			@Override
			public boolean isVisibleAction(Object target, Object sender) {
				UserBean userBean = (UserBean) target;
				return (userBean != null ? userBean.isEnabled() : false);
			}
		};
		return action;
	}

	protected TableAction createDeleteAction() {
		ClickListener listener;
		TableAction action;
		listener = new ClickListener() {

			@Override
			public void buttonClick(final ClickEvent event) {

				final UserBean userBean = (UserBean) table.getValue();
				if (userBean != null) {

					VaadinUtil.confirmMessage(UserSearch.this.getWindow(), VaadinUtil.getMessage("user.delete.confirm.title"),
							VaadinUtil.getMessage("user.delete.confirm.message", userBean.getUsername()), new ConfirmDialog.Listener() {

								@Override
								public void onClose(ConfirmDialog confirmDialog) {

									if (confirmDialog.isConfirmed()) {
										UserRepresentation ur = (UserRepresentation) SecurityUtil.getUsuario();
										userService.deleteUserSystem(ur.getIdSystem(), userBean.getIdUser(), ur);

										// Actualiza el resultado de la busqueda
										search(event);

										VaadinUtil.showMessage(UserSearch.this.getWindow(),
												VaadinUtil.getMessage("user.delete.success.message", userBean.getUsername()));
									}

								}

							});
				}
			}

		};

		action = new TableAction(VaadinUtil.getMessage("user.search.delete.action"), Constants.ICON_DELETE, listener) {

			@Override
			public boolean isVisibleAction(Object target, Object sender) {
				return target != null;
			}
		};
		return action;
	}

	protected TableAction createEditAction() {
		ClickListener listener;
		TableAction action;
		listener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UserBean userBean = (UserBean) table.getValue();
				if(userBean != null) {
					
					Participation participation = participationService.findById(userBean.getIdUser(), systemService.getCurrentSystem().getId());
					userBean.setUser(participation);
					
					userEditDataForm.setSearchPanel(UserSearch.this);
					userEditDataForm.init(userBean, userEditDataForm);
					UserSearch.this.getWindow().addWindow(userEditDataForm.createWindow());
				}
			}

		};
				
		action = new TableAction(VaadinUtil.getMessage("user.search.edit.action"), Constants.ICON_EDIT, listener) {

			@Override
			public boolean isVisibleAction(Object target, Object sender) {
				return target != null;
			}
		};
		return action;
	}

	protected TableAction createViewAction() {
		ClickListener listener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UserBean userBean = (UserBean) table.getValue();
				if(userBean != null) {
					userDataForm.init(userBean, userDataForm);
					UserSearch.this.getWindow().addWindow(userDataForm.createWindow());
				}
			}

		};

		TableAction action = new TableAction(VaadinUtil.getMessage("user.search.view.action"), Constants.ICON_VIEW, listener) {

			@Override
			public boolean isVisibleAction(Object target, Object sender) {
				return target != null;
			}
		};
		return action;
	}

	public void search(ClickEvent event) {

		try {

			UserSearchBean userSearchBean = form.getBeanItem().getBean();

			ParticipationFilter filter = new ParticipationFilter();
			if (userSearchBean.getProfile() != null) {
				filter.put(ParticipationFilter.PARTICIPATION_IDPROFILE, userSearchBean.getProfile().getId());
			}
			filter.put(ParticipationFilter.PARTICIPATION_IDSYSTEM, systemService.getCurrentSystem().getId());
			filter.put(ParticipationFilter.PARTICIPATION_LASTNAME, StringUtils.trimToNull(userSearchBean.getLastName()));
			filter.put(ParticipationFilter.PARTICIPATION_NAME, StringUtils.trimToNull(userSearchBean.getName()));
			filter.put(ParticipationFilter.PARTICIPATION_USERNAME, StringUtils.trimToNull(userSearchBean.getUsername()));
			filter.put(ParticipationFilter.PARTICIPATION_DELETED, Boolean.FALSE);

			Collection<Participation> participations = participationService.filter(filter);
			BeanItemContainer<UserBean> items = new BeanItemContainer<UserBean>(UserBean.class);
			for(Participation part: participations){
				items.addBean(converterService.convertTo(part, UserBean.class));
			}			
			reloadTableDataSource(items);

		} catch (Exception e) {
			// TODO
		}
	}

	protected void cleanSearch(ClickEvent event) {
		form.cleanFields();
	}

	@Override
	protected UserSearchBean getBeanForm() {
		return new UserSearchBean();
	}

	@Override
	protected void addFieldGenerator(PanelBeanForm<UserSearchBean> beanForm) {
		beanForm.addFieldGenerator("profile", new FieldGenerator() {

			@Override
			public Field createField(Item item, Object propertyId, com.vaadin.ui.Component uiContext) {
				ComboBox comboBox = new ComboBox();
				final BeanItemContainer<Profile> items = new BeanItemContainer<Profile>(Profile.class);
				items.addAll(profileService.findAllProfiles());
				comboBox.setContainerDataSource(items);
				comboBox.setItemCaptionPropertyId("name");
				return comboBox;
			}
		});
	}

	@Override
	protected String[] getVisibleItemProperties() {
		return FORM_FIELDS;
	}

	@Override
	protected void newEntity(ClickEvent event) {
		userEditDataForm.init(new UserBean(), userEditDataForm);
		this.getWindow().addWindow(userEditDataForm.createWindow());
	}

	@Override
	protected void addGeneratedColumn(Table table) {
		table.addGeneratedColumn("enabled", new ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				Label label = new Label();
				label.setIcon(((UserBean) itemId).isEnabled() ? Constants.ICON_CHECK : Constants.ICON_UNCHECK);
								
				VerticalLayout layout = new VerticalLayout();
				layout.addComponent(label);
				layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);

				return layout;
			}
		});
	}

	@Override
	protected String[] getColumnHeaders() {
		return COLUMN_HEADERS;
	}

	@Override
	protected Object[] getVisibleColumns() {
		return VISIBLE_COLUMNS_TABLE;
	}

	@Override
	protected Container getContainerDataSource() {
		return new BeanItemContainer<UserBean>(UserBean.class);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		UserBean userBean = (UserBean) event.getProperty().getValue();
		if (userBean != null){
			for (TableAction action : tableActions) {
				if (action.getButton() != null) {
					action.getButton().setEnabled(action.isVisibleAction(userBean, table));
				}
			}
		}
		else {
			for (TableAction action : tableActions) {
				if (action.getButton() != null) {
					action.getButton().setEnabled(false);
				}
			}
		}
	}

}
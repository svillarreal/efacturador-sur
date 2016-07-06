package com.topgroup.blankapp.view.perfil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.topgroup.blankapp.model.PerfilBean;
import com.topgroup.blankapp.model.RolBean;
import com.topgroup.blankapp.view.base.Constants;
import com.topgroup.commons.crud.business.service.ICRUDService;
import com.topgroup.commons.crud.command.CrudEditDataForm;
import com.topgroup.commons.crud.frontend.form.CrudPanelBeanForm;
import com.topgroup.commons.crud.model.CrudViewBean;
import com.topgroup.commons.crud.model.IEntity;
import com.topgroup.commons.security.model.Role;
import com.topgroup.commons.security.service.IRoleService;
import com.topgroup.commons.security.service.ISystemService;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.FieldGenerator;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;
import com.topgroup.commons.vaadin.view.layout.TableBottonLayout;
import com.topgroup.commons.vaadin.view.layout.TableLayout;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.event.LayoutEvents.LayoutClickNotifier;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Component("perfil/perfilBeanEdit")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class PerfilCrudEditDataForm extends CrudEditDataForm implements LayoutClickListener, ValueChangeListener, Handler {

	@Autowired
	IRoleService roleService;
	
	@Autowired
	ISystemService systemService;
	
	protected TableLayout tableLayout;

	protected Table table = new Table();
	
	private Button agregarButton;
		
	private FormLayout layoutDatosRoles;
	
	private List<TableAction> tableActions = new ArrayList<TableAction>(); 
				
	@Resource(name = "entityPerfilBean")
	void setEntityPerfilBean(IEntity entity) {
		this.setEntity(entity);
	}

	@Autowired
	@Override
	public void setCrudService(ICRUDService<Object> crudService) {
		super.setCrudService(crudService);
	}
		
	@Override
	protected CrudPanelBeanForm createBeanForm(final CrudViewBean parameter, final int mode) {

		CrudPanelBeanForm beanForm = new CrudPanelBeanForm(parameter) {
				
			private Panel layoutPerfil;

			private Panel layoutRoles;
			
			private FormLayout leftlayoutDatosPerfiles;
			
			private FormLayout rightlayoutDatosPerfiles;
							
			@Override
			protected void prepareLayout() {
				
				VerticalLayout verticalLayout = (VerticalLayout) getContent();
				verticalLayout.setMargin(false,true,false,true);
				verticalLayout.setSpacing(true);
				
				GridLayout layout = new GridLayout(2, 2);
				layout.setWidth(100, UNITS_PERCENTAGE);
				layout.setSpacing(true);
				layout.setMargin(false, true, true, true);
				
				leftlayoutDatosPerfiles = new FormLayout();
				leftlayoutDatosPerfiles.setMargin(false);
				leftlayoutDatosPerfiles.setWidth(100, UNITS_PERCENTAGE);
				layout.addComponent(leftlayoutDatosPerfiles, 0, 1);
			
				rightlayoutDatosPerfiles = new FormLayout();
				rightlayoutDatosPerfiles.setMargin(false);
				layout.addComponent(rightlayoutDatosPerfiles, 1, 1);
								
				layoutPerfil = new Panel(VaadinUtil.getMessage("datos.perfilBean"),layout);
				addComponent(layoutPerfil);
				
				layout = new GridLayout(2, 4);
				layout.setWidth(100, UNITS_PERCENTAGE);
				layout.setSpacing(true);
				layout.setMargin(false, true, true, true);
				
				armarMasterDetail(layout);
				
				layoutRoles = new Panel(VaadinUtil.getMessage("datos.perfilBean.roles"), layout);
				addComponent(layoutRoles);
				
			}
			
			@Override
			protected void attachField(Object propertyId, Field field) {
				String property = (String) propertyId;
				if ("name".equals(property)) {
					leftlayoutDatosPerfiles.addComponent(field);
				} else if("abbreviation".equals(property)){
					rightlayoutDatosPerfiles.addComponent(field);
				} else if (mode != MODE_VIEW_FORM){
					layoutDatosRoles.addComponent(field);
				}
			}

		};
			setStyleName(Reindeer.PANEL_LIGHT);

			if (mode == MODE_VIEW_FORM) {
				// No ejecuta las validaciones del formulario
				beanForm.setJsr303Validate(false);
				beanForm.setReadOnly(true);
			}

			this.setModeForm(mode);	
			

		return beanForm;
	}
	
	private void armarMasterDetail(GridLayout layout){
		
		if(this.getModeForm() == MODE_VIEW_FORM){
			layout.setRows(2);
			createTable();
			tableLayout = createTableLayout(table);
			tableLayout.setWidth(100, UNITS_PERCENTAGE);
			tableLayout.setMargin(true, false, false, false);
			layout.setComponentAlignment(tableLayout, Alignment.TOP_CENTER);
			layout.addComponent(tableLayout, 0, 1, 1, 1);
		} else {
			layoutDatosRoles = new FormLayout();
			layoutDatosRoles.setMargin(false);
			layoutDatosRoles.setWidth(100, UNITS_PERCENTAGE);
			layout.addComponent(layoutDatosRoles, 0, 1, 1, 1);
								
			HorizontalLayout buttonLayout = new HorizontalLayout();
			buttonLayout.setSpacing(false);
			buttonLayout.setMargin(false);
		
			Button button = createButtonAgregar();
			buttonLayout.addComponent(button);
			buttonLayout.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
			
			layout.addComponent(buttonLayout, 0, 2, 1, 2);
			layout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_RIGHT);
				
			createTable();
			tableLayout = createTableLayout(table);
			addTableActions();
			tableLayout.setWidth(100, UNITS_PERCENTAGE);
			tableLayout.setMargin(false);
			layout.setComponentAlignment(tableLayout, Alignment.TOP_CENTER);
			layout.addComponent(tableLayout, 0, 3, 1, 3);
		}
	}
	
	private Button createButtonAgregar() {

		agregarButton = new Button(VaadinUtil.getMessage("boton.agregar.label"));
		agregarButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				agregarRolGrilla();
			}
		});
		return agregarButton;
	}
	
	protected void createTable() {
		
		table.setWidth(100, UNITS_PERCENTAGE);
		table.setHeight(200, UNITS_PIXELS);
		table.setPageLength(10);
		BeanItemContainer<RolBean> items = new BeanItemContainer<RolBean>(RolBean.class);
		table.setContainerDataSource(items);
		table.setVisibleColumns(getVisibleColumns());
		table.setColumnHeaders(getColumnHeaders());
		table.addListener(this);
		table.addActionHandler(this);
		table.setSelectable(true);
	}
	
	protected String[] getVisibleColumns() {
		return new String[] { "id", "name" };
	}
	
	protected String[] getColumnHeaders() {
		return new String[] {"Código", "Nombre"};
	}
	
	private TableLayout createTableLayout(Table aTable) {
		TableLayout tableLayout = new TableBottonLayout(table, true);
		((LayoutClickNotifier) tableLayout).addListener(this);
		return tableLayout;
	}
		
	@Override
	public void layoutClick(LayoutClickEvent event) {
		com.vaadin.ui.Component component = event.getClickedComponent();
		Field field = (Field)component;
		if (field != null) {
			Object rowClick = field;
			if (rowClick != null) {
				table.focus();
				table.setValue(rowClick);
			}
		}
	}
			
	@Override
	protected void addFieldGenerator(PanelBeanForm<CrudViewBean> beanForm) {
		super.addFieldGenerator(beanForm);
		this.addFieldGeneratorRoles(beanForm);
	}
			
	protected void addFieldGeneratorRoles(PanelBeanForm<CrudViewBean> beanForm) {
		beanForm.addFieldGenerator("roles", new FieldGenerator() {

			@Override
			public Field createField(Item item, Object propertyId, com.vaadin.ui.Component uiContext) {
				ComboBox comboBox = new ComboBox();
				final BeanItemContainer<Role> items = new BeanItemContainer<Role>(Role.class);
				items.addAll(roleService.findRoles(systemService.getCurrentSystem().getId(),null,null));
				comboBox.setContainerDataSource(items);
				comboBox.setItemCaptionPropertyId("name");
				comboBox.setCaption(VaadinUtil.getMessage("perfil.rol.label"));
				comboBox.setNullSelectionAllowed(false);
				return comboBox;
			}
		});
	}
	
	@Override
	protected void afterInitForm() {
		
		try { //si es pantalla de edición/visualización completo la grilla con los roles que ya tiene asociado el perfil
			if (StringUtils.isNotBlank(BeanUtils.getProperty(form.getBeanItem().getBean(), entity.getIdentifierAttributeName()))){
				PerfilBean perfilBean = (PerfilBean) form.getBeanItem().getBean();
				Set<Role> rolesBean = perfilBean.getRoles();
				if(!rolesBean.isEmpty()){
						for(Role rol : rolesBean){
						table.addItem(rol);
					}
				}
			} else {
				super.afterInitForm();
			}
		} catch (IllegalAccessException e) {
			logger.error("Error al obtener el valor de propiedad '" + entity.getIdentifierAttributeName() + "'", e);
		} catch (InvocationTargetException e) {
			logger.error("Error al obtener el valor de propiedad '" + entity.getIdentifierAttributeName() + "'", e);
		} catch (NoSuchMethodException e) {
			logger.error("Error al obtener el valor de propiedad '" + entity.getIdentifierAttributeName() + "'", e);
		}
	}
		
	private void agregarRolGrilla(){
		if(form.getField("roles").getValue() != null && !form.getField("roles").getValue().getClass().getName().equals("java.util.HashSet")){
			if(!table.getItemIds().contains(form.getField("roles").getValue())){
				table.addItem(form.getField("roles").getValue());
				form.getField("roles").setValue(null); // limpio el valor del combo
			} else { //mostrar mensaje informando que ya tiene asociado el rol elegido
				Collection<Object> param = new ArrayList<Object>();
				param.add(((RolBean)form.getField("roles").getValue()).getName());
				VaadinUtil.showMessage(this.getWindow(), VaadinUtil.getMessage("error.perfil.rol.repetido", param.toArray()));
				form.getField("roles").setValue(null);
			}
		} else {
			VaadinUtil.showMessage(this.getWindow(), VaadinUtil.getMessage("error.perfil.rol.vacio"));
		}
	}	
		
	@Override
	public void accept(com.vaadin.ui.Component.Event event) {
		// actualiza la lista de roles asociadas al perfil 
		Set<Role> rolesPerfil = new HashSet<Role>();
		if (!table.getItemIds().isEmpty()){
			for(Object rol : table.getItemIds()){
				rolesPerfil.add((Role)rol);
				((PerfilBean)form.getBeanItem().getBean()).setRoles(rolesPerfil);
			}
		} else {
			((PerfilBean)form.getBeanItem().getBean()).setRoles(rolesPerfil);
		}
		super.accept(event);
		
		if(searchPanel != null ) {
			searchPanel.search((ClickEvent) event);
			setSearchPanel(null);
		}
	}
	
	@Override
	protected boolean validate(PanelBeanForm<CrudViewBean> panelBeanForm) {
		boolean validForm = true;
		if(!super.validate(panelBeanForm)){
			validForm = false;
		} else if (table.getItemIds().isEmpty()){
			panelBeanForm.setComponentError(new UserError(VaadinUtil.getMessage("error.perfil.roles.empty")));
			validForm = false;
		}
		return validForm;
	}
	
	@Override
	protected String getWindowWidth() {
		return "720px";
	}
	
	class TableAction extends Action {  

		private static final long serialVersionUID = -6255490618287019760L;

		private ClickListener listener;

		private Button button;

		public TableAction(String caption) {
			super(caption);
		}

		public TableAction(String caption, com.vaadin.terminal.Resource icon) {
			super(caption, icon);
		}

		public TableAction(String caption, ClickListener listener) {
			this(caption);
			this.listener = listener;
		}

		public TableAction(String caption, com.vaadin.terminal.Resource icon, ClickListener listener) {
			this(caption, icon);
			this.listener = listener;
		}

		public Button createButton() {
			button = new Button();
			button.setIcon(getIcon());
			button.setDescription(getCaption());
			button.setStyleName(BaseTheme.BUTTON_LINK);
			button.addListener(getListener());
			button.setEnabled(false);

			return button;
		}
		
		public ClickListener getListener() {
			return listener;
		}
		
		public Button getButton() {
			return button;
		}

		public boolean isVisibleAction(Object target, Object sender) {
			return target != null;
		}
		
	}
	
	private void addAction(TableAction action) { 
		tableActions.add(action);
		tableLayout.addActionComponent(action.createButton());
	}

	protected void addTableActions() {
		TableAction action = createEliminarAction();
		addAction(action);
		action.getButton();
	}
	
	protected TableAction createEliminarAction() {
		TableAction action;
		ClickListener listener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				RolBean row = (RolBean) table.getValue();
				if (row != null) {
					removerRolGrilla(row);
				}

			}

		};

		action = new TableAction(VaadinUtil.getMessage("perfil.roles.eliminar.label"), Constants.ICON_DELETE, listener) {

			@Override
			public boolean isVisibleAction(Object target, Object sender) {
				return !table.getItemIds().isEmpty();
			}
		};
		
		return action;
	}
	
	private void removerRolGrilla(RolBean row){
		table.removeItem(row);
	}
		
	@Override
	public Action[] getActions(Object target, Object sender) {
		List<TableAction> actions = new ArrayList<TableAction>();
		for (TableAction action : tableActions) {
			if (action.isVisibleAction(target, sender)) {
				actions.add(action);
			}
		}

		return actions.toArray(new Action[] {});
	}

	@Override
	public void handleAction(Action action, Object sender, Object target) {
		TableAction tableAction = (TableAction) action;
		tableAction.getListener().buttonClick(null);		
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		RolBean bean = (RolBean) event.getProperty().getValue();

		if (bean != null) {
			for (TableAction action : tableActions) {
				if (action.getButton() != null) {
					action.getButton().setEnabled(action.isVisibleAction(bean, this));
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

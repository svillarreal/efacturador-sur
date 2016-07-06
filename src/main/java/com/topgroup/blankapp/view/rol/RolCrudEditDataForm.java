package com.topgroup.blankapp.view.rol;

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

import com.topgroup.blankapp.model.AccessRowBean;
import com.topgroup.blankapp.model.RolBean;
import com.topgroup.blankapp.service.IAccessService;
import com.topgroup.blankapp.view.base.Constants;
import com.topgroup.commons.crud.business.service.ICRUDService;
import com.topgroup.commons.crud.command.CrudEditDataForm;
import com.topgroup.commons.crud.frontend.form.CrudPanelBeanForm;
import com.topgroup.commons.crud.model.CrudViewBean;
import com.topgroup.commons.crud.model.IEntity;
import com.topgroup.commons.security.model.Access;
import com.topgroup.commons.security.service.ISystemService;
import com.topgroup.commons.utils.lang.ConverterService;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.FieldGenerator;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;
import com.topgroup.commons.vaadin.view.layout.TableBottonLayout;
import com.topgroup.commons.vaadin.view.layout.TableLayout;
import com.topgroup.commons.vaadin.view.panel.BaseSearchPanel.TableAction;
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
@Component("rol/rolBeanEdit")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class RolCrudEditDataForm extends CrudEditDataForm implements LayoutClickListener, ValueChangeListener, Handler {
	
	@Autowired
	IAccessService accessService;
	
	@Autowired
	ISystemService systemService;
	
	@Autowired
	ConverterService converterService;
		
	protected TableLayout tableLayout;
	
	protected Table table = new Table();
	
	private Button agregarButton;
	
	private List<TableAction> tableActions = new ArrayList<TableAction>(); 
	
	private FormLayout layoutDatosAccesos;
		
	@Resource(name = "entityRolBean")
	void setEntityRolBean(IEntity entity) {
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
						
			private Panel layoutRol;

			private Panel layoutAcceso;
			
			private FormLayout leftlayoutDatosRoles;
			
			private FormLayout rightlayoutDatosRoles;
			
			@Override
			protected void prepareLayout() {
				
				VerticalLayout verticalLayout = (VerticalLayout) getContent();
				verticalLayout.setMargin(false,true,false,true);
				verticalLayout.setSpacing(true);
				
				GridLayout layout = new GridLayout(2, 2);
				layout.setWidth(100, UNITS_PERCENTAGE);
				layout.setSpacing(true);
				layout.setMargin(false, true, true, true);
											
				leftlayoutDatosRoles = new FormLayout();
				leftlayoutDatosRoles.setMargin(false);
				leftlayoutDatosRoles.setWidth(100, UNITS_PERCENTAGE);
				layout.addComponent(leftlayoutDatosRoles, 0, 1);
			
				rightlayoutDatosRoles = new FormLayout();
				rightlayoutDatosRoles.setMargin(false);
				layout.addComponent(rightlayoutDatosRoles, 1, 1);
				
				layoutRol = new Panel(VaadinUtil.getMessage("datos.rolBean"),layout);
				addComponent(layoutRol);
				
				layout = new GridLayout(2, 4);
				layout.setWidth(100, UNITS_PERCENTAGE);
				layout.setSpacing(true);
				layout.setMargin(false, true, true, true);
				
				armarMasterDetail(layout);
				
				layoutAcceso = new Panel(VaadinUtil.getMessage("datos.rolBean.accesos"), layout);
				addComponent(layoutAcceso);
			
			}
			
			@Override
			protected void attachField(Object propertyId, Field field) {
				String property = (String) propertyId;
				if ("name".equals(property)) {
					leftlayoutDatosRoles.addComponent(field);
				} else if("abbreviation".equals(property)){
					rightlayoutDatosRoles.addComponent(field);
				} else if (mode != MODE_VIEW_FORM){
					layoutDatosAccesos.addComponent(field);
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
			layoutDatosAccesos = new FormLayout();
			layoutDatosAccesos.setMargin(false);
			layoutDatosAccesos.setWidth(100, UNITS_PERCENTAGE);
			layout.addComponent(layoutDatosAccesos, 0, 1, 1, 1);
								
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
		BeanItemContainer<AccessRowBean> items = new BeanItemContainer<AccessRowBean>(AccessRowBean.class);
		table.setContainerDataSource(items);
		table.setVisibleColumns(getVisibleColumns());
		table.setColumnHeaders(getColumnHeaders());
		table.addListener(this);
		table.addActionHandler(this);
		table.setSelectable(true);
	}
	
	protected String[] getVisibleColumns() {
		return new String[] {"descripcion"};
	}
	
	protected String[] getColumnHeaders() {
		return new String[] {"Nombre Permiso"};
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
		this.addFieldGeneratorAccesos(beanForm);
	}
			
	protected void addFieldGeneratorAccesos(PanelBeanForm<CrudViewBean> beanForm) {
		beanForm.addFieldGenerator("accesses", new FieldGenerator() {

			@Override
			public Field createField(Item item, Object propertyId, com.vaadin.ui.Component uiContext) {
				ComboBox comboBox = new ComboBox();
				final BeanItemContainer<AccessRowBean> items = new BeanItemContainer<AccessRowBean>(AccessRowBean.class);
				Set<Access> accesos = accessService.findAccesses(systemService.getCurrentSystem().getId(), null, null);
				for(Access acceso: accesos){
					items.addBean(converterService.convertTo(acceso, AccessRowBean.class));
				}
				comboBox.setContainerDataSource(items);
				comboBox.setItemCaptionPropertyId("descripcion");
				comboBox.setCaption(VaadinUtil.getMessage("rol.acceso.label"));
				comboBox.setNullSelectionAllowed(false);
				return comboBox;
			}
		});
	}
	
	@Override
	protected void afterInitForm() {
		//si es pantalla de edición/visualización completo la grilla con los permisos que ya tiene el rol asignados
		try { 
			if (StringUtils.isNotBlank(BeanUtils.getProperty(form.getBeanItem().getBean(), entity.getIdentifierAttributeName()))){
				RolBean rolBean = (RolBean) form.getBeanItem().getBean();
				Set<Access> accesos = rolBean.getAccesses();
				if(!accesos.isEmpty()){
						for(Access acceso : accesos){
							if(acceso.getSystemId().equals(rolBean.getSystem().getId())){
								table.addItem(converterService.convertTo(acceso, AccessRowBean.class));
							}
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
		if(form.getField("accesses").getValue() != null && !form.getField("accesses").getValue().getClass().getName().equals("java.util.HashSet")) {
			if(!table.getItemIds().contains(form.getField("accesses").getValue())){
				table.addItem(form.getField("accesses").getValue());
				form.getField("accesses").setValue(null); // limpio el valor del combo
			} else { //mostrar mensaje informando que ya tiene ese rol asociado
				Collection<Object> param = new ArrayList<Object>();
				param.add(((AccessRowBean)form.getField("accesses").getValue()).getDescripcion());
				VaadinUtil.showMessage(this.getWindow(), VaadinUtil.getMessage("error.rol.acceso.repetido", param.toArray()));
				form.getField("accesses").setValue(null);
			}
		}
		else { //mostrar mensaje informando que no se eligió un rol 
			VaadinUtil.showMessage(this.getWindow(), VaadinUtil.getMessage("error.rol.acceso.vacio"));
		}
	}	
		
	@Override
	public void accept(com.vaadin.ui.Component.Event event) {
		form.getField("accesses").setValue(null);		
		// actualiza la lista de roles asociadas al perfil 
		Set<Access> accesos = new HashSet<Access>();
		if (!table.getItemIds().isEmpty()){
			for(Object acceso : table.getItemIds()){
				accesos.add((Access)acceso);
				((RolBean)form.getBeanItem().getBean()).setAccesses(accesos);
			}
		} else {
			((RolBean)form.getBeanItem().getBean()).setAccesses(accesos);
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
			panelBeanForm.setComponentError(new UserError(VaadinUtil.getMessage("error.rol.accesses.empty")));
			validForm = false;
		}
		return validForm;
	}
				
	@Override
	protected String getWindowWidth() {
		return "700px";
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

				AccessRowBean row = (AccessRowBean) table.getValue();
				if (row != null) {
					removerAccesoGrilla(row);
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
	
	private void removerAccesoGrilla(AccessRowBean row){
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
		AccessRowBean bean = (AccessRowBean) event.getProperty().getValue();
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

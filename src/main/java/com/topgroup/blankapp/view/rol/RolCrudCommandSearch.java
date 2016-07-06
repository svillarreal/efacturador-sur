package com.topgroup.blankapp.view.rol;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.topgroup.commons.crud.command.CrudCommnadSearch;

@SuppressWarnings("serial")
@Component("rolBean")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class RolCrudCommandSearch extends CrudCommnadSearch {

	
}

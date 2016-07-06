package com.topgroup.blankapp.validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.validation.Errors;

import com.topgroup.blankapp.model.PerfilBean;
import com.topgroup.commons.crud.business.validators.CrudDefaultValidator;
import com.topgroup.commons.crud.exception.CrudException;
import com.topgroup.commons.crud.model.CrudError;

public class PerfilBeanValidator extends CrudDefaultValidator<PerfilBean> {

	private static final Integer MAXLENGTH = 50;
	
	@Override
	public void validate(PerfilBean entity, Errors errors) throws CrudException {
		ArrayList<CrudError> crudErrors = new ArrayList<CrudError>();
		if(entity.getName().length() > MAXLENGTH){
			Collection<Object> param = new ArrayList<Object>();
			param.add("'Nombre Perfil'");
			param.add(MAXLENGTH);
			crudErrors.add(new CrudError(null, null, "error.maxLength", null, param.toArray()));
			throw new CrudException(crudErrors);
		}
	}

}

	
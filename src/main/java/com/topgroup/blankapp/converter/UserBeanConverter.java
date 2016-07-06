package com.topgroup.blankapp.converter;

import org.springframework.core.convert.converter.Converter;

import com.topgroup.blankapp.view.security.bean.UserBean;
import com.topgroup.commons.security.model.Participation;

public class UserBeanConverter implements Converter<Participation, UserBean> {

	@Override
	public UserBean convert(Participation source) {
		
		UserBean userBean = new UserBean();
		
		userBean.setIdUser(source.getId());
		userBean.setUsername(source.getUsername());
		userBean.setName(source.getName());
		userBean.setLastName(source.getLastName());
		userBean.setProfile(source.getProfile());
		userBean.setProfileName(source.getProfile().getName());
		userBean.setSector(source.getSector());
		userBean.setEmail(source.getEmail());
		userBean.setPhone(source.getPhone());
		userBean.setFax(source.getFax());
		userBean.setEnabled(source.isEnabled());
		userBean.setUser(source);
						
		return userBean;
	}

}

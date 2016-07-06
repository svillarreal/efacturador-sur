package com.topgroup.blankapp.model;

import java.io.Serializable;

public class AccessId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer actionId;

	private Integer resourceId;

	public AccessId() {

	}

	public AccessId(Integer actionId, Integer resourceId) {
		this.actionId = actionId;
		this.resourceId = resourceId;
	}

	public AccessId(String id) {
		if (id != null) {
			String[] ids = id.split("_");
			if (ids.length == 2) {
				this.actionId = Integer.valueOf(ids[0]);
				this.resourceId = Integer.valueOf(ids[1]);
			}
		}
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionId == null) ? 0 : actionId.hashCode());
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AccessId))
			return false;
		AccessId other = (AccessId) obj;
		if (actionId == null) {
			if (other.actionId != null)
				return false;
		} else if (!actionId.equals(other.actionId))
			return false;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return (actionId != null && resourceId != null) ? this.getActionId() + "_" + this.getResourceId() : null;
	}
}

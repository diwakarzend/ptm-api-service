package com.ptm.api.user.controller.vo;

import java.util.List;

public class RolePermissionDTO {
	
	private String role;
	
	private List<Permission> permission;
	
	
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public List<Permission> getPermission() {
		return permission;
	}


	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}


	public static class Permission{
		private String privilege;

		public String getPrivilege() {
			return privilege;
		}

		public void setPrivilege(String privilege) {
			this.privilege = privilege;
		}

		
		
	}

}

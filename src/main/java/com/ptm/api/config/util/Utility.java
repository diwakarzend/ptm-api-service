package com.ptm.api.config.util;

import org.apache.commons.lang3.StringUtils;

import com.ptm.api.user.controller.vo.ManagedUserVM;

public class Utility {
	
	public static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }

}

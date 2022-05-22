package com.ptm.api.user.controller.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * View Model object for storing a user's credentials.
 */
@Getter
@Setter
public class LoginVM {

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    private String password;
    
    private String tenantId;

    private Boolean rememberMe;

   

    @Override
    public String toString() {
        return "LoginVM{" +
            "username='" + username + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}

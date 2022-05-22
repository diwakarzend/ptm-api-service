package com.ptm.api.user.controller.dto;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class PasswordChangeDTO {
	@NotBlank
	@NotNull
    private String currentPassword;
	@NotBlank
	@NotNull
    private String newPassword;

    public PasswordChangeDTO() {
    }

    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {

        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

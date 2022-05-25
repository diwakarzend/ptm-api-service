package com.ptm.api.ptp.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ptm.api.config.constant.Eflags;
import com.ptm.api.ptp.entity.Ptp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Aman Garg
 *
 */
@Getter
@Setter
public class PtpDTO {

	@Size(min = 1, max = 100, message = "please pass valid veuserUUIDndorId")
	@NotBlank(message = "userUUID id can not be blank")
	protected String userUUID;

	@Size(min = 1, max = 100, message = "please pass valid vendorId")
	@NotBlank(message = "vendor id can not be blank")
	protected String vendorId;

	@Size(max = 100, message = "please pass valid qrDetails")
	@NotNull(message = "qrDetails can not be null")
	@NotBlank(message = "qrDetails can not be blank")
	protected String qrDetails;

	protected Double totalLimit;

	protected Double dailyLimit;

	protected LocalDateTime runningDate;

	protected LocalDateTime createdDate;

	protected String createdBy;

	protected LocalDateTime updatedDate;

	protected String updatedBy;

	protected Eflags isDeleted = Eflags.N;

	protected Eflags isStatus = Eflags.N;

	protected Eflags isPublished = Eflags.N;

	public PtpDTO() {
		// Empty constructor needed for Jackson.
	}

	public PtpDTO(Ptp ptp) {
		this.userUUID = ptp.getUserUUID();
		this.vendorId = ptp.getVendorId();
		this.qrDetails = ptp.getQrDetails();
		this.totalLimit = ptp.getTotalLimit();
		this.dailyLimit = ptp.getDailyLimit();
		this.runningDate = ptp.getRunningDate();
		this.createdDate = ptp.getCreatedDate();
		this.createdBy = ptp.getCreatedBy();
		this.updatedDate = ptp.getUpdatedDate();
		this.updatedBy = ptp.getUpdatedBy();
		this.isDeleted = ptp.getIsDeleted();
		this.isStatus = ptp.getIsStatus();
		this.isPublished = ptp.getIsPublished();

	}

}

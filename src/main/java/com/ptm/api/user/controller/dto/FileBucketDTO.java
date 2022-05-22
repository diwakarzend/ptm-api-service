package com.ptm.api.user.controller.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileBucketDTO {
	
	List<MultipartFile> files = new ArrayList<MultipartFile>();

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	

}

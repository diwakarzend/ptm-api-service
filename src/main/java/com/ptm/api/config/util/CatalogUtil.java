package com.ptm.api.config.util;

import java.util.UUID;

import com.ptm.api.catalog.collection.PtmUserComissionEntity;
import com.ptm.api.catalog.dto.UserHierarchyComissionDto;

public class CatalogUtil {

	public static String generateUniqueId() {
		return UUID.randomUUID().toString();
	}
	
	public static PtmUserComissionEntity mapUserComissonEntity(UserHierarchyComissionDto userHierarchyComissionDto) {
		PtmUserComissionEntity ptmUserComissionEntity = new PtmUserComissionEntity();
		ptmUserComissionEntity.setUserId(userHierarchyComissionDto.getUserId());
		ptmUserComissionEntity.setUserServiceMapping(userHierarchyComissionDto.getUserServiceMapping());
		
		return ptmUserComissionEntity;
	}
	
	
	public static UserHierarchyComissionDto mapUserComissonDto(PtmUserComissionEntity ptmUserComissionEntity) {
		UserHierarchyComissionDto userHierarchyComissionDto = new UserHierarchyComissionDto();
		userHierarchyComissionDto.setUserServiceMapping(ptmUserComissionEntity.getUserServiceMapping());
		// userHierarchyComissionDto.setOperatorId(ptmUserComissionEntity.getOperatorId());
		userHierarchyComissionDto.setUserId(ptmUserComissionEntity.getUserId());
		return userHierarchyComissionDto;
	}


}

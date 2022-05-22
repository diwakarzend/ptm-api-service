package com.ptm.api.catalog.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.ptm.api.catalog.collection.AepsComissionCofigCollection;
import com.ptm.api.catalog.collection.MoneyTransferConfigCollection;
import com.ptm.api.catalog.collection.PayoutConfigCollection;
import com.ptm.api.catalog.collection.PtmUserComissionEntity;
import com.ptm.api.catalog.dto.AepsConfigDto;
import com.ptm.api.catalog.dto.CommissionRangeDto;
import com.ptm.api.catalog.dto.DMTCommissionRangeDto;
import com.ptm.api.catalog.dto.DMTConfigDto;
import com.ptm.api.catalog.dto.InserCommissionDto;
import com.ptm.api.catalog.dto.PayoutCommissionRangeDto;
import com.ptm.api.catalog.dto.PayoutConfigDto;
import com.ptm.api.catalog.dto.PayoutRangeDetailsDto;
import com.ptm.api.catalog.dto.PayoutRouteDto;
import com.ptm.api.catalog.dto.PayoutUpdateDto;
import com.ptm.api.catalog.dto.UpdateCommissionDto;
import com.ptm.api.catalog.dto.UserHierarchyComissionDto;
import com.ptm.api.payout.dto.PtmPage;

public interface UserComissionService {

	PtmUserComissionEntity createUserHierarchyComission(UserHierarchyComissionDto userHierarchyComissionDto);

	UserHierarchyComissionDto findAllByUserId(String userId);

	Map<String, Double> getUserHierarchyCommission(String uuid, String userId, String serviceId, String operatorCode);

	void saveAepsCommission(AepsConfigDto aepsConfigDto);

	CommissionRangeDto getUserAepsCommission(String userId, Double amount);

	List<PayoutCommissionRangeDto> getUserPayoutCommission(String userId, Double amount, String route);

	void savepayoutCommission(PayoutConfigDto payoutConfigDto);

	void savemoneyTransferCommission(DMTConfigDto moneyTransferDto);

	DMTCommissionRangeDto getUserMoneyTransferCommission(String userId, Double amount, String route);

	void defaultRechargeCommission(PtmUserComissionEntity ptmUserComissionEntity);

	void aepsDefaultCommission(AepsComissionCofigCollection aepsComissionCofigCollection);

	void dsmtDefaultCommission(MoneyTransferConfigCollection moneyTransferConfigCollection);

	void payOutDefaultCommission(PayoutConfigCollection payoutConfigCollection);

	PtmUserComissionEntity updateUserHierarchyComission(UpdateCommissionDto updateCommissionDto);

	PtmUserComissionEntity insertUserHierarchyComission(InserCommissionDto inserCommissionDto);

	void updatePayOut(PayoutUpdateDto payoutUpdateDto);

	PtmPage<Map<String, Map<String, List<PayoutRangeDetailsDto>>>> getPayOutRange(Pageable pageable, String username);

	void updateRoute(PayoutRouteDto payoutRouteDto);

}

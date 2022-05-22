package com.ptm.api.catalog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptm.api.catalog.collection.AepsComissionCofigCollection;
import com.ptm.api.catalog.collection.MoneyTransferConfigCollection;
import com.ptm.api.catalog.collection.PayoutConfigCollection;
import com.ptm.api.catalog.collection.PtmUserComissionEntity;
import com.ptm.api.catalog.collection.PtmUserServiceMapping;
import com.ptm.api.catalog.collectionRepo.AepsComissionCofigRepository;
import com.ptm.api.catalog.collectionRepo.MoneyTransferConfigRespository;
import com.ptm.api.catalog.collectionRepo.PayoutConfigRepository;
import com.ptm.api.catalog.collectionRepo.PtmUserComissionRepository;
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
import com.ptm.api.catalog.dto.UserCommissonDto;
import com.ptm.api.catalog.dto.UserHierarchyComissionDto;
import com.ptm.api.catalog.service.UserComissionService;
import com.ptm.api.config.util.CatalogUtil;
import com.ptm.api.exception.CatalogServiceException;
import com.ptm.api.exception.UserServiceException;
import com.ptm.api.exception.code.CatalogExceptionCodeAndMassage;
import com.ptm.api.exception.code.UserExceptionCodeAndMassage;
import com.ptm.api.payout.dto.PtmPage;
import com.ptm.api.user.entity.User;
import com.ptm.api.user.repository.UserRepository;

@Service
public class UserComissionServiceImpl implements UserComissionService {

	private final Logger log = LoggerFactory.getLogger(UserComissionServiceImpl.class);

	@Autowired
	PtmUserComissionRepository ptmUserComissionRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AepsComissionCofigRepository aepsComissionCofigRepository;

	@Autowired
	private PayoutConfigRepository payoutConfigRepository;

	@Autowired
	private MoneyTransferConfigRespository moneyTransferConfigRespository;

	@Override
	public PtmUserComissionEntity createUserHierarchyComission(UserHierarchyComissionDto userHierarchyComissionDto) {

		if (Objects.isNull(userHierarchyComissionDto.getUserId())) {
			throw new CatalogServiceException(CatalogExceptionCodeAndMassage.NO_USER_FOUND);
		}

		// validateAndSetCommsion(userHierarchyComissionDto);

		PtmUserComissionEntity userHierarchyDetail = ptmUserComissionRepository
				.findAllByUserId(userHierarchyComissionDto.getUserId());
		log.info("User Hierarchy Comission: {}", userHierarchyDetail);

		if (Objects.isNull(userHierarchyDetail)) {
			PtmUserComissionEntity result = CatalogUtil.mapUserComissonEntity(userHierarchyComissionDto);
			result = ptmUserComissionRepository.save(result);

			User user = userRepository.findOneByUsername(userHierarchyComissionDto.getUserId()).get();

			user.setUserComissionUuid(result.getId());
			userRepository.save(user);

			return ptmUserComissionRepository.save(result);
		} else {
			throw new CatalogServiceException(CatalogExceptionCodeAndMassage.USER_ALREADY_EXISTS);
		}
	}

	@Override
	public UserHierarchyComissionDto findAllByUserId(String userId) {

		PtmUserComissionEntity userHierarchyDetail = ptmUserComissionRepository.findAllByUserId(userId);

		if (Objects.isNull(userHierarchyDetail)) {
			throw new CatalogServiceException(CatalogExceptionCodeAndMassage.NO_USER_FOUND);
		}
		UserHierarchyComissionDto result = CatalogUtil.mapUserComissonDto(userHierarchyDetail);
		log.info("User Hierarchy Comission: {}", result);
		return result;
	}

	@Override
	public Map<String, Double> getUserHierarchyCommission(String uuid, String userId, String serviceId,
			String operatorCode) {
		PtmUserComissionEntity userComissionObject = ptmUserComissionRepository.findAllByUserId(userId);

		System.out.println(userComissionObject.getUserId());

		Map<String, Double> map = userComissionObject.getUserServiceMapping().stream()
				.filter(predicate -> predicate.getServiceType().equals(serviceId))
				.map(mapper -> mapper.getUserHierarchyComission().entrySet().stream()
						.filter(predicate -> predicate.getKey().equals(operatorCode)).map(mapper1 -> mapper1.getValue())
						.flatMap(List::stream).collect(Collectors.toList()))
				.flatMap(List::stream)
				.collect(Collectors.toMap(keyMapper -> keyMapper.getUuid(), valueMapper -> valueMapper.getCommission()));

		log.info(" commission map : {}", map);
		return map;
	}

	@Override
	public void saveAepsCommission(AepsConfigDto aepsConfigDto) {
		AepsComissionCofigCollection aepsComissionCofigCollection = new AepsComissionCofigCollection();
		aepsComissionCofigCollection.setUserName(aepsConfigDto.getUserName());
		aepsComissionCofigCollection.setCommissionRange(aepsConfigDto.getCommissionRange());
		aepsComissionCofigRepository.save(aepsComissionCofigCollection);
	}

	@Override
	public void savemoneyTransferCommission(DMTConfigDto dmtConfigDto) {
		MoneyTransferConfigCollection moneyTransferConfigCollection = new MoneyTransferConfigCollection();
		moneyTransferConfigCollection.setUserName(dmtConfigDto.getUserName());
		moneyTransferConfigCollection.setCommissionRange(dmtConfigDto.getCommissionRange());
		moneyTransferConfigRespository.save(moneyTransferConfigCollection);
	}

	@Override
	public CommissionRangeDto getUserAepsCommission(String userId, Double amount) {
		AepsComissionCofigCollection aepsComissionCofigCollection = aepsComissionCofigRepository.findByUserName(userId);

		System.out.println("AePS commision data=====" + aepsComissionCofigCollection.toString());
		System.out.println("AePS amount withdrawl=====" + amount);

		return aepsComissionCofigCollection.getCommissionRange().stream()
				.filter(predicate -> amount >= predicate.getMinAepsAmount() && amount <= predicate.getMaxAepsAmount())
				// .map(mapper->mapper.getUserHierarchyComission())
				// .flatMap(List::stream)
				.findFirst().get();

	}

	@Override
	public List<PayoutCommissionRangeDto> getUserPayoutCommission(String userId, Double amount, String route) {
		PayoutConfigCollection aepsComissionCofigCollection = payoutConfigRepository.findByUserName(userId);

		System.out.println("pay out commision data=====" + aepsComissionCofigCollection.toString());
		System.out.println("pay out route=====" + route);
		return aepsComissionCofigCollection.getCommissionRange().stream()
				.filter(predicate -> amount >= predicate.getMinAmount() && amount <= predicate.getMaxAmount())
				.filter(predicate -> predicate.getRoute().equals(route))
				.collect(Collectors.toList());
	}

	@Override
	public DMTCommissionRangeDto getUserMoneyTransferCommission(String userId, Double amount, String route) {
		MoneyTransferConfigCollection moneyTransferConfig = moneyTransferConfigRespository.findByUserName(userId);

		System.out.println("money transfer route=====" + route);

		System.out.println("money transfer comission=====" + moneyTransferConfig.toString());

		return moneyTransferConfig.getCommissionRange().stream().filter(predicate -> predicate.getMinAmount() >= amount
				&& amount <= predicate.getMaxAmount() && predicate.getRoute().equals(route)).findFirst().get();
	}

	@Override
	public void savepayoutCommission(PayoutConfigDto payoutConfigDto) {
		PayoutConfigCollection payoutConfigCollection = new PayoutConfigCollection();
		payoutConfigCollection.setUserName(payoutConfigDto.getUserName());
		payoutConfigCollection.setCommissionRange(payoutConfigDto.getCommissionRange());
		payoutConfigRepository.save(payoutConfigCollection);
	}

	@Override
	public void defaultRechargeCommission(PtmUserComissionEntity ptmUserComissionEntity) {
		ptmUserComissionRepository.save(ptmUserComissionEntity);
	}

	@Override
	public void aepsDefaultCommission(AepsComissionCofigCollection aepsComissionCofigCollection) {
		aepsComissionCofigRepository.save(aepsComissionCofigCollection);
	}

	@Override
	public void dsmtDefaultCommission(MoneyTransferConfigCollection moneyTransferConfigCollection) {
		moneyTransferConfigRespository.save(moneyTransferConfigCollection);
	}

	@Override
	public void payOutDefaultCommission(PayoutConfigCollection payoutConfigCollection) {
		payoutConfigRepository.save(payoutConfigCollection);
	}

	@Override
	public PtmUserComissionEntity updateUserHierarchyComission(UpdateCommissionDto updateCommissionDto) {

		validateComssion(updateCommissionDto.getUserComissionMap());

		PtmUserComissionEntity userHierarchyDetail = ptmUserComissionRepository
				.findAllByUserId(updateCommissionDto.getUserName());

		for (PtmUserServiceMapping ptmUserServiceMapping : userHierarchyDetail.getUserServiceMapping()) {
			if (ptmUserServiceMapping.getServiceType().equals(updateCommissionDto.getServiceType())) {

				Map<String, List<UserCommissonDto>> commissionMap = ptmUserServiceMapping.getUserHierarchyComission();
				List<UserCommissonDto> userComissonList = commissionMap.get(updateCommissionDto.getOpCode());

				userComissonList = userComissonList.stream()
						.map(mapper -> setCommission(mapper, updateCommissionDto.getUserComissionMap()))
						.collect(Collectors.toList());

				commissionMap.put(updateCommissionDto.getOpCode(), userComissonList);
				ptmUserServiceMapping.setUserHierarchyComission(commissionMap);
			}
		}
		return ptmUserComissionRepository.save(userHierarchyDetail);
	}

	private void validateComssion(Map<String, Double> userComissionMap) {

		double totalPercentage = userComissionMap.entrySet().stream().mapToDouble(mapper -> mapper.getValue()).sum();

		if (totalPercentage != 100) {
			throw new CatalogServiceException(CatalogExceptionCodeAndMassage.GENERAL_ERROR);

		}

	}

	private UserCommissonDto setCommission(UserCommissonDto userComissonDto, Map<String, Double> userComissionMap) {
		userComissonDto.setCommission(userComissionMap.get(userComissonDto.getUuid()));
		return userComissonDto;
	}

	@Override
	public PtmUserComissionEntity insertUserHierarchyComission(InserCommissionDto inserCommissionDto) {
		PtmUserComissionEntity userHierarchyDetail = ptmUserComissionRepository
				.findAllByUserId(inserCommissionDto.getUserName());

		for (PtmUserServiceMapping ptmUserServiceMapping : userHierarchyDetail.getUserServiceMapping()) {
			if (ptmUserServiceMapping.getServiceType().equals(inserCommissionDto.getServiceType())) {

				Map<String, List<UserCommissonDto>> commissionMap = ptmUserServiceMapping.getUserHierarchyComission();
				List<UserCommissonDto> userComissonList = commissionMap.get(inserCommissionDto.getOpCode());

				userComissonList = userComissonList.stream()
						.map(mapper -> setCommission(mapper, inserCommissionDto.getUserComissionMap()))
						.collect(Collectors.toList());

				for (UserCommissonDto userComissonDto : inserCommissionDto.getCommComissonDtos()) {
					Optional<User> existingUser = userRepository.findOneByUsername(userComissonDto.getUuid());
					if (!existingUser.isPresent()) {
						throw new UserServiceException(UserExceptionCodeAndMassage.NO_USER_FOUND);
					}
				}
				userComissonList.addAll(inserCommissionDto.getCommComissonDtos());
				ptmUserServiceMapping.getUserHierarchyComission().put(inserCommissionDto.getOpCode(), userComissonList);

			}
		}

		double commissionSum = userHierarchyDetail.getUserServiceMapping().stream()
				.filter(predicate -> predicate.getServiceType().equals(inserCommissionDto.getServiceType()))
				.map(mapper -> mapper.getUserHierarchyComission().entrySet().stream()
						.filter(predicate -> predicate.getKey().equals(inserCommissionDto.getOpCode()))
						.map(mapper1 -> mapper1.getValue()).flatMap(List::stream).collect(Collectors.toList()))
				.flatMap(List::stream).mapToDouble(mapper -> mapper.getCommission()).sum();

		if (commissionSum != 100) {
			throw new CatalogServiceException(CatalogExceptionCodeAndMassage.GENERAL_ERROR);

		}

		return ptmUserComissionRepository.save(userHierarchyDetail);

	}
	
	@Override
	public void updatePayOut(PayoutUpdateDto payoutUpdateDto) {
		PayoutConfigCollection aepsComissionCofigCollection = payoutConfigRepository
				.findByUserName(payoutUpdateDto.getUserId());

		for (PayoutCommissionRangeDto obj : aepsComissionCofigCollection.getCommissionRange()) {
			if (Objects.equals(obj.getMinAmount(),payoutUpdateDto.getMinAmount())&&
					Objects.equals(payoutUpdateDto.getMaxAmount(),obj.getMaxAmount())
					&& obj.getRoute().equals(payoutUpdateDto.getRoute())) {
				obj.setMerchantApiCode(payoutUpdateDto.getMerchantCode());
				obj.setCommissionType(payoutUpdateDto.getCommissionType());
				for (UserCommissonDto userCommissonDto : obj.getUserHierarchyComission()) {
					userCommissonDto.setCommission(payoutUpdateDto.getCommission());
				}

			}
		}
		payoutConfigRepository.save(aepsComissionCofigCollection);
	}
	
	@Override
	public void updateRoute(PayoutRouteDto payoutRouteDto) {
		PayoutConfigCollection aepsComissionCofigCollection = payoutConfigRepository
				.findByUserName(payoutRouteDto.getUserId());

		for (PayoutCommissionRangeDto obj : aepsComissionCofigCollection.getCommissionRange()) {
			if (obj.getMinAmount()==payoutRouteDto.getMinAmount() && 
					payoutRouteDto.getMaxAmount()==obj.getMaxAmount()) {
				
				obj.setCommissionType(payoutRouteDto.getCommissionType());
				obj.setMerchantApiCode(payoutRouteDto.getRouteCode());
				for (UserCommissonDto userComissonDto : obj.getUserHierarchyComission()) {
					userComissonDto.setCommission(payoutRouteDto.getCommission());
				}

			}
		}
		payoutConfigRepository.save(aepsComissionCofigCollection);

	}

	@Override
	public PtmPage<Map<String, Map<String, List<PayoutRangeDetailsDto>>>> getPayOutRange(Pageable pageable, String username) {
		Page<PayoutConfigCollection> payoutcollection;
		if (username != null) {
			payoutcollection = payoutConfigRepository.findByUserName(pageable, username);
		} else {
			payoutcollection = payoutConfigRepository.findAll(pageable);
		}
		Map<String, Map<String, List<PayoutRangeDetailsDto>>> map = new HashMap<>();
		for (PayoutConfigCollection obj : payoutcollection) {
			map.put(obj.getUserName(), payoutCommissionList(obj.getCommissionRange()));
		}

		return new PtmPage<Map<String, Map<String, List<PayoutRangeDetailsDto>>>>(map, payoutcollection.getNumber(),
				payoutcollection.getNumberOfElements(), payoutcollection.getTotalElements(),
				payoutcollection.getTotalPages());
	}
	
	private Map<String, List<PayoutRangeDetailsDto>> payoutCommissionList(
			List<PayoutCommissionRangeDto> commissionRange) {

		return commissionRange.stream().collect(Collectors.groupingBy(PayoutCommissionRangeDto::getRoute,
				Collectors.mapping(mapper -> new PayoutRangeDetailsDto(mapper), Collectors.toList())));
	}

}

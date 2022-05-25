package com.ptm.api.ptp.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptm.api.ptp.dto.PtpDTO;
import com.ptm.api.ptp.entity.Ptp;
import com.ptm.api.ptp.repository.PtpRepository;
import com.ptm.api.ptp.service.PtpService;

@Service
public class PtpServiceImpl implements PtpService {

	private final Logger log = LoggerFactory.getLogger(PtpServiceImpl.class);

	@Autowired
	private PtpRepository ptpRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public PtpDTO createPTP(PtpDTO ptpDTO) {
		Ptp ptp = mapper.map(ptpDTO, Ptp.class);
		ptpRepository.save(ptp);
		log.info("Created Information for ptp: {}", ptpDTO);
		return ptpDTO;
	}

	/*
	 * @Transactional public PtpDTO updatePtp(PtpDTO ptpDTO) { return
	 * Optional.of(existingUser).filter(Optional::isPresent).map(Optional::get).map(
	 * user -> { user.setUsername(userDTO.getUserName());
	 * user.setFirstName(userDTO.getFirstName());
	 * user.setLastName(userDTO.getLastName()); user.setEmail(userDTO.getEmail());
	 * user.setLangKey(userDTO.getLangKey());
	 * 
	 * return user; }).map(PtpDTO::new).orElseThrow(() -> new
	 * UserServiceException(UserExceptionCodeAndMassage.GENERAL_ERROR)); }
	 */

	@Override
	public Page<PtpDTO> getAllManagedPtp(Pageable pageable) {
		return ptpRepository.findAll(pageable).map(PtpDTO::new);
	}

	@Override
	public Page<PtpDTO> getAllPtpByMerchantRole(Pageable pageable, String uuid) {
		return ptpRepository.findAllByUserUUID(pageable, uuid).map(PtpDTO::new);
	}

}

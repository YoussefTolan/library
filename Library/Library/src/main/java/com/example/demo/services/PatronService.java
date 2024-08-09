package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Patron;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mappinginterface.PatronMapper;
import com.example.demo.models.request.PatronRequestModel;
import com.example.demo.models.response.PatronResponseModel;
import com.example.demo.repositories.PatronRepository;
import com.example.demo.services.PatronService;
import com.example.demo.utils.BusinessLogicViolationException;


@Service
public class PatronService {

	@Autowired
	PatronRepository patronRepository;
	
	@Autowired
	PatronMapper patronMapper;
	
	//---------------------------------------------------------------
	

	public PatronResponseModel createPatron(PatronRequestModel patronRequestModel) {
		Patron patron = patronMapper.mapToPatronRequestModel(patronRequestModel);
		patronRepository.save(patron);
		return patronMapper.mapToPatronResponseModel(patron);
	}


	public PatronResponseModel updatePatronById(Long patronId, PatronRequestModel patronRequestModel) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name());
		Patron patronobj = patronMapper.mapToPatronRequestModel(patronRequestModel);
		patronRepository.save(patronobj);
		return patronMapper.mapToPatronResponseModel(patronobj);
	}


	public PatronResponseModel getPatronById(Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name());
		Patron patronObject = patron.get();
		return patronMapper.mapToPatronResponseModel(patronObject);
	}


	public List<PatronResponseModel> getAllPatrons() {
		List<Patron> patronList = patronRepository.findAll();
		if(patronList.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.LIST_IS_EMPTY.name());
		return patronMapper.mapToPatronResponseModelList(patronList);
	}

	
	public void deletePatronById(Long patronId) {
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name());
		patronRepository.deleteById(patronId);
	}
	
}

package com.example.demo.models.mappinginterface;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entities.Patron;
import com.example.demo.models.request.PatronRequestModel;
import com.example.demo.models.response.PatronResponseModel;


@Mapper(componentModel = "spring")
public interface PatronMapper {
	
	PatronResponseModel mapToPatronResponseModel(Patron patron);
	List<PatronResponseModel> mapToPatronResponseModelList(List<Patron> patron);

	Patron mapToPatronRequestModel(PatronRequestModel patronRequestModel);
    List<PatronRequestModel> mapToPatronRequestModelList(List<Patron> patrons);

}

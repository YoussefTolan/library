package com.example.demo.models.mappinginterface;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entities.BorrowingRecord;
import com.example.demo.models.response.BorrowingRecordResponseModel;


@Mapper(componentModel = "spring", uses= {BookMapper.class, PatronMapper.class})
public interface BorrowingRecordMapper {

	BorrowingRecordResponseModel mapToBorrowingRecordResponseModel(BorrowingRecord borrowingRecord);
	List<BorrowingRecordResponseModel> mapToBorrowingRecordResponseModelList(List<BorrowingRecord> borrowingRecord);

	// BorrowingRecordRequestModel mapToBorrowingRecordRequestModel();
	// List<BorrowingRecordRequestModel> mapToBorrowingRecordRequestModelList();

}


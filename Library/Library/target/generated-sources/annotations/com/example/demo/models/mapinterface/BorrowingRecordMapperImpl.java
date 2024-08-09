package com.example.demo.models.mapinterface;

import com.example.demo.entities.BorrowingRecord;
import com.example.demo.models.mappinginterface.BookMapper;
import com.example.demo.models.mappinginterface.BorrowingRecordMapper;
import com.example.demo.models.mappinginterface.PatronMapper;
import com.example.demo.models.response.BorrowingRecordResponseModel;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-09T02:02:13+0300",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240725-1906, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class BorrowingRecordMapperImpl implements BorrowingRecordMapper {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private PatronMapper patronMapper;

    @Override
    public BorrowingRecordResponseModel mapToBorrowingRecordResponseModel(BorrowingRecord borrowingRecord) {
        if ( borrowingRecord == null ) {
            return null;
        }

        BorrowingRecordResponseModel borrowingRecordResModel = new BorrowingRecordResponseModel();

        borrowingRecordResModel.setBook( bookMapper.mapToBookResponseModel( borrowingRecord.getBook() ) );
        borrowingRecordResModel.setBorrowDate( borrowingRecord.getBorrowDate() );
        borrowingRecordResModel.setId( borrowingRecord.getId() );
        borrowingRecordResModel.setPatron( patronMapper.mapToPatronResponseModel( borrowingRecord.getPatron() ) );
        borrowingRecordResModel.setReturnDate( borrowingRecord.getReturnDate() );

        return borrowingRecordResModel;
    }

    @Override
    public List<BorrowingRecordResponseModel> mapToBorrowingRecordResponseModelList(List<BorrowingRecord> borrowingRecord) {
        if ( borrowingRecord == null ) {
            return null;
        }

        List<BorrowingRecordResponseModel> list = new ArrayList<BorrowingRecordResponseModel>( borrowingRecord.size() );
        for ( BorrowingRecord borrowingRecord1 : borrowingRecord ) {
            list.add( mapToBorrowingRecordResponseModel(borrowingRecord1) );
        }

        return list;
    }
}

package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Book;
import com.example.demo.entities.BorrowingRecord;
import com.example.demo.entities.Patron;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mappinginterface.BorrowingRecordMapper;
import com.example.demo.models.response.BorrowingRecordResponseModel;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.BorrowingRecordRepository;
import com.example.demo.repositories.PatronRepository;
import com.example.demo.utils.BusinessLogicViolationException;


@Service
public class BorrowingRecordService {


	@Autowired
	BorrowingRecordRepository borrowingRecordRepository;
	
	@Autowired
	BorrowingRecordMapper borrowingRecordMapper;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	PatronRepository patronRepository;
	
	//---------------------------------------------------------------
	
	
	public BorrowingRecordResponseModel borrowBook(Long bookId,  Long patronId) {
		BorrowingRecord borrowingRecord = new BorrowingRecord();
		mapBorrowingRecordRequestModel(bookId, patronId, borrowingRecord);
		borrowingRecordRepository.save(borrowingRecord);
		return borrowingRecordMapper.mapToBorrowingRecordResponseModel(borrowingRecord);
	}

	
	public BorrowingRecordResponseModel returnBook(Long bookId,  Long patronId) {
		BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);
		if(borrowingRecord == null)
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BORROW_RECORD_FOR_THIS_ID.name());
		
		borrowingRecord.setReturnDate(LocalDateTime.now());
		borrowingRecordRepository.save(borrowingRecord);
		return borrowingRecordMapper.mapToBorrowingRecordResponseModel(borrowingRecord);
	}
	
	//keep the business validations in the service instead of mapping them.
	private void mapBorrowingRecordRequestModel(Long bookId,  Long patronId, BorrowingRecord borrowingRecord) {
		
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name());
		borrowingRecord.setBook(book.get());
		
		Optional<Patron> patron = patronRepository.findById(patronId);
		if(patron.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name());
		borrowingRecord.setPatron(patron.get());
		
		borrowingRecord.setBorrowDate(LocalDateTime.now());

	}

}


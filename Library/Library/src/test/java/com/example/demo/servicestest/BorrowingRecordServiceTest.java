package com.example.demo.services;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowingRecordServiceTest {

	@InjectMocks
	private BorrowingRecordService borrowingRecordService;

	@Mock
	private BorrowingRecordRepository borrowingRecordRepository;

	@Mock
	private BorrowingRecordMapper borrowingRecordMapper;

	@Mock
	private BookRepository bookRepository;

	@Mock
	private PatronRepository patronRepository;

	private Book book;
	private Patron patron;
	private BorrowingRecord borrowingRecord;
	private BorrowingRecordResponseModel borrowingRecordResponseModel;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		book = new Book();
		book.setId(1L);
		book.setTitle("Test Book");

		patron = new Patron();
		patron.setId(1L);
		patron.setName("Test Patron");

		borrowingRecord = new BorrowingRecord();
		borrowingRecord.setBook(book);
		borrowingRecord.setPatron(patron);
		borrowingRecord.setBorrowDate(LocalDateTime.now());

		borrowingRecordResponseModel = new BorrowingRecordResponseModel();
		borrowingRecordResponseModel.setBookId(1L);
		borrowingRecordResponseModel.setPatronId(1L);
		borrowingRecordResponseModel.setBorrowDate(LocalDateTime.now());
	}

	@Test
	public void testBorrowBook_Success() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(patronRepository.findById(anyLong())).thenReturn(Optional.of(patron));
		when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
		when(borrowingRecordMapper.mapToBorrowingRecordResponseModel(any(BorrowingRecord.class))).thenReturn(borrowingRecordResponseModel);

		BorrowingRecordResponseModel result = borrowingRecordService.borrowBook(1L, 1L);

		assertNotNull(result);
		assertEquals(1L, result.getBookId());
		assertEquals(1L, result.getPatronId());
		verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
	}

	@Test
	public void testBorrowBook_BookNotFound() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () ->
				borrowingRecordService.borrowBook(1L, 1L));

		assertEquals(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name(), exception.getMessage());
	}

	@Test
	public void testBorrowBook_PatronNotFound() {
		when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
		when(patronRepository.findById(anyLong())).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () ->
				borrowingRecordService.borrowBook(1L, 1L));

		assertEquals(ApiErrorMessageEnum.NO_MATCHING_PATRON_RECORD_FOR_THIS_ID.name(), exception.getMessage());
	}

	@Test
	public void testReturnBook_Success() {
		when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(borrowingRecord);
		when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
		when(borrowingRecordMapper.mapToBorrowingRecordResponseModel(any(BorrowingRecord.class))).thenReturn(borrowingRecordResponseModel);

		BorrowingRecordResponseModel result = borrowingRecordService.returnBook(1L, 1L);

		assertNotNull(result);
		assertNotNull(result.getBorrowDate());
		verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
	}

	@Test
	public void testReturnBook_RecordNotFound() {
		when(borrowingRecordRepository.findByBookIdAndPatronId(anyLong(), anyLong())).thenReturn(null);

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () ->
				borrowingRecordService.returnBook(1L, 1L));

		assertEquals(ApiErrorMessageEnum.NO_MATCHING_BORROW_RECORD_FOR_THIS_ID.name(), exception.getMessage());
	}
}

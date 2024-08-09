package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Book;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mappinginterface.BookMapper;
import com.example.demo.models.request.BookRequestModel;
import com.example.demo.models.response.BookResponseModel;
import com.example.demo.services.BookService;
import com.example.demo.utils.BusinessLogicViolationException;
import com.example.demo.repositories.BookRepository;


@Service
public class BookService{

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookMapper bookMapper;

	//-------------------------------------------------------
	

	public BookResponseModel createBook(BookRequestModel bookRequestModel) {
		Book book = bookMapper.mapToBookRequestModel(bookRequestModel);
		bookRepository.save(book);
		return bookMapper.mapToBookResponseModel(book);
	}


	@Transactional
	public BookResponseModel updateBookById(Long bookId, BookRequestModel bookReqModel) {
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name());
		Book bookObject = bookMapper.mapToBookRequestModel(bookReqModel);
		bookRepository.save(bookObject);
		return bookMapper.mapToBookResponseModel(bookObject);
	}


	public BookResponseModel getBookById(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name());
		Book bookObject = book.get();
		return bookMapper.mapToBookResponseModel(bookObject);
	}


	public List<BookResponseModel> getAllBooks() {
		List<Book> bookList = bookRepository.findAll();
		if(bookList.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.LIST_IS_EMPTY.name());
		return bookMapper.mapToBookResponseModelList(bookList);
	}
	

	public void deleteBookById(Long bookId) {
		Optional<Book> book = bookRepository.findById(bookId);
		if(book.isEmpty())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name());
		bookRepository.deleteById(bookId);
	}
	//-------------------------Helpers------------------------
	
	// private void mapBookReqModel(BookReqModel bookReqModel, Book book) {
	// 	book.setAuthor(bookReqModel.getAuthor());
	// 	book.setIsbn(bookReqModel.getIsbn());
	// 	book.setPublicationYear(bookReqModel.getPublicationYear());
	// 	book.setTitle(bookReqModel.getTitle());
	// }
}

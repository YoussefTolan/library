package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.request.BookRequestModel;
import com.example.demo.models.response.BookResponseModel;
import com.example.demo.services.BookService;
import com.example.demo.utils.BusinessLogicViolationException;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;

	// --------------------------------------------------------------------------------

	@PostMapping
	public ResponseEntity<BookResponseModel> createBook(@Valid @RequestBody BookRequestModel bookReqModel,
			BindingResult result) {
		if (result.hasErrors())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.DATA_VALIDATION_FAILED.name(),
					result.getAllErrors());
		try {
			return new ResponseEntity<>(bookService.createBook(bookReqModel), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(ApiErrorMessageEnum.UNIQUE_CONSTRAINT_VIOLATION.name());
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<BookResponseModel> updateBookById(@Valid @RequestBody BookRequestModel bookReqModel,
			@PathVariable("id") Long bookId, BindingResult result) {
		if (result.hasErrors())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.DATA_VALIDATION_FAILED.name(),
					result.getAllErrors());
		try {
			return new ResponseEntity<>(bookService.updateBookById(bookId, bookReqModel), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.UNIQUE_CONSTRAINT_VIOLATION.name(),
					result.getAllErrors());
		}
	}

	@GetMapping
	public ResponseEntity<List<BookResponseModel>> getAllBooks() {
		return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<BookResponseModel> getBookById(@PathVariable("id") Long bookId) {
		return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<BookResponseModel> deleteBookById(@PathVariable("id") Long bookId) {
		try {
			bookService.deleteBookById(bookId);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

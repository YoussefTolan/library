package com.example.demo.services;

import com.example.demo.entities.Book;
import com.example.demo.enums.ApiErrorMessageEnum;
import com.example.demo.models.mappinginterface.BookMapper;
import com.example.demo.models.request.BookRequestModel;
import com.example.demo.models.response.BookResponseModel;
import com.example.demo.repositories.BookRepository;
import com.example.demo.utils.BusinessLogicViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	@InjectMocks
	private BookService bookService;

	@Mock
	private BookRepository bookRepository;

	@Mock
	private BookMapper bookMapper;

	private Book book;
	private BookRequestModel bookRequestModel;
	private BookResponseModel bookResponseModel;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		book = new Book();
		book.setId(1L);
		book.setTitle("Test Book");
		book.setAuthor("Author");
		book.setIsbn("123456789");
		book.setPublicationYear(2021);

		bookRequestModel = new BookRequestModel();
		bookRequestModel.setTitle("Test Book");
		bookRequestModel.setAuthor("Author");
		bookRequestModel.setIsbn("123456789");
		bookRequestModel.setPublicationYear(2021);

		bookResponseModel = new BookResponseModel();
		bookResponseModel.setId(1L);
		bookResponseModel.setTitle("Test Book");

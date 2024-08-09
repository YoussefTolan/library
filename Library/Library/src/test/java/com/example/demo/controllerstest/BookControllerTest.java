package com.example.demo.controller;

import com.example.demo.models.request.BookReqModel;
import com.example.demo.models.response.BookResModel;
import com.example.demo.services.BookService;
import com.example.demo.utils.BusinessLogicViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	private BookResModel bookResModel;
	private BookReqModel bookReqModel;

	@BeforeEach
	public void setup() {
		bookReqModel = new BookReqModel();
		bookReqModel.setAuthor("John Doe");
		bookReqModel.setTitle("Spring Boot in Action");
		bookReqModel.setIsbn("1234567890");
		bookReqModel.setPublicationYear(2023);

		bookResModel = new BookResModel();
		bookResModel.setId(1L);
		bookResModel.setAuthor("John Doe");
		bookResModel.setTitle("Spring Boot in Action");
		bookResModel.setIsbn("1234567890");
		bookResModel.setPublicationYear(2023);
	}

	@Test
	public void testCreateBook() throws Exception {
		when(bookService.createBook(any(BookReqModel.class))).thenReturn(bookResModel);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"author\":\"John Doe\",\"title\":\"Spring Boot in Action\",\"isbn\":\"1234567890\",\"publicationYear\":2023}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.author").value("John Doe"))
				.andExpect(jsonPath("$.title").value("Spring Boot in Action"))
				.andExpect(jsonPath("$.isbn").value("1234567890"))
				.andExpect(jsonPath("$.publicationYear").value(2023));

		verify(bookService, times(1)).createBook(any(BookReqModel.class));
	}

	@Test
	public void testGetAllBooks() throws Exception {
		when(bookService.getAllBooks()).thenReturn(Arrays.asList(bookResModel));

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].author").value("John Doe"))
				.andExpect(jsonPath("$[0].title").value("Spring Boot in Action"));

		verify(bookService, times(1)).getAllBooks();
	}

	@Test
	public void testGetBookById() throws Exception {
		when(bookService.getBookById(1L)).thenReturn(bookResModel);

		mockMvc.perform(get("/books/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.author").value("John Doe"))
				.andExpect(jsonPath("$.title").value("Spring Boot in Action"));

		verify(bookService, times(1)).getBookById(1L);
	}

	@Test
	public void testUpdateBookById() throws Exception {
		when(bookService.updateBookById(eq(1L), any(BookReqModel.class))).thenReturn(bookResModel);

		mockMvc.perform(put("/books/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"author\":\"John Doe\",\"title\":\"Spring Boot in Action\",\"isbn\":\"1234567890\",\"publicationYear\":2023}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.author").value("John Doe"))
				.andExpect(jsonPath("$.title").value("Spring Boot in Action"));

		verify(bookService, times(1)).updateBookById(eq(1L), any(BookReqModel.class));
	}

	@Test
	public void testDeleteBookById() throws Exception {
		doNothing().when(bookService).deleteBookById(1L);

		mockMvc.perform(delete("/books/1"))
				.andExpect(status().isOk());

		verify(bookService, times(1)).deleteBookById(1L);
	}
}

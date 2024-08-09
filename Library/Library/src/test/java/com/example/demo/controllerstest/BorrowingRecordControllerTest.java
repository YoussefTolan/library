package com.example.demo.controller;

import com.example.demo.models.response.BorrowingRecordResponseModel;
import com.example.demo.services.BorrowingRecordService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BorrowingRecordController.class)
public class BorrowingRecordControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BorrowingRecordService borrowingRecordService;

	private BorrowingRecordResponseModel borrowingRecordResponseModel;

	@BeforeEach
	public void setup() {
		borrowingRecordResponseModel = new BorrowingRecordResponseModel();
		borrowingRecordResponseModel.setId(1L);
		borrowingRecordResponseModel.setBookId(1L);
		borrowingRecordResponseModel.setPatronId(1L);
		borrowingRecordResponseModel.setBorrowDate("2024-08-09");
		borrowingRecordResponseModel.setReturnDate(null);
	}

	@Test
	public void testCreateBorrowingRecord() throws Exception {
		when(borrowingRecordService.borrowBook(anyLong(), anyLong())).thenReturn(borrowingRecordResponseModel);

		mockMvc.perform(post("/borrow/1/patron/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.bookId").value(1L))
				.andExpect(jsonPath("$.patronId").value(1L))
				.andExpect(jsonPath("$.borrowDate").value("2024-08-09"));

		verify(borrowingRecordService, times(1)).borrowBook(anyLong(), anyLong());
	}

	@Test
	public void testUpdateBorrowingRecordById() throws Exception {
		borrowingRecordResponseModel.setReturnDate("2024-08-15");
		when(borrowingRecordService.returnBook(anyLong(), anyLong())).thenReturn(borrowingRecordResponseModel);

		mockMvc.perform(put("/return/1/patron/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.bookId").value(1L))
				.andExpect(jsonPath("$.patronId").value(1L))
				.andExpect(jsonPath("$.borrowDate").value("2024-08-09"))
				.andExpect(jsonPath("$.returnDate").value("2024-08-15"));

		verify(borrowingRecordService, times(1)).returnBook(anyLong(), anyLong());
	}
}

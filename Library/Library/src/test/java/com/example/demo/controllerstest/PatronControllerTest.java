package com.example.demo.controller;

import com.example.demo.models.request.PatronRequestModel;
import com.example.demo.models.response.PatronResponseModel;
import com.example.demo.services.PatronService;
import com.example.demo.utils.BusinessLogicViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatronController.class)
public class PatronControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatronService patronService;

	private PatronResponseModel patronResponseModel;

	@BeforeEach
	public void setup() {
		patronResponseModel = new PatronResponseModel();
		patronResponseModel.setId(1L);
		patronResponseModel.setName("John Doe");
		patronResponseModel.setEmail("john.doe@example.com");
		patronResponseModel.setMobile("123-456-7890");
	}

	@Test
	public void testCreatePatron() throws Exception {
		when(patronService.createPatron(any(PatronRequestModel.class))).thenReturn(patronResponseModel);

		String requestBody = "{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"mobile\": \"123-456-7890\" }";

		mockMvc.perform(post("/patrons")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.mobile").value("123-456-7890"));

		verify(patronService, times(1)).createPatron(any(PatronRequestModel.class));
	}

	@Test
	public void testUpdatePatronById() throws Exception {
		when(patronService.updatePatronById(anyLong(), any(PatronRequestModel.class))).thenReturn(patronResponseModel);

		String requestBody = "{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"mobile\": \"123-456-7890\" }";

		mockMvc.perform(put("/patrons/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.mobile").value("123-456-7890"));

		verify(patronService, times(1)).updatePatronById(anyLong(), any(PatronRequestModel.class));
	}

	@Test
	public void testGetAllPatrons() throws Exception {
		List<PatronResponseModel> patrons = Arrays.asList(patronResponseModel);
		when(patronService.getAllPatrons()).thenReturn(patrons);

		mockMvc.perform(get("/patrons")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1L))
				.andExpect(jsonPath("$[0].name").value("John Doe"))
				.andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
				.andExpect(jsonPath("$[0].mobile").value("123-456-7890"));

		verify(patronService, times(1)).getAllPatrons();
	}

	@Test
	public void testGetPatronById() throws Exception {
		when(patronService.getPatronById(anyLong())).thenReturn(patronResponseModel);

		mockMvc.perform(get("/patrons/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.mobile").value("123-456-7890"));

		verify(patronService, times(1)).getPatronById(anyLong());
	}

	@Test
	public void testDeletePatronById() throws Exception {
		doNothing().when(patronService).deletePatronById(anyLong());

		mockMvc.perform(delete("/patrons/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(patronService, times(1)).deletePatronById(anyLong());
	}
}

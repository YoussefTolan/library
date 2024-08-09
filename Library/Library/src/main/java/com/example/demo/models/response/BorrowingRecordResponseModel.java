package com.example.demo.models.response;


import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BorrowingRecordResponseModel implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -9026973430010525255L;

	private long id;
	private BookResponseModel book;
	private PatronResponseModel patron;
	private LocalDateTime borrowDate;
	private LocalDateTime returnDate;

}

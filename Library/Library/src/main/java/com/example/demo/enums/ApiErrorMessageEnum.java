package com.example.demo.enums;

public enum ApiErrorMessageEnum {

	NO_MATCHING_BORROW_RECORD_FOR_THIS_ID,
	NO_MATCHING_BOOK_RECORD_FOR_THIS_ID,
	NO_MATCHING_PATRON_RECORD_FOR_THIS_ID,
	LIST_IS_EMPTY,
	DATA_VALIDATION_FAILED,
	UNIQUE_CONSTRAINT_VIOLATION;
	
	private ApiErrorMessageEnum(){
		
	}
}

package com.example.demo.models.mappinginterface;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.entities.Book;
import com.example.demo.models.request.BookRequestModel;
import com.example.demo.models.response.BookResponseModel;

@Mapper(componentModel = "spring")
public interface BookMapper {

	BookResponseModel mapToBookResponseModel(Book book);
	List<BookResponseModel> mapToBookResponseModelList(List<Book> book);

	Book mapToBookRequestModel(BookRequestModel bookRequestModel);
	List<Book> mapToBookRequestModelList(List<BookRequestModel> bookRequestModel);

}

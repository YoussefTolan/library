package com.example.demo.models.mapinterface;

import com.example.demo.entities.Book;
import com.example.demo.models.mappinginterface.BookMapper;
import com.example.demo.models.request.BookRequestModel;
import com.example.demo.models.response.BookResponseModel;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-09T02:02:13+0300",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240725-1906, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookResponseModel mapToBookResponseModel(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponseModel bookResModel = new BookResponseModel();

        bookResModel.setAuthor( book.getAuthor() );
        bookResModel.setId( book.getId() );
        bookResModel.setIsbn( book.getIsbn() );
        bookResModel.setPublicationYear( book.getPublicationYear() );
        bookResModel.setTitle( book.getTitle() );

        return bookResModel;
    }

    @Override
    public List<BookResponseModel> mapToBookResponseModelList(List<Book> book) {
        if ( book == null ) {
            return null;
        }

        List<BookResponseModel> list = new ArrayList<BookResponseModel>( book.size() );
        for ( Book book1 : book ) {
            list.add( mapToBookResponseModel( book1 ) );
        }

        return list;
    }

    @Override
    public Book mapToBookRequestModel(BookRequestModel bookRequestModel) {
        Book book = new Book();
		book.setAuthor(bookRequestModel.getAuthor());
		book.setIsbn(bookRequestModel.getIsbn());
		book.setPublicationYear(bookRequestModel.getPublicationYear());
		book.setTitle(bookRequestModel.getTitle());
        return book;
    }

    @Override
    public List<Book> mapToBookRequestModelList(List<BookRequestModel> bookRequestModel) {
        if (bookRequestModel == null || bookRequestModel.isEmpty()) {
            return new ArrayList<>(); // Return an empty list if input is null or empty
        }
    
        List<Book> books = new ArrayList<>();
        for (BookRequestModel bookRequestModeltemp : bookRequestModel) {
            Book book = new Book();
            book.setAuthor(bookRequestModeltemp.getAuthor());
            book.setIsbn(bookRequestModeltemp.getIsbn());
            book.setPublicationYear(bookRequestModeltemp.getPublicationYear());
            book.setTitle(bookRequestModeltemp.getTitle());
            books.add(book);
        }
        return books;
    }
}

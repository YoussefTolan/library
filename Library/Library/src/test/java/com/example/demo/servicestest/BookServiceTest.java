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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private BookRequestModel bookRequestModel;
    private Book book;
    private BookResponseModel bookResponseModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bookRequestModel = new BookRequestModel();
        bookRequestModel.setTitle("Test Book");
        bookRequestModel.setAuthor("Test Author");
        bookRequestModel.setIsbn("123456789");
        bookRequestModel.setPublicationYear(2020);

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("123456789");
        book.setPublicationYear(2020);

        bookResponseModel = new BookResponseModel();
        bookResponseModel.setId(1L);
        bookResponseModel.setTitle("Test Book");
        bookResponseModel.setAuthor("Test Author");
        bookResponseModel.setIsbn("123456789");
        bookResponseModel.setPublicationYear(2020);
    }

    @Test
    void createBook_success() {
        when(bookMapper.mapToBookRequestModel(any(BookRequestModel.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBookResponseModel(any(Book.class))).thenReturn(bookResponseModel);

        BookResponseModel result = bookService.createBook(bookRequestModel);

        assertNotNull(result);
        assertEquals(bookResponseModel.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookRequestModel(any(BookRequestModel.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.mapToBookResponseModel(any(Book.class))).thenReturn(bookResponseModel);

        BookResponseModel result = bookService.updateBookById(1L, bookRequestModel);

        assertNotNull(result);
        assertEquals(bookResponseModel.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
            bookService.updateBookById(1L, bookRequestModel);
        });

        assertEquals(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name(), exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void getBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.mapToBookResponseModel(any(Book.class))).thenReturn(bookResponseModel);

        BookResponseModel result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(bookResponseModel.getTitle(), result.getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void getBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
            bookService.getBookById(1L);
        });

        assertEquals(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name(), exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllBooks_success() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.mapToBookResponseModelList(anyList())).thenReturn(List.of(bookResponseModel));

        List<BookResponseModel> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getAllBooks_noBooksFound() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
            bookService.getAllBooks();
        });

        assertEquals(ApiErrorMessageEnum.LIST_IS_EMPTY.name(), exception.getMessage());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void deleteBookById_success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        bookService.deleteBookById(1L);

        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteBookById_noBookFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
            bookService.deleteBookById(1L);
        });

        assertEquals(ApiErrorMessageEnum.NO_MATCHING_BOOK_RECORD_FOR_THIS_ID.name(), exception.getMessage());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookRepository, never()).deleteById(anyLong());
    }
}

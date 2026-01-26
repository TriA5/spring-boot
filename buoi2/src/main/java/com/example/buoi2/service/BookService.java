package com.example.buoi2.service;

import com.example.buoi2.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();
    private int nextId = 1;

    public BookService() {
        // Khởi tạo một số dữ liệu mẫu
        books.add(new Book(nextId++, "Java Programming", "John Doe"));
        books.add(new Book(nextId++, "Spring Boot Guide", "Jane Smith"));
    }

    // Lấy tất cả sách
    public List<Book> getAllBooks() {
        return books;
    }

    // Lấy sách theo ID
    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Thêm sách mới
    public Book addBook(Book newBook) {
        newBook.setId(nextId++);
        books.add(newBook);
        return newBook;
    }

    // Cập nhật sách
    public Book updateBook(int id, Book updatedBook) {
        Book book = getBookById(id);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            return book;
        }
        return null;
    }

    // Xóa sách
    public boolean deleteBook(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
}

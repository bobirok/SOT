package src.main.java.library.controllers;

import src.main.java.library.exceptions.BookExistsException;
import src.main.java.library.exceptions.BookNotFoundException;
import src.main.java.library.exceptions.BookOutOfOrderException;
import src.main.java.library.models.Book;

import java.util.ArrayList;
import java.util.Optional;

public class LibraryController {
    private ArrayList<Book> _books;
    private ArrayList<Book> _borrowedBooks;

    public LibraryController(ArrayList<Book> books, ArrayList<Book> borrowedBooks) {
        this._books = new ArrayList<>();
        this._borrowedBooks = borrowedBooks;
    }

    public ArrayList<Book> getAllBooks() {
        return this._books;
    }

    public ArrayList<Book> getAllBorrowedBooks() {
        return this._borrowedBooks;
    }

    public Book getBookById(int id) throws BookNotFoundException {
        Optional<Book> book = this._books.stream()
                .filter(x -> x.getId() == id)
                .findFirst();

        if(!book.isPresent()) throw new BookNotFoundException("Book with this id does not exist!");

        return book.get();
    }

    public Book getBookByTitle(String title) throws BookNotFoundException {
        Optional<Book> book = this._books.stream()
                .filter(x -> x.getAuthor().toLowerCase().equals(title.toLowerCase()))
                .findFirst();

        if(!book.isPresent()) throw new BookNotFoundException("Book with this title does not exist!");

        return book.get();
    }

    public void createBook(int id, String title, String author, int availableCopies) throws BookExistsException {
        Optional<Book> existingBook = this._books.stream()
                .filter(x -> x.getAuthor().toLowerCase().equals(title.toLowerCase()))
                .findFirst();

        if(existingBook.isPresent()) throw new BookExistsException("Book with this title exists!");

        this._books.add(new Book(id, title, author, availableCopies));
    }

    public Book updateBook(Book updatedBook) throws BookNotFoundException {
        Optional<Book> existingBook = this._books.stream()
                .filter(x -> x.getId() == updatedBook.getId())
                .findFirst();

        if(!existingBook.isPresent()) throw new BookNotFoundException("This book does not eixst");

        Book book = existingBook.get();
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setAvailableCopies(updatedBook.getAvailableCopies());

        return book;
    }

    public void deleteBook(int id) throws BookNotFoundException {
        Optional<Book> book = this._books.stream()
                .filter(x -> x.getId() == id)
                .findFirst();

        if(!book.isPresent()) throw new BookNotFoundException("Book with this id does not exist!");

        this._books.remove(book);
    }

    public void borrowCopyOfBook(int id) throws BookNotFoundException, BookOutOfOrderException {
        Optional<Book> book = this._books.stream()
                .filter(x -> x.getId() == id)
                .findFirst();

        if(!book.isPresent()) throw new BookNotFoundException("Book with this id does not exist!");

        if(!book.get().hasAvailableCopeis()) throw new BookOutOfOrderException("No available copies of this book!");

        book.get().borrowCopyOfBook();
    }

}

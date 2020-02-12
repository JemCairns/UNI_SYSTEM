package uni.system.my_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uni.system.my_project.exception.BookNotFoundException;
import uni.system.my_project.model.Book;
import uni.system.my_project.repository.BookRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    // Get All Notes
    @GetMapping("/books")
//    public List<Book> getAllNotes() {
    public List<Book> getAllNotes(@RequestParam(name="name", required=false, defaultValue = "Book_Name") String name, Model model) {
        model.addAttribute("name", name);
//        return bookRepository.findAll();
        return bookRepository.findAll();
    }

    // Create a new Note
    @PostMapping("/books")
    public Book createNote(@Valid @RequestBody Book book) {
        return bookRepository.save(book);
    }

    // Get a Single Note
    @GetMapping("/books/{id}")
    public String getNoteById(@PathVariable(value = "id") Long bookId, @RequestParam(name="name", required=false, defaultValue = "Book_Name") String name, Model model) throws BookNotFoundException {
//        Optional<Book> book = ;
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
        model.addAttribute("name", book.getBook_name());
        model.addAttribute("author", book.getAuthor_name());
        return "books";
    }

    // Update a Note
    @PutMapping("/books/{id}")
    public Book updateNote(@PathVariable(value = "id") Long bookId,
                           @Valid @RequestBody Book bookDetails) throws BookNotFoundException {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        book.setBook_name(bookDetails.getBook_name());
        book.setAuthor_name(bookDetails.getAuthor_name());
        book.setIsbn(bookDetails.getIsbn());

        Book updatedBook = bookRepository.save(book);

        return updatedBook;
    }

    // Delete a Note
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long bookId) throws BookNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        bookRepository.delete(book);

        return ResponseEntity.ok().build();
    }
}

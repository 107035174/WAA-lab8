package lab8.lab8.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lab8.lab8.model.Book;
import lab8.lab8.repository.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookRepository.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookRepository.getReferenceById(id);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    public ResponseEntity<Book> create(@RequestParam String title, @RequestParam String author) {
        Book book = new Book(title, author);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    public ResponseEntity<Book> update(@PathVariable long id, @RequestParam String title, @RequestParam String author) {
        if (bookRepository.existsById(id)) {
            Book book = new Book(title, author);
            book.setId(id);
            bookRepository.save(book);
            return new ResponseEntity<Book>(book, HttpStatus.OK);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"ADMIN\")")
    public ResponseEntity<String> delete(@PathVariable long id) {
        if (bookRepository.existsById(id)) {

            bookRepository.deleteById(id);
            return new ResponseEntity<String>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Book not found", HttpStatus.OK);
        }

    }

}

package oneToManyUnidirectional.unidirectionalOneToMany.controller;

import oneToManyUnidirectional.unidirectionalOneToMany.model.Author;
import oneToManyUnidirectional.unidirectionalOneToMany.model.Book;
import oneToManyUnidirectional.unidirectionalOneToMany.repository.AuthorRepository;
import oneToManyUnidirectional.unidirectionalOneToMany.repository.BookRepository;
import oneToManyUnidirectional.unidirectionalOneToMany.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/booksAuthor")
public class BookControllerAuthor {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookControllerAuthor(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository=authorRepository;
    }

    @PostMapping
    public ResponseEntity<Book> creates(@RequestBody @Valid Book book) {
        Optional<Author> optionalAuthor=authorRepository.findById(book.getAuthor().getId());
        if ( !optionalAuthor.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        book.setAuthor(optionalAuthor.get());

        Book savedBook = bookRepository.save(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).body(savedBook);
    }

}

package oneToManyUnidirectional.unidirectionalOneToMany.controller;


import oneToManyUnidirectional.unidirectionalOneToMany.model.Author;
import oneToManyUnidirectional.unidirectionalOneToMany.model.Book;
import oneToManyUnidirectional.unidirectionalOneToMany.model.Library;
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
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;


    @Autowired
    public BookController(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
         }
/*
    @Autowired
    public  BookController( BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository=bookRepository;
        this.authorRepository=authorRepository;
    }


 */
    @PostMapping
    public ResponseEntity<Book> create(@RequestBody @Valid Book book) {
        Optional<Library> optionalLibrary = libraryRepository.findById(book.getLibrary().getId());
        if (!optionalLibrary.isPresent() ) {
            return ResponseEntity.unprocessableEntity().build();
        }

        book.setLibrary(optionalLibrary.get());

        Book savedBook = bookRepository.save(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).body(savedBook);
    }






    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@RequestBody @Valid Book book, @PathVariable Integer id) {
        Optional<Library> optionalLibrary = libraryRepository.findById(book.getLibrary().getId());
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        book.setLibrary(optionalLibrary.get());
        book.setId(optionalBook.get().getId());
        bookRepository.save(book);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        bookRepository.delete(optionalBook.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAll(Pageable pageable) {
        return ResponseEntity.ok(bookRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalBook.get());
    }



    @GetMapping("/library/{libraryId}")
    public ResponseEntity<Page<Book>> getByLibraryId(@PathVariable Integer libraryId, Pageable pageable) {
        return ResponseEntity.ok(bookRepository.findByLibraryId(libraryId, pageable));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<Book>> getByAuthorId(@PathVariable Integer authorId,Pageable pageable){
        return ResponseEntity.ok(bookRepository.findByAuthorId(authorId,pageable));
    }



}

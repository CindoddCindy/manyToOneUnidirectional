package oneToManyUnidirectional.unidirectionalOneToMany.controller;


import oneToManyUnidirectional.unidirectionalOneToMany.model.Author;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {


    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Author> create(@Valid @RequestBody Author author) {
        Author savedAuthor = authorRepository.save(author);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAuthor.getId()).toUri();

        return ResponseEntity.created(location).body(savedAuthor);
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<Library> update(@PathVariable Integer id, @Valid @RequestBody Library library) {
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        library.setId(optionalLibrary.get().getId());
        libraryRepository.save(library);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Library> delete(@PathVariable Integer id) {
        Optional<Library> optionalLibrary = libraryRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        deleteLibraryInTransaction(optionalLibrary.get());

        return ResponseEntity.noContent().build();
    }

    @Transactional
    public void deleteLibraryInTransaction(Library library) {
        bookRepository.deleteByLibraryId(library.getId());
        libraryRepository.delete(library);
    }

 */

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable Integer id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (!optionalAuthor.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalAuthor.get());
    }

    @GetMapping("/")
    public ResponseEntity<Page<Author>> getAll(Pageable pageable) {
        return ResponseEntity.ok(authorRepository.findAll(pageable));
    }
}

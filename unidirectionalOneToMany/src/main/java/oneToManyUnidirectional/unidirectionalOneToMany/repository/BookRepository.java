package oneToManyUnidirectional.unidirectionalOneToMany.repository;

import oneToManyUnidirectional.unidirectionalOneToMany.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{
    Page<Book> findByLibraryId(Integer libraryId, Pageable pageable);

    Page<Book> findByAuthorId(Integer authorId, Pageable pageable);


    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE b.library.id = ?1")
    void deleteByLibraryId(Integer libraryId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE b.author.id = ?1")
    void deleteByAuthorId(Integer authorId);
}

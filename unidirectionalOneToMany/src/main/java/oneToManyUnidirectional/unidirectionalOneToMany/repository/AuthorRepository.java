package oneToManyUnidirectional.unidirectionalOneToMany.repository;

import oneToManyUnidirectional.unidirectionalOneToMany.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}

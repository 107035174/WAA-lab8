package lab8.lab8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lab8.lab8.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}

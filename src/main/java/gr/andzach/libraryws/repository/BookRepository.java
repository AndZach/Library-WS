package gr.andzach.libraryws.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.andzach.libraryws.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

	Optional<Book> findByTitle(String title);
	
	List<Book> findByAuthor(String author);
	
	List<Book> findBySectorSectorID(Integer id);
	
	

}

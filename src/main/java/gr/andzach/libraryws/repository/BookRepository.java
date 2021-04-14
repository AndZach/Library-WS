package gr.andzach.libraryws.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.andzach.libraryws.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>{

	Optional<Book> findByTitle(String title);
	
	List<Book> findByAuthor(String author);
	
//	List<Book> findBySectorSectorID(Integer id);
	List<Book> findBySectorSectorID(Integer id);
	
	

}

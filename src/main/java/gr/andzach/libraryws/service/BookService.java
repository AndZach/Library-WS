package gr.andzach.libraryws.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.andzach.libraryws.model.Book;
import gr.andzach.libraryws.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepos;
	
	public List<Book> getBooks() {
		return bookRepos.findAll();
	}

	public Optional<Book> getBook(Integer id) {
		return bookRepos.findById(id);
	}
	
	public Optional<Book> getBookByTitle(String title) {
		return bookRepos.findByTitle(title);
	}
	
	public List<Book> getBooksByAuthor(String author) {
		return bookRepos.findByAuthor(author);
	}
	
	public List<Book> getBooksBySectorId(Integer id) {
		return bookRepos.findBySectorSectorID(id);
	}
}

package gr.andzach.libraryws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.andzach.libraryws.dto.CreateBookDto;
import gr.andzach.libraryws.dto.GetUserDto;
import gr.andzach.libraryws.model.Book;
import gr.andzach.libraryws.model.Sector;
import gr.andzach.libraryws.repository.BookRepository;

@Service
@Transactional
public class BookService {
	
	@Autowired
	private BookRepository bookRepos;
	
	@Autowired
	private SectorService sectorService;
	
	public List<Book> getBooks() {
		return bookRepos.findAll();
	}

	public Book getBook(Long id) {
		Optional<Book> bookOptional = bookRepos.findById(id);
		if (bookOptional.isPresent()) {
			return bookOptional.get();
		} else {
			throw new EntityNotFoundException("The book with id "+id+" does not exist");
		}
		
	}
	
	public Book getBookByTitle(String title) {
		
		Optional<Book> book= bookRepos.findByTitle(title);
		if(book.isPresent()) {
			return book.get();
		} else {
			throw new EntityNotFoundException("There is no book with title: "+title);
		}
	}
	
	public List<Book> getBooksByAuthor(String author) {
		return bookRepos.findByAuthor(author);
	}
	
	public List<Book> getBooksBySectorId(Integer id) {
		return bookRepos.findBySectorSectorID(id);
	}
	
	public Book createBook(CreateBookDto bookDto) {
		
		Sector sector =	sectorService.getSector(bookDto.getSector());
		
		Book book = new Book();
		book.setAuthor(bookDto.getAuthor());
		book.setPages(bookDto.getPages());
		book.setSector(sector);
		book.setTitle(bookDto.getTitle());
		
		return bookRepos.save(book);
	}
	
	public Book updateBook(Long bookId, CreateBookDto bookDto) {
		
		getBook(bookId);
		
		Sector sector =	sectorService.getSector(bookDto.getSector());
		
		Book book = new Book();
		book.setAuthor(bookDto.getAuthor());
		book.setPages(bookDto.getPages());
		book.setSector(sector);
		book.setTitle(bookDto.getTitle());
		book.setBookID(bookId);
		
		Book updatedBook = bookRepos.save(book);
		return updatedBook;
	}
	
	public void deleteBook (Long bookId) {
		getBook(bookId);
		bookRepos.deleteById(bookId);
	}
}

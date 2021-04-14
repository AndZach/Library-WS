package gr.andzach.libraryws.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gr.andzach.libraryws.model.Book;
import gr.andzach.libraryws.repository.BookRepository;
import gr.andzach.libraryws.service.BookService;
import jdk.jfr.BooleanFlag;

@RestController()
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookRepository bookRepos;
	
//	@RequestMapping(path = "/", method = RequestMethod.GET)
	@GetMapping(path="")
 	public ResponseEntity getBooks() {
		List<Book> books = bookService.getBooks();
		if(books.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books were found!");
		} else {
			return ResponseEntity.ok(bookService.getBooks());
		}
	}
	
	@GetMapping(path = "/sector"/* , params= {"sectorId"} */)
	public ResponseEntity getBooksBySector(@RequestParam(/* required = true, */ defaultValue = "") Integer sectorId) {
		List<Book> booksBySector = bookService.getBooksBySectorId(sectorId);
		if(booksBySector.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books were found in "+sectorId+" Sector...");
		} else {
			return ResponseEntity.ok(booksBySector);
		}
		
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Book> getBook(@PathVariable Integer id) {
		Optional<Book> book = bookService.getBook(id);
		if (book.isPresent()) {
			return ResponseEntity.ok(book.get());
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path="/{title}")
	public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
		Optional<Book> book = bookService.getBookByTitle(title);
		if (book.isPresent()) {
			return ResponseEntity.ok(book.get());
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(path="/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createBook(@RequestBody Book book) {
		if (bookService.getBook(book.getBookID()).isEmpty()) {
			Book savedBook = bookRepos.save(book);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
		} else {
			return ResponseEntity.badRequest().body("There is already a book with ID: "+book.getBookID());
		}
	}
	
	@PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateBook(@PathVariable Integer id, @RequestBody Book book) {
		Optional<Book> bookToUpdate = bookService.getBook(id);
		if (bookToUpdate.isPresent() ) {
			Book updatedBook = bookRepos.save(book);
			return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no book with id: "+id);
		}
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity deleteBook(@PathVariable Integer id) {
		if(bookService.getBook(id).isPresent()) {
			bookRepos.deleteById(id);
			return ResponseEntity.ok("Book with ID: "+id+" was deleted!");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no book with id: "+id);
		}
		
	}
	

	

}

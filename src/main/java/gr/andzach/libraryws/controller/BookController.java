package gr.andzach.libraryws.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpHeaders;
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

import gr.andzach.libraryws.dto.CreateBookDto;
import gr.andzach.libraryws.model.Book;
import gr.andzach.libraryws.repository.BookRepository;
import gr.andzach.libraryws.service.BookService;
import jdk.jfr.BooleanFlag;

@RestController()
@RequestMapping(path = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	
	@RequestMapping(path = "", method = RequestMethod.GET)
 	public ResponseEntity<List<Book>> getBooks() {
		List<Book> books = bookService.getBooks();
		return ResponseEntity.ok(bookService.getBooks());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Book> getBook(@PathVariable Long id) {
		return ResponseEntity.ok(bookService.getBook(id));
	}
	
	@GetMapping(path = "", params= {"sectorId"} )
	public ResponseEntity<List<Book>> getBooksBySector(@RequestParam Integer sectorId) {
		List<Book> booksBySector = bookService.getBooksBySectorId(sectorId);
			return ResponseEntity.ok(booksBySector); 
	}
	
	@GetMapping(path="", params = "title")
	public ResponseEntity<Book> getBookByTitle(@RequestParam String title) {
		Book book = bookService.getBookByTitle(title);
			return ResponseEntity.ok(book);
	}
	
	@PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> createBook(@RequestBody CreateBookDto book, HttpServletRequest request) {
			Book createdBook = bookService.createBook(book);
			String stringURI = request.getRequestURL().toString() + createdBook.getBookID();
			HttpHeaders responseHeaders = new HttpHeaders();
			try {
				URI newURI = new URI(stringURI);
				responseHeaders.setLocation(newURI);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			 return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(createdBook);
	}
	
	@PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody CreateBookDto book) {
			Book updatedBook = bookService.updateBook(id, book);
			return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity deleteBook(@PathVariable Long id) {
			bookService.deleteBook(id);
			return ResponseEntity.ok("Book with ID: "+id+" was deleted!");
	}
	

}

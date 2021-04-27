package gr.andzach.libraryws.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@Entity
@Table(name="sectors")
public class Sector implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sectorID;
	private String description;
	private String name;
	
	//bi-directional many-to-one association to Book
	@OneToMany(mappedBy="sector")
	@JsonBackReference
	private List<Book> books;

	public Sector() {
	}
	
	public Sector(Long id, String description, String name) {
		super();
		this.sectorID=id;
		this.description = description;
		this.name = name;
	}


	public Long getSectorID() {
		return this.sectorID;
	}

	public void setSectorID(Long sectorID) {
		this.sectorID = sectorID;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Book> getBooks() {
		return this.books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Book addBook(Book book) {
		getBooks().add(book);
		book.setSector(this);

		return book;
	}

	public Book removeBook(Book book) {
		getBooks().remove(book);
		book.setSector(null);

		return book;
	}

}
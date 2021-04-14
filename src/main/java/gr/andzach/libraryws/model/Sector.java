package gr.andzach.libraryws.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the sectors database table.
 * 
 */
@Entity
@Table(name="sectors")
@NamedQuery(name="Sector.findAll", query="SELECT s FROM Sector s")
public class Sector implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int sectorID;
	private String description;
	private String name;
	private List<Book> books;

	public Sector() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getSectorID() {
		return this.sectorID;
	}

	public void setSectorID(int sectorID) {
		this.sectorID = sectorID;
	}


	@Lob
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


	//bi-directional many-to-one association to Book
	@OneToMany(mappedBy="sector")
	@JsonBackReference
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
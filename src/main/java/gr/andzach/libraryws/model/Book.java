package gr.andzach.libraryws.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;


/**
 * The persistent class for the books database table.
 * 
 */
@Entity
@Table(name="books")
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private int bookID;
	private String author;
	private int pages;
	private String title;
	private Date yearOfPublish;
	private Sector sector;

	public Book() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getBookID() {
		return this.bookID;
	}

	public void setBookID(int bookID) {
		this.bookID = bookID;
	}


	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public int getPages() {
		return this.pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Temporal(TemporalType.DATE)
	public Date getYearOfPublish() {
		return this.yearOfPublish;
	}

	public void setYearOfPublish(Date yearOfPublish) {
		this.yearOfPublish = yearOfPublish;
	}


	//bi-directional many-to-one association to Sector
	@ManyToOne
	@JoinColumn(name="SectorID")
	public Sector getSector() {
		return this.sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

}
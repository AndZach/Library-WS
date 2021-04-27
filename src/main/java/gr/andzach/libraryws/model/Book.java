package gr.andzach.libraryws.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;


@Entity
@Table(name="books")
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long bookID;
	
	private String author;
	private Integer pages;
	private String title;
	
	//bi-directional many-to-one association to Sector
	@ManyToOne
	@JoinColumn(name="Sector")
	private Sector sector;

	public Book() {
	}
	

	public Book(Long bookID, String author, Integer pages, String title, Sector sector) {
		super();
		this.bookID = bookID;
		this.author = author;
		this.pages = pages;
		this.title = title;
		this.sector = sector;
	}



	public Long getBookID() {
		return this.bookID;
	}

	public void setBookID(Long bookID) {
		this.bookID = bookID;
	}


	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public Integer getPages() {
		return this.pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Sector getSector() {
		return this.sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

}
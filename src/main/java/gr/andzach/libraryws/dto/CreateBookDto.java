package gr.andzach.libraryws.dto;

import gr.andzach.libraryws.model.Sector;

public class CreateBookDto {
	
	private String title;
	private String author;
	private Integer pages;
//	private CreateSectorDto sector;
	private Long sector;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
//	public CreateSectorDto getSector() {
//		return sector;
//	}
//	public void setSector(CreateSectorDto sector) {
//		this.sector = sector;
//	}
	public Long getSector() {
		return sector;
	}
	public void setSector(Long sector) {
		this.sector = sector;
	}
	
	
}

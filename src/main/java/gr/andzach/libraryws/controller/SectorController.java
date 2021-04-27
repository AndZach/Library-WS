package gr.andzach.libraryws.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.andzach.libraryws.dto.CreateSectorDto;
import gr.andzach.libraryws.model.Book;
import gr.andzach.libraryws.model.Sector;
import gr.andzach.libraryws.repository.SectorRepository;
import gr.andzach.libraryws.service.SectorService;

@RestController
@RequestMapping(path = "/sectors", produces=MediaType.APPLICATION_JSON_VALUE)
public class SectorController {

	@Autowired
	private SectorService sectorService;
	
	@GetMapping("")
	public ResponseEntity<List<Sector>> getSectors() {
		List<Sector> sectorList = sectorService.getSectors();
			return ResponseEntity.ok(sectorList);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Sector> getSector(@PathVariable Long id) {
		Sector sector = sectorService.getSector(id);
			return ResponseEntity.ok(sector);
	}
	
	@GetMapping(path="", params="name")
	public ResponseEntity<Sector> getSectorByName(@RequestParam String name) {
		Sector sector = sectorService.getSectorByName(name);
			return ResponseEntity.ok(sector);
	}
	
	@PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Sector> createSector(@RequestBody CreateSectorDto sector, HttpServletRequest request) {
			Sector createdSector = sectorService.createSector(sector);
			String stringURI = request.getRequestURL().toString()+"/"+createdSector.getSectorID(); 
			HttpHeaders responseHeaders=new HttpHeaders();
			try {
				URI newURI = new URI(stringURI);
				responseHeaders.setLocation(newURI);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(createdSector);
	}
	
	@PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateSector(@PathVariable Long id, @RequestBody CreateSectorDto sector) {
			Sector updatedsector = sectorService.updateSector(id, sector);
			return ResponseEntity.status(HttpStatus.OK).body(updatedsector);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity deleteSector(@PathVariable Long id) {
			sectorService.deleteSector(id);
			return ResponseEntity.ok("Sector with ID: "+id+" was deleted!");
	}

}

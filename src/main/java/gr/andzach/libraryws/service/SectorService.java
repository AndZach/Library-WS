package gr.andzach.libraryws.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.andzach.libraryws.dto.CreateSectorDto;
import gr.andzach.libraryws.model.Book;
import gr.andzach.libraryws.model.Sector;
import gr.andzach.libraryws.repository.SectorRepository;

@Service
@Transactional
public class SectorService {
	
	@Autowired
	private SectorRepository sectorRepos;
	
	public List<Sector> getSectors() {
		return sectorRepos.findAll();
	}

	public Sector getSector(Long id) {
		Optional<Sector> sector = sectorRepos.findById(id);
		if (sector.isPresent()) {
			return sector.get();
		} else {
			throw new EntityNotFoundException("There is no sector with id: "+id);
		}
	}
	
	public Sector getSectorByName(String name) {
		Optional<Sector> sector = sectorRepos.findByName(name);
		if(sector.isPresent()) {
			return sector.get();
		} else {
			throw new EntityNotFoundException("There is no sector with name: " + name);
		}
	}
	
	public Sector createSector(CreateSectorDto sectorDto) {
//		sector.setSectorID( null);
		Sector sector = new Sector(null, sectorDto.getDescription(), sectorDto.getName());
		return sectorRepos.save(sector);
	}
	
	public Sector updateSector (Long id, CreateSectorDto sectorDto) {
		getSector(id);
		Sector sector = new Sector(null, sectorDto.getDescription(), sectorDto.getName());
//		sector.setSectorID(id);		
		Sector updatedSector = sectorRepos.save(sector);
		return updatedSector;
	}
	
	public void deleteSector(Long id) {
		getSector(id);
		sectorRepos.deleteById(id);
	}



}

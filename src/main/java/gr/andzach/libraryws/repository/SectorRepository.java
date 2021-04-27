package gr.andzach.libraryws.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.andzach.libraryws.model.Book;
import gr.andzach.libraryws.model.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long>{
	
	Optional<Sector> findByName(String name);

	//List<Book> findSectorBooksBySectorID(Integer sectorID);
	
	
	
}

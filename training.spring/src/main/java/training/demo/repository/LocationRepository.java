package training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.demo.model.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    void deleteLocationByLocationId(int id);

    Location findByLocationId(int id);

    List<Location> findAll();
}
package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.lit.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends CrudRepository<City, Long> {
}

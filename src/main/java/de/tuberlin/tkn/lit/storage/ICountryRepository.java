package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.lit.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends CrudRepository<Country, Long> {
}

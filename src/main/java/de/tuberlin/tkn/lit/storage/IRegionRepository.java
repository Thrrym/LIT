package de.tuberlin.tkn.lit.storage;

import de.tuberlin.tkn.lit.model.lit.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionRepository extends CrudRepository<Region, Long> {
}

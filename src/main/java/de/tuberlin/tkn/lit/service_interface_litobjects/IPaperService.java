package de.tuberlin.tkn.lit.service_interface_litobjects;

import de.tuberlin.tkn.lit.model.lit.Paper;
import de.tuberlin.tkn.lit.storage_litobjects.IPaperRepository;

import java.util.List;

public interface IPaperService {

    List<Paper> findAll();

    IPaperRepository getRepository();
}

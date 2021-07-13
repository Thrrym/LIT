package de.tuberlin.tkn.lit.service_impl_litobjects;

import de.tuberlin.tkn.lit.model.lit.AuthorsWork;
import de.tuberlin.tkn.lit.service_interface_litobjects.IAuthorsWorkService;
import de.tuberlin.tkn.lit.storage_litobjects.IAuthorsWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XAuthorsWorkService implements IAuthorsWorkService {

    @Autowired
    private IAuthorsWorkRepository repository;

    @Override
    public IAuthorsWorkRepository getRepository() {
        return repository;
    }

    @Override
    public List<AuthorsWork> findAll() {

        return (List<AuthorsWork>) repository.findAll();
    }
}

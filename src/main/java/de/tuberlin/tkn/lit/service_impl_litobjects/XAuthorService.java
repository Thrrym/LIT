package de.tuberlin.tkn.lit.service_impl_litobjects;

import de.tuberlin.tkn.lit.model.lit.Author;
import de.tuberlin.tkn.lit.service_interface_litobjects.IAuthorService;
import de.tuberlin.tkn.lit.storage_litobjects.IAuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XAuthorService implements IAuthorService {

    @Autowired
    private IAuthorRepository repository;

    @Override
    public IAuthorRepository getRepository() {
        return repository;
    }

    @Override
    public List<Author> findAll() {
        return (List<Author>) repository.findAll();
    }
}

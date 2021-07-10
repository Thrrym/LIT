package de.tuberlin.tkn.lit.service_impl;

import de.tuberlin.tkn.lit.model.activitypub.activities.Undo;
import de.tuberlin.tkn.lit.model.activitypub.actors.Person;
import de.tuberlin.tkn.lit.service.IUndoService;
import de.tuberlin.tkn.lit.storage.IPersonRepository;
import de.tuberlin.tkn.lit.storage.IUndoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XUndoService implements IUndoService {

    @Override
    public IUndoRepository getRepository() {
        return repository;
    }

    @Autowired
    private IUndoRepository repository;


    @Override
    public List<Undo> findAll() {

        return (List<Undo>) repository.findAll();
    }
}

package de.tuberlin.tkn.lit.service_impl_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Undo;
import de.tuberlin.tkn.lit.service_interface_activities.IUndoService;
import de.tuberlin.tkn.lit.repos_activities.IUndoRepository;
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

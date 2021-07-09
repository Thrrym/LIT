package de.tuberlin.tkn.lit.service;

import de.tuberlin.tkn.lit.model.activitypub.activities.Block;
import de.tuberlin.tkn.lit.storage.IBlockRepository;

import java.util.List;

public interface IBlockService {

    List <Block> findAll();

    IBlockRepository getRepository();

}

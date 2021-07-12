package de.tuberlin.tkn.lit.service_interface_activities;

import de.tuberlin.tkn.lit.model.activitypub.activities.Block;
import de.tuberlin.tkn.lit.storage_activities.IBlockRepository;

import java.util.List;

public interface IBlockService {

    List <Block> findAll();

    IBlockRepository getRepository();

}

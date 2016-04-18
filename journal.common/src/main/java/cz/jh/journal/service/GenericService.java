/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.service;

import cz.jh.journal.model.DBEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jan.horky
 */
@Local
public interface GenericService<Entity extends DBEntity> {

    Entity create(Entity e);

    Entity update(Entity e);

    Entity delete(Long id);

    Entity find(Long id);

    // TODO: filter, order
    List<Entity> list(int first, int pageSize);

    // TODO: filter
    long count();
}

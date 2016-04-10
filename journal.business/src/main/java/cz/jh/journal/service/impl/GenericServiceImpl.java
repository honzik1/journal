/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.service.impl;

import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import cz.jh.journal.dao.GenericDao;
import cz.jh.journal.model.DBEntity;
import cz.jh.journal.service.GenericService;

/**
 *
 * @author jan.horky
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public abstract class GenericServiceImpl<Entity extends DBEntity> implements GenericService<Entity> {

    protected abstract GenericDao getRootEntityDao();

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Entity create(Entity e) {
        return (Entity) getRootEntityDao().saveAndFlush(e);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Entity update(Entity e) {
        return (Entity) getRootEntityDao().saveOrUpdate(e);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public Entity delete(Long id) {
        final Entity entity = (Entity) getRootEntityDao().findOne(id);
        getRootEntityDao().delete(id);
        return entity;
    }

    @Override
    public Entity find(Long id) {
        return (Entity) getRootEntityDao().findOne(id);
    }

    @Override
    public List<Entity> list(int first, int pageSize) {
        return getRootEntityDao().findAll(first, pageSize, "createTime", Boolean.FALSE, null, null, null);
    }
}

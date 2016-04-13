/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.business.service.impl;

import cz.jh.journal.business.dao.JournalDao;
import cz.jh.journal.business.model.Journal;
import cz.jh.journal.business.service.JournalService;
import cz.jh.journal.dao.GenericDao;
import cz.jh.journal.service.impl.GenericServiceImpl;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 *
 * @author jan.horky
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JournalServiceImpl extends GenericServiceImpl<Journal> implements JournalService {

    @Inject
    private JournalDao dao;

    @Override
    protected GenericDao getRootEntityDao() {
        return dao;
    }

}

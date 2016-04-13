/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.business.dao.impl;

import cz.jh.journal.business.dao.DisciplineDao;
import cz.jh.journal.business.model.Discipline;
import cz.jh.journal.dao.impl.GenericDaoImpl;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jan.horky
 */
@Named
@RequestScoped
public class DisciplineDaoImpl extends GenericDaoImpl<Discipline, Long> implements DisciplineDao {

    public DisciplineDaoImpl() {
        super(Discipline.class);
    }
}

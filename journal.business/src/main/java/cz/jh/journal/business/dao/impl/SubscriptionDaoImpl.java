/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.business.dao.impl;

import cz.jh.journal.business.dao.SubscriptionDao;
import cz.jh.journal.business.model.Subscription;
import cz.jh.journal.dao.impl.GenericDaoImpl;
import javax.inject.Named;

/**
 *
 * @author jan.horky
 */
@Named
public class SubscriptionDaoImpl extends GenericDaoImpl<Subscription, Long> implements SubscriptionDao {

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.business.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import cz.jh.journal.business.dao.UserDao;
import cz.jh.journal.business.model.User;
import cz.jh.journal.business.service.UserService;
import cz.jh.journal.dao.GenericDao;
import cz.jh.journal.service.ConfigurationService;
import static cz.jh.journal.service.ConfigurationServiceKey.JWT_EXPIRATION;
import static cz.jh.journal.service.ConfigurationServiceKey.JWT_SECRET;
import cz.jh.journal.service.impl.GenericServiceImpl;
import cz.jh.journal.util.JwtTokenUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jan.horky
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class.getName());

    @EJB
    private ConfigurationService conf;

    @Inject
    private UserDao dao;

    @Override
    protected GenericDao getRootEntityDao() {
        return dao;
    }

    @Override
    public String login(String email, String passHash) {

        final User user = dao.findOne("e", ImmutableList.of("e.email = :email and e.password = :passHash"), ImmutableMap.of("email", email, "passHash", passHash));
        if (user != null) {
            // TODO: issuer from configuration
            // TODO: save tokenUuid for user to DB
            JwtTokenUtil.Token token = JwtTokenUtil.createToken(
                    user.getId(),
                    conf.getInt(JWT_EXPIRATION),
                    conf.getString(JWT_SECRET),
                    null
            );

            return token.getJwt();
        } else {
            return null;
        }
    }

    @Override
    public User verifyToken(String jwt) {
        // TODO: must be cached
        Long userId = JwtTokenUtil.verifyToken(jwt, conf.getString(JWT_SECRET));
        return dao.findOne(userId);
    }

    @Override
    public void logout() {
        // TODO invalidate all user tokens
        log.info("User id:'{}' logged out.", context.get(LOGGED_USER_ID_KEY));
    }
}

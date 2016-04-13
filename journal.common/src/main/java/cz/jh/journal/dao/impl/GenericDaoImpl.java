package cz.jh.journal.dao.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import cz.jh.journal.dao.GenericDao;
import cz.jh.journal.model.DBEntity;
import cz.jh.journal.util.ProcessingContext;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Implementation of Generic DAO
 *
 * @author jan.horky
 * @param <Entity>
 * @param <ID>
 */
public abstract class GenericDaoImpl<Entity extends DBEntity, ID extends Serializable> extends ProcessingContext implements GenericDao<Entity, ID> {

    @PersistenceContext(unitName = "journalPU")
    protected EntityManager em;

    private final Class<Entity> entityType;

    protected GenericDaoImpl() {
        this.entityType = null;
    }

    protected GenericDaoImpl(Class<Entity> entityType) {
        this.entityType = entityType;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class<Entity> getEntityType() {
        return entityType;
    }

    @Override
    public Entity save(Entity e) {
        internalPersist(e);
        return e;
    }

    private void internalPersist(Entity e) {
        // update entity from context
        e.setCreatedBy((Long) context.get(LOGGED_USER_ID_KEY));

        getEm().persist(e);
    }

    private DBEntity internalSaveOrUpdate(DBEntity e) {
        // update entity from context
        e.setUpdatedBy((Long) context.get(LOGGED_USER_ID_KEY));

        if (getEm().contains(e)) {
            return e;
        }
        if (e.getId() == null) {
            getEm().persist(e);
            return e;
        }
        Entity saved = getEm().find(getEntityType(), e.getId());
        if (saved == null) {
            getEm().persist(e);
            return e;
        } else {
            return getEm().merge(e);
        }
    }

    @Override
    public Entity saveOrUpdate(Entity e) {
        return (Entity) internalSaveOrUpdate(e);
    }

    @Override
    public Entity saveAndFlush(Entity e) {
        internalPersist(e);
        getEm().flush();
        return e;
    }

    @Override
    public List<Entity> saveAll(Iterable<Entity> itrbl) {
        for (Entity entity : itrbl) {
            if (entity != null) {
                internalPersist(entity);
            }
        }
        return Lists.newArrayList(itrbl);
    }

    @Override
    public Entity findOne(ID id) {
        return getEm().find(getEntityType(), id);
    }

    @Override
    public List<Entity> findAll() {
        return getEm().createQuery("select e from " + getEntityType().getName() + " e").getResultList();
    }

    @Override
    public Entity findOne(String entityPrefix,
            List<String> conditions,
            Map<String, ? extends Object> parameters) {
        return findOne(entityPrefix, conditions, parameters, null);
    }

    @Override
    public Entity findOne(String entityPrefix,
            List<String> conditions,
            Map<String, ? extends Object> parameters, List<String> joins) {
        if (Strings.isNullOrEmpty(entityPrefix)) {
            entityPrefix = "e";
        }
        StringBuilder query = new StringBuilder();
        query.append("select ");
        query.append(entityPrefix);
        query.append(" from ");
        query.append(getEntityType().getName());
        query.append(" ");
        query.append(entityPrefix);
        query.append(" ");

        if (joins != null) {
            query.append(Joiner.on(" ").skipNulls().join(joins));
        }

        if (conditions != null && !conditions.isEmpty()) {
            query.append(" where ");
            query.append(Joiner.on(" and ").skipNulls().join(conditions));
        }

        Query jpaQuery = getEm().createQuery(query.toString());

        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, ? extends Object> e : parameters.entrySet()) {
                jpaQuery.setParameter(e.getKey(), e.getValue());
            }
        }
        final List resultList = jpaQuery.getResultList();
        return singleResult(resultList, "{0} results returned for query: " + query.toString());
    }

    /**
     * returns null if list is empty, its single item if it contains only one item and throws NonUniqueResultException if it contains multiple. Message of
     * exception is formed from exceptionFormatString using MessageFormat and pasing list size as its first ({0}) parameter.
     *
     * @param resultList
     * @param exceptionFormatString
     * @return
     */
    protected Entity singleResult(List resultList,
            String exceptionFormatString) {
        if (resultList.isEmpty()) {
            return null;
        } else if (resultList.size() > 1) {
            throw new NonUniqueResultException(MessageFormat.format(exceptionFormatString, resultList.size()));
        }
        return (Entity) resultList.get(0);
    }

    protected <R> R singleResult(List resultList,
            String exceptionFormatString, Class<R> clazz) {
        if (resultList.isEmpty()) {
            return null;
        } else if (resultList.size() > 1) {
            throw new NonUniqueResultException(MessageFormat.format(exceptionFormatString, resultList.size()));
        }
        return (R) resultList.get(0);
    }

    @Override
    public List<Entity> findAll(String entityPrefix,
            List<String> conditions,
            Map<String, Object> parameters) {
        return this.findAllInternal(-1, -1, null, null, entityPrefix, null, conditions, parameters);
    }

    @Override
    public List<Entity> findAll(String entityPrefix,
            List<String> joins,
            List<String> conditions,
            Map<String, Object> parameters) {
        return this.findAllInternal(-1, -1, null, null, entityPrefix, joins, conditions, parameters);
    }

    @Override
    public List<Entity> findAll(int first,
            int pageSize,
            String sortField,
            Boolean ascending,
            String entityPrefix,
            List<String> conditions,
            Map<String, Object> parameters) {
        return this.findAllInternal(first, pageSize, sortField, ascending, entityPrefix, null, conditions, parameters);
    }

    @Override
    public List<Entity> findAll(int first,
            int pageSize,
            String sortField,
            Boolean ascending,
            String entityPrefix,
            List<String> joins,
            List<String> conditions,
            Map<String, Object> parameters) {
        return this.findAllInternal(first, pageSize, sortField, ascending, entityPrefix, joins, conditions, parameters);
    }

    private List<Entity> findAllInternal(int first,
            int pageSize,
            String sortField,
            Boolean ascending,
            String entityPrefix,
            List<String> joins,
            List<String> conditions,
            Map<String, Object> parameters) {
        if (Strings.isNullOrEmpty(entityPrefix)) {
            entityPrefix = "e";
        }
        StringBuilder query = new StringBuilder();
        query.append("select ");
        query.append(entityPrefix);
        query.append(" from ");
        query.append(getEntityType().getName());
        query.append(" ");
        query.append(entityPrefix);
        query.append(" ");
        if (joins != null) {
            query.append(Joiner.on(" ").skipNulls().join(joins));
        }
        if (conditions != null && !conditions.isEmpty()) {
            query.append(" where ");
            query.append(Joiner.on(" and ").skipNulls().join(conditions));
        }
        boolean addSortById = false;
        if (!"id".equals(sortField)) {
            if (Strings.isNullOrEmpty(sortField)) {
                sortField = "id";
            } else {
                addSortById = true;
            }
        }
        if (ascending == null) {
            ascending = Boolean.TRUE;
        }
        if (!Strings.isNullOrEmpty(sortField)) {
            query.append(" order by ");
            addOrder(query, entityPrefix, sortField, ascending);
            if (addSortById) {
                query.append(", ");
                addOrder(query, entityPrefix, "id", ascending);
            }
        }

        Query jpaQuery = getEm().createQuery(query.toString());

        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, Object> e : parameters.entrySet()) {
                jpaQuery.setParameter(e.getKey(), e.getValue());
            }
        }
        if (first < 0 || pageSize < 0) {
            return jpaQuery.getResultList();
        } else {
            return jpaQuery.setFirstResult(first).setMaxResults(pageSize).getResultList();
        }
    }

    private void addOrder(StringBuilder query,
            String entityPrefix,
            String sortField,
            Boolean ascending) {
        query.append(entityPrefix);
        query.append(".");
        query.append(sortField);
        if (ascending != null) {
            if (ascending) {
                query.append(" asc");
            } else {
                query.append(" desc");
            }
        }
    }

    @Override
    public void delete(ID id) {
        getEm().remove(getEm().getReference(getEntityType(), id));
    }

    @Override
    public void delete(Iterable<Entity> itrbl) {
        for (Entity entity : itrbl) {
            getEm().remove(getEm().getReference(getEntityType(), entity.getId()));
        }
    }

    @Override
    public long count() {
        return (Long) getEm().createQuery("select count(e) from " + getEntityType().getName() + " e").getSingleResult();
    }

    @Override
    public long countFiltered(String entityPrefix,
            List<String> andCondition,
            Map<String, Object> parameters) {
        if (Strings.isNullOrEmpty(entityPrefix)) {
            entityPrefix = "e";
        }
        StringBuilder query = new StringBuilder();
        query.append("select count(");
        query.append(entityPrefix);
        query.append(") from ");
        query.append(getEntityType().getName());
        query.append(" ");
        query.append(entityPrefix);
        query.append(" ");
        if (andCondition != null && !andCondition.isEmpty()) {
            query.append(" where ");
            query.append(Joiner.on(" and ").skipNulls().join(andCondition));
        }

        Query jpaQuery = getEm().createQuery(query.toString());

        if (parameters != null && !parameters.isEmpty()) {
            for (Map.Entry<String, Object> e : parameters.entrySet()) {
                jpaQuery.setParameter(e.getKey(), e.getValue());
            }
        }

        return (Long) jpaQuery.getSingleResult();

    }

    @Override
    public boolean exists(ID id) {
        if (id == null) {
            return false;
        }
        Query query = getEm().createQuery("select count(e.id) from " + getEntityType().getName() + " e where e.id = :id");
        query.setParameter("id", id);
        return ((Long) query.getSingleResult()) == 1;
    }

    @Override
    public void flush() {
        getEm().flush();
    }

    @Override
    public Entity findBy(String name, Object value) {
        List<String> conditions = new ArrayList<>();
        ImmutableMap<String, Object> parameters;
        if (value == null) {
            conditions.add("e." + name + " is null");
            parameters = ImmutableMap.of();
        } else {
            conditions.add("e." + name + " = :value");
            parameters = ImmutableMap.of("value", value);

        }
        return findOne("e", conditions, parameters);
    }

    @Override
    public void detach(DBEntity e) {
        em.detach(e);
    }

}

package cz.jh.journal.dao;

import cz.jh.journal.model.DBEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 * Generic DAO interface with common method for handling persistency.
 *
 * @author jan.horky
 * @param <Entity>
 * @param <ID>
 */
@Local
public interface GenericDao<Entity extends DBEntity, ID extends Serializable> {

    /**
     * Save Entity
     *
     * @param e entity to save
     * @return Saved Entity
     */
    Entity save(Entity e);

    /**
     * Save or Update Entity
     *
     * @param e entity to save
     * @return Saved Entity
     */
    Entity saveOrUpdate(Entity e);

    /**
     * Save and Flush Entity
     *
     * @param e entity to save
     * @return Saved Entity
     */
    Entity saveAndFlush(Entity e);

    /**
     * Save All Entities
     *
     * @param itrbl collection of entities to save
     * @return List of saved entities
     */
    List<Entity> saveAll(Iterable<Entity> itrbl);

    /**
     * Find One Entity by Primary Key
     *
     * @param id
     * @return Found Entity
     */
    Entity findOne(ID id);

    /**
     * Generic method for smart finding
     *
     * @param entityPrefix - entity abbreviation used in query - nullable (default is 'e')
     * @param andCondition - list of condition which will be concated by AND (use entityPrefix in condition, e.g. e.field = :fieldValue)- nullable
     * @param parameters - parameters which will be set to query and replace placeholders in conditions (e.g. ("fieldValue", 5)) - nullable
     * @return entities selected from database based on given parameters
     */
    Entity findOne(String entityPrefix, List<String> andCondition, Map<String, ? extends Object> parameters);

    Entity findOne(String entityPrefix, List<String> andCondition, Map<String, ? extends Object> parameters, List<String> joins);

    List<Entity> findAll(String entityPrefix, List<String> andCondition, Map<String, Object> parameters);

    List<Entity> findAll(String entityPrefix, List<String> joins, List<String> andCondition, Map<String, Object> parameters);

    List<Entity> findAll(int first, int pageSize, String sortField, Boolean ascending, String entityPrefix, List<String> andCondition,
            Map<String, Object> parameters);

    /**
     * Generic method for smart filtering
     *
     * @param first - offset of select
     * @param pageSize - limit of select
     * @param sortField - order by field (note sortField is entity property) - nullable (default is database default)
     * @param ascending - is sorting ascending - nullable (default is database default)
     * @param entityPrefix - entity abbreviation used in query - nullable (default is 'e')
     * @param joins - list of joins in JPA format 'join' objPrefix.objPropertyToRelatedObject prefixOfJoinedObject (e.g. join e.related r)
     * @param andCondition - list of condition which will be concated by AND (use entityPrefix in condition, e.g. e.field = :fieldValue)- nullable
     * @param parameters - parameters which will be set to query and replace placeholders in conditions (e.g. ("fieldValue", 5)) - nullable
     * @return entities selected from database based on given parameters
     */
    List<Entity> findAll(int first, int pageSize, String sortField, Boolean ascending, String entityPrefix, List<String> joins, List<String> andCondition,
            Map<String, Object> parameters);

    /**
     * Find All Entities - be always sure you have small data (use rather findAll with pageSize)
     *
     * @return Found Entities
     */
    @Deprecated
    List<Entity> findAll();

    /**
     * Delete Entity
     *
     * @param e entity for delete
     */
    void delete(ID id);

    /**
     * delete iteration of Entities
     *
     * @param itrbl entitie for delete
     */
    void delete(Iterable<Entity> itrbl);

    /**
     * Get count of all Entities
     *
     * @return count of all Entities
     */
    long count();

    /**
     * Get count of Entities in filter
     *
     * @param entityPrefix
     * @param andCondition
     * @param parameters
     * @return count of filtered Entities
     */
    long countFiltered(String entityPrefix, List<String> andCondition, Map<String, Object> parameters);

    /**
     * Exists Entity by Key
     *
     * @param id
     * @return true if entity exists
     */
    boolean exists(ID id);

    /**
     * flush data to database
     */
    void flush();

    /**
     * Find at most one entity with property name equal to value
     *
     * @param name
     * @param value
     * @param valid
     * @return the entity
     */
    Entity findBy(String name, Object value);

    /**
     * Remove the given entity from the persistence context, causing a managed entity to become detached. Unflushed changes made to the entity if any (including
     * removal of the entity), will not be synchronized to the database. Entities which previously referenced the detached entity will continue to reference it.
     *
     * @param e entity instance
     */
    void detach(DBEntity e);
}

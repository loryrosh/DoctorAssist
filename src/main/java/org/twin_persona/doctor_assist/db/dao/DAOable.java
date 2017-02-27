/**
 * @author http://twin-persona.org
 *
 * Interface to work with DB entities.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.doctor_assist.db.dao;

import org.twin_persona.doctor_assist.db.models.BaseEntity;

import javax.persistence.PersistenceException;

public interface DAOable<T extends BaseEntity>
{
    /**
     * Adds object.
     *
     * @param obj Object to add
     * @throws PersistenceException
    */
    T add( T obj ) throws PersistenceException;

    /**
     * Updates object data.
     *
     * @param obj Object to update
     * @throws PersistenceException
    */
    void update( T obj ) throws PersistenceException;

    /**
     * Gets object by its id.
     *
     * @param id object id
     * @param objClass Object class
     * @throws PersistenceException
    */
    T getById( int id, Class objClass ) throws PersistenceException;

    /**
     * Removes object.
     *
     * @param obj Obj to delete
     * @param objClass Object class
     * @throws PersistenceException
    */
    void delete( T obj, Class objClass ) throws PersistenceException;
}

/**
 * @author http://twin-persona.org
 *
 * Base class to implement DAOable interface.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.doctor_assist.db.dao.impl;

import org.twin_persona.doctor_assist.db.dao.DAOable;
import org.twin_persona.doctor_assist.db.models.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

public class BaseEntityDAO<T extends BaseEntity> implements DAOable<T>
{
    @PersistenceContext
    private EntityManager entityManager = null;

    BaseEntityDAO( EntityManagerFactory factory )
    {
        if( entityManager == null )
            entityManager = factory.createEntityManager();
    }

    EntityManager getEntityManager()
    {
        return entityManager;
    }

    /**
     * Closes the session with the EntityManager.
    */
    public void close()
    {
        entityManager.close();
        entityManager = null;
    }

    public T add( T object )
    {
        EntityManager entityManager = getEntityManager();
        try
        {
            // starting the transaction
            entityManager.getTransaction().begin();

            // adding new object to the corresponding table
            T addedObj = entityManager.merge( object );

            // committing the transaction
            entityManager.getTransaction().commit();

            return addedObj;
        } catch( PersistenceException ex )
        {
            if( entityManager.getTransaction().isActive() )
                entityManager.getTransaction().rollback();
        }
        return null;
    }

    public void update( T object )
    {
        EntityManager entityManager = getEntityManager();
        try
        {
            // starting the transaction
            entityManager.getTransaction().begin();

            // updating object in the corresponding table
            entityManager.merge( object );

            // committing the transaction
            entityManager.getTransaction().commit();
        } catch( PersistenceException ex )
        {
            if( entityManager.getTransaction().isActive() )
                entityManager.getTransaction().rollback();
        }
    }

    public T getById( int id, Class objClass )
    {
        return ( T ) getEntityManager().find( objClass, id );
    }

    public void delete( T obj, Class objClass )
    {
        EntityManager entityManager = getEntityManager();
        try
        {
            // starting the transaction
            entityManager.getTransaction().begin();

            // removing the object
            entityManager.remove( getById( obj.getId(), objClass ) );

            // committing the transaction
            entityManager.getTransaction().commit();
        } catch( PersistenceException ex )
        {
            if( entityManager.getTransaction().isActive() )
                entityManager.getTransaction().rollback();
        }
    }
}

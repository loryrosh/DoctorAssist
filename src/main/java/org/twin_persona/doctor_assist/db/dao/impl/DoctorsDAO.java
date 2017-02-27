/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "doctors" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db.dao.impl;

import org.twin_persona.doctor_assist.db.dao.DAOable;
import org.twin_persona.doctor_assist.db.models.Doctor;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class DoctorsDAO extends BaseEntityDAO<Doctor> implements DAOable<Doctor>
{
    public DoctorsDAO( EntityManagerFactory factory ) {
        super( factory );
    }

    /**
     * Gets all doctors.
     *
     * @return List of all doctors.
     */
    public Optional<List<Doctor>> getAll() throws PersistenceException
    {
        TypedQuery<Doctor> namedQuery = getEntityManager()
                .createNamedQuery( "Doctors.getAll", Doctor.class );

        try
        {
            return Optional.of( namedQuery.getResultList() );
        } catch( Exception ex ){}
        return Optional.empty();
    }

    /**
     * Gets doctor by name and surname
     *
     * @param name doctor name
     * @param surname doctor surname
     * @return doctor
     */
    public Optional<Doctor> getByInfo( String name, String surname ) throws PersistenceException
    {
        TypedQuery<Doctor> namedQuery = getEntityManager()
                .createNamedQuery( "Doctors.getByInfo", Doctor.class );
        namedQuery.setParameter( "name", name );
        namedQuery.setParameter( "surname", surname );

        try
        {
            return Optional.of( namedQuery.getSingleResult() );
        } catch( Exception ex ){}
        return Optional.empty();
    }
}

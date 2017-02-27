/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "patients" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db.dao.impl;

import org.twin_persona.doctor_assist.db.dao.DAOable;
import org.twin_persona.doctor_assist.db.models.Patient;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class PatientsDAO extends BaseEntityDAO<Patient> implements DAOable<Patient>
{
    public PatientsDAO( EntityManagerFactory factory ) {
        super( factory );
    }

    /**
     * Gets patient by email
     *
     * @param email patient email
     * @return patient
     */
    public Optional<Patient> getByEmail( String email ) throws PersistenceException
    {
        TypedQuery<Patient> namedQuery = getEntityManager()
                .createNamedQuery( "Patients.getByEmail", Patient.class );
        namedQuery.setParameter( "email", email );

        try
        {
            return Optional.of( namedQuery.getSingleResult() );
        } catch( Exception ex ){}
        return Optional.empty();
    }
}

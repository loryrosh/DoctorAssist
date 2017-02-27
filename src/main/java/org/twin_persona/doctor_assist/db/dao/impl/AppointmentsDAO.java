/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "appointments" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db.dao.impl;

import org.twin_persona.doctor_assist.db.dao.DAOable;
import org.twin_persona.doctor_assist.db.models.Appointment;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.Optional;

public class AppointmentsDAO extends BaseEntityDAO<Appointment> implements DAOable<Appointment>
{
    public AppointmentsDAO( EntityManagerFactory factory ) {
        super( factory );
    }

    /**
     * Checks whether certain appointment exists
     *
     * @param timetableId timetableId id
     * @return appointment
     */
    public boolean timeIsFree( int timetableId ) throws PersistenceException
    {
        TypedQuery<Appointment> namedQuery = getEntityManager()
                .createNamedQuery( "Appointments.exists", Appointment.class );
        namedQuery.setParameter( "timetable_id", timetableId );

        try
        {
            if( Optional.of( namedQuery.getSingleResult() ).isPresent() )
                return false;
        } catch( Exception ex ){}
        return true;
    }
}

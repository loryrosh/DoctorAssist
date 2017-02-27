/**
 * @author http://twin-persona.org
 *
 * Class to implement DAOable interface for the "timetables" table.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db.dao.impl;

import org.twin_persona.doctor_assist.db.dao.DAOable;
import org.twin_persona.doctor_assist.db.models.Timetable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TimetablesDAO extends BaseEntityDAO<Timetable> implements DAOable<Timetable>
{
    public TimetablesDAO( EntityManagerFactory factory ) {
        super( factory );
    }

    /**
     * Gets timetable by doctor id
     *
     * @param id doctor id
     * @return timetable
     */
    public Optional<List<Timetable>> getByDoctorId( int id ) throws PersistenceException
    {
        TypedQuery<Timetable> namedQuery = getEntityManager()
                .createNamedQuery( "Timetables.getByDoctorId", Timetable.class );
        namedQuery.setParameter( "id", id );

        try
        {
            Optional<List<Timetable>> res = Optional.of( namedQuery.getResultList() );
            if( res.isPresent() )
                return res;
        } catch( Exception ex ){}
        return Optional.empty();
    }
}

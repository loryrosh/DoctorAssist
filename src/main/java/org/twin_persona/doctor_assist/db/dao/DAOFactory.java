/**
 * @author http://twin-persona.org
 *
 * Creates DAO models for DB entities.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.doctor_assist.db.dao;

import org.twin_persona.doctor_assist.db.dao.impl.*;
import org.twin_persona.doctor_assist.db.models.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import java.util.Optional;

public class DAOFactory
{
    private static final DAOFactory _instance = new DAOFactory();

    @PersistenceUnit
    private static EntityManagerFactory factory = null;

    private DAOFactory(){}

    /**
     * Provides reference to DAOFactory.
     *
     * @return Instance of DAOFactory.
     */
    public static DAOFactory getInstance() throws PersistenceException
    {
        if( factory == null )
        {
            factory = Persistence.createEntityManagerFactory( "doctor" );
            int i = 0;
        }
        return _instance;
    }

    /**
     * Gets the necessary DAO model.
     *
     * @param entityType    class of the entity
     * @return Ref to the specific BaseEntityDAO.
     */
    public Optional<BaseEntityDAO> getModel( Class entityType )
    {
        if( entityType.equals( Doctor.class ) )
            return factory != null ? Optional.of( new DoctorsDAO( factory ) ) : Optional.empty();
        if( entityType.equals( Patient.class ) )
            return factory != null ? Optional.of( new PatientsDAO( factory ) ) : Optional.empty();
        if( entityType.equals( Timetable.class ) )
            return factory != null ? Optional.of( new TimetablesDAO( factory ) ) : Optional.empty();
        if( entityType.equals( Appointment.class ) )
            return factory != null ? Optional.of( new AppointmentsDAO( factory ) ) : Optional.empty();

        return Optional.empty();
    }

    /**
     * Closes DB connection.
     */
    public static void closeConnection()
    {
        if( factory != null )
            factory.close();
    }
}

/**
 * @author http://twin-persona.org
 *
 * Manages all DAO resources.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db;

import org.twin_persona.doctor_assist.db.dao.impl.AppointmentsDAO;
import org.twin_persona.doctor_assist.db.dao.impl.DoctorsDAO;
import org.twin_persona.doctor_assist.db.dao.impl.PatientsDAO;
import org.twin_persona.doctor_assist.db.dao.impl.TimetablesDAO;
import org.twin_persona.doctor_assist.db.models.Appointment;
import org.twin_persona.doctor_assist.db.models.Doctor;
import org.twin_persona.doctor_assist.db.models.Patient;
import org.twin_persona.doctor_assist.db.models.Timetable;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ResourcesManager
{
    private static PatientsDAO _patientsDAO = null;
    private static DoctorsDAO _doctorsDAO = null;
    private static TimetablesDAO _timetablesDAO = null;
    private static AppointmentsDAO _appointmentsDAO = null;

    private static DBController _dbController = null;

    public ResourcesManager() {}

    public static void setDbController( DBController dbController )
    {
        _dbController = dbController;
    }

    public static void init()
    {
        if( _dbController != null )
        {
            try
            {
                _patientsDAO = ( PatientsDAO ) _dbController.getModelFactory().getModel( Patient.class ).orElse( null );
                _doctorsDAO = ( DoctorsDAO ) _dbController.getModelFactory().getModel( Doctor.class ).orElse( null );
                _timetablesDAO =
                    ( TimetablesDAO ) _dbController.getModelFactory().getModel( Timetable.class ).orElse( null );
                _appointmentsDAO =
                    ( AppointmentsDAO ) _dbController.getModelFactory().getModel( Appointment.class ).orElse( null );
            }
            catch( Exception ex ){}
        }
    }

    public static Optional<PatientsDAO> getPatientsDAO() { return Optional.of( _patientsDAO ); }
    public static Optional<TimetablesDAO> getTimetablesDAO() { return Optional.of( _timetablesDAO ); }
    public static Optional<AppointmentsDAO> getAppointmentsDAO() { return Optional.of( _appointmentsDAO ); }

    public static Optional<DoctorsDAO> getDoctorsDAO() { return Optional.of( _doctorsDAO ); }
    public static Optional<List<Doctor>> getDoctors()
    {
        Optional<DoctorsDAO> doctorsDAO = ResourcesManager.getDoctorsDAO();

        Optional<List<Doctor>> doctors = Optional.empty();
        if( doctorsDAO.isPresent() )
            doctors = doctorsDAO.get().getAll();
        return doctors;
    }

    public static String getDate()
    {
        LocalDate date = LocalDate.now();
        return date.getDayOfMonth() + " " +
                date.getMonth().getDisplayName( TextStyle.SHORT, Locale.UK ) + ", " + date.getYear();
    }

    public static void closeResources()
    {
        if( _patientsDAO != null )
            _patientsDAO.close();

        if( _doctorsDAO != null )
            _doctorsDAO.close();

        if( _timetablesDAO != null )
            _timetablesDAO.close();

        if( _appointmentsDAO != null )
            _appointmentsDAO.close();
    }
}

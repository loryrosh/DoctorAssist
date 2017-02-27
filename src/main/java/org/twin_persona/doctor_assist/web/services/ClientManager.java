/**
 * @author http://twin-persona.org
 *
 * Class to process client requests.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.web.services;

import com.google.gson.Gson;
import org.twin_persona.doctor_assist.db.ResourcesManager;
import org.twin_persona.doctor_assist.db.dao.impl.AppointmentsDAO;
import org.twin_persona.doctor_assist.db.dao.impl.DoctorsDAO;
import org.twin_persona.doctor_assist.db.dao.impl.PatientsDAO;
import org.twin_persona.doctor_assist.db.dao.impl.TimetablesDAO;
import org.twin_persona.doctor_assist.db.models.Appointment;
import org.twin_persona.doctor_assist.db.models.Doctor;
import org.twin_persona.doctor_assist.db.models.Patient;
import org.twin_persona.doctor_assist.db.models.Timetable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientManager
{
    private Patient _curPatient = null;

    public Optional<Patient> getCurPatient()
    {
        return Optional.of( _curPatient );
    }

    public void setCurPatient( String email )
    {
        Optional<PatientsDAO> patientsDAO = ResourcesManager.getPatientsDAO();
        if( patientsDAO.isPresent() )
        {
            Optional<Patient> patient = patientsDAO.get().getByEmail( email );
            if( patient.isPresent() )
                this._curPatient = patient.get();
        }
    }

    /**
     * Gets timetable by doctor name/surname.
     *
     * @param doctorInfo doctor name/surname
     * @return json string with timetables
     */
    public String getTimetablesByDoctor( String doctorInfo )
    {
        // splitting doctor info into name and surname
        String[] info = doctorInfo.split( " " );
        if( info.length == 2 )
        {
            Optional<DoctorsDAO> doctorsDAO = ResourcesManager.getDoctorsDAO();
            if( doctorsDAO.isPresent() )
            {
                // getting doctor obj from given name/surname
                Optional<Doctor> doctor = doctorsDAO.get().getByInfo( info[ 0 ], info[ 1 ] );
                if( doctor.isPresent() )
                {
                    Optional<TimetablesDAO> timetablesDAO = ResourcesManager.getTimetablesDAO();
                    if( timetablesDAO.isPresent() )
                    {
                        // getting required timetable
                        Optional<List<Timetable>> timetables = timetablesDAO.get().getByDoctorId( doctor.get().getId() );
                        if( timetables.isPresent() )
                            return chooseFreeTime( timetables.get() );
                    }
                }
            }
        }
        return "";
    }

    /**
     * Sets a given available doctor's timetable time for a given user.
     *
     * @param appoint_num specific doctor's timetable
     * @param userEmail current user's email
     * @return success string
     */
    public String makeAppointment( String appoint_num, Object userEmail )
    {
        Optional<AppointmentsDAO> appointmentsDAO = ResourcesManager.getAppointmentsDAO();
        if( appointmentsDAO.isPresent() )
        {
            Optional<TimetablesDAO> timetablesDAO = ResourcesManager.getTimetablesDAO();
            if( timetablesDAO.isPresent() )
            {
                Timetable curTimetable = timetablesDAO.get()
                        .getById( Integer.parseInt( appoint_num ), Timetable.class );

                Optional<Patient> curPatient = getCurPatient();
                if( curPatient.isPresent() )
                {
                    // adding new appointment
                    appointmentsDAO.get().add( new Appointment( curPatient.get(), curTimetable ) );
                    return "true";
                }
            }
        }
        return "false";
    }

/******************************************** private **********************************************/

    /**
     * Filters out free timetables
     *
     * @param timetables list of all timetables
     * @return json with resulting timetables
     */
    private String chooseFreeTime( List<Timetable> timetables )
    {
        Optional<AppointmentsDAO> appointmentsDAO = ResourcesManager.getAppointmentsDAO();
        if( appointmentsDAO.isPresent() )
        {
            // filter function
            List<Timetable> res = timetables.stream()
                    .filter( timetable -> appointmentsDAO.get().timeIsFree( timetable.getId() ) )
                    .collect( Collectors.toList() );
            return new Gson().toJson( res );
        }
        return "";
    }
}

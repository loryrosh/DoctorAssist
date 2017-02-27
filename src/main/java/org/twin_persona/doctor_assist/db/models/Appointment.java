/**
 * @author http://twin-persona.org
 *
 * Class for Appointment JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db.models;

import javax.persistence.*;

@Entity
@Table( name = "appointments" )
@NamedQueries
({
    @NamedQuery( name = "Appointments.exists",
            query = "SELECT a from Appointment a where a.doctorTimetable.id = :timetable_id" )
})
public class Appointment extends BaseEntity
{
    @OneToOne
    @JoinColumn( name = "patient_id" )
    private Patient patient;

    @OneToOne
    @JoinColumn( name = "timetable_id" )
    private Timetable doctorTimetable;

    public Appointment(){}

    public Appointment( Patient patient, Timetable doctorTimetable )
    {
        this.patient = patient;
        this.doctorTimetable = doctorTimetable;
    }

    public Patient getPatient() { return patient; }
    public void setPatient( Patient patient ) { this.patient = patient; }

    public Timetable getDoctorTimetable() { return doctorTimetable; }
    public void setDoctorTimetable( Timetable doctorTimetable )
    {
        this.doctorTimetable = doctorTimetable;
    }
}

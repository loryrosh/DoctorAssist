/**
 * @author http://twin-persona.org
 *
 * Class for Timetable JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db.models;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table( name = "timetables" )
@NamedQueries
({
    @NamedQuery( name = "Timetables.getByDoctorId",
            query = "SELECT t from Timetable t where t.doctor.id = :id" ),
})
public class Timetable extends BaseEntity
{
    @Basic
    @Column
    private LocalTime time;

    @ManyToOne
    @JoinColumn( name = "doctor_id" )
    private Doctor doctor;

    public Timetable(){};

    public Timetable( LocalTime time, Doctor doctor )
    {
        this.time = time;
        this.doctor = doctor;
    }

    public LocalTime getTime() { return time; }
    public void setTime( LocalTime time ) { this.time = time; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor( Doctor doctor ) { this.doctor = doctor; }
}

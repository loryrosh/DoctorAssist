/**
 * @author http://twin-persona.org
 *
 * Class for Doctor JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db.models;

import javax.persistence.*;

@Entity
@Table( name = "doctors" )
@NamedQueries
({
    @NamedQuery( name = "Doctors.getAll", query = "SELECT d from Doctor d" ),
    @NamedQuery( name = "Doctors.getByInfo",
            query = "SELECT d from Doctor d where d.name = :name and d.surname = :surname" )
})
public class Doctor extends BaseEntity
{
    @Basic
    @Column
    private String name;

    @Basic
    @Column
    private String surname;

    @Basic
    @Column
    private String profile;

    public Doctor(){}

    public Doctor( String name, String surname, String profile )
    {
        this.name = name;
        this.surname = surname;
        this.profile = profile;
    }

    public String getName(){ return name; }
    public void setName( String name ){ this.name = name; }

    public String getSurname(){ return surname; }
    public void setSurname( String surname ){ this.surname = surname; }

    public String getInfo()
    {
        return name + " " + surname;
    }

    public String getProfile() { return profile; }
    public void setProfile( String profile ) { this.profile = profile; }
}

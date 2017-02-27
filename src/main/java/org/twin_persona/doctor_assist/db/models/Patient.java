/**
 * @author http://twin-persona.org
 *
 * Class for Patient JPA entity.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.doctor_assist.db.models;

import javax.persistence.*;

@Entity
@Table( name = "patients" )
@NamedQueries
({
    @NamedQuery( name = "Patients.getByEmail",
            query = "SELECT p from Patient p where p.email = :email" )
})
public class Patient extends BaseEntity
{
    @Basic
    @Column
    private String email;

    @Basic
    @Column
    private String password;

    public Patient(){}

    public Patient( String email, String password )
    {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public void setEmail( String email ) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword( String password ) { this.password = password; }
}

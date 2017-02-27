/**
 * @author http://twin-persona.org
 *
 * Superclass for all JPA-entities.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    public int getId() { return id;}
    public void setId( int id )
    {
        this.id = id;
    }
}

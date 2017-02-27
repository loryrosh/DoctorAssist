/**
 * @author http://twin-persona.org
 *
 * Interface for classes that can be saved into the database.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.db;

public interface Storable
{
    /*
     * Opens connection to the storage
     *
     * @return	flag of success
     */
    boolean openConn();

    /*
     * Checks whether the connection is established
     *
     * @return	flag of success
     */
    boolean isConnected();

    /*
     * Closes connection to the storage
     */
    void closeConn();
}

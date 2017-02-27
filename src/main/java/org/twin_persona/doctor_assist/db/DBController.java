/**
 * @author http://twin-persona.org
 *
 * Class to implement the database controller.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.doctor_assist.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.twin_persona.doctor_assist.db.dao.DAOFactory;

import javax.persistence.PersistenceException;

public class DBController implements Storable
{
    private final static Logger logger = LoggerFactory.getLogger( DBController.class );
    private boolean _connected = false;

    public DBController(){}

    public boolean openConn()
    {
        try
        {
            DAOFactory.getInstance();
            _connected = true;
        } catch( Exception ex ) { logger.warn( ex.getMessage() ); return false; }
        return true;
    }

    public DAOFactory getModelFactory() throws PersistenceException
    {
        return DAOFactory.getInstance();
    }

    public boolean isConnected()
    {
        return _connected;
    }

    public void closeConn()
    {
        DAOFactory.closeConnection();
        _connected = false;
    }
}

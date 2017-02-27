/**
 * @author http://twin-persona.org
 *
 * Web entry point of the DoctorAssist webapp.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
 */

package org.twin_persona.doctor_assist.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.twin_persona.doctor_assist.db.DBController;
import org.twin_persona.doctor_assist.db.ResourcesManager;
import org.twin_persona.doctor_assist.web.services.ClientManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet
(
    name = "frontController",
    urlPatterns = { "/service" }
)
public class FrontController extends HttpServlet
{
    private final static Logger logger = LoggerFactory.getLogger( FrontController.class );

    private final ClientManager _clientManager = new ClientManager();

    public FrontController(){}

    private static DBController _dbController = null;
    public static DBController getDbController() { return _dbController; }

    @Override
    public void init() throws ServletException
    {
        super.init();

        _dbController = new DBController();
        _dbController.openConn();

        if( _dbController.isConnected() )
        {
            ResourcesManager.setDbController( _dbController );
            ResourcesManager.init();
        }
    }

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        if( request.getParameter( "logout" ) != null )
        {
            session.removeAttribute( "isAuthorized" );
            session.removeAttribute( "email" );

            request.getRequestDispatcher( "/" ).forward( request, response );
        }
        else
        {
            _clientManager.setCurPatient( session.getAttribute( "email" ).toString() );
            request.getRequestDispatcher( "/WEB-INF/jsp/client.jsp" ).forward( request, response );
        }
    }

    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException
    {
        if( request.getParameter( "get_timetable" ) != null )
        {
            String res = _clientManager.getTimetablesByDoctor( request.getParameter( "doctor_info" ) );
            _writeResponse( res, response );
            return;
        }

        boolean processAppointment = getAppointmentParam( request.getParameterMap() );
        if( processAppointment )
        {
            HttpSession session = request.getSession();
            String resp = _clientManager.makeAppointment(
                    request.getParameter( "time_num" ), session.getAttribute( "email" ) );
            _writeResponse( resp, response );
        }
    }

    @Override
    public void destroy()
    {
        _dbController.closeConn();
        ResourcesManager.closeResources();
        super.destroy();
    }

/******************************************** private **********************************************/

    /**
     * Searches for "appointment setting" post param
     *
     * @param params map of all post params
     * @return success flag
     */
    private boolean getAppointmentParam( Map<String, String[]> params )
    {
        List res = params.entrySet().stream()
                .filter( val -> val.getKey().startsWith( "set_appoint_" ) )
                .collect( Collectors.toList() );
        return ( res.size() > 0 );
    }

    /**
     * Writes servlet response
     *
     * @param msg response message
     * @param response servlet response object
     */
    private void _writeResponse( String msg, HttpServletResponse response )
            throws IOException
    {
        response.setContentType( "text/plain;charset=UTF-8" );

        if( !msg.equals( "" ) )
            response.getWriter().write( msg );
        else
            response.getWriter().write( "false" );
    }
}

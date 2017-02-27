/**
 * @author http://twin-persona.org
 *
 * Filter to check access info.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

package org.twin_persona.doctor_assist.web.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.twin_persona.doctor_assist.db.ResourcesManager;
import org.twin_persona.doctor_assist.db.dao.impl.PatientsDAO;
import org.twin_persona.doctor_assist.db.models.Patient;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebFilter
(
    filterName = "authFilter",
    servletNames = "FrontController"
)
public class AuthFilter implements Filter
{
    private final static Logger logger = LoggerFactory.getLogger( AuthFilter.class );

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException {}

    @Override
    public final void doFilter( final ServletRequest req, final ServletResponse resp, final FilterChain chain )
    {
        HttpServletRequest request = ( HttpServletRequest ) req;
        HttpSession session = request.getSession();

        try
        {
            if( request.getParameter( "check_passwd" ) != null || request.getParameter( "register" ) != null )
            {
                String email = request.getParameter( "email" );
                String passwd = request.getParameter( "passwd" );

                if( email != null && passwd != null )
                {
                    Optional<PatientsDAO> patientsDAO = ResourcesManager.getPatientsDAO();
                    if( patientsDAO.isPresent() )
                    {
                        // comparing with the input password
                        if( request.getParameter( "check_passwd" ) != null )
                        {
                            Optional<Patient> patient = patientsDAO.get().getByEmail( email );
                            if( !patient.isPresent() ||
                                ( patient.isPresent() && !patient.get().getPassword().equals( passwd ) ) )
                            {
                                _writeError( resp );
                                return;
                            }
                        }
                        else
                        {
                            // adding new patient
                            Patient patient = new Patient( email, passwd );
                            patientsDAO.get().add( patient );
                        }

                        // setting session params
                        session.setAttribute( "isAuthorized", true );
                        session.setAttribute( "email", email );
                        chain.doFilter( req, resp );
                    }
                }
            }
            else if( session.getAttribute( "isAuthorized" ) != null
                    && session.getAttribute( "isAuthorized" ).equals( true ) )
                chain.doFilter( req, resp );
        } catch( final Exception ex )
        {
            logger.warn( ex.getMessage() );
            _writeError( resp );
        }
    }

    @Override
    public void destroy() {}

/******************************************** private **********************************************/

    private void _writeError( ServletResponse resp )
    {
        resp.setContentType( "text/plain" );

        try
        {
            resp.getWriter().write( "false" );
        } catch( IOException ex ){}
    }
}

/**
 * @author http://twin-persona.org
 *
 * JS functions to process forms.
 *
 * Licence:
 * GPL-3.0 (http://www.gnu.org/licenses/gpl-3.0.html)
*/

$( document ).ready( function()
{
    var statusField = $( '#status_message' );
    var contentField = $( '.content_result' );

    function getFormArray( elem, msgField )
    {
        // serializing all elements of the form
        var form_data = elem.closest( 'form' ).serializeArray();

        // adding button to the array of form fields
        form_data.push({ name: elem.attr( 'name' ) });

        if( has_empty_fields( form_data, msgField ) )
            return '';
        return form_data;
    }

    function has_empty_fields( data, msgField )
    {
        var res = false;
        $.each( data, function( i, field )
        {
            //alert( field.name + ' ' + field.value + ' ' + field.type );
            if ( field.value == '' && field.type != 'submit' )
            {
                show_alert( "Please fill in all the fields!", msgField, false );
                res = true;
                return false;
            }
        });
        return res;
    }

    function show_alert( msg, msgField, isSuccess )
    {
        if( isSuccess )
            msgField.removeClass( 'bg-danger' ).addClass( 'bg-success' );
        else
            msgField.removeClass( 'bg-success' ).addClass( 'bg-danger' );
        msgField.toggle( true ).text( msg );
    }

    function showResponse( resp, msgField )
    {
        if( resp == "false" )
            show_alert( "Unsuccessful attempt. Please try once again.", msgField, false );
        else
            show_alert( resp, msgField, true );
    }

    function hideMsgs()
    {
        statusField.toggle( false );
        contentField.toggle( false );
    }

    function prepareContentField()
    {
        contentField.toggle( true );

        var contentBody = contentField.find( 'tbody' );
        contentBody.empty();
        return contentBody;
    }

/************************************************* general settings *************************************************/

    // stop submit requests
    $( 'form, .table-striped' ).submit( function( e )
    {
        e.preventDefault();
    });

    $( 'button' ).click( function()
    {
        hideMsgs();
    });

    // hide error message when switching between tabs
    $( '.navbar-nav a, #navbar' ).click( function()
    {
        hideMsgs();
    });

    $.ajaxSetup(
    {
        url: "service",
        type: "POST"
    });

/************************************************* client forms *****************************************************/

    // authorize client
    $( '#check_passwd, #register' ).click( function()
    {
        var form_data = getFormArray( $( this ), statusField );
        if( form_data != '' )
        {
            $.ajax(
            {
                data: form_data,
                success: function ( resp )
                {
                    if( resp == "false" )
                        show_alert( "Incorrect password!", statusField, false );
                    else
                        location.href = "service";
                }
            });
        }
        else
            return false;
    });

    // show doctor's timetable for today
    $( '#get_timetable' ).click( function()
    {
        var form_data = getFormArray( $( this ), statusField );
        if( form_data != '' )
        {
            $.ajax(
            {
                data: form_data,
                dataType: 'json',
                success: function ( resp )
                {
                    if( resp == "false" )
                    {
                        showResponse( resp, statusField );
                        return false;
                    }
                    else if( resp == "" )
                    {
                        showResponse( "No available appointments found!", statusField );
                        return false;
                    }

                    var contentBody = prepareContentField();
                    $.each( resp, function( index, value )
                    {
                        // '00' is reduced to single '0' when transferred from the server
                        if( value.time.minute == '0' )
                            value.time.minute = '00';

                        var htmlContent =
                            "<tr><td>" + value.time.hour + ":" + value.time.minute +
                            "</td><td>" + value.doctor.name + " " + value.doctor.surname +
                            "</td><td>" + value.doctor.profile + "</td><td>" +
                            "<form method='post'>" +
                                "<input type='hidden' id='time_num' name='time_num' value='" + value.id + "'>" +
                                "<button type='submit' class='btn btn-success btn-xs' " +
                                    "id='set_appoint_" + value.id + "' name='set_appoint_" + value.id + "'>Set</button>" +
                            "</form></td></tr>";
                        contentBody.append( $( htmlContent ) );
                    });
                }
            });
        }
        else
            return false;
    });

    // set an appointment with the doctor
    $( '.table-striped' ).on( 'click', '.btn-success',  function()
    {
        var form_data = getFormArray( $( this ), statusField );
        if( form_data != '' )
        {
            $.ajax(
            {
                data: form_data,
                success: function ( resp )
                {
                    if( resp == "false" )
                        show_alert( "Error setting an appointment. Please try later", statusField, false );
                    else
                    {
                        show_alert( "An appointment was successfully set!", statusField, false );
                        $( this ).disable();
                    }
                }
            });
        }
        else
            return false;
    });
});

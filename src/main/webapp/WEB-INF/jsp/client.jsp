<%@ page import="org.twin_persona.doctor_assist.db.ResourcesManager" %>
<%@ page import="org.twin_persona.doctor_assist.db.models.Doctor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="templates/header.jsp" %>

<% Optional<List<Doctor>> doctors = ResourcesManager.getDoctors();%>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="../../index.jsp">DoctorAssist&trade; <span class="delimeter"></span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active">
                    <a href="">Your email: <strong><%= session.getAttribute( "email" ) %></strong></a>
                </li>
                <li><a href="service?logout=">&nbsp;>&nbsp;Logout</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="../../index.jsp">Manage appointments with your doctors online!</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="jumbotron">
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="find_doctor">
                <form method="post" action="service">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-xs-5">
                                <label for="doctor_info">Choose doctor:</label>
                                <select class="form-control" name="doctor_info" id="doctor_info">
                                    <option></option>
                                    <% if( doctors.isPresent() ){
                                        for( Doctor doctor : doctors.get() ) {
                                    %>
                                            <option><%= doctor.getInfo() %></option>
                                    <%  }
                                    }%>
                                </select><br/>
                                <button type="submit" class="btn btn-primary"
                                        name="get_timetable" id="get_timetable">Submit</button>
                            </div>
                        </div>
                    </div>
                </form>
                <div class="content_result">
                    <table class="table table-striped">
                        <caption>Appointments available for today (<%= ResourcesManager.getDate() %>):</caption>
                        <thead>
                        <tr>
                            <th>Time</th>
                            <th>Doctor</th>
                            <th>Profile</th>
                            <th>Appointment</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id="status_message" class="bg-danger"></div>
    </div>
</div>

<%@ include file="templates/footer.jsp" %>

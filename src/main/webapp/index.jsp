<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/jsp/templates/header.jsp" %>

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">DoctorAssist&trade; <span class="delimeter"></span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="">Log into application:</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="index.jsp">Manage appointments with your doctors online!</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="jumbotron">
        <div id="service_buttons">
            <button type="button" class="btn btn-info btn-lg btn-block role" data-toggle="modal" data-target="#loginWindow">Enter as a client</button>
            <div class="modal fade" id="loginWindow" tabindex="-1" role="dialog" aria-labelledby="loginWindowLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="loginWindowLabel">Please provide your credentials:</h4>
                        </div>
                        <div class="modal-body">
                            <form method="post" action="service" id="login" name="login">
                                <div class="form-group">
                                    <label for="email">Email:</label>
                                    <input type="email" class="form-control" name="email" id="email" value=""><br/>
                                    <label for="passwd">Password:</label>
                                    <input type="password" class="form-control" name="passwd" id="passwd" value=""><br/>
                                    <button type="submit" class="btn btn-primary" id="check_passwd" name="check_passwd">Log in</button>
                                    <button type="submit" class="btn btn-primary" id="register" name="register">Register</button>
                                </div>
                            </form>
                        </div>
                        <br/>
                        <div id="status_message" class="bg-danger"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="WEB-INF/jsp/templates/footer.jsp" %>

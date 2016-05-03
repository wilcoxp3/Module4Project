<%-- 
    Document   : users
    Created on : Mar 30, 2016, 7:29:06 PM
    Author     : Paul
--%>

<%@page import="wilcoxp3.DataAccessObjectFactory"%>
<%@page import="wilcoxp3.DataAccessObject"%>
<%@page import="wilcoxp3.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User manager</title>
        <%
            DataAccessObject<User> userDao = (DataAccessObject) this.getServletContext().getAttribute("productBean");
            if (userDao == null) {
                userDao = DataAccessObjectFactory.getUserDao();
                this.getServletContext().setAttribute("userBean", userDao);
            }
        %>
    </head>
    <body>
        <h1>User Manager</h1>
        <h2>View, edit, and delete users below.</h2>
        <p></p>
        <c:forEach var="u" items="${userBean.readAll()}">
            <div>
                <form action="users" method="POST">
                    <label>
                        <span>Username</span>
                        <input type="text" name="username" value="${u.username}" readonly="readonly"/>
                    </label>
                    <label>
                        <span>Password</span>
                        <input type="password" name="password"/>
                    </label>
                    <label>
                        <span>Administrator</span>
                        <input type="checkbox" name="isAdministrator" value="true" ${u.isAdministrator() ? "checked" : ""} />
                    </label>
                    <label>
                        <span>Inventory Manager</span>
                        <input type="checkbox" name="isInventoryManager" value="true" ${u.isInventoryManager() ? "checked" : ""}/>
                    </label>
                    <input type="submit" name="button" value="Edit" />
                    <input type="submit" name="button" value="Delete" />
                </form>
            </div>
        </c:forEach>
        <div>
            <h2>Create new user below.</h2>
        </div>
        <div>
            <form action="users" method="post">
                <div>
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" />
                </div>
                <div>
                    <label for="password">Password</label>
                    <input type="text" id="password" name="password" />
                </div>
                <div>
                    <label for="isAdministrator">Administrator</label>
                    <input type="checkbox" id="isAdministrator" name="isAdministrator" />
                </div>
                <div>
                    <label for="isInventoryManager">Inventory Manager</label>
                    <input type="checkbox" id="isInventoryManager" name="isInventoryManager" />
                </div>
                <div>
                    <input type="submit" value="Create" name="button" />
                </div>
            </form>
        </div>
        <form action="users" method="post">
            <c:if test="${sessionScope.currentUser != null 
                          && userBean.read(currentUser.username).isInventoryManager()}">
                  <input type="submit" value="Manage Inventory" name="button" />
            </c:if>
        </form>
        <form action="login" method="post">
            <input type="submit" value="Logout" name="button" />
        </form>

    </body>
</html>


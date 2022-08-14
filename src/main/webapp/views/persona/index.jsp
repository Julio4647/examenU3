<%--
  Created by IntelliJ IDEA.
  User: JULIO
  Date: 13/08/2022
  Time: 05:30 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Personas</title>
    <jsp:include page="../../templates/head.jsp"/>
</head>
<body>
<jsp:include page="../../templates/navbar.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-12">
            <c:if test="${param['result']}">
                <p style="color: white"><c:out value="${param['message']}"></c:out> </p>
            </c:if>
            <div class="card">
                <div class="card-header">
                    <div class="row">
                        <div class="col-9" style="color: dimgray">
                            <h2>PERSONAS</h2>
                        </div>
                        <div class="col-3 text-end">
                            <a href="create-persona" class="btn btn-outline-success">Registrar usuario</a>
                        </div>
                    </div>
                </div>
                <div class="card-body" style="background-color: dodgerblue">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Curp</th>
                            <th>Birthday</th>
                            <th>option</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="personas" items="${personas}" varStatus="status">
                            <tr>
                                <td>
                                    <c:out value="${status.count}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${persona.name}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${persona.surname}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${persona.curp}"></c:out>
                                </td>
                                <td>
                                    <c:out value="${persona.birthday}"></c:out>
                                </td>
                                <td>
                                    <form action="save-persona" method="post">
                                        <input type="hidden" value="${persona.id}" name="id"/>
                                        <button type="submit" class="btn btn-warning btn-sm">editar</button>
                                    </form>
                                    <form action="delete-persona" method="post">
                                        <input type="hidden" value="${persona.id}" name="id"/>
                                        <button type="submit" class="btn btn-danger btn-sm">eliminar</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../templates/footer.jsp"/>
</body>
</html>

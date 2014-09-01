<%@page import="model.Persona"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Ordenador"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Ordenadores</title>
	<link rel="stylesheet" type="text/css" href="../css/comun.css">
	<style>
		.errores {
			background-color: red; 
			color: white; 
			margin-left: auto;
			margin-right: auto;
			width: 60%		
		}
	</style>
	<script>
		const AGREGAR = 0;
		const MODIFICAR = 1;
	
		var confirmar = function() {
			var confirmado = confirm("Está seguro de que desea eliminar la persona seleccionada?");
			return confirmado; // true o false
		};
		
		var guardar = function(op) {
			var form = document.forms.formOrdenador;
			
			if (op == AGREGAR)
				form.action = "Agregar";
			else if (op == MODIFICAR)
				form.action = "Modificar";
			
			form.submit();
		};
	</script>
</head>
<body>
	<h1>Ordenadores</h1>
	<div class="errores">
	<c:forEach var="e" items="${sessionScope.errores}">
				${e}<br/>
	</c:forEach>
	<% session.removeAttribute("errores"); %>
	</div>
	
	<form name="formOrdenador" method="post">
	
	<c:set var="o" value="${sessionScope.ordenador}"/>
	<input 
		type="hidden" 
		name="inputId" 
		id="inputId"
		value="${o.id}"/>
	<table class="tablaCentrada tablaFormulario">
		<tr>
			<td>Nombre:</td>
			<td>
				<input 
					type="text" 
					name="inputNombre" 
					id="inputNombre"
					value="${o.nombre}"/>
			</td>
		</tr>
		<tr>
			<td>Serial:</td>
			<td>
				<input 
					type="text" 
					name="inputSerial" 
					id="inputSerial"
					value="${o.serial}"/>
			</td>
		</tr>
		<tr>
			<td>Dueño:</td>
			<td>
				<select name="inputPersona">
					<option value="-1">Seleccione un dueño</option>
					<c:forEach var="p" items="${requestScope.personas}">
						<c:set var="selected" value=""/>
						<c:if test="${o.persona.id == p.id}">
							<c:set var="selected" value="selected"/>
						</c:if>
					
						<option value="${p.id}" ${selected}>
							${p.nombre} ${p.apellido}
						</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<c:choose>
					<c:when test="${empty ord}"><!-- Valida si ordenador es igual a null -->
						<input type="button" value="Agregar" onclick="guardar(AGREGAR)"/>
					</c:when>
					<c:otherwise>
						<!-- En cancelar: forma alternativa de realizar una petición con JS -->
						<input type="button" value="Cancelar" onclick="window.location.href='Inicio'"/>
						<input type="button" value="Modificar" onclick="guardar(MODIFICAR)"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
	</form>
	<% session.removeAttribute("ordenador"); %>
	<br/>

	<table id="tablaOrdenadores" class="tablaCentrada tablaDatos">
		<thead>
			<tr>
				<th>#</th>
				<th>Id</th>
				<th>Nombre</th>
				<th>Serial</th>
				<th>Dueño</th>
				<th>Mostrar</th>
				<th>Eliminar</th>			
			</tr>
		</thead>

		<tbody id="tbodyOrdenadores">
			<c:set var="num" value="1"/><!-- int num = 1; -->
			<c:forEach var="o" items="${requestScope.ordenadores}">
				<tr>
					<td>${num}</td>
					<td>${o.id}</td>
					<td>${o.nombre}</td>
					<td>${o.serial}</td>
					<td>${o.persona.nombre} ${o.persona.apellido}</td>
					<td><a
							href="Mostrar?id=${o.id}"> 
								mostrar
						</a>
					</td>
					<td><a 
							href="<%= getServletContext().getContextPath() %>/ordenador/Eliminar?id=${o.id}" 
							onclick="return confirmar()">
								eliminar
						</a>
					</td>
				</tr>
				
				<c:set var="num" value="${num + 1}"/>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
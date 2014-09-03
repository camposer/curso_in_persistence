<%@page import="model.Ordenador"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Persona"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Personas</title>
	<link rel="stylesheet" type="text/css" href="../css/comun.css">
	<link rel="stylesheet" type="text/css" href="../css/persona.css">
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
			var form = document.forms.formPersona;
			
			if (op == AGREGAR)
				form.action = "Agregar";
			else if (op == MODIFICAR)
				form.action = "Modificar";
			
			form.submit();
		};
	</script>
</head>
<body>
	<h1>Personas</h1>
	<%@ include file="/jsp/comun/cabecera.jsp" %>
	
	<div class="errores">
	<%
		List<String> errores = (List<String>)
			session.getAttribute("errores"); 
		if (errores != null && errores.size() > 0) 
			for (String e : errores) {
	%>
				<%= e %><br/>
	<%
			}
		session.removeAttribute("errores");
	%>
	</div>
	
	<form name="formPersona" method="post">
	
	<%
		Persona persona = (Persona)session.getAttribute("persona");
		String fecha = "";
		if (persona != null && persona.getFechanacimiento() != null)
			fecha = new SimpleDateFormat("yyyy-MM-dd")
				.format(persona.getFechanacimiento());
	%>
	
	<input 
		type="hidden" 
		name="inputId" 
		id="inputId"
		value="<%= (persona!=null)?persona.getId():"" %>"/>
	<table class="tablaCentrada tablaFormulario">
	
		<tr>
			<td>Nombre:</td>
			<td>
				<input 
					type="text" 
					name="inputNombre" 
					id="inputNombre"
					value="<%= (persona!=null)?persona.getNombre():"" %>"/>
			</td>
		</tr>
		<tr>
			<td>Apellido:</td>
			<td>
				<input 
					type="text" 
					name="inputApellido" 
					id="inputApellido"
					value="<%= (persona!=null)?persona.getApellido():"" %>"/>
			</td>
		</tr>
		<tr>
			<td>Fecha:</td>
			<td>
				<input 
					type="text" 
					name="inputFecha" 
					id="inputFecha"
					value="<%= fecha %>"/>
			</td>
		</tr>
		<tr>
			<td>Altura:</td>
			<td>
				<input 
					type="text" 
					name="inputAltura" 
					id="inputAltura"
					value="<%= (persona!=null)?persona.getAltura():"" %>"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<!-- Ordenador -->
				<hr/>
				<div id="tituloOrdenadores">Ordenadores:</div>			
				<table>
					<tr>
						<td>Nombre:</td>
						<td>
							<input 
								type="text" 
								name="inputNombreOrdenador" 
								id="inputNombreOrdenador"
								value=""/>
						</td>
					</tr>
					<tr>
						<td>Serial:</td>
						<td>
							<input 
								type="text" 
								name="inputSerialOrdenador" 
								id="inputSerialOrdenador"
								value=""/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<% 
					if (persona == null) {
				%>
				<input type="button" value="Agregar" onclick="guardar(AGREGAR)"/>
				<% 
					} else {
				%>
				<!-- En cancelar: forma alternativa de realizar una petición con JS -->
				<input type="button" value="Cancelar" onclick="window.location.href='Inicio'"/>
				<input type="button" value="Modificar" onclick="guardar(MODIFICAR)"/>
				<% 
					}
				%>
			</td>
		</tr>
	</table>
	</form>
	<%
		if (persona != null)
			session.removeAttribute("persona");
	%>
	<br/>

	<table id="tablaPersonas" class="tablaCentrada tablaDatos">
		<thead>
			<tr>
				<th>#</th>
				<th>Id</th>
				<th>Nombre</th>
				<th>Apellido</th>
				<th>Fecha</th>
				<th>Altura</th>
				<th>Ordenadores</th>
				<th>Mostrar</th>
				<th>Eliminar</th>			
			</tr>
		</thead>

		<tbody id="tbodyPersonas">
		<%
			// Obteniendo personas que son enviadas desde el Controlador
			List<Persona> personas = (List<Persona>)
				request.getAttribute("personas");
		
			if (personas != null) {
				int num = 1;
				for (Persona p : personas) {
		%>
					<tr>
						<td><%= num++ %></td>
						<td><%= p.getId() %></td>
						<td><%= p.getNombre() %></td>
						<td><%= p.getApellido() %></td>
						<td><%= p.getFechanacimiento() %></td>
						<td><%= p.getAltura() %></td>
						<td>
							<% 
								List<Ordenador> ordenadores = (List<Ordenador>)
									p.getOrdenadores();
								if (ordenadores != null) for (Ordenador o : ordenadores) { 
							%>
								<a href="../ordenador/Mostrar?id=<%= o.getId() %>">
									<%= o.getNombre() %> - <%= o.getSerial() %><br/>
								</a>
							<%
								}
							%>
						</td>
						<td><a
								href="Mostrar?id=<%= p.getId() %>"> 
									mostrar
							</a>
						</td>
						<td><a 
								href="<%= getServletContext().getContextPath() %>/persona/Eliminar?id=<%= p.getId() %>" 
								onclick="return confirmar()">
									eliminar
							</a>
						</td>
					</tr>
		<%
				}
			}
		%>
		</tbody>
	</table>
</body>
</html>
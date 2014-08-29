package gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import service.PersonaService;
import model.Persona;

public class PersonaGui {
	private Scanner scanner;
	
	public PersonaGui() {
		this.scanner = new Scanner(System.in);
	}
	
	public void iniciar() {
		while (true) {
			System.out.println("\nPersona");
			System.out.println("1. Agregar");
			System.out.println("2. Consultar todos");
			System.out.println("3. Consultar uno");
			System.out.println("4. Modificar");
			System.out.println("5. Eliminar");
			System.out.println("6. Salir");
			System.out.println("?");
			
			String opcion = scanner.nextLine();
			if (opcion.equals("1"))
				agregarPersona();
			else if (opcion.equals("2"))
				obtenerPersonas();
			else if (opcion.equals("3"))
				obtenerPersona();
			else if (opcion.equals("4"))
				modificarPersona();
			else if (opcion.equals("5"))
				eliminarPersona();
			else if (opcion.equals("6"))
				break;
		}
	}

	private void eliminarPersona() {
		System.out.print("Id? ");
		String sid = scanner.nextLine();
		Integer id = Integer.parseInt(sid);
		
		new PersonaService().eliminarPersona(id);
	}

	private void modificarPersona() {
		System.out.print("Id? ");
		String sid = scanner.nextLine();
		Integer id = Integer.parseInt(sid);
		
		Persona p = new PersonaService().obtenerPersona(id);

		if (p == null) { // Si no existe la persona sale del método
			System.out.println("No existe la persona para el id = " + id);
			return;
		}

		System.out.println(p); // Imprimo datos de la persona actual

		System.out.print("Nombre? ");
		String nombre = scanner.nextLine();
		p.setNombre(nombre);
		
		System.out.print("Apellido? ");
		String apellido = scanner.nextLine();
		p.setApellido(apellido);
		
		System.out.print("Altura? ");
		String saltura = scanner.nextLine();
		Float altura = Float.parseFloat(saltura);
		p.setAltura(altura);
		
		System.out.print("Fnac? ");
		String sfnac = scanner.nextLine();
		Date fnac;
		try {
			fnac = new SimpleDateFormat("yyyy-MM-dd").parse(sfnac);
			p.setFechanacimiento(fnac);
		} catch (ParseException e) {
			System.out.println("Fecha inválida");
			e.printStackTrace();
			return;
		}
		
		new PersonaService().modificarPersona(p);

	}

	private void obtenerPersona() {
		System.out.print("Id? ");
		String sid = scanner.nextLine();
		Integer id = Integer.parseInt(sid);
		
		Persona p = new PersonaService().obtenerPersona(id);
		System.out.println(p);
	}

	private void obtenerPersonas() {
		List<Persona> personas = new PersonaService().obtenerPersonas();
		if (personas != null) for (Persona p : personas) 
			System.out.println(p);
	}

	private void agregarPersona() {
		System.out.print("Nombre? ");
		String nombre = scanner.nextLine();
		System.out.print("Apellido? ");
		String apellido = scanner.nextLine();
		System.out.print("Altura? ");
		String saltura = scanner.nextLine();
		Float altura = Float.parseFloat(saltura);
		System.out.print("Fnac? ");
		String sfnac = scanner.nextLine();
		Date fnac;
		try {
			fnac = new SimpleDateFormat("yyyy-MM-dd").parse(sfnac);
		} catch (ParseException e) {
			System.out.println("Fecha inválida");
			e.printStackTrace();
			return;
		}
		
		Persona p = new Persona(nombre, apellido, altura, fnac);
		
		new PersonaService().agregarPersona(p);
	}

}

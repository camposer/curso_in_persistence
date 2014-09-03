package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the PERSONA database table.
 * 
 */
@Entity
@Table(name="PERSONA")
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	private Float altura;

	@Column(nullable=false, length=50)
	private String apellido;

	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	@Column(nullable=false, length=50)
	private String nombre;

	//bi-directional many-to-one association to Ordenador
	//@OneToMany(mappedBy="persona", fetch=FetchType.EAGER)
	//@OneToMany(mappedBy="persona", cascade=CascadeType.ALL) // => LAZY y Cascada en PERSIST, MERGE, REMOVE
	@OneToMany(mappedBy="persona") // => LAZY
	private List<Ordenador> ordenadores;

	public Persona() {
	}

	public Persona(String nombre, String apellido, 
			Float altura, Date fechaNacimiento) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.altura = altura;
		this.fechanacimiento = fechaNacimiento;
	}
		
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getAltura() {
		return this.altura;
	}

	public void setAltura(Float altura) {
		this.altura = altura;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Ordenador> getOrdenadores() {
		return this.ordenadores;
	}

	public void setOrdenadores(List<Ordenador> ordenadores) {
		this.ordenadores = ordenadores;
	}

	public Ordenador addOrdenador(Ordenador ordenador) {
		getOrdenadores().add(ordenador);
		ordenador.setPersona(this);

		return ordenador;
	}

	public Ordenador removeOrdenador(Ordenador ordenador) {
		getOrdenadores().remove(ordenador);
		ordenador.setPersona(null);

		return ordenador;
	}
	
	// TODO Mejorar precisión (no cuenta los meses)
	@Transient
	public Integer getEdad() {
		Calendar hoy = Calendar.getInstance();
		Calendar fnac = Calendar.getInstance();
		fnac.setTime(this.fechanacimiento);
		
		return hoy.get(Calendar.YEAR) - fnac.get(Calendar.YEAR);
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", altura=" + altura + ", apellido="
				+ apellido + ", fechanacimiento=" + fechanacimiento
				+ ", nombre=" + nombre + "]";
	}
}
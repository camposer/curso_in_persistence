DROP TABLE ordenador;
DROP TABLE persona;

CREATE TABLE persona (
	id INTEGER NOT NULL 
		GENERATED ALWAYS AS IDENTITY 
		(START WITH 1, INCREMENT BY 1),
	nombre VARCHAR(50) NOT NULL,
	apellido VARCHAR(50) NOT NULL,
	altura FLOAT,
	fechaNacimiento DATE,
	PRIMARY KEY(id)
);

INSERT INTO persona(nombre, apellido, altura, fechaNacimiento)
VALUES('Juan', 'Pérez', 1.70, '1980-02-12');
INSERT INTO persona(nombre, apellido, altura, fechaNacimiento)
VALUES('María', 'García', 1.60, '1979-03-22');
INSERT INTO persona(nombre, apellido, altura, fechaNacimiento)
VALUES('Pedro', 'González', 1.80, '1960-04-30');

SELECT * FROM persona;

CREATE TABLE ordenador (
	id INTEGER NOT NULL 
		GENERATED ALWAYS AS IDENTITY 
		(START WITH 1, INCREMENT BY 1),
	nombre VARCHAR(50) NOT NULL,
	serial VARCHAR(50) NOT NULL,
	persona_id INT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (persona_id) REFERENCES persona(id)
);
INSERT INTO ordenador(nombre, serial, persona_id)
VALUES('Ordenador #1', '123', 1);
INSERT INTO ordenador(nombre, serial, persona_id)
VALUES('Ordenador #2', '456', 2);
INSERT INTO ordenador(nombre, serial, persona_id)
VALUES('Ordenador #2', '456', 2);
INSERT INTO ordenador(nombre, serial, persona_id)
VALUES('Ordenador #3', '456', 3);

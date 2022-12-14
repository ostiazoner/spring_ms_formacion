package edu.indra.alumnos.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.indra.comun.entity.Alumno;


//TODO basándose en el ejemplo de esta web https://howtodoinjava.com/spring-boot2/pagination-sorting-example/
//haced un servicio que me devuelva en páginas los alumnos ordenados por edad de menor a mayor 
//cuando lo tengáis, me lo ponéis en el codeshare, gracias :)

@Repository
public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {
//public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
	
	//1 KEY WORD QUERIES
	
		//1 OBTENER UN LISTADO DE ALUMNOS EN UN RANGO DE EDAD
		public Iterable<Alumno> findByEdadBetween(int edad_min, int edad_max);
		
		//1.1 OBTENER UN LISTADO DE ALUMNOS EN UN RANGO DE EDAD paginado
		public Page<Alumno> findByEdadBetween(int edad_min, int edad_max, Pageable pageable);
		
		//2 OBTENER UN LISTADO DE ALUMNOS CUYO NOMBRE CUMPLA UN PATRÓN con like (hay que concatenar los comodines %)
		public Iterable<Alumno> findByNombreLike(String buscar);
		
		//3 OBTENER UN LISTADO DE ALUMNOS CUYO NOMBRE CUMPLA UN PATRÓN con Containing (NO HACE FALTA concatenar los comodines %)
		public Iterable<Alumno> findByNombreContaining(String cadena);
	
	//2 JPQL - "Agnóstico"
		
		//4 BUSQUEDA DE ALUMNOS POR NOMBRE O APELLIDO
		
		@Query("SELECT a FROM Alumno a WHERE a.nombre LIKE %?1% OR a.apellido LIKE %?1%")
		public Iterable<Alumno> busquedaPorNombreOApellidoJPQL (String patron);
		
		//TODO hacer el servicio y el controlador 
		//4.1 CON PAGINACIÓN
		@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
		public Page<Alumno> busquedaPorNombreOApellidoPaginado (String patron, Pageable pageable);
		
	
	//3 NATIVAS
		
		@Query(value =  "SELECT * FROM alumnos a WHERE a.nombre LIKE %?1% OR a.apellido LIKE %?1%", nativeQuery = true)
		public Iterable<Alumno> busquedaPorNombreOApellidoNativa (String patron);
	
	//4 PROCEDIMIENTOS ALMACENADOS 
		
		//1 DEFINIRLOS EN BASE DE DATOS X
		//2 REFERENCIARLOS EN LA ENTIDAD ALUMNO como para invocarlos desde JAVA x
		//3 HACER MÉTODOS EN ALUMNO REPOSITORY @PROCEDURE que referencian al 2 x
		
		@Procedure(name="Alumno.alumnosRegistradosHoy")
		public Iterable<Alumno> procedimientoAlumnosAltaHoy();
		
		@Procedure(name="Alumno.alumnosEdadMediaMinMax")
		public Map<String, Number> procedimientoEstadisticosEdad(int edadmax, int edadmin, float edadmedia);
		
		@Procedure(name="Alumno.alumnosNombreComo")
		public Iterable<Alumno> procedimientoAlumnosNombreComo(@Param("patron") String patron);
	
	//5 CRITERIA API x
	
	//6 PAGINACIÓN - CONSULTAS

}
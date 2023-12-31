package com.example.demo.services;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<EstudianteEntity> encontrarTodos(){
        return (List<EstudianteEntity>) estudianteRepository.findAll();
    }

    public void guardarEstudiante(String rut, String apellidos, String nombres, String fecha_nacimiento, String tipo_colegio, String nombre_colegio, Integer anio_egreso, String tipo_pago, Integer n_cuotas) {
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRut(rut);
        estudiante.setApellidos(apellidos);
        estudiante.setNombres(nombres);
        estudiante.setFecha_nacimiento(fecha_nacimiento);
        estudiante.setTipo_colegio(tipo_colegio);
        estudiante.setNombre_colegio(nombre_colegio);
        estudiante.setAnio_egreso(anio_egreso);
        estudiante.setTipo_pago(tipo_pago);
        estudiante.setN_cuotas(n_cuotas);
        estudianteRepository.save(estudiante);
    }

}

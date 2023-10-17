package com.example.demo;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.repositories.EstudianteRepository;
import com.example.demo.services.EstudianteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class EstudianteServiceTest {

    @InjectMocks
    private EstudianteService estudianteService;

    @Mock
    private EstudianteRepository estudianteRepository;

    @Test
    public void guardarEstudiante() {
        String rut = "20.340.456-3";
        String apellidos = "Contreras Aravena";
        String nombres = "Jorge Alejandro";
        String fecha_nacimiento = "04-05-1990";
        String tipo_colegio = "Municipal";
        String nombre_colegio = "Nido de Agilas";
        Integer anio_egreso = 2015;
        String tipo_pago = "Cuotas";
        Integer n_cuotas = 10;

        when(estudianteRepository.save(Mockito.any(EstudianteEntity.class))).thenReturn(new EstudianteEntity());
        estudianteService.guardarEstudiante(rut, apellidos, nombres, fecha_nacimiento, tipo_colegio, nombre_colegio, anio_egreso, tipo_pago, n_cuotas);
        Mockito.verify(estudianteRepository, Mockito.times(1)).save(Mockito.any(EstudianteEntity.class));
    }

    @Test
    public void encontrarTodos(){
        EstudianteEntity estudiante = new EstudianteEntity("20.485.432-9", "Perez", "Juan", "2000-05-02", "Municipal", "Liceo Nacional", 2020, "Cuotas", 10);

        when(estudianteRepository.findAll()).thenReturn(Collections.singletonList(estudiante));
        List<EstudianteEntity> result = estudianteService.encontrarTodos();

        assertEquals(Collections.singletonList(estudiante), result);
    }


}

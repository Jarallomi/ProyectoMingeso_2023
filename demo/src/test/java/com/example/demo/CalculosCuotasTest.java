package com.example.demo;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.SubirArchivoEntity;
import com.example.demo.services.CalculosCuotas;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculosCuotasTest {

    CalculosCuotas cuotas = new CalculosCuotas();
    EstudianteEntity estudiante = new EstudianteEntity();

    SubirArchivoEntity archivo = new SubirArchivoEntity();

    @Test
    void calcularCuotaAnioEgreso(){
        estudiante.setAnio_egreso(2023);

        double cuotaFinal = cuotas.calcularCuotaAnioEgreso(estudiante, 1500000);
        assertEquals(1275000, cuotaFinal, 0.0);

    }

    @Test
    void calcularCuotaPuntajes(){
        archivo.setPuntaje(900);

        double cuotaFinal = cuotas.calcularCuotaPuntajes(archivo, 10000);
        assertEquals(9500, cuotaFinal, 0.0);
    }
}

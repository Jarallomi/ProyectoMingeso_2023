package com.example.demo;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.SubirArchivoEntity;
import com.example.demo.services.CalculosCuotas;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculosCuotasTest {

    CalculosCuotas cuotas = new CalculosCuotas();
    EstudianteEntity estudiante = new EstudianteEntity();

    SubirArchivoEntity archivo = new SubirArchivoEntity();

    @Test
    void calcularCuotaAnioEgreso(){
        estudiante.setAnio_egreso(2023);

        double cuotaFinal = cuotas.calcularCuotaAnioEgreso(estudiante, 1500000);
        assertEquals(1275000, cuotaFinal, 0.0);

        estudiante.setAnio_egreso(2021);
        cuotaFinal = cuotas.calcularCuotaAnioEgreso(estudiante, 1500000);
        assertEquals(1380000, cuotaFinal, 0.0);

    }

    @Test
    void calcularCuotaPuntajes(){
        archivo.setPuntaje(900);

        double cuotaFinal = cuotas.calcularCuotaPuntajes(archivo, 10000);
        assertEquals(9500, cuotaFinal, 0.0);

        archivo.setPuntaje(960);
        cuotaFinal = cuotas.calcularCuotaPuntajes(archivo, 10000);
        assertEquals(9000, cuotaFinal, 0.0);

        archivo.setPuntaje(860);
        cuotaFinal = cuotas.calcularCuotaPuntajes(archivo, 10000);
        assertEquals(9800, cuotaFinal, 0.0);
    }


   @Test
    void calcularCuotaTipoColegio(){
        estudiante.setTipo_colegio("Municipal");

        double cuotaFinal = cuotas.calcularCuotaTipoColegio(estudiante, 1500000);
        assertEquals(1200000, cuotaFinal, 0.0);

        estudiante.setTipo_colegio("Subvencionado");

        double cuotaFinal2 = cuotas.calcularCuotaTipoColegio(estudiante, 1500000);
        assertEquals(1350000, cuotaFinal2, 0.0);
    }

   @Test
    public void calcularCuotaFinal(){
        estudiante.setTipo_pago("Cuotas");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setAnio_egreso(2020);

        archivo.setPuntaje(800);

        double cuotaFinal = cuotas.calcularCuotaFinal(estudiante, archivo, 1500000);
        assertEquals(1222000, cuotaFinal, 0.0);

        estudiante.setTipo_pago("Contado");
        cuotaFinal = cuotas.calcularCuotaFinal(estudiante, archivo, 1500000);
        assertEquals(820000, cuotaFinal, 0.0);

    }
}

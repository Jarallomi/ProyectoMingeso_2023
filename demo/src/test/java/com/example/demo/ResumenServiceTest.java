package com.example.demo;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.ResumenEntity;
import com.example.demo.entities.SubirArchivoEntity;
import com.example.demo.repositories.ResumenRepository;
import com.example.demo.services.CalculosCuotas;
import com.example.demo.services.EstudianteService;
import com.example.demo.services.ResumenService;
import com.example.demo.services.SubirArchivoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ResumenServiceTest {
    @InjectMocks
    private ResumenService resumenService;
    @Mock
    private ResumenRepository resumenRepository;
    @Mock
    private EstudianteService estudianteService;
    @Mock
    private SubirArchivoService archivoService;
    @Mock
    private CalculosCuotas calculosCuotas;
    private ResumenEntity resumenEjemplo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        resumenEjemplo = new ResumenEntity();

        resumenEjemplo.setNombre("Juan Perez");
        resumenEjemplo.setN_examenes(3);
        resumenEjemplo.setPromedio(700);
        resumenEjemplo.setMonto_total_a_pagar(1420000);
        resumenEjemplo.setTipo_de_pago("Cuotas");
        resumenEjemplo.setN_cuotas_pactadas(7);
        resumenEjemplo.setN_cuotas_pagadas(0);
        resumenEjemplo.setMonto_total_pagado(0);
        resumenEjemplo.setFecha_ultimo_pago(null);
        resumenEjemplo.setSaldo_por_pagar(262857);
        resumenEjemplo.setN_cuotas_retraso(0);
    }

    @Test
    public void guardarResumen() {
        EstudianteEntity estudiante = new EstudianteEntity("20.485.432-9", "Perez", "Juan", "2000-05-02", "Municipal", "Nido de Agilas", 2018, "Cuotas", 5);
        SubirArchivoEntity archivo = new SubirArchivoEntity("20.485.432-9", "2021-10-14", 600, 3);

        when(estudianteService.encontrarTodos()).thenReturn(Arrays.asList(estudiante));
        when(resumenRepository.findById(anyString())).thenReturn(Optional.empty());
        when(archivoService.obtenerPorRutOptional(anyString())).thenReturn(Optional.of(archivo));
        when(calculosCuotas.calcularCuotaFinal(any(), any(), anyDouble())).thenReturn(1000000.0);

        resumenService.guardarResumen();

        verify(resumenRepository).save(any(ResumenEntity.class));
    }


    @Test
    public void obtenerTodosLosResumenes() {
        ResumenEntity resumen1 = new ResumenEntity();
        ResumenEntity resumen2 = new ResumenEntity();

        List<ResumenEntity> resumenesEsperados = Arrays.asList(resumen1, resumen2);
        when(resumenRepository.findAll()).thenReturn(resumenesEsperados);
        List<ResumenEntity> result = resumenService.obtenerTodosLosResumenes();

        assertEquals(resumenesEsperados, result, "El método debería entregar todos los 'resumenes'");
    }

    @Test
    public void modificarCuota() {
        when(resumenRepository.findById(anyString())).thenReturn(Optional.of(resumenEjemplo));

        Date fecha_pago;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        fecha_pago = cal.getTime();

        resumenService.modificarCuota(fecha_pago, "20.485.432-9");

        verify(resumenRepository).save(any(ResumenEntity.class));

        assertEquals(fecha_pago, resumenEjemplo.getFecha_ultimo_pago());
        assertEquals(1, resumenEjemplo.getN_cuotas_pagadas());
        assertTrue(resumenEjemplo.getSaldo_por_pagar() > 1000);
        assertTrue(resumenEjemplo.getMonto_total_a_pagar() > 7000);

        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
    }

    @Test
    public void modificarCuota2() {
        when(resumenRepository.findById(anyString())).thenReturn(Optional.of(resumenEjemplo));

        Date fecha_pago = new Date();

        resumenService.modificarCuota(fecha_pago, "20.485.432-9");

        verify(resumenRepository).save(any(ResumenEntity.class));

        assertEquals(fecha_pago, resumenEjemplo.getFecha_ultimo_pago());
        assertEquals(1, resumenEjemplo.getN_cuotas_pagadas());
        assertTrue(resumenEjemplo.getSaldo_por_pagar() > 1000);
        assertEquals(262857, resumenEjemplo.getMonto_total_pagado());
        assertEquals(1420000, resumenEjemplo.getMonto_total_a_pagar());

        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
        resumenService.modificarCuota(fecha_pago, "20.485.432-9");
    }

    @Test
    public void formatearRut() {
        String rut = null;
        String esperado = "";
        assertEquals(esperado, resumenService.formatearRut(rut));

        rut = "204854329";
        esperado = "20.485.432-9";
        assertEquals(esperado, resumenService.formatearRut(rut));
    }
}

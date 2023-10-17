package com.example.demo;

import com.example.demo.entities.SubirArchivoEntity;
import com.example.demo.repositories.SubirArchivoRepository;
import com.example.demo.services.SubirArchivoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class SubirArchivoServiceTest {

    @InjectMocks
    private SubirArchivoService subirArchivoService;
    @Mock
    private SubirArchivoRepository archivoRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void obtenerArchivo() {
        SubirArchivoEntity archivo1 = new SubirArchivoEntity("20.485.432-9", "2021-10-14", 600, 2);
        SubirArchivoEntity archivo2 = new SubirArchivoEntity("21.123.456-7", "2021-10-14", 800, 3);

        ArrayList<SubirArchivoEntity> archivos_esperados = new ArrayList<>(Arrays.asList(archivo1, archivo2));
        when(archivoRepository.findAll()).thenReturn(archivos_esperados);
        ArrayList<SubirArchivoEntity> archivos_obtenidos = subirArchivoService.obtenerArchivo();

        assertEquals(archivos_esperados, archivos_obtenidos);
    }

    @Test
    public void obtenerPorRutExistente() {
        String rut_ejemplo = "20.485.432-9";
        String rut_ejemplo_2 = "21.123.456-7";
        SubirArchivoEntity archivo_ejemplo = new SubirArchivoEntity(rut_ejemplo, "2021-10-14", 600, 3);

        when(archivoRepository.findById(rut_ejemplo)).thenReturn(Optional.of(archivo_ejemplo));
        SubirArchivoEntity archivoObtenido = subirArchivoService.obtenerPorRut(rut_ejemplo);

        assertEquals(archivo_ejemplo, archivoObtenido);

        when(archivoRepository.findById(rut_ejemplo_2)).thenReturn(Optional.empty());
        archivoObtenido = subirArchivoService.obtenerPorRut(rut_ejemplo_2);

        assertNull(archivoObtenido);
    }

    @Test
    public void testObtenerPorRutOptional() {

        SubirArchivoEntity archivo = new SubirArchivoEntity("20.485.432-9", "2021-10-14", 600, 3);
        String rut_ejemplo = "20.485.432-9";

        when(archivoRepository.findById(rut_ejemplo)).thenReturn(Optional.of(archivo));
        Optional<SubirArchivoEntity> resultado = subirArchivoService.obtenerPorRutOptional(rut_ejemplo);

        assertEquals(Optional.of(archivo), resultado);
    }

    @Test
    public void guardarArchivoDB() {
        SubirArchivoEntity estudiante_existente = new SubirArchivoEntity("20.485.432-2", "2021-10-13", 600, 2);

        when(archivoRepository.findById("20.485.432-2")).thenReturn(Optional.of(estudiante_existente));

        subirArchivoService.guardarArchivoDB("20.485.432-2", "2021-10-14", 700);

        verify(archivoRepository).save(any(SubirArchivoEntity.class));
        assertEquals("2021-10-14", estudiante_existente.getFecha());
        assertEquals(3, estudiante_existente.getN_examenes());
        assertEquals(433, estudiante_existente.getPuntaje());

        subirArchivoService.guardarArchivoDB("20.485.432-3", "2021-10-14", 700);
    }



}

package com.example.demo.services;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.ResumenEntity;
import com.example.demo.entities.SubirArchivoEntity;
import com.example.demo.repositories.EstudianteRepository;
import com.example.demo.repositories.ResumenRepository;
import com.example.demo.repositories.SubirArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResumenService {

    private final CalculosCuotas calculosCuotas;

    @Autowired
    private ResumenRepository resumenRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private SubirArchivoRepository archivoRepository;
    @Autowired
    public ResumenService(CalculosCuotas calculosCuotas){
        this.calculosCuotas = calculosCuotas;
    }

    public void guardarResumen(){
        List<EstudianteEntity> estudiantes = (List<EstudianteEntity>) estudianteRepository.findAll();

        for (EstudianteEntity estudiante : estudiantes){
            String rut = estudiante.getRut();

            Optional<SubirArchivoEntity> archivoOpcional = archivoRepository.findById(rut);

            if (archivoOpcional.isPresent()){
                SubirArchivoEntity archivo = archivoOpcional.get();

                double monto_previo = calculosCuotas.calcularCuotaFinal(estudiante, archivo, 1500000);
                int monto_a_pagar = (int) monto_previo;
                ResumenEntity resumen = resumenRepository.findById(rut).orElse(new ResumenEntity());
                resumen.setRut(rut);
                resumen.setNombre(estudiante.getNombres() + " " + estudiante.getApellidos());
                resumen.setN_examenes(archivo.getN_examenes());
                resumen.setPromedio(archivo.getPuntaje());
                resumen.setMonto_total_a_pagar(monto_a_pagar);
                resumen.setTipo_de_pago(estudiante.getTipo_pago());
                resumen.setN_cuotas_pactadas(estudiante.getN_cuotas());
                resumen.setN_cuotas_pagadas(0);
                resumen.setMonto_total_pagado(0);
                resumen.setFecha_ultimo_pago(null);
                resumen.setSaldo_por_pagar((monto_a_pagar / estudiante.getN_cuotas()));
                resumen.setN_cuotas_retraso(0);
                resumenRepository.save(resumen);
            }
        }
    }

    public List<ResumenEntity> obtenerTodosLosResumenes() {
        return (List<ResumenEntity>) resumenRepository.findAll();
    }


}

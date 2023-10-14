package com.example.demo.services;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.ResumenEntity;
import com.example.demo.entities.SubirArchivoEntity;
import com.example.demo.repositories.EstudianteRepository;
import com.example.demo.repositories.ResumenRepository;
import com.example.demo.repositories.SubirArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResumenService {

    @Autowired
    private CalculosCuotas calculosCuotas;
    @Autowired
    private ResumenRepository resumenRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private SubirArchivoRepository archivoRepository;

    public void guardarResumen(){
        List<EstudianteEntity> estudiantes = (List<EstudianteEntity>) estudianteRepository.findAll();

        for (EstudianteEntity estudiante : estudiantes){
            String rut = estudiante.getRut();
            Optional<ResumenEntity> resumenOpcional = resumenRepository.findById(rut);

            if(resumenOpcional.isEmpty()) {
                Optional<SubirArchivoEntity> archivoOpcional = archivoRepository.findById(rut);
                if (archivoOpcional.isPresent()) {
                    SubirArchivoEntity archivo = archivoOpcional.get();

                    double monto_previo = calculosCuotas.calcularCuotaFinal(estudiante, archivo, 1500000);
                    int monto_a_pagar = (int) monto_previo;
                    ResumenEntity resumen = new ResumenEntity();
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
                    resumen.setSaldo_por_pagar(((monto_a_pagar - 70000) / estudiante.getN_cuotas()) + 70000);
                    resumen.setN_cuotas_retraso(0);
                    resumenRepository.save(resumen);
                }
            }
        }

    }

    public List<ResumenEntity> obtenerTodosLosResumenes() {
        return (List<ResumenEntity>) resumenRepository.findAll();
    }

    public void modificarCuota(Date fecha_pago, String rut){
            ResumenEntity resumen = resumenRepository.findById(rut).get();
            if(resumenRepository.findById(rut).isPresent()) {
                Calendar fecha_nueva = Calendar.getInstance();

                int monto_por_pagar = resumen.getSaldo_por_pagar();
                int n_cuotas_pagadas = resumen.getN_cuotas_pagadas() + 1;
                int n_cuotas_nuevas = resumen.getN_cuotas_pactadas() - n_cuotas_pagadas;

                int meses_atraso = calcularMesesAtraso(fecha_pago, fecha_nueva.getTime());

                if (meses_atraso >= 1) {
                    double interes = (0.03 * meses_atraso) + 1;

                    if (n_cuotas_nuevas == 0) {
                        n_cuotas_nuevas = 1;
                    }

                    resumen.setFecha_ultimo_pago(fecha_pago);
                    resumen.setMonto_total_pagado(resumen.getMonto_total_pagado() + monto_por_pagar);
                    int nuevo_monto_1 = (int) ((resumen.getMonto_total_a_pagar() - resumen.getMonto_total_pagado()) * interes);
                    int nuevo_monto_2 = resumen.getMonto_total_pagado() + nuevo_monto_1;
                    resumen.setN_cuotas_pagadas(n_cuotas_pagadas);
                    resumen.setSaldo_por_pagar((int) (((resumen.getMonto_total_a_pagar() - resumen.getMonto_total_pagado()) / n_cuotas_nuevas) * interes));
                    resumen.setMonto_total_a_pagar(nuevo_monto_2);
                } else {
                    if (n_cuotas_nuevas == 0) {
                        n_cuotas_nuevas = 1;
                    }
                    resumen.setFecha_ultimo_pago(fecha_pago);
                    resumen.setMonto_total_pagado(resumen.getMonto_total_pagado() + monto_por_pagar);
                    resumen.setN_cuotas_pagadas(n_cuotas_pagadas);
                    resumen.setSaldo_por_pagar(((resumen.getMonto_total_a_pagar() - resumen.getMonto_total_pagado()) / n_cuotas_nuevas));
                }
                if (resumen.getN_cuotas_pagadas() > resumen.getN_cuotas_pactadas()) {
                    resumen.setN_cuotas_pagadas(resumen.getN_cuotas_pactadas());
                }
                resumenRepository.save(resumen);
            }
    }

    private int calcularMesesAtraso(Date fechaPago, Date fechaActual) {
        Calendar calPago = Calendar.getInstance();
        calPago.setTime(fechaPago);

        Calendar calActual = Calendar.getInstance();
        calActual.setTime(fechaActual);

        int years = calActual.get(Calendar.YEAR) - calPago.get(Calendar.YEAR);
        int months = calActual.get(Calendar.MONTH) - calPago.get(Calendar.MONTH);

        return years * 12 + months;
    }

    public String formatearRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            return "";
        }

        String rut_numerico = rut.substring(0, rut.length() - 1);
        String digito_verificador = rut.substring(rut.length() - 1);

        StringBuilder rut_formateado = new StringBuilder();
        int contador = 0;

        for (int i = rut_numerico.length() - 1; i >= 0; i--) {
            if (contador == 3) {
                rut_formateado.insert(0, ".");
                contador = 0;
            }
            rut_formateado.insert(0, rut_numerico.charAt(i));
            contador++;
        }

        rut_formateado.append("-");
        rut_formateado.append(digito_verificador);

        return rut_formateado.toString();
    }


}

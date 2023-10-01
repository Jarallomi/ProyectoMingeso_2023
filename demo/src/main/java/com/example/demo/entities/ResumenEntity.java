package com.example.demo.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "resumen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenEntity {

    @Id
    @NotNull
    private String rut;

    private String nombre;
    private Integer n_examenes;
    private Integer promedio;
    private Integer monto_total_a_pagar;
    private String tipo_de_pago;
    private Integer n_cuotas_pactadas;
    private Integer n_cuotas_pagadas;
    private Integer monto_total_pagado;
    private Date fecha_ultimo_pago;
    private Integer saldo_por_pagar;
    private Integer n_cuotas_retraso;


}
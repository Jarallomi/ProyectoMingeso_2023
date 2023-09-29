package com.example.demo.services;

import com.example.demo.entities.EstudianteEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class CalculosCuotas {

public double calcularCuotaTipoColegio(EstudianteEntity estudiante, double arancel){
    double cuotaFinal = arancel;
    if ("Municipal".equals(estudiante.getTipo_colegio())){
        cuotaFinal = cuotaFinal*0.8;
    }
    else if ("Subvencionado".equals(estudiante.getTipo_colegio())){
        cuotaFinal = cuotaFinal*0.9;
    }
    return cuotaFinal;
}

public double calcularCuotaAnioEgreso(EstudianteEntity estudiante, double arancel){
    double cuotaFinal = arancel;
    LocalDate fechaActual = LocalDate.now();
    int diferencia = fechaActual.getYear() - estudiante.getAnio_egreso();
    if(diferencia == 0){
        cuotaFinal = cuotaFinal*0.85;
    }
    else if(diferencia > 0 && diferencia < 3){
        cuotaFinal = cuotaFinal*0.92;
    }
    else if(diferencia >= 3 && diferencia < 5){
        cuotaFinal = cuotaFinal*0.96;
    }
    return cuotaFinal;
}


}

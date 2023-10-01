package com.example.demo.services;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.SubirArchivoEntity;
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

public double calcularCuotaPuntajes(SubirArchivoEntity estudiante, double cuota){
    double cuotaFinal = cuota;
    int puntaje = estudiante.getPuntaje();
    if (puntaje >= 950 && puntaje <= 1000){
        cuotaFinal = cuotaFinal*0.9;
    }
    else if (puntaje >= 900 && puntaje <= 949){
        cuotaFinal = cuotaFinal*0.95;
    }
    else if (puntaje >= 850 && puntaje <= 899){
        cuotaFinal = cuotaFinal*0.98;
    }
    return cuotaFinal;
}

public double calcularCuotaFinal(EstudianteEntity estudiante, SubirArchivoEntity archivo, double arancel){
    double cuota_3 = 0;
    if("Cuotas".equals(estudiante.getTipo_pago())){
        double cuota_1 = calcularCuotaTipoColegio(estudiante, arancel);
        double cuota_2 = calcularCuotaAnioEgreso(estudiante, cuota_1);
        cuota_3 = calcularCuotaPuntajes(archivo, cuota_2) + 70000;
    }
    else if("Contado".equals(estudiante.getTipo_pago())){
        cuota_3 = (arancel * 0.5) + 70000;
    }
    return cuota_3;
}


}

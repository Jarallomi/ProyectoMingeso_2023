package com.example.demo.controllers;

import com.example.demo.entities.ResumenEntity;
import com.example.demo.services.ResumenService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping
public class ResumenController {

    @Autowired
    private ResumenService resumenService;

    @GetMapping("/lista")
    public String mostrarResumen(Model model) {
        // Obtener todos los resúmenes y pasarlos al modelo
        resumenService.guardarResumen();
        model.addAttribute("resumenes", resumenService.obtenerTodosLosResumenes());
        return "lista"; // Renderizar la vista "lista.html"
    }

    @GetMapping("/registrarPagoCuotas")
    public String mostrarFormularioPagoCuotas(@RequestParam("rut") String rut, Model model) {
        model.addAttribute("rut", rut);
        return "formularioPagoCuotas"; // Nombre del HTML del formulario de pago de cuotas
    }


    @PostMapping("/registrarPagoCuotas")
    public String registrarPagoCuotas(@ModelAttribute("rut") String rut,
                                      @RequestParam("fechaPago") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaPago) {
        // Llama a tu servicio para registrar el pago de cuotas
        String rutNuevo = resumenService.formatearRut(rut);
        resumenService.modificarCuota(fechaPago, rutNuevo);

        // Redirecciona a alguna página de confirmación o a donde desees
        return "redirect:/lista"; // Redireccionar a la lista de resúmenes u otra página
    }



}

package com.example.demo.controllers;

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
        resumenService.guardarResumen();
        model.addAttribute("resumenes", resumenService.obtenerTodosLosResumenes());
        return "lista";
    }

    @GetMapping("/registrarPagoCuotas")
    public String mostrarFormularioPagoCuotas(@RequestParam("rut") String rut, Model model) {
        model.addAttribute("rut", rut);
        return "formularioPagoCuotas";
    }

    @PostMapping("/registrarPagoCuotas")
    public String registrarPagoCuotas(@ModelAttribute("rut") String rut,
                                      @RequestParam("fechaPago") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaPago) {
        String rutNuevo = resumenService.formatearRut(rut);
        resumenService.modificarCuota(fechaPago, rutNuevo);
        return "redirect:/lista";
    }

}

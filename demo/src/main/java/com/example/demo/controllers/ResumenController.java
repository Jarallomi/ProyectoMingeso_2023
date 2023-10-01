package com.example.demo.controllers;

import com.example.demo.entities.ResumenEntity;
import com.example.demo.services.ResumenService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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




}

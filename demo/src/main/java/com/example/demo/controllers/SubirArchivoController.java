package com.example.demo.controllers;

import com.example.demo.entities.SubirArchivoEntity;
import com.example.demo.services.SubirArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class SubirArchivoController {

    @Autowired
    private SubirArchivoService subirArchivo;

    @GetMapping("/subirArchivo")
    public String main() {
        return "subirArchivo";
    }

    @PostMapping("/subirArchivo")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
       if (!file.isEmpty()){
        subirArchivo.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo subido con exito!");
        subirArchivo.leerArchivo(file);
        }
    else{
        redirectAttributes.addFlashAttribute("mensaje", "Error: el archivo esta vacio.");
        }
        return "redirect:/subirArchivo";
    }

    @GetMapping("/informacionArchivo")
    public String listar(Model model){
        ArrayList<SubirArchivoEntity> datas = subirArchivo.obtenerArchivo();
        model.addAttribute("datas", datas);
        return "informacionArchivo";
    }

}

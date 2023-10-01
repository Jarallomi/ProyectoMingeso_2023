package com.example.demo.services;

import com.example.demo.entities.SubirArchivoEntity;
import com.example.demo.repositories.SubirArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class SubirArchivoService {


    @Autowired
    private SubirArchivoRepository archivoRepository;

    private final Logger logg = LoggerFactory.getLogger(SubirArchivoService.class);

    public ArrayList<SubirArchivoEntity> obtenerArchivo() {
        return (ArrayList<SubirArchivoEntity>) archivoRepository.findAll();
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo subido");
                }
                catch(IOException e){
                    logg.error("ERROR",e);
                }
            }
            return "Archivo subido con exito!";
        }
        else{
            return "No se pudo subir el archivo";
        }
    }

    @Generated
    public void leerArchivo(MultipartFile file){
        String direccion = file.getOriginalFilename();
        String texto = "";
        BufferedReader bf = null;
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarArchivoDB(bfRead.split(";")[0], bfRead.split(";")[1], Integer.valueOf(bfRead.split(";")[2]));
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leído exitosamente");
        }catch(Exception e){
            System.err.println("No se encontró el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }
    public SubirArchivoEntity obtenerPorRut(String rut){
        System.out.println("\n\nAQUÍ ESTA TODO: " + archivoRepository.findAll());
        Optional<SubirArchivoEntity> archivo = archivoRepository.findById(rut);

        return archivo.orElse(null);
    }
    public void guardarArchivo(SubirArchivoEntity archivo) {
        archivoRepository.save(archivo);
    }

    public void guardarArchivoDB(String RUT, String fecha, Integer puntaje){
        SubirArchivoEntity estudianteAVG = obtenerPorRut(RUT);
        if (estudianteAVG != null){
            SubirArchivoEntity estudianteExistente = estudianteAVG;
            estudianteExistente.setFecha(fecha);
            estudianteExistente.setN_examenes(estudianteAVG.getN_examenes() + 1);
            int promedio = (estudianteAVG.getPuntaje() + puntaje) / (estudianteExistente.getN_examenes());
            estudianteExistente.setPuntaje(promedio);
            guardarArchivo(estudianteExistente);
        }
        else{
            SubirArchivoEntity nuevoArchivo = new SubirArchivoEntity();
            nuevoArchivo.setRut(RUT);
            nuevoArchivo.setFecha(fecha);
            nuevoArchivo.setPuntaje(puntaje);
            nuevoArchivo.setN_examenes(1);
            guardarArchivo(nuevoArchivo);
        }

    }

    public void eliminarArchivo(ArrayList<SubirArchivoEntity> archivos){
        archivoRepository.deleteAll(archivos);
    }




}

package com.example.demo.repositories;

import com.example.demo.entities.SubirArchivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubirArchivoRepository extends CrudRepository<SubirArchivoEntity, String> {
}

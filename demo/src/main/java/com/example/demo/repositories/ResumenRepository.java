package com.example.demo.repositories;

import com.example.demo.entities.EstudianteEntity;
import com.example.demo.entities.ResumenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumenRepository extends CrudRepository<ResumenEntity, String> {
}

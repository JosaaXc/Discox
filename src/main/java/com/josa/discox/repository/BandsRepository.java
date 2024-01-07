package com.josa.discox.repository;

import com.josa.discox.model.Bands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BandsRepository extends JpaRepository<Bands, Long> {
    @Query("SELECT b FROM Bands b WHERE LOWER(b.bandName) LIKE LOWER(CONCAT('%',:nombre,'%'))")
    List<Bands> findByBandName(@Param("nombre") String nombre);
}
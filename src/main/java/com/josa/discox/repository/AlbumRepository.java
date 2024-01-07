package com.josa.discox.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.josa.discox.model.Album;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("SELECT a FROM Album a WHERE LOWER(a.albumName) LIKE LOWER(CONCAT('%',:nombre,'%'))")
    List<Album> findByAlbumName(@Param("nombre") String nombre);
}
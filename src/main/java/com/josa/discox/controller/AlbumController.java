package com.josa.discox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;
import com.josa.discox.model.Album;
import com.josa.discox.service.AlbumService;
import com.josa.discox.service.BandsService;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;
    @Autowired
    private BandsService bandsService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.obtenerTodosLosAlbumes();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/{parametro}")
    public ResponseEntity<?> obtenerAlbum(@PathVariable("parametro") String parametro) {
        try {
            Long id = Long.parseLong(parametro);
            Optional<Album> albumPorId = albumService.obtenerAlbumPorId(id);
            return albumPorId.map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (NumberFormatException e) {
            List<Album> albumesPorNombre = albumService.obtenerAlbumPorAlbumName(parametro);
            return ResponseEntity.ok(albumesPorNombre);
        }
    }

    @PostMapping
    public ResponseEntity<String> createAlbum(@RequestBody Album album) {
        if (!bandsService.existsById(album.getBand().getId())) {
            return new ResponseEntity<>("the band id provided doesn't exist", HttpStatus.BAD_REQUEST);
        }

        if(album.getAlbumName() == null || album.getAlbumName().isEmpty()) {
            return new ResponseEntity<>("The album name is required", HttpStatus.BAD_REQUEST);
        }

        if(album.getYear() < 1000 || album.getYear() > 2024) {
            return new ResponseEntity<>("Year must be a 4-digit number", HttpStatus.BAD_REQUEST);
        }

        if (album.getReview() != null && ((String) album.getReview()).length() > 500) {
            return new ResponseEntity<>("The review exceeds the 500 character limit", HttpStatus.BAD_REQUEST);
        }

        albumService.guardarAlbum(album);
        return new ResponseEntity<String>("Album/Ep created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable("id") Long id, @RequestBody Album album) {

        if (!bandsService.existsById(album.getBand().getId())) {
            return new ResponseEntity<>("the band id provided doesn't exist", HttpStatus.BAD_REQUEST);
        }

        if (album.getReview() != null && ((String) album.getReview()).length() > 500) {
            return new ResponseEntity<>("The review exceeds the 500 character limit", HttpStatus.BAD_REQUEST);
        }

        Optional<Album> optionalAlbum = albumService.obtenerAlbumPorId(id);
        if (!optionalAlbum.isPresent()) {
            return new ResponseEntity<>("El álbum no existe", HttpStatus.BAD_REQUEST);
        }

        album.setId(id);
        albumService.guardarAlbum(album);
        return new ResponseEntity<>("Album/Ep updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable("id") Long id) {
        Optional<Album> existingAlbum = albumService.obtenerAlbumPorId(id);
        if (existingAlbum.isPresent()) {
            albumService.eliminarAlbum(id);
            return new ResponseEntity<>("Album successfully deleted", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("No album found with the provided ID", HttpStatus.NOT_FOUND);
        }
    }
}
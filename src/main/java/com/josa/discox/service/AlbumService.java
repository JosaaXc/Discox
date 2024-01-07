package com.josa.discox.service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.josa.discox.model.Album;
import com.josa.discox.repository.AlbumRepository;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> obtenerTodosLosAlbumes() {
        return albumRepository.findAll();
    }
    
    public Album guardarAlbum(Album album) {
        return albumRepository.save(album);
    }

    public Optional<Album> obtenerAlbumPorId(Long id) {
        return albumRepository.findById(id);
    }
    
    public List<Album> obtenerAlbumPorAlbumName(String albumName) {
        return albumRepository.findByAlbumName(albumName);
    }

    public void eliminarAlbum(Long id) {
        albumRepository.deleteById(id);
    }
}


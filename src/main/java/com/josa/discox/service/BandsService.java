package com.josa.discox.service;

import com.josa.discox.model.Bands;
import com.josa.discox.repository.BandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandsService {

    @Autowired
    private BandsRepository bandsRepository;

    public List<Bands> obtenerTodasLasBandas() {
        return bandsRepository.findAll();
    }

    public Bands guardarBanda(Bands bands) {
        return bandsRepository.save(bands);
    }

    public Optional<Bands> obtenerBandaPorId(Long id) {
        return bandsRepository.findById(id);
    }
    
    public List<Bands> obtenerBandasPorNombre(String nombre) {
        return bandsRepository.findByBandName(nombre);
    }

    public void eliminarBanda(Long id) {
        bandsRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return bandsRepository.existsById(id);
    }
}
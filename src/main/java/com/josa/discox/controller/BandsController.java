package com.josa.discox.controller;

import com.josa.discox.model.Bands;
import com.josa.discox.service.BandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bands")
public class BandsController {

    @Autowired
    private BandsService bandsService;

    @GetMapping
    public ResponseEntity<List<Bands>> obtenerTodasLasBandas() {
        return new ResponseEntity<>(bandsService.obtenerTodasLasBandas(), HttpStatus.OK);
    }

    @GetMapping("/{parametro}")
    public ResponseEntity<?> obtenerBanda(@PathVariable("parametro") String parametro) {
        try {
            Long id = Long.parseLong(parametro);
            Optional<Bands> bandaPorId = bandsService.obtenerBandaPorId(id);
            return bandaPorId.map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } catch (NumberFormatException e) {
            List<Bands> bandasPorNombre = bandsService.obtenerBandasPorNombre(parametro);
            return ResponseEntity.ok(bandasPorNombre);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarBanda(@RequestBody Bands bands) {
        if (bands.getBandYear() < 1000 || bands.getBandYear() > 2024) {
            return new ResponseEntity<>("Year must be a 4-digit number", HttpStatus.BAD_REQUEST);
        }

        if (bands.getIsActive() != true && bands.getIsActive() != false) {
            return new ResponseEntity<>("IsActive must be a boolean value", HttpStatus.BAD_REQUEST);
        }

        try {
            Bands savedBands = bandsService.guardarBanda(bands);
            return new ResponseEntity<>(savedBands, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while saving the band", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarBanda(@PathVariable("id") Long id, @RequestBody Bands bands) {
        if (bands.getBandYear() < 1000 || bands.getBandYear() > 2024) {
            return new ResponseEntity<>("Year must be a 4-digit number", HttpStatus.BAD_REQUEST);
        }

        if (bands.getIsActive() != true && bands.getIsActive() != false) {
            return new ResponseEntity<>("IsActive must be a boolean value", HttpStatus.BAD_REQUEST);
        }

        return bandsService.obtenerBandaPorId(id)
                .map(existingBands -> {
                    existingBands.setBandName(bands.getBandName());
                    existingBands.setBandYear(bands.getBandYear());
                    existingBands.setIsActive(bands.getIsActive());
                    try {
                        bandsService.guardarBanda(existingBands);
                        return ResponseEntity.ok(existingBands);
                    } catch (Exception e) {
                        return new ResponseEntity<>("An error occurred while updating the band", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarBanda(@PathVariable("id") Long id) {
        Optional<Bands> existingBands = bandsService.obtenerBandaPorId(id);
        if (existingBands.isPresent()) {
            bandsService.eliminarBanda(id);
            return new ResponseEntity<>("Band successfully removed", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("There is no band with the given ID", HttpStatus.NOT_FOUND);
        }
    }
}
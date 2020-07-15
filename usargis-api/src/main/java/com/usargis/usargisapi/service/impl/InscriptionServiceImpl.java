package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.model.Inscription;
import com.usargis.usargisapi.model.embeddable.InscriptionId;
import com.usargis.usargisapi.repository.InscriptionRepository;
import com.usargis.usargisapi.service.contract.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscriptionServiceImpl implements InscriptionService {

    private InscriptionRepository inscriptionRepository;

    @Autowired
    public InscriptionServiceImpl(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    @Override
    public List<Inscription> findAll() {
        return inscriptionRepository.findAll();
    }

    @Override
    public Optional<Inscription> findById(InscriptionId id) {
        return inscriptionRepository.findById(id);
    }

    @Override
    public Inscription save(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public void delete(Inscription inscription) {
        inscriptionRepository.delete(inscription);
    }
}
package com.usargis.usargisapi.repository;

import com.usargis.usargisapi.model.Inscription;
import com.usargis.usargisapi.model.embeddable.InscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, InscriptionId> {
}

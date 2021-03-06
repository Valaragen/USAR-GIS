package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.InscriptionDto;
import com.usargis.usargisapi.core.model.Inscription;
import com.usargis.usargisapi.service.contract.InscriptionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class InscriptionController implements ApiRestController {

    private InscriptionService inscriptionService;
    private ModelMapperService modelMapperService;

    @Autowired
    public InscriptionController(InscriptionService inscriptionService, ModelMapperService modelMapperService) {
        this.inscriptionService = inscriptionService;
        this.modelMapperService = modelMapperService;
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.INSCRIPTIONS_PATH)
    public ResponseEntity<List<InscriptionDto.InscriptionResponse>> findAllInscriptions() {
        List<Inscription> inscriptions = inscriptionService.findAll();
        if (inscriptions.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_INSCRIPTION_FOUND);
        }
        return new ResponseEntity<>(inscriptions.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.INSCRIPTIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<InscriptionDto.InscriptionResponse> getInscriptionById(@PathVariable Long id) {
        Optional<Inscription> inscriptionOptional = inscriptionService.findById(id);
        Inscription inscription = inscriptionOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_INSCRIPTION_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(inscription), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PostMapping(Constant.INSCRIPTIONS_PATH)
    public ResponseEntity<InscriptionDto.InscriptionResponse> createNewInscription(@RequestBody @Valid InscriptionDto.InscriptionPostRequest inscriptionCreateDto) {
        Inscription inscription = inscriptionService.create(inscriptionCreateDto);
        return new ResponseEntity<>(convertToResponseDto(inscription), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PutMapping(Constant.INSCRIPTIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<InscriptionDto.InscriptionResponse> updateInscription(@PathVariable Long id, @RequestBody @Valid InscriptionDto.InscriptionPostRequest updateDto) {
        Inscription inscription = inscriptionService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(inscription), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @DeleteMapping(Constant.INSCRIPTIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteInscription(@PathVariable Long id) {
        inscriptionService.delete(inscriptionService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_INSCRIPTION_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private InscriptionDto.InscriptionResponse convertToResponseDto(Inscription inscription) {
        return modelMapperService.map(inscription, InscriptionDto.InscriptionResponse.class);
    }

}

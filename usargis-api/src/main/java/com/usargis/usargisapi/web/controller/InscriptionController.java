//package com.usargis.usargisapi.web.controller;
//
//import com.usargis.usargisapi.core.dto.InscriptionDto;
//import com.usargis.usargisapi.core.model.Inscription;
//import com.usargis.usargisapi.service.contract.InscriptionService;
//import com.usargis.usargisapi.service.contract.ModelMapperService;
//import com.usargis.usargisapi.util.Constant;
//import com.usargis.usargisapi.util.ErrorConstant;
//import com.usargis.usargisapi.web.exception.NotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.text.MessageFormat;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "')")
//@RestController
//public class InscriptionController {
//
//    private InscriptionService inscriptionService;
//    private ModelMapperService modelMapperService;
//
//    @Autowired
//    public InscriptionController(InscriptionService inscriptionService, ModelMapperService modelMapperService) {
//        this.inscriptionService = inscriptionService;
//        this.modelMapperService = modelMapperService;
//    }
//
//    @GetMapping(Constant.INSCRIPTIONS_PATH)
//    public ResponseEntity<List<InscriptionDto.Response>> findAllInscriptions() {
//        List<Inscription> inscriptions = inscriptionService.findAll();
//        if (inscriptions.isEmpty()) {
//            throw new NotFoundException(ErrorConstant.NO_INSCRIPTIONS_FOUND);
//        }
//        return new ResponseEntity<>(inscriptions.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
//    }
//
//    @GetMapping(Constant.INSCRIPTIONS_PATH + Constant.SLASH_ID_PATH)
//    public ResponseEntity<InscriptionDto.Response> getInscriptionById(@PathVariable Long id) {
//        Optional<Inscription> inscriptionOptional = inscriptionService.findById(id);
//        Inscription inscription = inscriptionOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_INSCRIPTION_FOUND_FOR_ID, id)));
//        return new ResponseEntity<>(convertToResponseDto(inscription), HttpStatus.OK);
//    }
//
//    @PostMapping(Constant.INSCRIPTIONS_PATH)
//    public ResponseEntity<InscriptionDto.Response> createNewInscription(@RequestBody InscriptionDto.PostRequest inscriptionCreateDto) {
//        Inscription inscription = inscriptionService.create(inscriptionCreateDto);
//        return new ResponseEntity<>(convertToResponseDto(inscription), HttpStatus.CREATED);
//    }
//
//    @PutMapping(Constant.INSCRIPTIONS_PATH + Constant.SLASH_ID_PATH)
//    public ResponseEntity<InscriptionDto.Response> updateInscription(@PathVariable Long id, @RequestBody InscriptionDto.PostRequest updateDto) {
//        Inscription inscription = inscriptionService.update(id, updateDto);
//        return new ResponseEntity<>(convertToResponseDto(inscription), HttpStatus.OK);
//    }
//
//    @DeleteMapping(Constant.INSCRIPTIONS_PATH + Constant.SLASH_ID_PATH)
//    public ResponseEntity deleteInscription(@PathVariable Long id) {
//        inscriptionService.delete(inscriptionService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_INSCRIPTION_FOUND_FOR_ID, id))));
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    private InscriptionDto.Response convertToResponseDto(Inscription inscription) {
//        return modelMapperService.map(inscription, InscriptionDto.Response.class);
//    }
//
//}

package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.MissionDto;
import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MissionController implements ApiRestController {

    private MissionService missionService;
    private ModelMapperService modelMapperService;

    @Autowired
    public MissionController(MissionService missionService, ModelMapperService modelMapperService) {
        this.missionService = missionService;
        this.modelMapperService = modelMapperService;
    }

    @GetMapping(Constant.MISSIONS_PATH)
    public ResponseEntity<List<MissionDto.Response>> findAllMissions() {
        List<Mission> missions = missionService.findAll();
        if (missions.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_MISSION_FOUND);
        }
        return new ResponseEntity<>(missions.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<MissionDto.Response> getMissionById(@PathVariable Long id) {
        Optional<Mission> missionOptional = missionService.findById(id);
        Mission mission = missionOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(mission), HttpStatus.OK);
    }

    @PostMapping(Constant.MISSIONS_PATH)
    public ResponseEntity<MissionDto.Response> createNewMission(@RequestBody @Valid MissionDto.PostRequest missionCreateDto) {
        Mission mission = missionService.create(missionCreateDto);
        return new ResponseEntity<>(convertToResponseDto(mission), HttpStatus.CREATED);
    }

    @PutMapping(Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<MissionDto.Response> updateMission(@PathVariable Long id, @RequestBody @Valid MissionDto.PostRequest updateDto) {
        Mission mission = missionService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(mission), HttpStatus.OK);
    }

    @DeleteMapping(Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteMission(@PathVariable Long id) {
        missionService.delete(missionService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private MissionDto.Response convertToResponseDto(Mission mission) {
        return modelMapperService.map(mission, MissionDto.Response.class);
    }
}

package com.usargis.usargisapi.web.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('" + Constant.MEMBER_ROLE + "')")
    @GetMapping(Constant.MISSIONS_PATH)
    public ResponseEntity<List<MissionDto.MissionResponse>> findAllMissions() {
        List<Mission> missions = missionService.findAll();
        if (missions.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_MISSION_FOUND);
        }
        return new ResponseEntity<>(missions.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.MEMBER_ROLE + "')")
    @GetMapping(Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<MissionDto.MissionResponse> getMissionById(@PathVariable Long id) {
        Optional<Mission> missionOptional = missionService.findById(id);
        Mission mission = missionOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(mission), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PostMapping(Constant.MISSIONS_PATH)
    public ResponseEntity<MissionDto.MissionResponse> createNewMission(@RequestBody @Valid MissionDto.MissionPostRequest missionCreateDto) {
        Mission mission = missionService.create(missionCreateDto);
        return new ResponseEntity<>(convertToResponseDto(mission), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PutMapping(Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<MissionDto.MissionResponse> updateMission(@PathVariable Long id, @RequestBody @Valid MissionDto.MissionPostRequest updateDto) {
        Mission mission = missionService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(mission), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @DeleteMapping(Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteMission(@PathVariable Long id) {
        missionService.delete(missionService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PatchMapping(path = Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH, consumes = "application/json-patch+json")
    public ResponseEntity<MissionDto.MissionResponse> patchMission(@PathVariable Long id, @RequestBody JsonPatch patchDocument) throws JsonPatchException {
        Mission mission = missionService.patch(id, patchDocument);
        return new ResponseEntity<>(convertToResponseDto(mission), HttpStatus.OK);
    }

    private MissionDto.MissionResponse convertToResponseDto(Mission mission) {
        return modelMapperService.map(mission, MissionDto.MissionResponse.class);
    }
}

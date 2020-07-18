package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.model.Mission;
import com.usargis.usargisapi.service.contract.MissionService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "')")
@RestController
public class MissionController {

    private MissionService missionService;

    @Autowired
    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @GetMapping(Constant.MISSIONS_PATH)
    public ResponseEntity<List<Mission>> findAllMissions() {
        List<Mission> missions = missionService.findAll();
        if (missions.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_MISSIONS_FOUND);
        }
        return new ResponseEntity<>(missions, HttpStatus.OK);
    }

    @GetMapping(Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
        Optional<Mission> mission = missionService.findById(id);
        Mission result = mission.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(Constant.MISSIONS_PATH)
    public ResponseEntity<Mission> createNewMission(@RequestBody Mission mission) {
        //TODO implement
        Mission result = missionService.save(mission);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(Constant.MISSIONS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Mission> deleteMission(@PathVariable Long id) {
        missionService.delete(missionService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_MISSION_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.TeamDto;
import com.usargis.usargisapi.core.model.Team;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.TeamService;
import com.usargis.usargisapi.util.Constant;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TeamController implements ApiController {

    private TeamService teamService;
    private ModelMapperService modelMapperService;

    @Autowired
    public TeamController(TeamService teamService, ModelMapperService modelMapperService) {
        this.teamService = teamService;
        this.modelMapperService = modelMapperService;
    }

    @GetMapping(Constant.TEAMS_PATH)
    public ResponseEntity<List<TeamDto.Response>> findAllTeams() {
        List<Team> teams = teamService.findAll();
        if (teams.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_TEAMS_FOUND);
        }
        return new ResponseEntity<>(teams.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Constant.TEAMS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<TeamDto.Response> getTeamById(@PathVariable Long id) {
        Optional<Team> teamOptional = teamService.findById(id);
        Team team = teamOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(team), HttpStatus.OK);
    }

    @PostMapping(Constant.TEAMS_PATH)
    public ResponseEntity<TeamDto.Response> createNewTeam(@RequestBody TeamDto.PostRequest teamCreateDto) {
        Team team = teamService.create(teamCreateDto);
        return new ResponseEntity<>(convertToResponseDto(team), HttpStatus.CREATED);
    }

    @PutMapping(Constant.TEAMS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<TeamDto.Response> updateTeam(@PathVariable Long id, @RequestBody TeamDto.PostRequest updateDto) {
        Team team = teamService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(team), HttpStatus.OK);
    }

    @DeleteMapping(Constant.TEAMS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteTeam(@PathVariable Long id) {
        teamService.delete(teamService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private TeamDto.Response convertToResponseDto(Team team) {
        return modelMapperService.map(team, TeamDto.Response.class);
    }
}

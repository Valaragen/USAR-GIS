package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.model.Team;
import com.usargis.usargisapi.service.contract.TeamService;
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
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.TEAMS_PATH)
    public ResponseEntity<List<Team>> findAllTeams() {
        List<Team> teams = teamService.findAll();
        if (teams.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_TEAMS_FOUND);
        }
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.TEAMS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamService.findById(id);
        Team result = team.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PostMapping(Constant.TEAMS_PATH)
    public ResponseEntity<Team> createNewTeam(@RequestBody Team team) {
        Team result = teamService.save(team);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(Constant.TEAMS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Team> deleteTeam(@PathVariable Long id) {
        teamService.delete(teamService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_TEAM_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

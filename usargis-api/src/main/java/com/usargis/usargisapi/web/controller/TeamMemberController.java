package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.TeamMemberDto;
import com.usargis.usargisapi.core.model.TeamMember;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.service.contract.TeamMemberService;
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
public class TeamMemberController implements ApiRestController {

    private TeamMemberService teamMemberService;
    private ModelMapperService modelMapperService;

    @Autowired
    public TeamMemberController(TeamMemberService teamMemberService, ModelMapperService modelMapperService) {
        this.teamMemberService = teamMemberService;
        this.modelMapperService = modelMapperService;
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.TEAM_MEMBERS_PATH)
    public ResponseEntity<List<TeamMemberDto.TeamMemberResponse>> findAllTeamMembers() {
        List<TeamMember> teamMembers = teamMemberService.findAll();
        if (teamMembers.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_TEAM_MEMBER_FOUND);
        }
        return new ResponseEntity<>(teamMembers.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.TEAM_MEMBERS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<TeamMemberDto.TeamMemberResponse> getTeamMemberById(@PathVariable Long id) {
        Optional<TeamMember> teamMemberOptional = teamMemberService.findById(id);
        TeamMember teamMember = teamMemberOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_TEAM_MEMBER_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(teamMember), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PostMapping(Constant.TEAM_MEMBERS_PATH)
    public ResponseEntity<TeamMemberDto.TeamMemberResponse> createNewTeamMember(@RequestBody @Valid TeamMemberDto.TeamMemberPostRequest teamMemberCreateDto) {
        TeamMember teamMember = teamMemberService.create(teamMemberCreateDto);
        return new ResponseEntity<>(convertToResponseDto(teamMember), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PutMapping(Constant.TEAM_MEMBERS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<TeamMemberDto.TeamMemberResponse> updateTeamMember(@PathVariable Long id, @RequestBody @Valid TeamMemberDto.TeamMemberPostRequest updateDto) {
        TeamMember teamMember = teamMemberService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(teamMember), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @DeleteMapping(Constant.TEAM_MEMBERS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteTeamMember(@PathVariable Long id) {
        teamMemberService.delete(teamMemberService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_TEAM_MEMBER_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private TeamMemberDto.TeamMemberResponse convertToResponseDto(TeamMember teamMember) {
        return modelMapperService.map(teamMember, TeamMemberDto.TeamMemberResponse.class);
    }

}

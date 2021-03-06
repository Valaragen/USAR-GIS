package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.GroupDto;
import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.service.contract.GroupService;
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
public class GroupController implements ApiRestController {

    private GroupService groupService;
    private ModelMapperService modelMapperService;

    @Autowired
    public GroupController(GroupService groupService, ModelMapperService modelMapperService) {
        this.groupService = groupService;
        this.modelMapperService = modelMapperService;
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.GROUPS_PATH)
    public ResponseEntity<List<GroupDto.GroupResponse>> findAllGroups() {
        List<Group> groups = groupService.findAll();
        if (groups.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_GROUP_FOUND);
        }
        return new ResponseEntity<>(groups.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @GetMapping(Constant.GROUPS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<GroupDto.GroupResponse> getGroupById(@PathVariable Long id) {
        Optional<Group> groupOptional = groupService.findById(id);
        Group group = groupOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(group), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PostMapping(Constant.GROUPS_PATH)
    public ResponseEntity<GroupDto.GroupResponse> createNewGroup(@RequestBody @Valid GroupDto.GroupPostRequest groupCreateDto) {
        Group group = groupService.create(groupCreateDto);
        return new ResponseEntity<>(convertToResponseDto(group), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @PutMapping(Constant.GROUPS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<GroupDto.GroupResponse> updateGroup(@PathVariable Long id, @RequestBody @Valid GroupDto.GroupPostRequest updateDto) {
        Group group = groupService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(group), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('" + Constant.LEADER_ROLE + "')")
    @DeleteMapping(Constant.GROUPS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        groupService.delete(groupService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private GroupDto.GroupResponse convertToResponseDto(Group group) {
        return modelMapperService.map(group, GroupDto.GroupResponse.class);
    }
}

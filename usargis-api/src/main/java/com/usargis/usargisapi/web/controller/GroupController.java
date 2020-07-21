package com.usargis.usargisapi.web.controller;

import com.usargis.usargisapi.core.dto.GroupDto;
import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.service.contract.GroupService;
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

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('" + Constant.ADMIN_ROLE + "')")
@RestController
public class GroupController {

    private GroupService groupService;
    private ModelMapperService modelMapperService;

    @Autowired
    public GroupController(GroupService groupService, ModelMapperService modelMapperService) {
        this.groupService = groupService;
        this.modelMapperService = modelMapperService;
    }

    @GetMapping(Constant.GROUPS_PATH)
    public ResponseEntity<List<GroupDto.Response>> findAllGroups() {
        List<Group> groups = groupService.findAll();
        if (groups.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_GROUPS_FOUND);
        }
        return new ResponseEntity<>(groups.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(Constant.GROUPS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<GroupDto.Response> getGroupById(@PathVariable Long id) {
        Optional<Group> groupOptional = groupService.findById(id);
        Group group = groupOptional.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(convertToResponseDto(group), HttpStatus.OK);
    }

    @PostMapping(Constant.GROUPS_PATH)
    public ResponseEntity<GroupDto.Response> createNewGroup(@RequestBody GroupDto.PostRequest groupCreateDto) {
        Group group = groupService.create(groupCreateDto);
        return new ResponseEntity<>(convertToResponseDto(group), HttpStatus.CREATED);
    }

    @PutMapping(Constant.GROUPS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<GroupDto.Response> updateGroup(@PathVariable Long id, @RequestBody GroupDto.PostRequest updateDto) {
        Group group = groupService.update(id, updateDto);
        return new ResponseEntity<>(convertToResponseDto(group), HttpStatus.OK);
    }

    @DeleteMapping(Constant.GROUPS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        groupService.delete(groupService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private GroupDto.Response convertToResponseDto(Group group) {
        return modelMapperService.map(group, GroupDto.Response.class);
    }
}

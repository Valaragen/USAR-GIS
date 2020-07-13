package com.usargis.usargisapi.web.controller.v1;

import com.usargis.usargisapi.model.Group;
import com.usargis.usargisapi.service.contract.GroupService;
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

@RestController
@RequestMapping(Constant.V1_PATH)
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(Constant.GROUPS_PATH)
    public ResponseEntity<List<Group>> findAllGroups() {
        List<Group> groups = groupService.findAll();
        if (groups.isEmpty()) {
            throw new NotFoundException(ErrorConstant.NO_GROUPS_FOUND);
        }
        return new ResponseEntity<>(groups, HttpStatus.FOUND);
    }

    @GetMapping(Constant.GROUPS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Optional<Group> group = groupService.findById(id);
        Group result = group.orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, id)));
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping(Constant.GROUPS_PATH)
    public ResponseEntity<Group> createNewGroup(@RequestBody Group group) {
        //TODO implement
        Group result = groupService.save(group);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping(Constant.GROUPS_PATH + Constant.SLASH_ID_PATH)
    public ResponseEntity<Group> deleteGroup(@PathVariable Long id) {
        groupService.delete(groupService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, id))));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

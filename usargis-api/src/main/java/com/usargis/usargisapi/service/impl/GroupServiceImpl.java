package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.GroupDto;
import com.usargis.usargisapi.core.model.Group;
import com.usargis.usargisapi.repository.GroupRepository;
import com.usargis.usargisapi.service.contract.GroupService;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import com.usargis.usargisapi.util.ErrorConstant;
import com.usargis.usargisapi.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private ModelMapperService modelMapperService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ModelMapperService modelMapperService) {
        this.groupRepository = groupRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }

    @Override
    public Group create(GroupDto.PostRequest createDto) {
        Group groupToCreate = new Group();
        modelMapperService.map(createDto, groupToCreate);
        return save(groupToCreate);
    }

    @Override
    public Group update(Long id, GroupDto.PostRequest updateDto) {
        Group groupToUpdate = findById(id).orElseThrow(() -> new NotFoundException(
                        MessageFormat.format(ErrorConstant.NO_GROUP_FOUND_FOR_ID, id)
                )
        );
        modelMapperService.map(updateDto, groupToUpdate);
        return save(groupToUpdate);
    }
}

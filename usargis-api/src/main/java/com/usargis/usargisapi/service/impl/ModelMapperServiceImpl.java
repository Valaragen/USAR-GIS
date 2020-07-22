package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.*;
import com.usargis.usargisapi.core.model.*;
import com.usargis.usargisapi.service.contract.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperServiceImpl implements ModelMapperService {

    private ModelMapper modelMapper;

    @Autowired
    public ModelMapperServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    //Mapping rules
    private void configureModelMapper() {
        //Availability Create DTO to entity
        PropertyMap<AvailabilityDto.Create, Availability> availabilityCreateToEntityPropertyMap = new PropertyMap<AvailabilityDto.Create, Availability>() {
            protected void configure() {
                skip().setId(null);
                skip().setMission(null);
                skip().setUserInfo(null);
            }
        };
        modelMapper.addMappings(availabilityCreateToEntityPropertyMap);

        //Team PostRequest DTO to entity
        PropertyMap<TeamDto.PostRequest, Team> teamPostRequestToEntityPropertyMap =
                new PropertyMap<TeamDto.PostRequest, Team>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setMission(null);
                    }
                };
        modelMapper.addMappings(teamPostRequestToEntityPropertyMap);

        //Inscription PostRequest DTO to entity
        PropertyMap<InscriptionDto.PostRequest, Inscription> inscriptionPostRequestToEntityPropertyMap =
                new PropertyMap<InscriptionDto.PostRequest, Inscription>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setEvent(null);
                        skip().setUserInfo(null);
                    }
                };
        modelMapper.addMappings(inscriptionPostRequestToEntityPropertyMap);

        //TeamMember PostRequest DTO to entity
        PropertyMap<TeamMemberDto.PostRequest, TeamMember> teamMemberPostRequestToEntityPropertyMap =
                new PropertyMap<TeamMemberDto.PostRequest, TeamMember>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setTeam(null);
                        skip().setUserInfo(null);
                    }
                };
        modelMapper.addMappings(teamMemberPostRequestToEntityPropertyMap);

        //NotificationMessage PostRequest DTO to entity
        PropertyMap<NotificationMessageDto.PostRequest, NotificationMessage> notificationMessagePostRequestToEntityPropertyMap =
                new PropertyMap<NotificationMessageDto.PostRequest, NotificationMessage>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setNotification(null);
                    }
                };
        modelMapper.addMappings(notificationMessagePostRequestToEntityPropertyMap);
    }

    @Override
    public <T> T map(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    @Override
    public void merge(Object source, Object destination) {
        modelMapper.map(source, destination);
    }
}

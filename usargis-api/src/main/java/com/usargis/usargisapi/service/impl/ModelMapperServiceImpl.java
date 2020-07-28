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
        PropertyMap<AvailabilityDto.AvailabilityCreate, Availability> availabilityCreateToEntityPropertyMap = new PropertyMap<AvailabilityDto.AvailabilityCreate, Availability>() {
            protected void configure() {
                skip().setId(null);
                skip().setMission(null);
                skip().setUserInfo(null);
            }
        };
        modelMapper.addMappings(availabilityCreateToEntityPropertyMap);

        //Team PostRequest DTO to entity
        PropertyMap<TeamDto.TeamPostRequest, Team> teamPostRequestToEntityPropertyMap =
                new PropertyMap<TeamDto.TeamPostRequest, Team>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setMission(null);
                    }
                };
        modelMapper.addMappings(teamPostRequestToEntityPropertyMap);

        //Inscription PostRequest DTO to entity
        PropertyMap<InscriptionDto.InscriptionPostRequest, Inscription> inscriptionPostRequestToEntityPropertyMap =
                new PropertyMap<InscriptionDto.InscriptionPostRequest, Inscription>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setEvent(null);
                        skip().setUserInfo(null);
                    }
                };
        modelMapper.addMappings(inscriptionPostRequestToEntityPropertyMap);

        //TeamMember PostRequest DTO to entity
        PropertyMap<TeamMemberDto.TeamMemberPostRequest, TeamMember> teamMemberPostRequestToEntityPropertyMap =
                new PropertyMap<TeamMemberDto.TeamMemberPostRequest, TeamMember>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setTeam(null);
                        skip().setUserInfo(null);
                    }
                };
        modelMapper.addMappings(teamMemberPostRequestToEntityPropertyMap);

        //NotificationMessage PostRequest DTO to entity
        PropertyMap<NotificationMessageDto.NotificationMessagePostRequest, NotificationMessage> notificationMessagePostRequestToEntityPropertyMap =
                new PropertyMap<NotificationMessageDto.NotificationMessagePostRequest, NotificationMessage>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setNotification(null);
                    }
                };
        modelMapper.addMappings(notificationMessagePostRequestToEntityPropertyMap);

        //Notification PostRequest DTO to entity
        PropertyMap<NotificationDto.NotificationPostRequest, Notification> notificationPostRequestToEntityPropertyMap =
                new PropertyMap<NotificationDto.NotificationPostRequest, Notification>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setMission(null);
                        skip().setEvent(null);
                    }
                };
        modelMapper.addMappings(notificationPostRequestToEntityPropertyMap);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public <T> T map(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

    @Override
    public void map(Object source, Object destinationType) {
        modelMapper.map(source, destinationType);
    }
}

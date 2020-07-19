package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.core.dto.AvailabilityDto;
import com.usargis.usargisapi.core.dto.NotificationMessageDto;
import com.usargis.usargisapi.core.dto.TeamDto;
import com.usargis.usargisapi.core.model.Availability;
import com.usargis.usargisapi.core.model.NotificationMessage;
import com.usargis.usargisapi.core.model.Team;
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
        //Availability Create DTO To entity
        PropertyMap<AvailabilityDto.Create, Availability> availabilityCreateToEntityPropertyMap = new PropertyMap<AvailabilityDto.Create, Availability>() {
            protected void configure() {
                skip().setId(null);
                skip().setMission(null);
                skip().setUserInfo(null);
            }
        };
        modelMapper.addMappings(availabilityCreateToEntityPropertyMap);

        //NotificationMessage PostRequest DTO to entity
        PropertyMap<NotificationMessageDto.PostRequest, NotificationMessage> notificationMessagePostRequestToEntityPropertyMap =
                new PropertyMap<NotificationMessageDto.PostRequest, NotificationMessage>() {
                    protected void configure() {
                        map().getId().setContentType(source.getContentType());
                    }
                };
        modelMapper.addMappings(notificationMessagePostRequestToEntityPropertyMap);

        //NotificationMessage entity to Response DTO
        PropertyMap<NotificationMessage, NotificationMessageDto.Response> notificationMessageEntityToResponsePropertyMap =
                new PropertyMap<NotificationMessage, NotificationMessageDto.Response>() {
                    protected void configure() {
                        map().setContentType(source.getId().getContentType());
                    }
                };
        modelMapper.addMappings(notificationMessageEntityToResponsePropertyMap);

        //Team PostRequest DTO to entity
        PropertyMap<TeamDto.PostRequest, Team> teamPostRequestToEntityPropertyMap =
                new PropertyMap<TeamDto.PostRequest, Team>() {
                    protected void configure() {
                        skip().setId(null);
                        skip().setMission(null);
                    }
                };
        modelMapper.addMappings(teamPostRequestToEntityPropertyMap);


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

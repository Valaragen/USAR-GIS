package com.usargis.usargisapi.service.impl;

import com.usargis.usargisapi.service.contract.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperServiceImpl implements ModelMapperService {

    private ModelMapper modelMapper;

    @Autowired
    public ModelMapperServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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

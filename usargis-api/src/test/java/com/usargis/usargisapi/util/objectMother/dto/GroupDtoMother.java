package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.GroupDto;

public class GroupDtoMother {
    public static GroupDto.GroupPostRequest.GroupPostRequestBuilder postRequestSample() {
        return GroupDto.GroupPostRequest.builder()
                .name("sampleGroup name");
    }
}

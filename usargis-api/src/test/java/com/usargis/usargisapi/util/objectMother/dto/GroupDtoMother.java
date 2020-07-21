package com.usargis.usargisapi.util.objectMother.dto;

import com.usargis.usargisapi.core.dto.GroupDto;

public class GroupDtoMother {
    public static GroupDto.PostRequest.PostRequestBuilder postRequestSample() {
        return GroupDto.PostRequest.builder()
                .name("sampleGroup name");
    }

    public static GroupDto.PostRequest.PostRequestBuilder responseSample() {
        return GroupDto.PostRequest.builder()
                .name("sampleGroup name");
    }
}

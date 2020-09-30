//package com.usargis.usargisapi.web.controller.converter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.fge.jsonpatch.JsonPatch;
//import org.apache.commons.lang.NotImplementedException;
//import org.springframework.http.HttpInputMessage;
//import org.springframework.http.HttpOutputMessage;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.AbstractHttpMessageConverter;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.http.converter.HttpMessageNotWritableException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JsonPatchHttpMessageConverter extends AbstractHttpMessageConverter<JsonPatch> {
//
//    public JsonPatchHttpMessageConverter() {
//        super(MediaType.valueOf("application/json-patch+json"));
//    }
//
//    @Override
//    protected JsonPatch readInternal(Class<? extends JsonPatch> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
//
//        final ObjectMapper mapper = new ObjectMapper();
//        try {
//            return mapper.readValue(inputMessage.getBody(), JsonPatch.class);
//        } catch (Exception e) {
//            throw new HttpMessageNotReadableException(e.getMessage(), inputMessage);
//        }
//    }
//
//    @Override
//    protected void writeInternal(JsonPatch jsonPatch, HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
//        throw new NotImplementedException("The write Json patch is not implemented");
//    }
//
//    @Override
//    protected boolean supports(Class<?> clazz) {
//        return JsonPatch.class.isAssignableFrom(clazz);
//    }
//
//}
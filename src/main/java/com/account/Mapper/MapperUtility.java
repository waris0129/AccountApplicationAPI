package com.account.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtility {

    private final ModelMapper modelMapper;

    public MapperUtility(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public <T> T convert(Object from, T to){
        return modelMapper.map(from,(Type)to);
    }


}

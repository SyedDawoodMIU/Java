package com.waa.assignments.helper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListMapper {

    @Autowired
    ModelMapper modelMapper;

    /**
     *
     * This method will traverse through a list and map all the elements with a Dto or vice versa
     *
     * @param list This is the list of elements that you want to convert to.
     * @param convertTo This is the type of class that it will convert to.
     * @return This will return a generic type holding a list of converted elements (NOTE: Downcast to the wiling type when you call the method)
     */
    public <T, E> List<E> mapList(List<T> list, Class<E> convertTo){
        return list.stream()
                .map(e -> modelMapper.map(e, convertTo))
                .collect(Collectors.toList());
    }
}

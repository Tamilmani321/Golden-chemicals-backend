package com.gld.mapper;

import org.mapstruct.Mapper;

import com.gld.dto.UserDto;
import com.gld.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

//	UserMapper userMapper = Mappers.getMapper(UserMapper.class);

	UserDto entityToDto(User user);

	User dtoToEntity(UserDto userDto);

}

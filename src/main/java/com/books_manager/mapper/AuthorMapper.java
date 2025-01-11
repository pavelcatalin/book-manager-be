package com.books_manager.mapper;

import com.books_manager.dto.AuthorDTO;
import com.books_manager.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(source = "name", target = "name")
    AuthorDTO toDto(Author author);

    @Mapping(source = "id", target = "id")
    Author toEntity(AuthorDTO authorDTO);
}

package ru.test.authorization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.test.authorization.dto.AuthorizationFormDto;
import ru.test.authorization.dto.AuthorizationFormResponseDto;
import ru.test.authorization.entity.AuthorizationFormEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorizationFormMapper {

    @Mapping(target = "id", ignore = true)
    AuthorizationFormEntity toEntity(AuthorizationFormDto dto);

    AuthorizationFormResponseDto toAuthorizationFormDto(AuthorizationFormEntity entity);
}

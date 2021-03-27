package fr.polytech.info4.service.mapper;


import fr.polytech.info4.domain.*;
import fr.polytech.info4.service.dto.CoursierDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Coursier} and its DTO {@link CoursierDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CoursierMapper extends EntityMapper<CoursierDTO, Coursier> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CoursierDTO toDto(Coursier coursier);

    @Mapping(source = "userId", target = "user")
    Coursier toEntity(CoursierDTO coursierDTO);

    default Coursier fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coursier coursier = new Coursier();
        coursier.setId(id);
        return coursier;
    }
}

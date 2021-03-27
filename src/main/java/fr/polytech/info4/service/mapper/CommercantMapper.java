package fr.polytech.info4.service.mapper;


import fr.polytech.info4.domain.*;
import fr.polytech.info4.service.dto.CommercantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commercant} and its DTO {@link CommercantDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommercantMapper extends EntityMapper<CommercantDTO, Commercant> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    CommercantDTO toDto(Commercant commercant);

    @Mapping(source = "userId", target = "user")
    Commercant toEntity(CommercantDTO commercantDTO);

    default Commercant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commercant commercant = new Commercant();
        commercant.setId(id);
        return commercant;
    }
}

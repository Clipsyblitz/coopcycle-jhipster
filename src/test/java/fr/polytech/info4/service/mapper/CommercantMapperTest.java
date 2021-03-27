package fr.polytech.info4.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommercantMapperTest {

    private CommercantMapper commercantMapper;

    @BeforeEach
    public void setUp() {
        commercantMapper = new CommercantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commercantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commercantMapper.fromId(null)).isNull();
    }
}

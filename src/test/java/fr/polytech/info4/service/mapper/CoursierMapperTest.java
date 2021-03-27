package fr.polytech.info4.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CoursierMapperTest {

    private CoursierMapper coursierMapper;

    @BeforeEach
    public void setUp() {
        coursierMapper = new CoursierMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(coursierMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(coursierMapper.fromId(null)).isNull();
    }
}

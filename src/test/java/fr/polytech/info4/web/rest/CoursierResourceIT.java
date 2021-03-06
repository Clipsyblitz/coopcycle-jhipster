package fr.polytech.info4.web.rest;

import fr.polytech.info4.CoopcycleApp;
import fr.polytech.info4.domain.Coursier;
import fr.polytech.info4.repository.CoursierRepository;
import fr.polytech.info4.service.CoursierService;
import fr.polytech.info4.service.dto.CoursierDTO;
import fr.polytech.info4.service.mapper.CoursierMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CoursierResource} REST controller.
 */
@SpringBootTest(classes = CoopcycleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CoursierResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSPORT_MEAN = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPORT_MEAN = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private CoursierRepository coursierRepository;

    @Autowired
    private CoursierMapper coursierMapper;

    @Autowired
    private CoursierService coursierService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoursierMockMvc;

    private Coursier coursier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coursier createEntity(EntityManager em) {
        Coursier coursier = new Coursier()
            .name(DEFAULT_NAME)
            .surname(DEFAULT_SURNAME)
            .transportMean(DEFAULT_TRANSPORT_MEAN)
            .phone(DEFAULT_PHONE);
        return coursier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Coursier createUpdatedEntity(EntityManager em) {
        Coursier coursier = new Coursier()
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .transportMean(UPDATED_TRANSPORT_MEAN)
            .phone(UPDATED_PHONE);
        return coursier;
    }

    @BeforeEach
    public void initTest() {
        coursier = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoursier() throws Exception {
        int databaseSizeBeforeCreate = coursierRepository.findAll().size();
        // Create the Coursier
        CoursierDTO coursierDTO = coursierMapper.toDto(coursier);
        restCoursierMockMvc.perform(post("/api/coursiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursierDTO)))
            .andExpect(status().isCreated());

        // Validate the Coursier in the database
        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeCreate + 1);
        Coursier testCoursier = coursierList.get(coursierList.size() - 1);
        assertThat(testCoursier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCoursier.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testCoursier.getTransportMean()).isEqualTo(DEFAULT_TRANSPORT_MEAN);
        assertThat(testCoursier.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createCoursierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursierRepository.findAll().size();

        // Create the Coursier with an existing ID
        coursier.setId(1L);
        CoursierDTO coursierDTO = coursierMapper.toDto(coursier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursierMockMvc.perform(post("/api/coursiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coursier in the database
        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursierRepository.findAll().size();
        // set the field null
        coursier.setName(null);

        // Create the Coursier, which fails.
        CoursierDTO coursierDTO = coursierMapper.toDto(coursier);


        restCoursierMockMvc.perform(post("/api/coursiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursierDTO)))
            .andExpect(status().isBadRequest());

        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursierRepository.findAll().size();
        // set the field null
        coursier.setSurname(null);

        // Create the Coursier, which fails.
        CoursierDTO coursierDTO = coursierMapper.toDto(coursier);


        restCoursierMockMvc.perform(post("/api/coursiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursierDTO)))
            .andExpect(status().isBadRequest());

        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransportMeanIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursierRepository.findAll().size();
        // set the field null
        coursier.setTransportMean(null);

        // Create the Coursier, which fails.
        CoursierDTO coursierDTO = coursierMapper.toDto(coursier);


        restCoursierMockMvc.perform(post("/api/coursiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursierDTO)))
            .andExpect(status().isBadRequest());

        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursierRepository.findAll().size();
        // set the field null
        coursier.setPhone(null);

        // Create the Coursier, which fails.
        CoursierDTO coursierDTO = coursierMapper.toDto(coursier);


        restCoursierMockMvc.perform(post("/api/coursiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursierDTO)))
            .andExpect(status().isBadRequest());

        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCoursiers() throws Exception {
        // Initialize the database
        coursierRepository.saveAndFlush(coursier);

        // Get all the coursierList
        restCoursierMockMvc.perform(get("/api/coursiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coursier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME)))
            .andExpect(jsonPath("$.[*].transportMean").value(hasItem(DEFAULT_TRANSPORT_MEAN)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getCoursier() throws Exception {
        // Initialize the database
        coursierRepository.saveAndFlush(coursier);

        // Get the coursier
        restCoursierMockMvc.perform(get("/api/coursiers/{id}", coursier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coursier.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME))
            .andExpect(jsonPath("$.transportMean").value(DEFAULT_TRANSPORT_MEAN))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }
    @Test
    @Transactional
    public void getNonExistingCoursier() throws Exception {
        // Get the coursier
        restCoursierMockMvc.perform(get("/api/coursiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoursier() throws Exception {
        // Initialize the database
        coursierRepository.saveAndFlush(coursier);

        int databaseSizeBeforeUpdate = coursierRepository.findAll().size();

        // Update the coursier
        Coursier updatedCoursier = coursierRepository.findById(coursier.getId()).get();
        // Disconnect from session so that the updates on updatedCoursier are not directly saved in db
        em.detach(updatedCoursier);
        updatedCoursier
            .name(UPDATED_NAME)
            .surname(UPDATED_SURNAME)
            .transportMean(UPDATED_TRANSPORT_MEAN)
            .phone(UPDATED_PHONE);
        CoursierDTO coursierDTO = coursierMapper.toDto(updatedCoursier);

        restCoursierMockMvc.perform(put("/api/coursiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursierDTO)))
            .andExpect(status().isOk());

        // Validate the Coursier in the database
        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeUpdate);
        Coursier testCoursier = coursierList.get(coursierList.size() - 1);
        assertThat(testCoursier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCoursier.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testCoursier.getTransportMean()).isEqualTo(UPDATED_TRANSPORT_MEAN);
        assertThat(testCoursier.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingCoursier() throws Exception {
        int databaseSizeBeforeUpdate = coursierRepository.findAll().size();

        // Create the Coursier
        CoursierDTO coursierDTO = coursierMapper.toDto(coursier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursierMockMvc.perform(put("/api/coursiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(coursierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coursier in the database
        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCoursier() throws Exception {
        // Initialize the database
        coursierRepository.saveAndFlush(coursier);

        int databaseSizeBeforeDelete = coursierRepository.findAll().size();

        // Delete the coursier
        restCoursierMockMvc.perform(delete("/api/coursiers/{id}", coursier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Coursier> coursierList = coursierRepository.findAll();
        assertThat(coursierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

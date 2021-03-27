package fr.polytech.info4.web.rest;

import fr.polytech.info4.CoopcycleApp;
import fr.polytech.info4.domain.Commercant;
import fr.polytech.info4.repository.CommercantRepository;
import fr.polytech.info4.service.CommercantService;
import fr.polytech.info4.service.dto.CommercantDTO;
import fr.polytech.info4.service.mapper.CommercantMapper;

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
 * Integration tests for the {@link CommercantResource} REST controller.
 */
@SpringBootTest(classes = CoopcycleApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CommercantResourceIT {

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private CommercantRepository commercantRepository;

    @Autowired
    private CommercantMapper commercantMapper;

    @Autowired
    private CommercantService commercantService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommercantMockMvc;

    private Commercant commercant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commercant createEntity(EntityManager em) {
        Commercant commercant = new Commercant()
            .companyName(DEFAULT_COMPANY_NAME)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE);
        return commercant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commercant createUpdatedEntity(EntityManager em) {
        Commercant commercant = new Commercant()
            .companyName(UPDATED_COMPANY_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE);
        return commercant;
    }

    @BeforeEach
    public void initTest() {
        commercant = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommercant() throws Exception {
        int databaseSizeBeforeCreate = commercantRepository.findAll().size();
        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);
        restCommercantMockMvc.perform(post("/api/commercants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isCreated());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeCreate + 1);
        Commercant testCommercant = commercantList.get(commercantList.size() - 1);
        assertThat(testCommercant.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCommercant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCommercant.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createCommercantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commercantRepository.findAll().size();

        // Create the Commercant with an existing ID
        commercant.setId(1L);
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommercantMockMvc.perform(post("/api/commercants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCompanyNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercantRepository.findAll().size();
        // set the field null
        commercant.setCompanyName(null);

        // Create the Commercant, which fails.
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);


        restCommercantMockMvc.perform(post("/api/commercants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isBadRequest());

        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercantRepository.findAll().size();
        // set the field null
        commercant.setAddress(null);

        // Create the Commercant, which fails.
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);


        restCommercantMockMvc.perform(post("/api/commercants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isBadRequest());

        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = commercantRepository.findAll().size();
        // set the field null
        commercant.setPhone(null);

        // Create the Commercant, which fails.
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);


        restCommercantMockMvc.perform(post("/api/commercants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isBadRequest());

        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommercants() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        // Get all the commercantList
        restCommercantMockMvc.perform(get("/api/commercants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commercant.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getCommercant() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        // Get the commercant
        restCommercantMockMvc.perform(get("/api/commercants/{id}", commercant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commercant.getId().intValue()))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }
    @Test
    @Transactional
    public void getNonExistingCommercant() throws Exception {
        // Get the commercant
        restCommercantMockMvc.perform(get("/api/commercants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommercant() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();

        // Update the commercant
        Commercant updatedCommercant = commercantRepository.findById(commercant.getId()).get();
        // Disconnect from session so that the updates on updatedCommercant are not directly saved in db
        em.detach(updatedCommercant);
        updatedCommercant
            .companyName(UPDATED_COMPANY_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE);
        CommercantDTO commercantDTO = commercantMapper.toDto(updatedCommercant);

        restCommercantMockMvc.perform(put("/api/commercants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isOk());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
        Commercant testCommercant = commercantList.get(commercantList.size() - 1);
        assertThat(testCommercant.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCommercant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCommercant.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingCommercant() throws Exception {
        int databaseSizeBeforeUpdate = commercantRepository.findAll().size();

        // Create the Commercant
        CommercantDTO commercantDTO = commercantMapper.toDto(commercant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommercantMockMvc.perform(put("/api/commercants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commercantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commercant in the database
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommercant() throws Exception {
        // Initialize the database
        commercantRepository.saveAndFlush(commercant);

        int databaseSizeBeforeDelete = commercantRepository.findAll().size();

        // Delete the commercant
        restCommercantMockMvc.perform(delete("/api/commercants/{id}", commercant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commercant> commercantList = commercantRepository.findAll();
        assertThat(commercantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

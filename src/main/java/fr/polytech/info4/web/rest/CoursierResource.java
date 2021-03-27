package fr.polytech.info4.web.rest;

import fr.polytech.info4.service.CoursierService;
import fr.polytech.info4.web.rest.errors.BadRequestAlertException;
import fr.polytech.info4.service.dto.CoursierDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.polytech.info4.domain.Coursier}.
 */
@RestController
@RequestMapping("/api")
public class CoursierResource {

    private final Logger log = LoggerFactory.getLogger(CoursierResource.class);

    private static final String ENTITY_NAME = "coursier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoursierService coursierService;

    public CoursierResource(CoursierService coursierService) {
        this.coursierService = coursierService;
    }

    /**
     * {@code POST  /coursiers} : Create a new coursier.
     *
     * @param coursierDTO the coursierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coursierDTO, or with status {@code 400 (Bad Request)} if the coursier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coursiers")
    public ResponseEntity<CoursierDTO> createCoursier(@Valid @RequestBody CoursierDTO coursierDTO) throws URISyntaxException {
        log.debug("REST request to save Coursier : {}", coursierDTO);
        if (coursierDTO.getId() != null) {
            throw new BadRequestAlertException("A new coursier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoursierDTO result = coursierService.save(coursierDTO);
        return ResponseEntity.created(new URI("/api/coursiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coursiers} : Updates an existing coursier.
     *
     * @param coursierDTO the coursierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursierDTO,
     * or with status {@code 400 (Bad Request)} if the coursierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coursierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coursiers")
    public ResponseEntity<CoursierDTO> updateCoursier(@Valid @RequestBody CoursierDTO coursierDTO) throws URISyntaxException {
        log.debug("REST request to update Coursier : {}", coursierDTO);
        if (coursierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoursierDTO result = coursierService.save(coursierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursierDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /coursiers} : get all the coursiers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coursiers in body.
     */
    @GetMapping("/coursiers")
    public ResponseEntity<List<CoursierDTO>> getAllCoursiers(Pageable pageable) {
        log.debug("REST request to get a page of Coursiers");
        Page<CoursierDTO> page = coursierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /coursiers/:id} : get the "id" coursier.
     *
     * @param id the id of the coursierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coursierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coursiers/{id}")
    public ResponseEntity<CoursierDTO> getCoursier(@PathVariable Long id) {
        log.debug("REST request to get Coursier : {}", id);
        Optional<CoursierDTO> coursierDTO = coursierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coursierDTO);
    }

    /**
     * {@code DELETE  /coursiers/:id} : delete the "id" coursier.
     *
     * @param id the id of the coursierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coursiers/{id}")
    public ResponseEntity<Void> deleteCoursier(@PathVariable Long id) {
        log.debug("REST request to delete Coursier : {}", id);
        coursierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

package fr.polytech.info4.web.rest;

import fr.polytech.info4.service.CommercantService;
import fr.polytech.info4.web.rest.errors.BadRequestAlertException;
import fr.polytech.info4.service.dto.CommercantDTO;

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
 * REST controller for managing {@link fr.polytech.info4.domain.Commercant}.
 */
@RestController
@RequestMapping("/api")
public class CommercantResource {

    private final Logger log = LoggerFactory.getLogger(CommercantResource.class);

    private static final String ENTITY_NAME = "commercant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommercantService commercantService;

    public CommercantResource(CommercantService commercantService) {
        this.commercantService = commercantService;
    }

    /**
     * {@code POST  /commercants} : Create a new commercant.
     *
     * @param commercantDTO the commercantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commercantDTO, or with status {@code 400 (Bad Request)} if the commercant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commercants")
    public ResponseEntity<CommercantDTO> createCommercant(@Valid @RequestBody CommercantDTO commercantDTO) throws URISyntaxException {
        log.debug("REST request to save Commercant : {}", commercantDTO);
        if (commercantDTO.getId() != null) {
            throw new BadRequestAlertException("A new commercant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommercantDTO result = commercantService.save(commercantDTO);
        return ResponseEntity.created(new URI("/api/commercants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commercants} : Updates an existing commercant.
     *
     * @param commercantDTO the commercantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commercantDTO,
     * or with status {@code 400 (Bad Request)} if the commercantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commercantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commercants")
    public ResponseEntity<CommercantDTO> updateCommercant(@Valid @RequestBody CommercantDTO commercantDTO) throws URISyntaxException {
        log.debug("REST request to update Commercant : {}", commercantDTO);
        if (commercantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommercantDTO result = commercantService.save(commercantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commercantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commercants} : get all the commercants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commercants in body.
     */
    @GetMapping("/commercants")
    public ResponseEntity<List<CommercantDTO>> getAllCommercants(Pageable pageable) {
        log.debug("REST request to get a page of Commercants");
        Page<CommercantDTO> page = commercantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commercants/:id} : get the "id" commercant.
     *
     * @param id the id of the commercantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commercantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commercants/{id}")
    public ResponseEntity<CommercantDTO> getCommercant(@PathVariable Long id) {
        log.debug("REST request to get Commercant : {}", id);
        Optional<CommercantDTO> commercantDTO = commercantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commercantDTO);
    }

    /**
     * {@code DELETE  /commercants/:id} : delete the "id" commercant.
     *
     * @param id the id of the commercantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commercants/{id}")
    public ResponseEntity<Void> deleteCommercant(@PathVariable Long id) {
        log.debug("REST request to delete Commercant : {}", id);
        commercantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}

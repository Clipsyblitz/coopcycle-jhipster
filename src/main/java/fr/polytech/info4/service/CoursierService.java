package fr.polytech.info4.service;

import fr.polytech.info4.service.dto.CoursierDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.info4.domain.Coursier}.
 */
public interface CoursierService {

    /**
     * Save a coursier.
     *
     * @param coursierDTO the entity to save.
     * @return the persisted entity.
     */
    CoursierDTO save(CoursierDTO coursierDTO);

    /**
     * Get all the coursiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CoursierDTO> findAll(Pageable pageable);


    /**
     * Get the "id" coursier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoursierDTO> findOne(Long id);

    /**
     * Delete the "id" coursier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

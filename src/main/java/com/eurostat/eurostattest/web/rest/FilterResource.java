package com.eurostat.eurostattest.web.rest;

import com.eurostat.eurostattest.domain.Filter;
import com.eurostat.eurostattest.repository.FilterRepository;
import com.eurostat.eurostattest.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.eurostat.eurostattest.domain.Filter}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FilterResource {

    private final Logger log = LoggerFactory.getLogger(FilterResource.class);

    private static final String ENTITY_NAME = "eurostatTestFilter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FilterRepository filterRepository;

    public FilterResource(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    /**
     * {@code POST  /filters} : Create a new filter.
     *
     * @param filter the filter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new filter, or with status {@code 400 (Bad Request)} if the filter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/filters")
    public ResponseEntity<Filter> createFilter(@RequestBody Filter filter) throws URISyntaxException {
        log.debug("REST request to save Filter : {}", filter);
        if (filter.getId() != null) {
            throw new BadRequestAlertException("A new filter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Filter result = filterRepository.save(filter);
        return ResponseEntity
            .created(new URI("/api/filters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /filters/:id} : Updates an existing filter.
     *
     * @param id the id of the filter to save.
     * @param filter the filter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filter,
     * or with status {@code 400 (Bad Request)} if the filter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the filter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/filters/{id}")
    public ResponseEntity<Filter> updateFilter(@PathVariable(value = "id", required = false) final Long id, @RequestBody Filter filter)
        throws URISyntaxException {
        log.debug("REST request to update Filter : {}, {}", id, filter);
        if (filter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Filter result = filterRepository.save(filter);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, filter.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /filters/:id} : Partial updates given fields of an existing filter, field will ignore if it is null
     *
     * @param id the id of the filter to save.
     * @param filter the filter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated filter,
     * or with status {@code 400 (Bad Request)} if the filter is not valid,
     * or with status {@code 404 (Not Found)} if the filter is not found,
     * or with status {@code 500 (Internal Server Error)} if the filter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/filters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Filter> partialUpdateFilter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Filter filter
    ) throws URISyntaxException {
        log.debug("REST request to partial update Filter partially : {}, {}", id, filter);
        if (filter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, filter.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!filterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Filter> result = filterRepository
            .findById(filter.getId())
            .map(existingFilter -> {
                if (filter.getConfiguration() != null) {
                    existingFilter.setConfiguration(filter.getConfiguration());
                }

                return existingFilter;
            })
            .map(filterRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, filter.getId().toString())
        );
    }

    /**
     * {@code GET  /filters} : get all the filters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of filters in body.
     */
    @GetMapping("/filters")
    public List<Filter> getAllFilters() {
        log.debug("REST request to get all Filters");
        return filterRepository.findAll();
    }

    /**
     * {@code GET  /filters/:id} : get the "id" filter.
     *
     * @param id the id of the filter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the filter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/filters/{id}")
    public ResponseEntity<Filter> getFilter(@PathVariable Long id) {
        log.debug("REST request to get Filter : {}", id);
        Optional<Filter> filter = filterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(filter);
    }

    /**
     * {@code DELETE  /filters/:id} : delete the "id" filter.
     *
     * @param id the id of the filter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/filters/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) {
        log.debug("REST request to delete Filter : {}", id);
        filterRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

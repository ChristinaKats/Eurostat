package com.eurostat.eurostattest.web.rest;

import com.eurostat.eurostattest.repository.CrawlerRepository;
import com.eurostat.eurostattest.service.CrawlerService;
import com.eurostat.eurostattest.service.dto.CrawlerDTO;
import com.eurostat.eurostattest.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.eurostat.eurostattest.domain.Crawler}.
 */
@RestController
@RequestMapping("/api")
public class CrawlerResource {

    private final Logger log = LoggerFactory.getLogger(CrawlerResource.class);

    private static final String ENTITY_NAME = "eurostatTestCrawler";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrawlerService crawlerService;

    private final CrawlerRepository crawlerRepository;

    public CrawlerResource(CrawlerService crawlerService, CrawlerRepository crawlerRepository) {
        this.crawlerService = crawlerService;
        this.crawlerRepository = crawlerRepository;
    }

    /**
     * {@code POST  /crawlers} : Create a new crawler.
     *
     * @param crawlerDTO the crawlerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crawlerDTO, or with status {@code 400 (Bad Request)} if the crawler has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/crawlers")
    public ResponseEntity<CrawlerDTO> createCrawler(@Valid @RequestBody CrawlerDTO crawlerDTO) throws URISyntaxException {
        log.debug("REST request to save Crawler : {}", crawlerDTO);
        if (crawlerDTO.getId() != null) {
            throw new BadRequestAlertException("A new crawler cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CrawlerDTO result = crawlerService.save(crawlerDTO);
        return ResponseEntity
            .created(new URI("/api/crawlers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /crawlers/:id} : Updates an existing crawler.
     *
     * @param id the id of the crawlerDTO to save.
     * @param crawlerDTO the crawlerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crawlerDTO,
     * or with status {@code 400 (Bad Request)} if the crawlerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crawlerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/crawlers/{id}")
    public ResponseEntity<CrawlerDTO> updateCrawler(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CrawlerDTO crawlerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Crawler : {}, {}", id, crawlerDTO);
        if (crawlerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crawlerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crawlerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CrawlerDTO result = crawlerService.save(crawlerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, crawlerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /crawlers/:id} : Partial updates given fields of an existing crawler, field will ignore if it is null
     *
     * @param id the id of the crawlerDTO to save.
     * @param crawlerDTO the crawlerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crawlerDTO,
     * or with status {@code 400 (Bad Request)} if the crawlerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the crawlerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the crawlerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/crawlers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CrawlerDTO> partialUpdateCrawler(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CrawlerDTO crawlerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Crawler partially : {}, {}", id, crawlerDTO);
        if (crawlerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crawlerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crawlerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CrawlerDTO> result = crawlerService.partialUpdate(crawlerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, crawlerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /crawlers} : get all the crawlers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crawlers in body.
     */
    @GetMapping("/crawlers")
    public ResponseEntity<List<CrawlerDTO>> getAllCrawlers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Crawlers");
        Page<CrawlerDTO> page = crawlerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crawlers/:id} : get the "id" crawler.
     *
     * @param id the id of the crawlerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crawlerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/crawlers/{id}")
    public ResponseEntity<CrawlerDTO> getCrawler(@PathVariable Long id) {
        log.debug("REST request to get Crawler : {}", id);
        Optional<CrawlerDTO> crawlerDTO = crawlerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(crawlerDTO);
    }

    /**
     * {@code DELETE  /crawlers/:id} : delete the "id" crawler.
     *
     * @param id the id of the crawlerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/crawlers/{id}")
    public ResponseEntity<Void> deleteCrawler(@PathVariable Long id) {
        log.debug("REST request to delete Crawler : {}", id);
        crawlerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

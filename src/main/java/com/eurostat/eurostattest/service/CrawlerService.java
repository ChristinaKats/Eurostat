package com.eurostat.eurostattest.service;

import com.eurostat.eurostattest.service.dto.CrawlerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.eurostat.eurostattest.domain.Crawler}.
 */
public interface CrawlerService {
    /**
     * Save a crawler.
     *
     * @param crawlerDTO the entity to save.
     * @return the persisted entity.
     */
    CrawlerDTO save(CrawlerDTO crawlerDTO);

    /**
     * Partially updates a crawler.
     *
     * @param crawlerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CrawlerDTO> partialUpdate(CrawlerDTO crawlerDTO);

    /**
     * Get all the crawlers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CrawlerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" crawler.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CrawlerDTO> findOne(Long id);

    /**
     * Delete the "id" crawler.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

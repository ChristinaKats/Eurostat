package com.eurostat.eurostattest.service.impl;

import com.eurostat.eurostattest.domain.Crawler;
import com.eurostat.eurostattest.domain.Filter;
import com.eurostat.eurostattest.repository.CrawlerRepository;
import com.eurostat.eurostattest.service.CrawlerService;
import com.eurostat.eurostattest.service.dto.CrawlerDTO;
import com.eurostat.eurostattest.service.mapper.CrawlerMapper;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Crawler}.
 */
@Service
@Transactional
public class CrawlerServiceImpl implements CrawlerService {

    private final Logger log = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    private final CrawlerRepository crawlerRepository;

    private final CrawlerMapper crawlerMapper;

    public CrawlerServiceImpl(CrawlerRepository crawlerRepository, CrawlerMapper crawlerMapper) {
        this.crawlerRepository = crawlerRepository;
        this.crawlerMapper = crawlerMapper;
    }

    @Override
    public CrawlerDTO save(CrawlerDTO crawlerDTO) {
        log.debug("Request to save Crawler : {}", crawlerDTO);

        Crawler crawler = crawlerMapper.toEntity(crawlerDTO);
        crawler.setFilters(crawlerDTO.getFilters());
        crawler = crawlerRepository.save(crawler);
        return crawlerMapper.toDto(crawler);
    }

    @Override
    public Optional<CrawlerDTO> partialUpdate(CrawlerDTO crawlerDTO) {
        log.debug("Request to partially update Crawler : {}", crawlerDTO);

        return crawlerRepository
            .findById(crawlerDTO.getId())
            .map(existingCrawler -> {
                crawlerMapper.partialUpdate(existingCrawler, crawlerDTO);

                return existingCrawler;
            })
            .map(crawlerRepository::save)
            .map(crawlerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CrawlerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Crawlers");
        return crawlerRepository.findAll(pageable).map(crawlerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CrawlerDTO> findOne(Long id) {
        log.debug("Request to get Crawler : {}", id);
        return crawlerRepository.findById(id).map(crawlerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Crawler : {}", id);
        crawlerRepository.deleteById(id);
    }
}

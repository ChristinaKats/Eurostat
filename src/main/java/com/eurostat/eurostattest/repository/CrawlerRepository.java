package com.eurostat.eurostattest.repository;

import com.eurostat.eurostattest.domain.Crawler;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Crawler entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CrawlerRepository extends JpaRepository<Crawler, Long> {}

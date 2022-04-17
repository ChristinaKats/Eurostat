package com.eurostat.eurostattest.service.dto;

import net.minidev.json.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.eurostat.eurostattest.domain.Filter} entity.
 */
public class FilterDTO implements Serializable {

    private Long id;

    private String configuration;

    @JsonIgnore
    private CrawlerDTO crawler;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public CrawlerDTO getCrawler() {
        return crawler;
    }

    public void setCrawler(CrawlerDTO crawler) {
        this.crawler = crawler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilterDTO)) {
            return false;
        }

        FilterDTO filterDTO = (FilterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, filterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilterDTO{" +
            "id=" + getId() +
            ", configuration='" + getConfiguration() + "'" +
            ", crawler=" + getCrawler() +
            "}";
    }
}

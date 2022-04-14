package com.eurostat.eurostattest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Filter.
 */
@Entity
@Table(name = "filter")
public class Filter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "configuration")
    private String configuration;

    @ManyToOne
    @JsonIgnoreProperties(value = { "filters" }, allowSetters = true)
    private Crawler filters;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Filter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public Filter configuration(String configuration) {
        this.setConfiguration(configuration);
        return this;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public Crawler getFilters() {
        return this.filters;
    }

    public void setFilters(Crawler crawler) {
        this.filters = crawler;
    }

    public Filter filters(Crawler crawler) {
        this.setFilters(crawler);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Filter)) {
            return false;
        }
        return id != null && id.equals(((Filter) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Filter{" +
            "id=" + getId() +
            ", configuration='" + getConfiguration() + "'" +
            "}";
    }
}

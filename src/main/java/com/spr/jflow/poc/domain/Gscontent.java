package com.spr.jflow.poc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Gscontent.
 */
@Entity
@Table(name = "gscontent")
public class Gscontent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "object_id", nullable = false)
    private String objectId;

    @Column(name = "repo_content_path")
    private String repoContentPath;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "parent_id")
    private String parentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public Gscontent objectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getRepoContentPath() {
        return repoContentPath;
    }

    public Gscontent repoContentPath(String repoContentPath) {
        this.repoContentPath = repoContentPath;
        return this;
    }

    public void setRepoContentPath(String repoContentPath) {
        this.repoContentPath = repoContentPath;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Gscontent creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getParentId() {
        return parentId;
    }

    public Gscontent parentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gscontent gscontent = (Gscontent) o;
        if (gscontent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gscontent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gscontent{" +
            "id=" + getId() +
            ", objectId='" + getObjectId() + "'" +
            ", repoContentPath='" + getRepoContentPath() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", parentId='" + getParentId() + "'" +
            "}";
    }
}

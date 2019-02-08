package com.spr.jflow.poc.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Titledata.
 */
@Entity
@Table(name = "titledata")
public class Titledata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "object_id")
    private String objectId;

    @NotNull
    @Size(max = 256)
    @Column(name = "object_name", length = 256, nullable = false)
    private String objectName;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "product_title")
    private String productTitle;

    @Column(name = "short_title")
    private String shortTitle;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

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

    public Titledata objectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public Titledata objectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getProductId() {
        return productId;
    }

    public Titledata productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public Titledata productTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public Titledata shortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        return this;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Titledata creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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
        Titledata titledata = (Titledata) o;
        if (titledata.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), titledata.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Titledata{" +
            "id=" + getId() +
            ", objectId='" + getObjectId() + "'" +
            ", objectName='" + getObjectName() + "'" +
            ", productId='" + getProductId() + "'" +
            ", productTitle='" + getProductTitle() + "'" +
            ", shortTitle='" + getShortTitle() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}

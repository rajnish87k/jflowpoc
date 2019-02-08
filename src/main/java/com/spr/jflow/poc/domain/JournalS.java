package com.spr.jflow.poc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.spr.jflow.poc.domain.enumeration.Language;

import com.spr.jflow.poc.domain.enumeration.CompanyGroup;

/**
 * A JournalS.
 */
@Entity
@Table(name = "journal_s")
public class JournalS implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "object_id")
    private String objectId;

    @NotNull
    @Column(name = "object_name", nullable = false)
    private String objectName;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "title")
    private String title;

    @Column(name = "short_title")
    private String shortTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "primary_language")
    private Language primaryLanguage;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_group")
    private CompanyGroup companyGroup;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publishing_segment")
    private String publishingSegment;

    @Column(name = "imprint")
    private String imprint;

    @Column(name = "medium")
    private String medium;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @OneToMany(mappedBy = "required")
    private Set<JournalR> journalRS = new HashSet<>();

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

    public JournalS objectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public JournalS objectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getProductId() {
        return productId;
    }

    public JournalS productId(String productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public JournalS title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public JournalS shortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        return this;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public Language getPrimaryLanguage() {
        return primaryLanguage;
    }

    public JournalS primaryLanguage(Language primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
        return this;
    }

    public void setPrimaryLanguage(Language primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public CompanyGroup getCompanyGroup() {
        return companyGroup;
    }

    public JournalS companyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
        return this;
    }

    public void setCompanyGroup(CompanyGroup companyGroup) {
        this.companyGroup = companyGroup;
    }

    public String getPublisher() {
        return publisher;
    }

    public JournalS publisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishingSegment() {
        return publishingSegment;
    }

    public JournalS publishingSegment(String publishingSegment) {
        this.publishingSegment = publishingSegment;
        return this;
    }

    public void setPublishingSegment(String publishingSegment) {
        this.publishingSegment = publishingSegment;
    }

    public String getImprint() {
        return imprint;
    }

    public JournalS imprint(String imprint) {
        this.imprint = imprint;
        return this;
    }

    public void setImprint(String imprint) {
        this.imprint = imprint;
    }

    public String getMedium() {
        return medium;
    }

    public JournalS medium(String medium) {
        this.medium = medium;
        return this;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public JournalS creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Set<JournalR> getJournalRS() {
        return journalRS;
    }

    public JournalS journalRS(Set<JournalR> journalRS) {
        this.journalRS = journalRS;
        return this;
    }

    public JournalS addJournalR(JournalR journalR) {
        this.journalRS.add(journalR);
        journalR.setRequired(this);
        return this;
    }

    public JournalS removeJournalR(JournalR journalR) {
        this.journalRS.remove(journalR);
        journalR.setRequired(null);
        return this;
    }

    public void setJournalRS(Set<JournalR> journalRS) {
        this.journalRS = journalRS;
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
        JournalS journalS = (JournalS) o;
        if (journalS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), journalS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JournalS{" +
            "id=" + getId() +
            ", objectId='" + getObjectId() + "'" +
            ", objectName='" + getObjectName() + "'" +
            ", productId='" + getProductId() + "'" +
            ", title='" + getTitle() + "'" +
            ", shortTitle='" + getShortTitle() + "'" +
            ", primaryLanguage='" + getPrimaryLanguage() + "'" +
            ", companyGroup='" + getCompanyGroup() + "'" +
            ", publisher='" + getPublisher() + "'" +
            ", publishingSegment='" + getPublishingSegment() + "'" +
            ", imprint='" + getImprint() + "'" +
            ", medium='" + getMedium() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}

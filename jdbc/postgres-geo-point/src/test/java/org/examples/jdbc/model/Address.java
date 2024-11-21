package org.examples.jdbc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.examples.jdbc.internal.PGpointType;
import org.hibernate.annotations.Type;
import org.postgresql.geometric.PGpoint;

@Entity
@Table(schema = "tst")
public class Address {
    private Long id;

    PGpoint location;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Type(PGpointType.class)
    public PGpoint getLocation() {
        return location;
    }

    public void setLocation(PGpoint geoPoint) {
        this.location = geoPoint;
    }
}

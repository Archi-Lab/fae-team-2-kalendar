package de.th.koeln.fae.microservice_kalendar.kalender.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/*
Um einer Klasse eine UUID hinzuzufügen, muss diese mit EntityUUID4 extended werden.
Macht die Verwendung von UUIDs explizit.
 */

@MappedSuperclass
public class EntityUUID4 {

    @Id
    private UUID id;

    //region Konstruktoren
    public EntityUUID4(final UUID id) {
        this.id = id;
    }

    public EntityUUID4() {
        this.id = UUID.randomUUID();
    }
    //endregion

    //region Getter,Setter
    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }
    //endregion

    //region Override-Methoden
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EntityUUID4)) {
            return false;
        }

        final EntityUUID4 other = (EntityUUID4) obj;
        final EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.getId(), other.getId());

        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();

        hcb.append(this.getId());

        return hcb.toHashCode();
    }
    //endregion
}

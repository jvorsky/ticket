package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hillel.persistence.entity.util.YesNoConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractModifyEntity<ID extends Serializable> extends AbstractEntity<ID> {

    @Column(name = "create_date")
    @CreationTimestamp
    private Instant createDate;

    @Column(name = "active")
    @Convert(converter = YesNoConverter.class)
    private boolean active = true;
}

package com.zhang.security.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@MappedSuperclass
public class FBEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    // oracle
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wrhpSequence")
//    @SequenceGenerator(name = "wrhpSequence", sequenceName = "SEQ_wrhp", allocationSize = 1)

    private Long id;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        FBEntity that = (FBEntity) obj;

        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

}

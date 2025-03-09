package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "Governrate_Admin")
public class GovernorateAdmin extends Admin {

    @ManyToOne
    @JoinColumn(name = "Governorate_ID", nullable = false)
    private Governorate governorate;


}

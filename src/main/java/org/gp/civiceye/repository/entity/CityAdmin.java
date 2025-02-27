package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "City_Admin")
public class CityAdmin extends Admin{

    @ManyToOne
    @JoinColumn(name = "City_ID", nullable = false)
    private City city;

}


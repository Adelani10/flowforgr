package com.flowforgr.FlowForgr.shared.entity;

import jakarta.persistence.*;
import lombok.*;


@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
public class AppBaseEntity {

    @Id
    @EqualsAndHashCode.Include()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
}

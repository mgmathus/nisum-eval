package com.nisum.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "phones")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    private String number;

    @Column()
    private String citycode;

    @Column()
    private String countrycode;

    @Column()
    private Long userId;
}

package com.example.jsonh2carcomplexdb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Data
@Entity
@Table(name = "warehouse")
public class Warehouse {
    @JsonProperty("_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    @OneToOne(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Location location;
    @JsonProperty("cars")
    @OneToOne(mappedBy = "warehouse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Car car;
}

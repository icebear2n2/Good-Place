package com.icebear2n2.goodplace.store.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "store")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Store {
    @Id @GeneratedValue
    private Long id;
    private String   placeId;
    private String name;
    private String address;
    private String phone;
    private String foodType;
    private String storeImgUrl;

    @Column(name = "coordinate_x")
    private String  coordinateX;
    @Column(name = "coordinate_y")
    private String coordinateY;
}

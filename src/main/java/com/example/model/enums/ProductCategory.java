package com.example.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    ROAD("road", "https://cdn.mos.cms.futurecdn.net/RFYJ7YqbRWcvGHYHrUxNXi.jpg"),
    GRAVEL("gravel", "https://www.canyon.com/on/demandware.static/-/Sites-canyon-master/default/dwaa39ae23/images/full/Full_2021_/2021/Full_2021_grail-cf-sl-8_2717_bu-bk_P5.png"),
    MOUNTAIN("mountain", "https://velo.pl/sites/default/files/products/Accent_bikes_MTB_HERO%20CARBON%20Team1.jpg"),
    E_BIKE("e_bike", "https://www.1enduro.pl/wp-content/uploads/2020/04/giant-trance-e-3-pro-2020-800x437.jpg");

    private final String value;
    private final String imgUrl;
}
package com.example.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    ROAD("road", "https://images.squarespace-cdn.com/content/v1/592be1e48419c2e1dd1a7e44/1615531308791-ZH9WJP9LK9RK2S81YJTN/V3Rs-frozen-blue-1.jpg?format=1500w"),
    GRAVEL("gravel", "https://s14761.pcdn.co/wp-content/uploads/sites/3/2018/06/canyon-inflite-cf-sl-disc-8-team_c1291-1.jpg"),
    MOUNTAIN("mountain", "https://velo.pl/sites/default/files/products/Accent_bikes_MTB_HERO%20CARBON%20Team1.jpg"),
    E_BIKE("e_bike", "https://www.1enduro.pl/wp-content/uploads/2020/04/giant-trance-e-3-pro-2020-800x437.jpg");

    private final String value;
    private final String imgUrl;
}
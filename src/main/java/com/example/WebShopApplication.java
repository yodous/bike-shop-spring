package com.example;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.embeddable.Address;
import com.example.model.enums.ProductCategory;
import com.example.model.enums.Role;
import com.example.repository.CartRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class WebShopApplication {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebShopApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDb() {
        // login: "admin"
        // password: = "admin"
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("$2a$10$GyFVw6rXYWs98HxSo8/KxuBp6eibn2TrdDz2Fx5UpeF9fxiT2cE8m");
        admin.setRole(Role.ADMIN);
        admin.setEnabled(true);

        // login: "user"
        // password: = "password"
        User user = new User("user", "$2a$10$am24QWgB59l/K0ZQIqKL7uMMaY1rDtwEsDLy11BAZJh5Hny0kM//i", "Elton", "John", "elton@john.com", "1111-2222-3333-4444", Role.USER, new Address("Klodzko", "Wiosenna 1", "50-400"));
        user.setEnabled(true);
        userRepository.saveAll(List.of(admin, user));
        cartRepository.save(new Cart(user));

        Product product0 = new Product("Scott Addcit 10", "SCOTT Addict 10 Disc został zaprojektowany od podstaw z myślą o dłuższych dniach w siodle. Dzięki geometrii, która jest mniej skoncentrowana na wyścigach...", ProductCategory.ROAD, 4250, "https://www.scott.pl/photo_product/20468/20468_800.jpg");
        Product product1 = new Product("Canyon Aeroad CF SL 8 Disc", "Uzyskaj wydajność UCI WorldTour na bardziej dostępnym poziomie. Dzięki nowo opracowanej, superczystej ramie aerodynamicznej i zintegrowanemu kokpitowi...", ProductCategory.ROAD, 4500, "https://www.canyon.com/dw/image/v2/BCML_PRD/on/demandware.static/-/Sites-canyon-master/default/dw7ac5474f/images/full/full_2021_/2021/full_2021_aeroad-disc-cf-sl-8_2448_bk_gy_P5.jpg?sw=661&sh=661&sm=fit&sfrm=png&bgcolor=F4F4F4");
        Product product2 = new Product("Accent Hero 29", "Nasz główny „bohater”, czyli topowy full z nowej linii rowerów do cross-country, który da Ci przewagę na każdej górskiej trasie. W tej wersji nie stosujemy żadnych kompromisów. ...", ProductCategory.MOUNTAIN, 4799, "https://velo.pl/sites/default/files/styles/large/public/products/Accent_bikes_MTB_HERO%20CARBON%20Team1.jpg?itok=MWa90kpI");
        Product product3 = new Product("Spark RC 900 SL AXS", "SCOTT Addict 10 Disc został zaprojektowany od podstaw z myślą o dłuższych dniach w siodle. Dzięki geometrii, która jest mniej skoncentrowana na wyścigach...", ProductCategory.MOUNTAIN, 10500, "https://evolutionbikes.it/wp-content/uploads/2021/07/280500_1699174_png_zoom_5.jpg");
        Product product4 = new Product("Addict Gravel Disc", "Całkiem nowy Addict Gravel Disc pozwoli przejechać każdą drogę w każdych warunkach pogodowych. Lekka rama z włókien karbonu HMF, komponenty Syncros....", ProductCategory.GRAVEL, 3999, "https://www.scott.pl/photo_product/26622/26622_3000.jpg");
        Product product5 = new Product("Trek Madone SLR 6 eTap", "Madone SLR 6 eTap definiuje nowy poziom najwyższej jakości roweru wyczynowego, dzięki zapewniającej większy komfort jazdy regulowanej rurze górnej IsoSpeed.", ProductCategory.ROAD, 8050, "https://trek.scene7.com/is/image/TrekBicycleProducts/MadoneSLR6eTap_21_35023_A_Primary?wid=1200");
        Product product6 = new Product("Trek Supercaliber 9.9 XX1 AXS", "Supercaliber 9.9 XX1 AXS to nasz najszybszy, najlżejszy i najsprawniejszy rower na wyścigi XC. Dostępny tylko u nas wbudowany w rurę górną damper IsoStrut to podstawa wydajnego zawieszenia Supercaliber...", ProductCategory.MOUNTAIN, 12000, "https://trek.scene7.com/is/image/TrekBicycleProducts/Supercaliber99XX1AXS_21_33448_A_Primary?$responsive-pjpg$&cache=on,on&wid=800&hei=600");
        Product product7 = new Product("Specialized Turbo Levo Comp 29 2021", "Turbo Levo Comp ma być przede wszystkim rasowym szlakowcem. Korzysta z geometrii, kinematyki i designu naszych najnowszych szlakowców (w tym Stumpjumpera)...", ProductCategory.E_BIKE, 7125, "https://www.greenbike.pl/images/95221-51_LEVO-COMP-29-REDWD-WHTMTN_HERO.jpg");
        Product product8 = new Product("Pinarello Dogma F12 disk", "Trzeba się naprawdę mocno przyjrzeć, żeby zauważyć różnice w stosunku do poprzedniego modelu, Dogmy F10. Na pewno wygładzono linię przejścia między widelcem a ramą, dzięki czemu wygląd F12 jest spójniejszy...", ProductCategory.ROAD, 12500, "https://e-velomania.pl/userdata/public/gfx/3210/15-team-ineos-disk-1200x849.jpg");
        Product product9 = new Product("Colnago V3RS", "Kiedy Colnago stworzył początkowe projekty dla wszystkich nowych V3R, skupił się na swoich doświadczeniach z V1-r i V2-r...", ProductCategory.ROAD, 9500, "https://www.colnago.com/wp-content/uploads/2019/06/Colnago-V3Rs-frozen-blue-RC19-laterale-archivio.jpg");

        productRepository.saveAll(List.of(product0, product1, product2, product3, product4, product5, product6, product7, product8, product9));
    }
}

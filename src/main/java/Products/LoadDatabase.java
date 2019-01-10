package Products;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(final ProductRepository repository){
        return args -> {
            log.info("Preloading " + repository.save(new Product(1l, "Hire Me", 14.00, 3)));
            log.info("Preloading " + repository.save(new Product(2l, "George Xu", 11.35, 2)));
            log.info("Preloading " + repository.save(new Product(3l, "Empty Xu", 29.00, 0)));
        };
    }
}

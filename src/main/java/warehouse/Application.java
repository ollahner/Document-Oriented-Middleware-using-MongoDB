package warehouse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import warehouse.model.Product;
import warehouse.model.Warehouse;
import warehouse.repository.WarehouseRepository;
import warehouse.service.WarehouseService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
    /*
	@Bean
	CommandLineRunner dataLoader(WarehouseService warehouseService, WarehouseRepository repository) {
		return args -> {
			if (repository.count() > 0) {
				return;
			}

			List<Product> sampleProducts = new ArrayList<>(List.of(
					new Product(443175L, "Bio Orangensaft Sonne", "Getraenk", 2500),
					new Product(871895L, "Bio Apfelsaft Gold", "Getraenk", 3420),
					new Product(926885L, "Ariel Waschmittel Color", "Waschmittel", 478),
					new Product(234811L, "Mampfi Katzenfutter Rind", "Tierfutter", 1324),
					new Product(893173L, "Saugstauberbeutel Ingres", "Reinigung", 7390),
					new Product(112233L, "Grillkohle Premium", "Garten", 950),
					new Product(332211L, "Haferdrink Barista", "Getraenk", 1875),
					new Product(998877L, "Universalreiniger Fresh", "Reinigung", 650),
					new Product(554433L, "Hundefutter Lamm", "Tierfutter", 890),
					new Product(776655L, "Waschmittel Sensitiv", "Waschmittel", 520)
			));

			Warehouse warehouse = new Warehouse(
					1L,
					"Linz Central",
					"4010",
					"Linz",
					"Austria",
					Instant.now(),
					sampleProducts
			);

			warehouseService.createWarehouse(warehouse);
		};
	}
    */

}

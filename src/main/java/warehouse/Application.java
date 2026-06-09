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

	@Bean
	CommandLineRunner dataLoader(WarehouseService warehouseService, WarehouseRepository repository) {
		return args -> {
			repository.deleteAll();

			String[] categories = {"Getraenk", "Reinigung", "Tierfutter", "Garten", "Waschmittel", "Snacks"};
			List<Warehouse> warehouses = new ArrayList<>();

			// Erstelle 5 Warehouses
			for (long i = 1; i <= 5; i++) {
				Warehouse w = new Warehouse(
						i,
						"Lager " + i,
						"100" + i,
						"Stadt " + i,
						"Austria",
						Instant.now(),
						new ArrayList<>()
				);
				warehouses.add(w);
			}

			// Erstelle 300 Produkte und verteile sie auf die 5 Warehouses
			for (long i = 1; i <= 300; i++) {
				String category = categories[(int) (i % categories.length)];
				Product p = new Product(
						i,
						"Produkt " + i,
						category,
						(int) (Math.random() * 500) + 10 // Zufällige Menge zwischen 10 und 510
				);

				// Reihum einem Warehouse hinzufügen
				int warehouseIndex = (int) (i % warehouses.size());
				warehouses.get(warehouseIndex).getProductData().add(p);
			}

			// Alle Warehouses speichern
			for (Warehouse w : warehouses) {
				warehouseService.createWarehouse(w);
			}

			System.out.println("[DEBUG_LOG] 5 Warehouses mit insgesamt 300 Produkten wurden initialisiert.");
		};
	}
}

package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import progetto.persistenza.DBManager;
import progetto.persistenza.model.Immobile;

@SpringBootTest
class ProgettoApplicationTest {
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void getImmobili() {
		List<Immobile> immobili=DBManager.getInstance().getImmobileDao().findAll();
		for(Immobile i:immobili) {
			assertNotNull(i.getCategoria());
			System.out.println("Immobile:"+i.toString());
		}
	}

}

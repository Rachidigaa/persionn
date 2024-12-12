package fr.uga.miage.m1.my_projet_g1_10;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class MyProjetG110ApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		// Vérifie que le contexte de l'application est bien chargé
		assertNotNull(applicationContext, "Le contexte de l'application n'a pas pu être chargé.");
	}
}


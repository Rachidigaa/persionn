package fr.uga.miage.m1.my_projet_g1_10.strategiesCreator;

import fr.uga.miage.m1.my_projet_g1_10.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.strategies.*;
import fr.uga.miage.m1.my_projet_g1_10.strategiesCreators.StrategieFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

 class StrategieFactoryTest {

    @Test
     void testGetStrategieDonnantDonnant() {
        assertTrue(StrategieFactory.getStrategie(Strategie.DONNANTDONNANT) instanceof DonnantDonnant);
    }

    @Test
     void testGetStrategieRancunier() {
        assertTrue(StrategieFactory.getStrategie(Strategie.RANCUNIER) instanceof Rancunier);
    }

    @Test
     void testGetStrategieToujoursCooperer() {
        assertTrue(StrategieFactory.getStrategie(Strategie.TOUJOURSCOOPERER) instanceof ToujoursCooperer);
    }

    @Test
     void testGetStrategieToujoursTrahir() {
        assertTrue(StrategieFactory.getStrategie(Strategie.TOUJOURSTRAHIR) instanceof ToujoursTrahir);
    }

    @Test
     void testGetStrategieAleatoire() {
        assertTrue(StrategieFactory.getStrategie(Strategie.ALEATOIRE) instanceof Aleatoire);
    }

    @Test
     void testGetStrategieDonnantDonnantAleatoire() {
        assertTrue(StrategieFactory.getStrategie(Strategie.DONNANTDONNANTALEATOIRE) instanceof DonnantDonnantAleatoire);
    }

    @Test
     void testGetStrategieDonnantPourDeuxDonnantAleatoire() {
        assertTrue(StrategieFactory.getStrategie(Strategie.DONNANTPOURDEUXDONNANTALEATOIRE) instanceof DonnantPourDeuxDonnantAleatoire);
    }

    @Test
     void testGetStrategieDonnantDonnantSoupconneux() {
        assertTrue(StrategieFactory.getStrategie(Strategie.DONNANTDONNANTSOUPCONNEUX) instanceof DonnantDonnantSoupconneux);
    }

    @Test
     void testGetStrategieDonnantPourDeuxDonnant() {
        assertTrue(StrategieFactory.getStrategie(Strategie.DONNANTPOURDEUXDONNANT) instanceof DonnantPourDeuxDonnant);
    }

    @Test
     void testGetStrategiePacificateurNaif() {
        assertTrue(StrategieFactory.getStrategie(Strategie.PACIFICATEURNAIF) instanceof PacificateurNaif);
    }

    @Test
     void testGetStrategieSondeurNaif() {
        assertTrue(StrategieFactory.getStrategie(Strategie.SONDEURNAIF) instanceof SondeurNaif);
    }

    @Test
     void testGetStrategieSondeurRepentant() {
        assertTrue(StrategieFactory.getStrategie(Strategie.SONDEURREPENTANT) instanceof SondeurRepentant);
    }

     @Test
     void testGetStrategieGraduel() {
         assertTrue(StrategieFactory.getStrategie(Strategie.GRADUEL) instanceof Graduel);
     }

    @Test
     void testGetStrategieInvalidThrowsException() {
        assertThrows(NullPointerException.class, () -> StrategieFactory.getStrategie(null));
    }

    @Test
     void testPrivateConstructor() {
        try {
            var constructor = StrategieFactory.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            assertThrows(InvocationTargetException.class, constructor::newInstance);
        } catch (Exception e) {
            fail("Failed to test private constructor.");
        }
    }
}


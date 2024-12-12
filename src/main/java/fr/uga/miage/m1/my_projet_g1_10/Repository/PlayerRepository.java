package fr.uga.miage.m1.my_projet_g1_10.Repository;

import fr.uga.miage.m1.my_projet_g1_10.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    // Ajouter des méthodes de recherche si nécessaire
    Player findPlayerById(long id);
}

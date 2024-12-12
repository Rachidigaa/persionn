package fr.uga.miage.m1.my_projet_g1_10.Repository;

import fr.uga.miage.m1.my_projet_g1_10.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByStatus(String status);
}

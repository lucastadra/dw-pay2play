package dw.Pay2Play.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import dw.Pay2Play.models.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Integer> {
    List<Jogador> findByNomeContaining(String nome);
}
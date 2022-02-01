package dw.Pay2Play.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import dw.Pay2Play.models.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
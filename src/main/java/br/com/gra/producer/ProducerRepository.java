package br.com.gra.producer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerModel, Long> {

    Optional<ProducerModel> findByName(String name);
}

package br.com.gra.producer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gra.producer.model.ProducerModel;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerModel, Long> {

    Optional<ProducerModel> findByName(String name);
}

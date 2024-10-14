package br.com.gra.studio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gra.studio.model.StudioModel;

@Repository
public interface StudioRepository extends JpaRepository<StudioModel, Long> {

    Optional<StudioModel> findByName(String name);
}

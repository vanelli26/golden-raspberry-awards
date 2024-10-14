package br.com.gra.studio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<StudioModel, Long> {

    Optional<StudioModel> findByName(String name);
}

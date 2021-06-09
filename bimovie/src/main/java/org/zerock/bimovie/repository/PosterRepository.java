package org.zerock.bimovie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.bimovie.entity.Poster;

public interface PosterRepository extends JpaRepository<Poster, Long> {
}

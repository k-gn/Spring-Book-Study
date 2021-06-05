package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m " +
            "left outer join Review r on r.movie = m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m " +
            "left outer join MovieImage mi on mi.movie = m and mi.inum = (select max(mi2.inum) from MovieImage mi2 where mi2.movie = m) " +
            "left outer join Review r on r.movie = m " +
            "where m.mno = :mno")
    List<Object[]> getMovieWithAll(Long mno);
}
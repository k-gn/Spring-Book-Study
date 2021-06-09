package org.zerock.bimovie.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.bimovie.entity.Movie;
import org.zerock.bimovie.entity.Poster;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PosterRepository posterRepository;

    @Transactional
    @Test
    @Commit
    public void testInsert() {
        Poster poster1 = Poster.builder()
                .fname("극한직업포스터1.jpg")
                .build();

        Poster poster2 = Poster.builder()
                .fname("극한직업포스터2.jpg")
                .build();

//        posterRepository.save(poster1);
//        posterRepository.save(poster2);

        Movie movie = Movie.builder()
                .title("극한직업")
                .build();

        movie.addPoster(poster1);
        movie.addPoster(poster2);

        movieRepository.save(movie);

    }

    @Transactional
    @Test
    @Commit
    public void testAddPoster() {

        Movie movie = movieRepository.getById(1L);

        Poster poster3 = Poster.builder()
                .fname("극한직업포스터3.jpg")
                .build();

        movie.addPoster(poster3);

        movieRepository.save(movie);

    }

    @Transactional
    @Test
    @Commit
    public void testRemovePoster() {

        Movie movie = movieRepository.getById(1L);
        movie.removePoster(2L);
        movieRepository.save(movie);
    }

    @Test
    public void insertMovie() {
        IntStream.rangeClosed(10, 100).forEach(i -> {
            Movie movie = Movie.builder().title("세게명작" + i).build();

            movie.addPoster(Poster.builder().fname("세게명작" + i + "포스터1.jpg").build());
            movie.addPoster(Poster.builder().fname("세게명작" + i + "포스터2.jpg").build());

            movieRepository.save(movie);
        });
    }

//    @Transactional
    @Test
    public void testPaging1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Movie> result = movieRepository.findAll(pageable);

        result.getContent().forEach(m -> {
            System.out.println(m.getMno());
            System.out.println(m.getTitle());
            System.out.println(m.getPosterList().size());
        });
    }

    @Test
    public void testPaging2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Movie> result = movieRepository.findAll2(pageable);

        result.getContent().forEach(m -> {
            System.out.println(m.getMno());
            System.out.println(m.getTitle());
            System.out.println(m.getPosterList());
        });
    }

    @Test
    public void testPaging3() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.findAll3(pageable);

        result.getContent().forEach(arr -> {
            System.out.println(arr[0]);
            System.out.println(arr[1]);
            System.out.println(arr[2]);
        });
    }
}
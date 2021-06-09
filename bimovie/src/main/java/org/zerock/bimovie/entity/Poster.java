package org.zerock.bimovie.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "movie")
@Table(name = "tbl_poster")
public class Poster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fname;

    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

}

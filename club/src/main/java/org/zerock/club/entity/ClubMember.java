package org.zerock.club.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ClubMember extends BaseEntity {

    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial;

    // JPA의 데이터 타입은 크게 엔티티 타입과 값 타입이 있다
    // 엔티티 타입은 @Entity로 정의하는 객체이고, 값 타입은 int, Integer, String 처럼 단순 값으로 사용하는 자바 기본 타입이나 객체를 말한다
    // (영한님은 여기서 엔티티 타입은 살아있는 생물이고, 값 타입은 단순한 수치 정보라고 표현했다.)

    // @ElementCollection
    // Entity가 아닌 단순한 형태의 객체 집합을 정의하고 관리하는 방법이다. (여러개의 값 타입을 저장할 떄 사용)
    // 부모 Entity Id와 추가 컬럼(basic or embedded 타입)으로 구성 (자동으로 테이블을 생성)
    // 값타입 컬렉션은 진짜 정말 단순한거 저장하거나 할때 사용. 추적 필요없을때 사용
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    public void addMemberRole(ClubMemberRole clubMemberRole) {
        roleSet.add(clubMemberRole);
    }
}

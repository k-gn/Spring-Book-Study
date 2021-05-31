package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {

        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("====================================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {

        Long mno = 100L;

        Memo result = memoRepository.getOne(mno);
        System.out.println("====================================");

        System.out.println(result);
    }

    @Test
    public void testUpdate() {

        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete() {

        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {

        Pageable pageable = PageRequest.of(9, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);
        System.out.println("================================");

        // 총 페이지 수
        System.out.println("Total Pages : " + result.getTotalPages());

        // 전체 개수
        System.out.println("Total Count : " + result.getTotalElements());

        // 현재 페이지 번호 ( 0부터 시작 )
        System.out.println("Page Number : " + result.getNumber());

        // 페이지당 데이터 개수
        System.out.println("Page Size : " + result.getSize());

        // 이전, 다음 페이지 존재 여부
        System.out.println("has next page ? " + result.hasNext());
        System.out.println("has next page ? " + result.hasPrevious());

        // 시작 페이지 여부
        System.out.println("first page ? " + result.isFirst());

        // 가져 온 페이지의 데이터 개수
        System.out.println(result.getNumberOfElements());

        // 가져온 데이터 목록 (List)
        System.out.println(result.getContent());

        // 마지막 페이지 여부
        System.out.println(result.isLast());

        // 비어있는지 여부
        System.out.println(result.isEmpty());
        
        // 스트림 가져오기
        System.out.println(result.get());

        System.out.println("================================================");

        result.get().forEach(System.out::println);
    }

    @Test
    public void testSort() {

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);

//        Pageable pageable = PageRequest.of(0, 10, sort1);
        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(System.out::println);
    }

    @Test
    public void testQueryMethod() {

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }

        System.out.println("====================================");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(System.out::println);
    }


    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethod() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    @Test
    public void queryAnnotationMethod() {
//        List<Memo> list = memoRepository.getListDesc();
//        list.stream().forEach(System.out::println);

//        int result = memoRepository.updateMemoText(99L, "Update Text");
//        System.out.println(result);

//        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
//        Page<Memo> result = memoRepository.getListWithQuery(10L, pageable);
//        System.out.println(result.getContent());

//        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").ascending());
//        Page<Object[]> result = memoRepository.getListWithQueryObject(10L, pageable);
//        result.get().forEach(res -> {
//            for (int i=0; i< res.length; i++) {
//                System.out.println(res[i]);
//            }
//        });

        System.out.println("==========================================");
        List<Memo> list = memoRepository.getNativeResult();
        list.stream().forEach(System.out::println);
    }


}
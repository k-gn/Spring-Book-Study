package org.zerock.ex3.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex3.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {

    @GetMapping("/ex1")
    public void ex1() { // 리턴 타입이 void 면 해당 경로와 동일한 파일을 templates 에서 찾아 보여준다.
        log.info("ex1............");
    }

    @GetMapping({"/ex2", "/exLink"})
    public void exModel(Model model) {
        // asLongStream() : IntStream의 int 요소를 long 요소로 타입 변환해서 LongStream을 생성
        // mapToObj : 기본형 특화 스트림을 객체 Stream으로 변환해주는 메서드
        // Collect : Stream의 데이터를 변형 등의 처리를 하고 원하는 자료형으로 변환 (필터링, 매핑된 요소들을 새로운 컬렉션에 수집해서 리턴)
        // Collector : 어떤 요소를 어떤 컬렉션에 수집할 것인가를 나타낸다.
        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> {
           SampleDTO dto = SampleDTO.builder()
                   .sno(i)
                   .first("First.." + i)
                   .last("Last.." + i)
                   .regTime(LocalDateTime.now())
                   .build();
           return dto;
        }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }

    @GetMapping("/exInline")
    public String exInline(RedirectAttributes ra) {
        log.info("exInline");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First..100")
                .last("Last..100")
                .regTime(LocalDateTime.now())
                .build();
        ra.addFlashAttribute("result", "success");
        ra.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3() {
        log.info("ex3");
    }
}

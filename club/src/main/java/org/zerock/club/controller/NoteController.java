package org.zerock.club.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.club.dto.NoteDTO;
import org.zerock.club.entity.Note;
import org.zerock.club.service.Noteservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/notes/")
@RequiredArgsConstructor
public class NoteController {

    private final Noteservice noteservice; //final

    @PostMapping(value = "")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO){

        log.info("-----------register-------------------------------");
        log.info(noteDTO);

        Long num = noteservice.register(noteDTO);

        return new ResponseEntity<>(num, HttpStatus.OK);
    }

    // produces는 request에 'Accept' Header가 produces에 명시한 MediaType에 포함될때 명시한 type으로 response를 해준다.
    // consumes는 request에 'Contest-Type' Header가 consumes에 명시한 MediaType에 포함될때 명시한 type으로 response를 해준다.
    // Consumes : 수신 하고자하는 데이터 포맷을 정의한다.
    // Produces : 출력하고자 하는 데이터 포맷을 정의한다.
    @GetMapping(value = "/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num){

        log.info("-----------read-------------------------------");
        log.info(num);
        return new ResponseEntity<>(noteservice.get(num), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> getList(String email){

        log.info("-----------getList-------------------------------");
        log.info(email);

        return new ResponseEntity<>(noteservice.getAllWithWriter(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("num") Long num){

        log.info("-----------remove-------------------------------");
        log.info(num);

        noteservice.remove(num);

        return new ResponseEntity<>("removed", HttpStatus.OK);

    }

    @PutMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO){

        log.info("-----------modify-------------------------------");
        log.info(noteDTO);

        noteservice.modify(noteDTO);

        return new ResponseEntity<>("modified", HttpStatus.OK);

    }


}






















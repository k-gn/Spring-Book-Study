package org.zerock.mreview.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.mreview.dto.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles) {
        log.info("start uploadAjax");
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for(MultipartFile uploadFile : uploadFiles) {

            if(uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            log.info("fileName : " + fileName);

            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            log.info("saveName : " + saveName);

            File saveFile = new File(saveName);
//            Path savePath = Paths.get(saveName);
            log.info("saveFile : " + saveFile);
//            log.info("savePath : " + savePath);
            try {
//                uploadFile.transferTo(savePath);
                uploadFile.transferTo(saveFile);

                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;

                File thumbnailFile = new File(thumbnailSaveName);

//                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);
                Thumbnailator.createThumbnail(saveFile, thumbnailFile, 100, 100);

                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("end uploadAjax");
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);
        log.info("str : " + str);
        log.info("folderPath : " + folderPath);
        log.info("File.separator : " + File.separator);
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            log.info("fileName : " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);

            log.info("file : " + file);

            HttpHeaders header = new HttpHeaders();
            // 로컬 파일 시스템에 저장되어 있는 파일을 바이너리 형태로 내려주는 REST API 에서
            // 파일을 내려줄 때 Content-Type 헤더에 Mime Type을 알맞게 지정해줘야 한다.
            header.add("Content-Type", Files.probeContentType(file.toPath())); // 실제 파일의 내용이 아니라 파일의 확장자를 이용하여 마임타입을 판단
            log.info("Files.probeContentType(file.toPath()) : " +  Files.probeContentType(file.toPath()));
            
            // FileCopyUtils : 파일 및 스트림 복사를 위한 유틸리티 메소드 집합체
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
            log.info("FileCopyUtils.copyToByteArray(file) : " + FileCopyUtils.copyToByteArray(file)); // file 내용을 byte[] 로 복사 후 리턴
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName) {

        String srcFileName = null;

        try {
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            boolean result = file.delete();

            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            log.info("file.getParent() : " + file.getParent());
            log.info("file.getName() : " + file.getName());

            result = thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

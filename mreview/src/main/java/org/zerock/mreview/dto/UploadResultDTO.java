package org.zerock.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@Log4j2
// # implements Serializable
// 생성한 객체를 파일로 저장할 일이 있을 수도 있습니다.
// 저장한 객체를 읽을 일이 생길 수도 있습니다.
// 다른 서버에서 생성한 객체를 받을 일도 생길 수 있습니다.
// 이럴 때 꼭 필요한 것이 Serializable 입니다. 우리가 만든 클래스가 파일에 읽거나 쓸 수 있도록 하거나, 다른 서버로 보내거나 받을 수 있도록 하려면 반드시 이 인터페이스를 구현해야 합니다.
// 자바 직렬화란 자바 시스템 내부에서 사용되는 객체 또는 데이터를 외부의 자바 시스템에서도 사용할 수 있도록 바이트(byte) 형태로 데이터 변환하는 기술과 바이트로 변환된 데이터를 다시 객체로 변환하는 기술(역직렬화)을 아울러서 이야기합니다.
// 시스템적으로 이야기하자면 JVM(Java Virtual Machine 이하 JVM)의 메모리에 상주(힙 또는 스택)되어 있는 객체 데이터를 바이트 형태로 변환하는 기술과 직렬화된 바이트 형태의 데이터를 객체로 변환해서 JVM으로 상주시키는 형태를 같이 이야기합니다.
// + transient 예약어를 사용하여 선언한 변수는 Serializable의 대상에서 제외됩니다.
public class UploadResultDTO {

    private String fileName;
    private String uuid;
    private String folderPath;

    // getXXX 메소드는 브라우저로 전송되면 자동으로 사용 가능
    public String getImageURL() {
        log.info("getImageURL");
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL() {
        log.info("getThumbnailURL");
        try {
            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

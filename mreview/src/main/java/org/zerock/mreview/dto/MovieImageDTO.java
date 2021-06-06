package org.zerock.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieImageDTO {

    private String uuid;

    private String imgName;

    private String path;

    // url은 ASCII 문자열을 이용해서만 전송될 수 있다. 즉 ASCII가 아닌 한글, 특수 문자(Unsafe, Reserved)는 두개의 16진수를 사용하는 octet형태로 encode된다.
    // URL은 아스키코드로 이뤄져야하기 때문에 그 외의 문자는 “%”와 16진수 문자를 조합해 인코딩 한다는 것이다. 이것을이스케이프(escape) 처리된 url이라고도 한다.
    // url은 ASCII문자열을 이용해서만 전달할 수 있는데 alphanumeric character, underscores를 제외하고 URL 내의 모든 문자는 의도치 않게 변형될 수 있으므로 인코딩을 해야한다.
    // 아스키 이외의 문자는 다 인코딩 해야된다.
    // Ajax 에서는 기본인코딩이 UTF-8 이다.
    public String getImageURL() {
        try {
            return URLEncoder.encode(path + "/" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

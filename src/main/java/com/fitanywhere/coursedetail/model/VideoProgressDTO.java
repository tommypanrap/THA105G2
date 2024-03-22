package com.fitanywhere.coursedetail.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VideoProgressDTO {
    private String uId;
    private String cdId;
    private double progress;

    public VideoProgressDTO(String uId, String cdId, double progress) {
        this.uId = uId;
        this.cdId = cdId;
        this.progress = progress;
    }


}

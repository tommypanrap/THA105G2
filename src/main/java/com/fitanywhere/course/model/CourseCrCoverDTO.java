package com.fitanywhere.course.model;

public class CourseCrCoverDTO {
    private Integer crId;

    private byte[] crCover;

    public CourseCrCoverDTO() {
    }

    public CourseCrCoverDTO(Integer crId, byte[] crCover) {
        this.crId = crId;
        this.crCover = crCover;
    }

    public Integer getCrId() {
        return crId;
    }

    public void setCrId(Integer crId) {
        this.crId = crId;
    }

    public byte[] getCrCover() {
        return crCover;
    }

    public void setCrCover(byte[] crCover) {
        this.crCover = crCover;
    }
}

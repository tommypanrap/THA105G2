package com.fitanywhere.detail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDTO implements java.io.Serializable {
    private byte[] crCover;
    private String crTitle;
    private Integer cdPrice;


}

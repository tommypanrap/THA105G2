package com.fitanywhere.forumpost.model;

import java.sql.Timestamp;
import java.util.Base64;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForumPostDTO {
	private Integer uId;
	private Integer fpId;
	private String fpCategory;
	private String fpTitle;
	private String fpContent;
	private Integer fpStatus;
	private Timestamp fpTime;
	private Timestamp fpUpdate;
	private String fpPic;
	private Integer fpViews;

	public String convertPicToBase64(byte[] fpPic) {
		if (fpPic != null && fpPic.length > 0) {
			return Base64.getEncoder().encodeToString(fpPic);
		} else {
			return null;
		}
	}

}
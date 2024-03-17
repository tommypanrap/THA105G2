package com.fitanywhere.forumreply.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForumReplyDTO {
	private Integer uId;
	private Integer frId;
	private Integer fpId;
	private String frContent;
	private Integer frStatus;
	private Timestamp frTime;
	private Timestamp frUpdate;
	private byte[] frPic;
}

package com.fitanywhere.usercourse.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.user.model.UserVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_course")
public class UserCourseVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "us_cr_id")
	private Integer usCrId;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="cr_id")
	private CourseVO courseVO;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="u_id")
	private UserVO userVO;

}

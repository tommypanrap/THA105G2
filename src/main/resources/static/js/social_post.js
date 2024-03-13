$(document).ready(function() {
	//連結按鈕的地方
	$(".add-post").on("click", function(e) {
		e.preventDefault();
		$(".lightbox-add-post").removeClass("none");
	});

	// 關閉按鈕
	$("button.btn_model_close").on("click", function() {
		$(".lightbox-add-post").addClass("none");
		// alert("123");
	});

	// $("#lightbox").on("click", function(){
	// $("#lightbox").addClass("none");
	// })

	$("#lightbox > article").on("click", function(e) {
		e.stopPropagtion();
	});

	$(".edit").on("click", function() {

		let articlePost = $(this).closest(".article-post");

		let postContent = articlePost.find(".post-content");
		let postContentEdit = articlePost.find(".post-content-edit");

		let previewImage = articlePost.find("#preview-image");
		let originalPic =  articlePost.find(".original-pic");
		
		console.log(previewImage);
		console.log("original-pic:"+originalPic);
		console.log("original-pic src:"+originalPic.attr('src'));
		    previewImage.attr('src', originalPic.attr('src'));


		postContent.toggleClass("hide");
		postContentEdit.toggleClass("show");


	});

	$(".delete-socialpost").on("click", function() {

		alert("delete");

		let innerHTMLContent = $(".spid").html();
		var spid = parseInt(innerHTMLContent, 10);

		var data = {
			spStatus: 0,
			spid: spid

		};

		const formData = new FormData();
		for (const key in data) {
			formData.append(key, data[key]);
		}

		$.ajax({
			url: '/socialpost/update_for_delete',
			type: 'POST',
			data: formData,
			contentType: false, // 必須為 false，告訴 jQuery 不要設置 contentType
			processData: false, // 必須為 false，告訴 jQuery 不要處理數據
			success: function(responseData) {
				window.alert("已刪除貼文");
				window.location.href = 'student_socialpost';
			},
			error: function(error) {
				console.error('Error:', error);
			}
		});


	});

	$(".search-member-social").on("keydown", function(e) {
		if (e.keyCode === 13) {
			let searchValue = String($(this).val());

			let innerHTMLContent = $(".uid-for-send").html();
			let uid = parseInt(innerHTMLContent, 10);

			//			console.log("searchValue:" + searchValue);

			var data = {
				searchValue: searchValue,
				uId: uid
			}

			const formData = new FormData();
			for (const key in data) {
				formData.append(key, data[key]);
			}

			$.ajax({
				url: '/socialpost/search_social_member',
				type: 'POST',
				data: formData,
				contentType: false, // 必須為 false，告訴 jQuery 不要設置 contentType
				processData: false, // 必須為 false，告訴 jQuery 不要處理數據
				success: function(responseData) {
					//					window.alert("進來ajax");
					window.location.href = 'student_socialpost';
				},
				error: function(xhr, status, error) {
					console.error('Error:', error);
					console.log('Status:', status);
					console.log('Response Text:', xhr.responseText);
				}
			});

		}
	});


	$(".social-reply-input").on("keydown", function(e) {
		if (e.keyCode === 13) {
			//			alert("social-reply-input");

			let replyValue = String($(this).val());
			console.log("replyValue:" + typeof replyValue);

			let spidValue = $(this).siblings(".spid").text();
			console.log("spid:", typeof spidValue);

			var data = {
				replyValue: replyValue,
				spid: spidValue
			}

			const formData = new FormData();
			for (const key in data) {
				formData.append(key, data[key]);
			}

			$.ajax({
				url: '/socialpost/add_social_reply',
				type: 'POST',
				data: formData,
				contentType: false, // 必須為 false，告訴 jQuery 不要設置 contentType
				processData: false, // 必須為 false，告訴 jQuery 不要處理數據
				success: function(responseData) {
					//					window.alert("進來ajax");
					window.location.href = 'student_socialpost';
				},
				error: function(xhr, status, error) {
					console.error('Error:', error);
					console.log('Status:', status);
					console.log('Response Text:', xhr.responseText);
				}
			});
		}

	});
document.getElementById('update-pic').addEventListener('change',function(e){
	
			const preview = document.getElementById('preview-image');
		const file = e.target.files[0];
		const reader = new FileReader();

		reader.onloadend = function() {
			preview.src = reader.result;
		}

		if (file) {
			reader.readAsDataURL(file);
		} else {
			preview.src = "";
		}
	
	
	
	
})
	
	
	
	
	


	


	//
	//	let navUId = $(".navUId").text();
	//	//	let navUIdNumber = parseInt(navUId);
	//
	//
	//	var storedNavUId = navUIdNumber = sessionStorage.getItem('navUId', navUIdNumber);
	//
	//	if (storedNavUId && !isNaN(storedNavUId)) {
	//		navUId = storedNavUId;
	//		console.log("storedNavUId:"+storedNavUId);
	////		var data = {
	////			navUIdPost: navUId
	////		}
	////
	////
	////			;
	////
	////		const formData = new FormData();
	////		for (const key in data) {
	////			formData.append(key, data[key]);
	////		}
	////		$.ajax({
	////			url: '/socialpost/student_socialpost',
	////			type: 'POST',
	////			data: formData,
	////			contentType: false, // 必須為 false，告訴 jQuery 不要設置 contentType
	////			processData: false, // 必須為 false，告訴 jQuery 不要處理數據
	////			success: function(responseData) {
	////				//					window.alert("進來ajax");
	////				//				window.location.href = 'student_socialpost';
	////			},
	////			error: function(xhr, status, error) {
	////				console.error('Error:', error);
	////				console.log('Status:', status);
	////				console.log('Response Text:', xhr.responseText);
	////			}
	////		});
	//
	//	}
	//
	//
	//	if (navUId && !isNaN(navUId)) {
	//		var navUIdNumber = parseInt(navUId);
	//		console.log(navUIdNumber);
	//		sessionStorage.setItem('navUId', navUIdNumber);
	//
	//
	//		var data = {
	//			navUIdPost: navUId
	//		}
	//
	//
	//			;
	//
	//		const formData = new FormData();
	//		for (const key in data) {
	//			formData.append(key, data[key]);
	//		}
	//
	//		$.ajax({
	//			url: '/socialpost/student_socialpost',
	//			type: 'GET',
	//			data: formData,
	//			contentType: false, // 必須為 false，告訴 jQuery 不要設置 contentType
	//			processData: false, // 必須為 false，告訴 jQuery 不要處理數據
	//			success: function(responseData) {
	//				//					window.alert("進來ajax");
	//				//				window.location.href = 'student_socialpost';
	//			},
	//			error: function(xhr, status, error) {
	//				console.error('Error:', error);
	//				console.log('Status:', status);
	//				console.log('Response Text:', xhr.responseText);
	//			}
	//		});
	//
	//
	//
	//	} else {
	//		console.log('navUId is either empty or not a number');
	//	}

});

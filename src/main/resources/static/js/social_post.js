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
		let originalPic = articlePost.find(".original-pic");

		console.log(previewImage);
		console.log("original-pic:" + originalPic);
		console.log("original-pic src:" + originalPic.attr('src'));
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
	//搜尋成員
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

	//新增留言
	$(".social-reply-input").on("keydown",async function(e) {
		if (e.keyCode === 13) {
			//			alert("social-reply-input");

			let replyValue = $(this).val().trim();
			if (!replyValue) return;

			//先用jquery寫進下方的div
			var newReply = $('<div class="social-reply"></div>').text(replyValue);

			var replyMember = $('<div class="reply-member"></div>');

			//拿input的值
			let replyMemberText = $('<div class="reply-member-text"></div>');

			let userPhotoUrl = await handleFetchUserImage();
			//圖片先亂寫
			var userPhoto = $('<img>').attr('src', userPhotoUrl);

			//先寫死
			var memberNickname = $('<p class="member-nickname"></p>').text("user001");
			var replyTime = $('<p class="reply-time"></p>').text("20:00");
			replyMemberText.append(memberNickname, replyTime);


			replyMember.append(userPhoto, replyMemberText);



			var replyContent = $('<div class="reply-content"></div>').text(replyValue);

			var socialReply = $('<div class="social-reply"></div>').append(replyMember, replyMember, replyContent);
			$('.post-content').append(socialReply);

			//清空輸入框
			$(this).val('');

			let spidValue = $(this).siblings(".spid").text();
			console.log("spid:", typeof spidValue);
			
			let uIdForAddReply = parseInt($('.uId').text());

			var data = {
				replyValue: replyValue,
				spid: spidValue,
				uId: uIdForAddReply
			}

			const formData = new FormData();
			for (const key in data) {
				formData.append(key, data[key]);
			}

			$.ajax({
				url: `/socialposts/${spidValue}/replies`,
				type: 'POST',
				data: formData,
				contentType: false, // 必須為 false，告訴 jQuery 不要設置 contentType
				processData: false, // 必須為 false，告訴 jQuery 不要處理數據
				success: function(responseData) {
					console.log("成功增加留言");
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
	document.getElementById('update-pic').addEventListener('change', function(e) {

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

async function fetchUserImage() {
    let uId = parseInt($('.uId').text());
    let image_Url;

    try {
        const response = await fetch('/user_api/user_headshot_test', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ u_id: uId }),
        });

        if (!response.ok) {
            throw new Error('response error');
        }

        const blob = await response.blob();
        image_Url = URL.createObjectURL(blob);
        console.log("image_Url:" + image_Url);
    } catch (error) {
        console.error('找不到user image:', error);
    }

    return image_Url; 
}


async function handleFetchUserImage() {
    return await fetchUserImage();
}


//handleFetchUserImage();




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


//連結按鈕的地方
$(".add-post").on("click",function(e){
e.preventDefault();
$("#lightbox").removeClass("none");
});

// 關閉按鈕
$("button.btn_model_close").on("click", function(){
    $("#lightbox").addClass("none");
    // alert("123");
});

// $("#lightbox").on("click", function(){
// $("#lightbox").addClass("none");
// })

$("#lightbox > article").on("click", function(e){
e.stopPropagtion();
});


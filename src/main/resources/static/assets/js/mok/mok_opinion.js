//連結按鈕的地方
$(".button_model").on("click",function(e){
    e.preventDefault();
    $("#lightbox").removeClass("none");
});

// 關閉按鈕
$("button.btn_model_close").on("click", function(){
    $("#lightbox").addClass("none");
});

$("#lightbox").on("click", function(){
    $("#lightbox").addClass("none");
})

$("#lightbox > article").on("click", function(e){
    e.stopPropagtion();
});

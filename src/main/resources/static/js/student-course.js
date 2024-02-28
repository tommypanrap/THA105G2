$(".tab-course").on("click",function(e){
    e.preventDefault();
    
    let getClass = $(this).attr("class");

    console.log(getClass);

    let courseCointainers = $(".course-container");

    for(i=0;i<$(".course-container").length;i++){
        let courseCointainerForIndex = `.course-container:eq(${i})`;

        let courseCointainerClass = $(courseCointainerForIndex).attr("class");

        $(courseCointainerForIndex).removeClass("show");
    }

    if(getClass.includes("tab-mycourse")){
        $(".mycourse-course-container").addClass("show");
    }else if(getClass.includes("tab-collect")){
        $(".collect-course-container").addClass("show");
    }else if(getClass.includes("tab-history-order")){
        $(".history-order-course-container").addClass("show");
    }else if(getClass.includes("tab-receive")){
        $(".receive-course-container").addClass("show");
    }else if(getClass.includes("tab-history-browse")){
        $(".history-browse-course-container").addClass("show");
    }else if(getClass.includes("tab-history-subscribe")){
        $(".subscription").addClass("show");
    }
})


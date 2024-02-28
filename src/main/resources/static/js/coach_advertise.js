



$(".ad-plan-inner-select").on("click",function(e){
    e.preventDefault();
    
    //取得點擊該標籤的class
    let getClass = $(this).attr("class");
    let catchWhatPlan = "";
    //看點到的標籤長怎麼樣，就把對應的字串存到變數裡
    switch(getClass){
        case "ad-plan-inner-select plan-a":
            catchWhatPlan = "plan-a-content";
            break;
        case "ad-plan-inner-select plan-b":
            catchWhatPlan = "plan-b-content";
            break;
    }

    
    //把方案通通選起來
    let adPlan = $(".ad-plan");

    //迭代方案
    for(i=0;i<$(".ad-plan").length;i++){
        let adPlanSelecter = `.ad-plan:eq(${i})`

        //取得方案的class
        let adPlanCLass = $(adPlanSelecter).attr("class");
        //把方案的active刪掉
        $(adPlanSelecter).removeClass("active");


        //如果方案有包含catchWhatPlan，就增加active這個class
            if(adPlanCLass.includes(catchWhatPlan)){
                $(adPlanSelecter).addClass("active");
            }
    }

    


})
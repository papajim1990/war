/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var next;
var userid;
var access_token;
var before;
var help;
var mapp;
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function SelectAllOptions(){
    $(".option-checker").each(function () {
        if($(this).prop("checked")==false){
    $(this).trigger("change");
    $(this).prop("checked",true);
        }
});
}
function defaultActionAjax(data) {
    var results = data;
    var hotel = results.Hotel;
    var comments = results.Comments;

    for (var i = 0; i < comments.length; i++) {

        var k = 0;
        var comment = comments[i].com;
        var sentences = comments[i].sentence;

        //alert(comment.commentBodyPos + " /// " + comment.commentBodyNeg);

        $("#reviews-reviews-reviews").append(
                '<li data-id="' + comment.commentId + '" data-pagerposition="' + i + '" data-reviewer-type="' + comment.commentReviwerType + '" data-reviewer-country="' + comment.commentCountry + '" data-date-review="' + comment.dateReview + '" class="review_item clearfix review_item ">' +
                '<p class="review_item_date" data-et-view="bLTLRZYGaQDDFWJcXe:1">' +
                comment.dateReview +
                '</p>' +
                '<div class="review_item_reviewer">' +
                '<div>' +
                '<img class="avatar-mask ava-pad-bottom ava-default" src="https://t-ec.bstatic.com/static/img/review/avatars/ava-p/866dca38dcc31cb6fa2e9b4c475bd32e681b0080.png" alt="" onerror="this.onerror = null;this.src = "https://t-ec.bstatic.com/static/img/review/avatars/ava-p/866dca38dcc31cb6fa2e9b4c475bd32e681b0080.png";">' +
                '</div>' +
                '<h4>' +
                '</h4>' +
                '<span class="reviewer_country">' + comment.commentCountry +
                '<span class="reviewer_country_flag sflag slang-sk  ">' +
                '</span>' +
                '</span>' +
                '<div class="user_age_group">' +
                comment.commentReviwerType +
                '</div>' +
                '<div class="user_badge_list">' +
                '<img width="20' +
                '" src="https://s-ec.bstatic.com/static/img/ugc/badges/badge_review_level1/f11fbfff4efab774acb19f9ad27422c9ed2fe154.png" class="js-fly-content-tooltip " data-content-tooltip="<p><strong>Status: Wordsmith Level 1</strong><br><br>Wordsmiths love to write about their trips and keep coming back to tell us more!</p>" data-extra-class-tooltip="fly-content-tooltip r-badge-tooltip">' +
                '</div>' +
                '<div class="review_item_user_review_count">' +
                '</div>' +
                '<div class="review_item_user_helpful_count">' +
                '</div>' +
                '</div>' +
                '<div class="review_item_review">' +
                '<div class="review_item_review_container lang_ltr">' +
                '<div class="review_item_review_header">' +
                '<div class="' +
                'review_item_header_score_container' +
                '">' +
                '<span class=" review-score-widget review-score-widget__superb review-score-widget__score-only      review-score-widget__no-subtext    jq_tooltip  " data-et-click="customGoal:adUAAdCMAZTZWKNYT:1" data-et-mouseenter="customGoal:adUAAdCMAZTZWKNYT:2" id="b_tt_holder_1">' +
                '<span id="topics-reviews-badge-' + comment.commentId + '" class="review-score-badge">' +
                '</span>' +
                '</span>' +
                '</div>' +
                '<div class="review_item_header_content_container">' +
                '<div id="topics-reviews-container-' + comment.commentId + '" class="review_item_header_content' +
                '"><ul class="topics-ul-container"></ul>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '<div class="review_item_review_content">' +
                '<p id="reviews-content-negative-' + comment.commentId + '" class="review_neg"><i class="far fa-minus-square"></i>' +
                '</p>' +
                '<p id="reviews-content-positive-' + comment.commentId + '" class="review_pos"><i class="far fa-plus-square"></i>' +
                '</p>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</li>');
        var negative = 0;
        var positive = 0;
        var Inside = [];
        for (var j = 0; j < sentences.length; j++) {
            if (sentences[j].sentenceobject.sentiment === 0) {
                $('#reviews-content-negative-' + comment.commentId).append("<span id='sentence" + sentences[j].sentenceobject.sentenceid + "'>" + sentences[j].sentenceobject.sentenceText + "</span>");
            } else {
                $('#reviews-content-positive-' + comment.commentId).append("<span id='sentence" + sentences[j].sentenceobject.sentenceid + "'>" + sentences[j].sentenceobject.sentenceText + "</span>");
            }
            var aspects = sentences[j].Aspects;
            for (var k = 0; k < aspects.length; k++) {

                //alert(aspects[k].aspect +" "+aspects[k].polarity);
                $("#sentence" + sentences[j].sentenceobject.sentenceid).addClass("category-" + aspects[k].aspect.replace(/[^a-zA-Z0-9]+/g, "") + "-" + aspects[k].polarity);

                Inside.push(aspects[k].aspect.toString() + "-" + aspects[k].polarity.toString());


                if (aspects[k].polarity === "positive") {
                    positive++;
                }
                if (aspects[k].polarity === "negative") {
                    negative++;
                }
            }
        }
        //alert(positive);
        //alert(negative);
        if (positive < negative) {
            $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-thumbs-down"></i>');
        } else if (positive > negative) {
            $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-thumbs-up"></i>');


        } else {
            $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-question-circle"></i>');



        }
        Inside = Inside.filter(onlyUnique);
        for (var q = 0; q < Inside.length; q++) {
            //alert(Inside[q]);
            $("#topics-reviews-container-" + comment.commentId + " ul").append(
                    '<li>' +
                    '<div data-comment="' + comment.commentId + '" class="topic-div-container-' + Inside[q].split("-")[1] + '" data-topic="' + Inside[q].split("-")[0] + '" data-sentiment="' + Inside[q].split("-")[1] + '">' +
                    Inside[q].split("-")[0] +
                    '</div>' +
                    '</li>'

                    );

        }
    }
    $(document).on("click", ".topics-ul-container li div", function () {
        $("#" + $(this).parent().parent().parent().attr("id") + " li div").removeClass("picked-div");
        //$(".topics-ul-container li div").removeClass("picked-div");
        $(this).addClass("picked-div");
        var topic = $(this).attr("data-topic");
        var idcom = $(this).attr("data-comment");
        var sentiment = $(this).attr("data-sentiment");
        $("li[data-id='" + idcom + "'] span").removeClass("higlighted-sentence");
        $("li[data-id='" + idcom + "']" + " .category-" + topic.replace(/[^a-zA-Z0-9]+/g, "") + "-" + sentiment).addClass("higlighted-sentence");
    });

    return comments.length;
}
/////////
//
function BringCommentsByAspect() {
    $("#total-group-1").prop("checked", true);
    $("#countryusersort input:radio[name='group__1']").parent().removeClass("selected-highl");
    if ($("#total-group-1").prop("checked") === true) {
        $("#total-group-1").parent().addClass("selected-highl");
    }
    $("#total-group-2").prop("checked", true);
    $("#typeofusersort input:radio[name='group__2']").parent().removeClass("selected-highl");
    if ($("#total-group-2").prop("checked") === true) {
        $("#total-group-2").parent().addClass("selected-highl");
    }

    var dat = {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")};
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");
    $.ajax({
        type: "POST",
        url: "Home?action=AspecFilter&start=10&end=0",
        data: $("#aspexts-criteria-form").serialize() + "&" + $.param(dat), // serializes the form's elements.
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        },
        success: function (data)
        {
            var offset = 0;
            var Total = data.Total;
            //alert(Total);
            var Count = defaultActionAjax(data);
            if (Total - Count > 0) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getnextByAspect(' + (offset + 10) + ',' + Total + ');" id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center page-number-reviews">1 -' + Count +
                        '</p> ' +
                        '</div>' +
                        '</div>');
            }
// take an array to store values
            $('input[type="checkbox"][name="group__3"]:checked').each(function () {
                //alert($(this).val().replace(/\W+/g, " ") + "-" + "negative");
                $(".category-" + $(this).val().replace(/\W+/g, " ") + "-" + "negative").addClass("higlighted-sentence");  //push values in array
                $(".category-" + $(this).val().replace(/\W+/g, " ") + "-" + "positive").addClass("higlighted-sentence");
            });
        }
    });
}
function getnextByAspect(offset, Total) {
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");
    var dat = {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")};
    $.ajax({

        type: "POST",
        url: "Home?action=AspecFilter&start=10&end=" + offset,
        data: $("#aspexts-criteria-form").serialize() + "&" + $.param(dat), // serializes the form's elements.
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);
        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        }, // serializes the form's elements.
        success: function (data)
        {
            var count = defaultActionAjax(data);
            if (offset + count >= Total) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getnextByAspect(' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Next page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (Total > offset + count && offset !== 0) {

                $(".review-list-topic-filter").first().append('<div class=" row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getnextByAspect(' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getnextByAspect(' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (offset === 0 && Total > offset + count) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getnextByAspect(' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my">' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            }
            $(".sliding-panel-widget-scrollable ").animate({scrollTop: $("#reviews-reviews-reviews").offset().top
            }, 2000);
            $('input[type="checkbox"][name="group__3"]:checked').each(function () {
                //alert($(this).val().replace(/\W+/g, " ") + "-" + "negative");
                $(".category-" + $(this).val().replace(/\W+/g, " ") + "-" + "negative").addClass("higlighted-sentence");  //push values in array
                $(".category-" + $(this).val().replace(/\W+/g, " ") + "-" + "positive").addClass("higlighted-sentence");
            });
        }
    });
}
//
function BringCommentsBothAll() {
    $("#filters-topics-sort-option input").prop("checked", false).parent().parent().css({"background-color": "#ccc", "color": "#333"});


    $("#total-group-1").prop("checked", true);
    $("#total-group-2").prop("checked", true);
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");
    var offset = 0;
    $.ajax({

        type: "GET",
        url: "Home?action=BringCommentsBothAll" + "&start=10" + "&end=0",
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);
        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        },
        // serializes the form's elements.
        success: function (data) {
            var Total = data.Total;
            //alert(Total);
            var Count = defaultActionAjax(data);
            if (Total - Count > 0) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getnextAll(' + (offset + 10) + ',' + Total + ');" id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center page-number-reviews">1 -' + Count +
                        '</p> ' +
                        '</div>' +
                        '</div>');
            }



        }

    });
}
function getnextAll(offset, Total) {
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");

    $.ajax({

        type: "GET",
        url: "Home?action=BringCommentsBothAll&start=10" + "&end=" + offset,
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        }, // serializes the form's elements.
        success: function (data)
        {
            var count = defaultActionAjax(data);
            if (offset + count >= Total) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getnextAll(' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Next page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (Total > offset + count && offset !== 0) {

                $(".review-list-topic-filter").first().append('<div class=" row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getnextAll(' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getnextAll(' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (offset === 0 && Total > offset + count) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getnextAll(' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my">' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            }
            $(".sliding-panel-widget-scrollable ").animate({scrollTop: $("#reviews-reviews-reviews").offset().top
            }, 2000);
        }
    });
}
///////
//////
function BringCommentsByBoth(Country, TypeOfUser) {
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");
    var offset = 0;
    $.ajax({

        type: "GET",
        url: "Home?action=BringCommentsBothFilters&Country=" + Country + "&TypeOfUser=" + TypeOfUser + "&start=10" + "&end=0",
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        },
        // serializes the form's elements.
        success: function (data) {
            var Total = data.total;
            var Count = defaultActionAjax(data);
            if (Total - Count > 0) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getpagecommentsOffsetByBoth(' + "'" + Country + "'" + ',' + "'" + TypeOfUser + "'" + ',' + (offset + 10) + ',' + Total + ');" id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center page-number-reviews">1 -' + Count +
                        '</p> ' +
                        '</div>' +
                        '</div>');
            }



        }

    });
}
function getpagecommentsOffsetByBoth(Country, TypeOfUser, offset, Total) {
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");

    $.ajax({

        type: "GET",
        url: "Home?action=BringCommentsBothFilters&Country=" + Country + "&TypeOfUser=" + TypeOfUser + "&start=10" + "&end=" + offset,
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        }, // serializes the form's elements.
        success: function (data)
        {
            var count = defaultActionAjax(data);
            if (offset + count >= Total) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffsetByBoth(' + "'" + Country + "'" + ',' + "'" + TypeOfUser + "'" + ',' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Next page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (Total > offset + count && offset !== 0) {

                $(".review-list-topic-filter").first().append('<div class=" row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffsetByBoth(' + "'" + Country + "'" + ',' + "'" + TypeOfUser + "'" + ',' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getpagecommentsOffsetByBoth(' + "'" + Country + "'" + ',' + "'" + TypeOfUser + "'" + ',' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (offset === 0 && Total > offset + count) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffsetByBoth(' + "'" + Country + "'" + ',' + "'" + TypeOfUser + "'" + ',' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my">' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            }
            $(".sliding-panel-widget-scrollable ").animate({scrollTop: $("#reviews-reviews-reviews").offset().top
            }, 2000);
        }
    });
}
///////////
function BringCommentsByTypeOfUser(TypeOfUser, Total) {
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");
    var offset = 0;
    $.ajax({

        type: "GET",
        url: "Home?action=BringCommentsForTypeOfUser&TypeOfUser=" + TypeOfUser + "&start=10" + "&end=0",
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        },
        // serializes the form's elements.
        success: function (data) {
            var Count = defaultActionAjax(data);
            if (Total - Count > 0) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getpagecommentsOffsetTypeOfUser(' + "'" + TypeOfUser + "'" + ',' + (offset + 10) + ',' + Total + ');" id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center page-number-reviews">1 -' + Count +
                        '</p> ' +
                        '</div>' +
                        '</div>');
            }



        }

    });
}
function getpagecommentsOffsetTypeOfUser(TypeOfUser, offset, Total) {
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");

    $.ajax({

        type: "GET",
        url: "Home?action=BringCommentsForTypeOfUser&TypeOfUser=" + TypeOfUser + "&start=10" + "&end=" + offset,
        data: {"urlhotel": ("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        }, // serializes the form's elements.
        success: function (data)
        {
            var count = defaultActionAjax(data);
            if (offset + count >= Total) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffsetTypeOfUser(' + "'" + TypeOfUser + "'" + ',' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Next page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (Total > offset + count && offset !== 0) {

                $(".review-list-topic-filter").first().append('<div class=" row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffsetTypeOfUser(' + "'" + TypeOfUser + "'" + ',' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getpagecommentsOffsetTypeOfUser(' + "'" + TypeOfUser + "'" + ',' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (offset === 0 && Total > offset + count) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffsetTypeOfUser(' + "'" + TypeOfUser + "'" + ',' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my">' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            }
            $(".sliding-panel-widget-scrollable ").animate({scrollTop: $("#reviews-reviews-reviews").offset().top
            }, 2000);
        }
    });
}
function BringCommentsByCountry(Country, Total) {
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");
    var offset = 0;
    $.ajax({

        type: "GET",
        url: "Home?action=BringCommentsForCountry&Country=" + Country + "&start=10" + "&end=0",
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        }, // serializes the form's elements.
        success: function (data) {
            var Count = defaultActionAjax(data);
            if (Total - Count > 0) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getpagecommentsOffset(' + "'" + Country + "'" + ',' + (offset + 10) + ',' + Total + ');" id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center page-number-reviews">1 -' + Count +
                        '</p> ' +
                        '</div>' +
                        '</div>');
            }



        }

    });

}

function getpagecommentsOffset(Country, offset, Total) {
    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");

    $.ajax({

        type: "GET",
        url: "Home?action=BringCommentsForCountry&Country=" + Country + "&start=10" + "&end=" + offset,
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        }, // serializes the form's elements.
        success: function (data)
        {
            var count = defaultActionAjax(data);
            if (offset + count >= Total) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffset(' + "'" + Country + "'" + ',' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Next page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (Total > offset + count && offset !== 0) {

                $(".review-list-topic-filter").first().append('<div class=" row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffset(' + "'" + Country + "'" + ',' + (offset - 10) + ',' + Total + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getpagecommentsOffset(' + "'" + Country + "'" + ',' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else if (offset === 0 && Total > offset + count) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getpagecommentsOffset(' + "'" + Country + "'" + ',' + (offset + 10) + ',' + Total + ');"  id="review_next_page_link_my">' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + offset + ' - ' + offset + count +
                        '</p>' +
                        '</div>' +
                        '</div>');
            }
            $(".sliding-panel-widget-scrollable ").animate({scrollTop: $("#reviews-reviews-reviews").offset().top
            }, 2000);
        }
    });
}

function getnextpagecomments(start, end, beforetotal, total, rangestart, rangeend) {
    start = start + 10;
    end = end + 10;
    //alert(start);

    var forward;
    var backward;
    var reviewstotal = rangeend - end;
    //alert(reviewstotal);
    if (reviewstotal > 0) {
        forward = 9;
    }
    if (reviewstotal < 0 || reviewstotal === 0) {
        forward = 0;
    }
    //alert(forward);

    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");

    $.ajax({

        type: "GET",
        url: "Home?action=BringComments&start=" + (start) + "&end=" + (end),
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")},
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        }, // serializes the form's elements.
        success: function (data)
        {
            var results = data;
            var hotel = results.Hotel;
            var comments = results.Comments;

            for (var i = 0; i < comments.length; i++) {
                total++;
                var k = 0;
                var comment = comments[i].com;
                var sentences = comments[i].sentence;


                $("#reviews-reviews-reviews").append(
                        '<li data-id="' + comment.commentId + '" data-pagerposition="' + i + '" data-reviewer-type="' + comment.commentReviwerType + '" data-reviewer-country="' + comment.commentCountry + '" data-date-review="' + comment.dateReview + '" class="review_item clearfix review_item ">' +
                        '<p class="review_item_date" data-et-view="bLTLRZYGaQDDFWJcXe:1">' +
                        comment.dateReview +
                        '</p>' +
                        '<div class="review_item_reviewer">' +
                        '<div>' +
                        '<img class="avatar-mask ava-pad-bottom ava-default" src="https://t-ec.bstatic.com/static/img/review/avatars/ava-p/866dca38dcc31cb6fa2e9b4c475bd32e681b0080.png" alt="" onerror="this.onerror = null;this.src = "https://t-ec.bstatic.com/static/img/review/avatars/ava-p/866dca38dcc31cb6fa2e9b4c475bd32e681b0080.png";">' +
                        '</div>' +
                        '<h4>' +
                        '</h4>' +
                        '<span class="reviewer_country">' + comment.commentCountry +
                        '<span class="reviewer_country_flag sflag slang-sk  ">' +
                        '</span>' +
                        '</span>' +
                        '<div class="user_age_group">' +
                        comment.commentReviwerType +
                        '</div>' +
                        '<div class="user_badge_list">' +
                        '<img width="20' +
                        '" src="https://s-ec.bstatic.com/static/img/ugc/badges/badge_review_level1/f11fbfff4efab774acb19f9ad27422c9ed2fe154.png" class="js-fly-content-tooltip " data-content-tooltip="<p><strong>Status: Wordsmith Level 1</strong><br><br>Wordsmiths love to write about their trips and keep coming back to tell us more!</p>" data-extra-class-tooltip="fly-content-tooltip r-badge-tooltip">' +
                        '</div>' +
                        '<div class="review_item_user_review_count">' +
                        '</div>' +
                        '<div class="review_item_user_helpful_count">' +
                        '</div>' +
                        '</div>' +
                        '<div class="review_item_review">' +
                        '<div class="review_item_review_container lang_ltr">' +
                        '<div class="review_item_review_header">' +
                        '<div class="' +
                        'review_item_header_score_container' +
                        '">' +
                        '<span class=" review-score-widget review-score-widget__superb review-score-widget__score-only      review-score-widget__no-subtext    jq_tooltip  " data-et-click="customGoal:adUAAdCMAZTZWKNYT:1" data-et-mouseenter="customGoal:adUAAdCMAZTZWKNYT:2" id="b_tt_holder_1">' +
                        '<span id="topics-reviews-badge-' + comment.commentId + '" class="review-score-badge">' +
                        '</span>' +
                        '</span>' +
                        '</div>' +
                        '<div class="review_item_header_content_container">' +
                        '<div id="topics-reviews-container-' + comment.commentId + '" class="review_item_header_content' +
                        '"><ul class="topics-ul-container"></ul>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '<div class="review_item_review_content">' +
                        '<p id="reviews-content-negative-' + comment.commentId + '" class="review_neg"><i class="far fa-minus-square"></i>' +
                        '</p>' +
                        '<p id="reviews-content-positive-' + comment.commentId + '" class="review_pos"><i class="far fa-plus-square"></i>' +
                        '</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</li>');
                var negative = 0;
                var positive = 0;
                var Inside = [];
                for (var j = 0; j < sentences.length; j++) {
                    if (sentences[j].sentenceobject.sentiment === 0) {
                        $('#reviews-content-negative-' + comment.commentId).append("<span id='sentence" + sentences[j].sentenceobject.sentenceid + "'>" + sentences[j].sentenceobject.sentenceText + "</span>");
                    } else {
                        $('#reviews-content-positive-' + comment.commentId).append("<span id='sentence" + sentences[j].sentenceobject.sentenceid + "'>" + sentences[j].sentenceobject.sentenceText + "</span>");
                    }
                    var aspects = sentences[j].Aspects;
                    for (var k = 0; k < aspects.length; k++) {
                        $("#sentence" + sentences[j].sentenceobject.sentenceid).addClass("category-" + aspects[k].aspect.replace(/[^a-zA-Z0-9]+/g, "") + "-" + aspects[k].polarity);
                        //alert(aspects[k].aspect +" "+aspects[k].polarity);
                        Inside.push(aspects[k].aspect.toString() + "-" + aspects[k].polarity.toString());

                        if (aspects[k].polarity === "positive") {
                            positive++;
                        }
                        if (aspects[k].polarity === "negative") {
                            negative++;
                        }
                    }

                }
                if (positive < negative) {
                    $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-thumbs-down"></i>');
                } else if (positive > negative) {
                    $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-thumbs-up"></i>');


                } else {
                    $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-question-circle"></i>');



                }
                Inside = Inside.filter(onlyUnique);
                for (var q = 0; q < Inside.length; q++) {
                    $("#topics-reviews-container-" + comment.commentId + " ul").append(
                            '<li>' +
                            '<div data-comment="' + comment.commentId + '" class="topic-div-container-' + Inside[q].split("-")[1] + '" data-topic="' + Inside[q].split("-")[0] + '" data-sentiment="' + Inside[q].split("-")[1] + '">' +
                            Inside[q].split("-")[0] +
                            '</div>' +
                            '</li>'

                            );

                }
            }
            $(document).on("click", ".topics-ul-container li div", function () {
                $("#" + $(this).parent().parent().parent().attr("id") + " li div").removeClass("picked-div");
                //$(".topics-ul-container li div").removeClass("picked-div");
                $(this).addClass("picked-div");
                var topic = $(this).attr("data-topic");
                var idcom = $(this).attr("data-comment");
                var sentiment = $(this).attr("data-sentiment");
                $("li[data-id='" + idcom + "'] span").removeClass("higlighted-sentence");
                $("li[data-id='" + idcom + "']" + " .category-" + topic.replace(/[^a-zA-Z0-9]+/g, "") + "-" + sentiment).addClass("higlighted-sentence");
            });


            beforetotal = beforetotal + comments.length;
            if (forward === 0) {
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getbeforepagecomments(' + (start) + ',' + (end) + ',' + beforetotal + ',' + total + ',' + rangestart + ',' + rangeend + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Next page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + beforetotal + ' - ' + total +
                        '</p>' +
                        '</div>' +
                        '</div>');
            } else {

                $(".review-list-topic-filter").first().append('<div class=" row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center ">' +
                        '<a href="javascript:getbeforepagecomments(' + (start) + ',' + (end) + ',' + beforetotal + ',' + total + ',' + rangestart + ',' + rangeend + ');"  id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getnextpagecomments(' + (start) + ',' + (end) + ',' + beforetotal + ',' + total + ',' + rangestart + ',' + rangeend + ');"  id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + beforetotal + ' - ' + total +
                        '</p>' +
                        '</div>' +
                        '</div>');
            }
            $(".sliding-panel-widget-scrollable ").animate({scrollTop: $("#reviews-reviews-reviews").offset().top
            }, 2000);
        }
    });
}
function getbeforepagecomments(start, end, beforetotal, total, rangestart, rangeend) {
    start = start - 10;
    end = end - 10;

    var forward;
    var backward;
    var reviewstotal = start - rangestart;
    if (reviewstotal > 0) {
        backward = 9;
    } else {
        backward = 0;
    }


    $("#reviews-reviews-reviews").html("");
    $("#pager-div-panel").remove();
    $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");

    $.ajax({
        type: "GET",
        url: "Home?action=BringComments&start=" + (start) + "&end=" + (end),
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")}, // serializes the form's elements.
        beforeSend: function () {
            $("#loading-reviews-gif").show();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
            $("input:checkbox[name='group__3']").prop('disabled', true);

        },
        complete: function () {
            $("#loading-reviews-gif").hide();
            $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
            $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
            $("input:checkbox[name='group__3']").prop('disabled', false);
        },
        success: function (data)
        {
            var results = data;
            var hotel = results.Hotel;
            var comments = results.Comments;

            for (var i = 0; i < comments.length; i++) {
                total--;
                var k = 0;
                var comment = comments[i].com;
                var sentences = comments[i].sentence;

                $("#reviews-reviews-reviews").append(
                        '<li data-id="' + comment.commentId + '" data-pagerposition="' + i + '" data-reviewer-type="' + comment.commentReviwerType + '" data-reviewer-country="' + comment.commentCountry + '" data-date-review="' + comment.dateReview + '" class="review_item clearfix review_item ">' +
                        '<p class="review_item_date" data-et-view="bLTLRZYGaQDDFWJcXe:1">' +
                        comment.dateReview +
                        '</p>' +
                        '<div class="review_item_reviewer">' +
                        '<div>' +
                        '<img class="avatar-mask ava-pad-bottom ava-default" src="https://t-ec.bstatic.com/static/img/review/avatars/ava-p/866dca38dcc31cb6fa2e9b4c475bd32e681b0080.png" alt="" onerror="this.onerror = null;this.src = "https://t-ec.bstatic.com/static/img/review/avatars/ava-p/866dca38dcc31cb6fa2e9b4c475bd32e681b0080.png";">' +
                        '</div>' +
                        '<h4>' +
                        '</h4>' +
                        '<span class="reviewer_country">' + comment.commentCountry +
                        '<span class="reviewer_country_flag sflag slang-sk  ">' +
                        '</span>' +
                        '</span>' +
                        '<div class="user_age_group">' +
                        comment.commentReviwerType +
                        '</div>' +
                        '<div class="user_badge_list">' +
                        '<img width="20' +
                        '" src="https://s-ec.bstatic.com/static/img/ugc/badges/badge_review_level1/f11fbfff4efab774acb19f9ad27422c9ed2fe154.png" class="js-fly-content-tooltip " data-content-tooltip="<p><strong>Status: Wordsmith Level 1</strong><br><br>Wordsmiths love to write about their trips and keep coming back to tell us more!</p>" data-extra-class-tooltip="fly-content-tooltip r-badge-tooltip">' +
                        '</div>' +
                        '<div class="review_item_user_review_count">' +
                        '</div>' +
                        '<div class="review_item_user_helpful_count">' +
                        '</div>' +
                        '</div>' +
                        '<div class="review_item_review">' +
                        '<div class="review_item_review_container lang_ltr">' +
                        '<div class="review_item_review_header">' +
                        '<div class="' +
                        'review_item_header_score_container' +
                        '">' +
                        '<span class=" review-score-widget review-score-widget__superb review-score-widget__score-only      review-score-widget__no-subtext    jq_tooltip  " data-et-click="customGoal:adUAAdCMAZTZWKNYT:1" data-et-mouseenter="customGoal:adUAAdCMAZTZWKNYT:2" id="b_tt_holder_1">' +
                        '<span id="topics-reviews-badge-' + comment.commentId + '" class="review-score-badge">' +
                        '</span>' +
                        '</span>' +
                        '</div>' +
                        '<div class="review_item_header_content_container">' +
                        '<div id="topics-reviews-container-' + comment.commentId + '" class="review_item_header_content' +
                        '"><ul class="topics-ul-container"></ul>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '<div class="review_item_review_content">' +
                        '<p id="reviews-content-negative-' + comment.commentId + '" class="review_neg"><i class="far fa-minus-square"></i>' +
                        '</p>' +
                        '<p id="reviews-content-positive-' + comment.commentId + '" class="review_pos"><i class="far fa-plus-square"></i>' +
                        '</p>' +
                        '</div>' +
                        '</div>' +
                        '</div>' +
                        '</li>');
                var negative = 0;
                var positive = 0;
                var Inside = [];
                for (var j = 0; j < sentences.length; j++) {
                    if (sentences[j].sentenceobject.sentiment === 0) {
                        $('#reviews-content-negative-' + comment.commentId).append("<span id='sentence" + sentences[j].sentenceobject.sentenceid + "'>" + sentences[j].sentenceobject.sentenceText + "</span>");
                    } else {
                        $('#reviews-content-positive-' + comment.commentId).append("<span id='sentence" + sentences[j].sentenceobject.sentenceid + "'>" + sentences[j].sentenceobject.sentenceText + "</span>");
                    }
                    var aspects = sentences[j].Aspects;
                    for (var k = 0; k < aspects.length; k++) {
                        $("#sentence" + sentences[j].sentenceobject.sentenceid).addClass("category-" + aspects[k].aspect.replace(/[^a-zA-Z0-9]+/g, "") + "-" + aspects[k].polarity);
                        //alert(aspects[k].aspect +" "+aspects[k].polarity);
                        Inside.push(aspects[k].aspect.toString() + "-" + aspects[k].polarity.toString());
                        if (aspects[k].polarity === "positive") {
                            positive++;
                        }
                        if (aspects[k].polarity === "negative") {
                            negative++;
                        }
                    }
                }
                if (positive < negative) {
                    $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-thumbs-down"></i>');
                } else if (positive > negative) {
                    $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-thumbs-up"></i>');


                } else {
                    $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-question-circle"></i>');



                }
                Inside = Inside.filter(onlyUnique);
                for (var q = 0; q < Inside.length; q++) {
                    // alert(Inside[q]);
                    $("#topics-reviews-container-" + comment.commentId + " ul").append(
                            '<li>' +
                            '<div data-comment="' + comment.commentId + '" class="topic-div-container-' + Inside[q].split("-")[1] + '" data-topic="' + Inside[q].split("-")[0] + '" data-sentiment="' + Inside[q].split("-")[1] + '">' +
                            Inside[q].split("-")[0] +
                            '</div>' +
                            '</li>'

                            );

                }
            }
            $(document).on("click", ".topics-ul-container li div", function () {
                $("#" + $(this).parent().parent().parent().attr("id") + " li div").removeClass("picked-div");
                //$(".topics-ul-container li div").removeClass("picked-div");
                $(this).addClass("picked-div");
                var topic = $(this).attr("data-topic");
                var idcom = $(this).attr("data-comment");
                var sentiment = $(this).attr("data-sentiment");
                $("li[data-id='" + idcom + "'] span").removeClass("higlighted-sentence");
                $("li[data-id='" + idcom + "']" + " .category-" + topic.replace(/[^a-zA-Z0-9]+/g, "") + "-" + sentiment).addClass("higlighted-sentence");
            });



            beforetotal = beforetotal - comments.length;
            if (backward === 0) {
                beforetotal = 1;
                total = comments.length;
                $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                        'Previous page' +
                        '</button>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getnextpagecomments(' + (start) + ',' + (end) + ',' + beforetotal + ',' + total + ',' + rangestart + ',' + rangeend + ');" id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + beforetotal + ' - ' + total + '</p>' +
                        '</div>' +
                        '</div>');
            } else {
                $(".review-list-topic-filter").first().append('<div class="row" id="pager-div-panel">' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getbeforepagecomments(' + (start) + ',' + (end) + ',' + beforetotal + ',' + total + ',' + rangestart + ',' + rangeend + ');" id="review_previous_page_link_my">' +
                        'Previous page' +
                        '</a>' +
                        '</p>' +
                        '</div>' +
                        '<div class="col-sm-6">' +
                        '<p class="text-center">' +
                        '<a href="javascript:getnextpagecomments(' + (start) + ',' + (end) + ',' + beforetotal + ',' + total + ',' + rangestart + ',' + rangeend + ');" id="review_next_page_link_my" >' +
                        'Next page' +
                        '</a>' +
                        '</p>' +
                        ' </div>' +
                        '<div class="col-sm-12">' +
                        '<p class="text-center">' + beforetotal + ' - ' + total +
                        '</p>' +
                        '</div>' +
                        '</div>');
            }
            $(".sliding-panel-widget-scrollable ").animate({scrollTop: $("#reviews-reviews-reviews").offset().top
            }, 2000);
        }

    });
}
function logout() {

    $.ajax({
        type: "GET",

        url: "Home?action=Logout", // serializes the form's elements.
        success: function (data)
        {
            window.location.replace("newjsp.jsp");
        }
    });
}
function sameheightcards() {

    var maxHeight = 0;

    $(".event-card").each(function () {
        if ($(this).height() > maxHeight) {
            maxHeight = $(this).height();
        }
    });

    $(".event-card").height(maxHeight);
}
//
function maximizeevents(thiss) {

    if ($("#" + thiss).css("display") === "none") {
        $("#" + thiss).slideDown();
    } else {
        $("#" + thiss).slideUp();
    }
}

$(document).ready(function () {
    
    $('.collapse').on('show.bs.collapse', function (e) {
        // Get clicked element that initiated the collapse...
        clicked = $(document).find("[href='#" + $(e.target).attr('id') + "']");
    });
    $(".sliding-panel-widget-content").css("box-sizing", "content-box");
    $(".sliding-panel-widget-close-button").css("right", "650px");
    $("#review_list_score_container").append('<div id="review_list_page_container" style="display: block;"><script src="https://t-ec.bstatic.com/static/js/reviewlist_ecv6/8b1a5c08f44f42c21bd9f16017cfc84175d05c8a.js"></script>' +
            '<div data-et-view="YdVRZYGGcAcSBRe:1"></div>' +
            '<div data-et-view="adUAVGZaZfLLIMLaUJeaILYJO:1 adUAVGZaZfLLIMLaUJeaILYJO:3"></div>' +
            '<div class="review-list-topic-filter inline-block">' +
            '<div data-et-view="adUAVGZaefUPcSBXe:1"></div>' +
            '<div class="row"><div class="col-sm-12"><p class="review-list-topic-filter__heading">' +
            'Select a topic to filter reviews' +
            '</p></div>' +
            '<div class="col-sm-12"><form id="aspexts-criteria-form" method="post" action="Home?action=AspecFilter"><ul id="filters-topics-sort-option" class="review-list-topic-filter__list clearfix">' +
            '</form></ul></div></div>' + '<div data-et-view="adUAAEGeCaHQDSWLBebaPNfELT:1"></div>' +
            '<ul id="reviews-reviews-reviews" class="review_list" data-et-view="adUAVGPRALTQcJC:9 bLTLcLcJUVZAQfTbaebaFFae:1 ">' + ''
            + '</ul>' + '</div>' + '</div>');
   // alert(window.location.href);

    $.ajax({
        type: "GET",
        url: "Home?action=BringTopicsHotel",
        data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")}, // serializes the form's elements.

        success: function (data)
        {
            var AspectsIn = [];
            var Asp = data.AspectsHotel;
            for (var p = 0; p < Asp.length; p++) {
                if (AspectsIn.indexOf(Asp[p].aspect) === -1) {
                    AspectsIn[p] = Asp[p].aspect;
                    $("#filters-topics-sort-option").append("<li class='review-list-topic-filter__list-item'><label class='review-list-topic-link'><input type='checkbox' name='group__3' value='" + Asp[p].aspect + "' class='checkboxes-aspects-filter'/>" + Asp[p].aspect + "</label></li>");
                }
            }
            var dat = {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")};

            $("#filters-topics-sort-option li input").change(function () {
                if ($(this).prop("checked") === true) {
                    $(this).parent().parent().css({"background-color": "#003580", "color": "#fff"});
                } else {
                    $(this).parent().parent().css({"background-color": "#ccc", "color": "#333"});
                }
                if ($("#filters-topics-sort-option li input:checked").length > 0) {
                    BringCommentsByAspect();
                } else {
                    BringCommentsBothAll();
                }

            });
        }

    });


    ///////
    if ($("#show_reviews_tab").length !== 0) {
        $("#reviews-reviews-reviews").html("");
        $("#pager-div-panel").remove();
        //alert(window.location.href);
        var rangestart;
        var rangeend;
        var total = 0;
        var beforetotal = 1;
        $("#reviews-error-message").css("max-width", "unset");
        $("#reviews-error-message").css("text-align", "unset");
        $("#reviews-error-message").css("margin", "0");
        $("#reviews-error-message").css("display", "block");

        $(".language_filter").first().html('');
        $('#reviews-error-message').css("border-bottom", "1px solid #ededed");
        $('#reviews-error-message').html(
                '<div style="display:none" data-js-change-dropdown-to-radio-s="2"></div>' +
                '<div style="display:none" data-js-change-dropdown-to-radio-s="4"></div>' +
                ' <div class="reviews-sorting__wrapper">' +
                '<div id="countryusersort" class="reviews-sorting__row">' + '</div>' + '<div id="typeofusersort" class="reviews-sorting__row">' + '</div>' + '</div>');
        $.ajax({
            type: "GET",
            url: "Home?action=BringSortOptions",
            data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")}, // serializes the form's elements.
            success: function (data)
            {
                $('#countryusersort').append(
                        '<label class="reviews-sorting__title"> Show reviews from: </label>' +
                        '<label class="reviews-sorting__label selected-highl"> <input type="radio" name="group__1" class="reviews-sorting__input" data-js-radio-inp="#reviewer_type_filter" checked="" id="total-group-1"  value="total"> <span class="reviews-sorting__span"> All reviewers </span> </label>'
                        );
                $('#typeofusersort').append(
                        '<label class="reviews-sorting__title"> Show reviews from: </label>' +
                        '<label class="reviews-sorting__label selected-highl"> <input type="radio" name="group__2" class="reviews-sorting__input" data-js-radio-inp="#reviewer_type_filter" checked="" id="total-group-2" value="total"> <span class="reviews-sorting__span"> All reviewers </span> </label>'
                        );


                var countries = data.CountrySort;
                var types = data.typeSort;
                for (var i = 0; i < countries.length; i++) {
                    $('#countryusersort').append(
                            '<label class="reviews-sorting__label"> <input type="radio" name="group__1" class="reviews-sorting__input" data-js-radio-inp="#reviewer_type_filter"  data-quantity="' + countries[i].countCommentCountry + '"  data-score=""  data-dist="' + (i + 2) + '"  value="' + countries[i].commentCountry + '"> <span class="reviews-sorting__span">' + countries[i].commentCountry + '(' + countries[i].countCommentCountry + ')' + '</span> </label>'

                            );
                }
                for (var j = 0; j < types.length; j++) {
                    $('#typeofusersort').append(
                            '<label class="reviews-sorting__label"> <input type="radio" name="group__2" class="reviews-sorting__input" data-js-radio-inp="#reviewer_type_filter"  data-quantity="' + types[j].commentCountReviwerType + '"  data-score=""  data-dist="' + (j + 2) + '"  value="' + types[j].commentReviwerType + '"> <span class="reviews-sorting__span">' + types[j].commentReviwerType + '(' + types[j].commentCountReviwerType + ')' + '</span> </label>'

                            );
                }
                /* '<div style="display:none">' +
                 '<span class="review-filter-container"> <label for="reviewer_type_filter" class="review_label">Show reviews from:</label> <select class="reviewer_type_filter" id="reviewer_type_filter"> <option data-quantity="99" data-customer-type="total" data-score="" data-cc="gr" data-dist="1" data-pagename="driopon-apartment" data-add="" value="total"> All reviewers </option> <option data-quantity="44" data-customer-type="family_with_children" data-score="" data-cc="gr" data-dist="2" data-pagename="driopon-apartment" data-add="" value="family_with_children"> Families (44) </option> <option data-quantity="6" data-customer-type="couple" data-score="" data-cc="gr" data-dist="3" data-pagename="driopon-apartment" data-add="" value="couple"> Couples (6) </option> <option data-quantity="45" data-customer-type="review_category_group_of_friends" data-score="" data-cc="gr" data-dist="4" data-pagename="driopon-apartment" data-add="" value="review_category_group_of_friends"> Groups of friends (45) </option> <option data-quantity="4" data-customer-type="solo_traveller" data-score="" data-cc="gr" data-dist="5" data-pagename="driopon-apartment" data-add="" value="solo_traveller"> Solo travellers (4) </option> <option data-quantity="3" data-customer-type="business_traveller" data-score="" data-cc="gr" data-dist="6" data-pagename="driopon-apartment" data-add="" value="business_traveller"> Business travellers (3) </option> </select> <label for="review_score_filter" class="invisible_spoken">Show reviews from:</label> <select class="reviewer_type_filter" id="review_score_filter"> <option data-customer-type="total" data-cc="gr" data-dist="" data-pagename="driopon-apartment" data-add="" value="">All review scores</option> <option data-customer-type="total" data-cc="gr" data-dist="1" data-pagename="driopon-apartment" data-add="" value="review_adj_superb"> Superb: 9+ (72) </option> <option data-customer-type="total" data-cc="gr" data-dist="2" data-pagename="driopon-apartment" data-add="" value="review_adj_good"> Good: 7 ? 9 (24) </option> <option data-customer-type="total" data-cc="gr" data-dist="3" data-pagename="driopon-apartment" data-add="" value="review_adj_average_okay"> Okay: 5 ? 7 (1) </option> <option data-customer-type="total" data-cc="gr" data-dist="4" data-pagename="driopon-apartment" data-add="" value="review_adj_poor"> Poor: 3 ? 5 (1) </option> <option data-customer-type="total" data-cc="gr" data-dist="5" data-pagename="driopon-apartment" data-add="" value="review_adj_very_poor"> Very poor: 1 ? 3 (1) </option> </select> </span>' +
                 '</div>' +
                 '<div data-et-view="adUAVGZaFFdJeBLdaRO:1"></div>' +
                 '<div data-et-view="adUAVGZEGMLaJeaILYJO:1' +
                 'adUAVGZEGMLaJeaILYJO:2">' +
                 '</div>' +
                 ' <div data-et-view="ZOOTdCNBLQFVRZaHLSGDIXaO:1"></div>' +
                 '<div class="review_sort_container" data-et-view="adUAVGPTLKGBfJaPdfcSPDDBRe:1 adUAVGPTLKGBfJaPdfcSPDDBRe:3 adUAVGPZZaPDYGPVEYNUEFLO:1">' +
                 '<label class="reviews-sorting__title"> Sort by: </label>' +
                 '<label class="reviews-sorting__label"> <input type="radio" value="" name="group__3" class="reviews-sorting__input" checked="" data-js-radio-inp="#review_sort"> <span class="reviews-sorting__span"> Recommended </span> </label>' +
                 '<label class="reviews-sorting__label"> <input type="radio" value="f_recent_desc" name="group__3" class="reviews-sorting__input" data-js-radio-inp="#review_sort"> <span class="reviews-sorting__span"> Date (newer to older) </span> </label>' +
                 '<label class="reviews-sorting__label"> <input type="radio" value="f_recent_asc" name="group__3" class="reviews-sorting__input" data-js-radio-inp="#review_sort"> <span class="reviews-sorting__span"> Date (older to newer) </span> </label>' +
                 '<div style="display:none">' +
                 ' <label for="review_sort" class="review_label">Sort by:</label>' +
                 '<select class="review_sort" id="review_sort" data-et-click="adUAVGPTLKGBfJaPdfcSPDDBRe:2 adUAVGPTLKGBfJaPdfcSPDDBRe:5"> <option value="">Recommended</option> <option value="f_recent_desc">Date (newer to older)</option> <option value="f_recent_asc">Date (older to newer)</option></select>' +
                 '</div>' +
                 '</div>' +
                 '</div>'
                 */
                jQuery.each($("#countryusersort input:radio[name='group__1']"), function (i, al) {

                    $(al).change(function () {
                        $("#countryusersort input:radio[name='group__1']").parent().removeClass("selected-highl");
                        if ($(this).prop("checked") === true) {
                            $(this).parent().addClass("selected-highl");
                        }
                        // alert($("#countryusersort input:radio[name='group__1']:checked").val());
                        // alert($("#typeofusersort input:radio[name='group__2']:checked").val());
                        $("#filters-topics-sort-option input").prop("checked", false).parent().parent().css({"background-color": "#ccc", "color": "#333"});
                        if ($("#typeofusersort input:radio[name='group__2']:checked").val() === 'total' && $("#countryusersort input:radio[name='group__1']:checked").val() !== 'total') {
                            BringCommentsByCountry(this.value, $(this).attr("data-quantity"));
                        } else if ($("#typeofusersort input:radio[name='group__2']:checked").val() !== 'total' && $("#countryusersort input:radio[name='group__1']:checked").val() !== 'total') {
                            BringCommentsByBoth(this.value, $("#typeofusersort input:radio[name='group__2']:checked").val());
                        } else if ($("#typeofusersort input:radio[name='group__2']:checked").val() !== 'total' && $("#countryusersort input:radio[name='group__1']:checked").val() === 'total') {
                            BringCommentsByTypeOfUser($("#typeofusersort input:radio[name='group__2']:checked").val(), $("#typeofusersort input:radio[name='group__2']:checked").attr("data-quantity"));
                        } else if ($("#typeofusersort input:radio[name='group__2']:checked").val() === 'total' && $("#countryusersort input:radio[name='group__1']:checked").val() === 'total') {
                            BringCommentsBothAll();
                        }
                    });
                });
                jQuery.each($("#typeofusersort input:radio[name='group__2']"), function (i, al) {

                    $(al).change(function () {
                        $("#typeofusersort input:radio[name='group__2']").parent().removeClass("selected-highl");
                        if ($(this).prop("checked") === true) {
                            $(this).parent().addClass("selected-highl");
                        }
                        $("#filters-topics-sort-option input").prop("checked", false).parent().parent().css({"background-color": "#ccc", "color": "#333"});
                        //alert($("#countryusersort input:radio[name='group__1']:checked").val());
                        //alert($("#typeofusersort input:radio[name='group__2']:checked").val());
                        if ($("#countryusersort input:radio[name='group__1']:checked").val() === 'total' && $("#typeofusersort input:radio[name='group__2']:checked").val() !== 'total') {
                            BringCommentsByTypeOfUser(this.value, $(this).attr("data-quantity"));
                        } else if ($("#typeofusersort input:radio[name='group__2']:checked").val() !== 'total' && $("#countryusersort input:radio[name='group__1']:checked").val() !== 'total') {
                            BringCommentsByBoth($("#countryusersort input:radio[name='group__1']:checked").val(), $("#typeofusersort input:radio[name='group__2']:checked").val());
                        } else if ($("#typeofusersort input:radio[name='group__2']:checked").val() === 'total' && $("#countryusersort input:radio[name='group__1']:checked").val() !== 'total') {
                            BringCommentsByCountry($("#countryusersort input:radio[name='group__1']:checked").val(), $("#countryusersort input:radio[name='group__1']:checked").attr("data-quantity"));
                        } else if ($("#typeofusersort input:radio[name='group__2']:checked").val() === 'total' && $("#countryusersort input:radio[name='group__1']:checked").val() === 'total') {
                            BringCommentsBothAll();
                        }
                    });
                });
                var pager = data.pager;
                // alert(JSON.stringify(pager));

                rangestart = pager.hstart;
                rangeend = pager.end;
                //alert(rangestart);
                //alert(rangeend);

                $("#reviews-reviews-reviews").append("<div class='row'><div class='col-sm-12'><img id='loading-reviews-gif' src='Css/05741525b70c7ca6bcb88afd4aa16632.gif'/></div></div>");
                $.ajax({
                    type: "GET",
                    url: "Home?action=BringComments&start=" + rangestart + "&end=" + (rangestart + 9) + "",
                    data: {"urlhotel": window.location.href.replace("http://"+extractRootDomain(window.location.href)+"/UrlContoller?q=", "")}, // serializes the form's elements.
                    beforeSend: function () {
                        $("#loading-reviews-gif").show();
                        $("#countryusersort input:radio[name='group__1']").prop('disabled', true);
                        $("#typeofusersort input:radio[name='group__2']").prop('disabled', true);
                        $("input:checkbox[name='group__3']").prop('disabled', true);

                    },
                    complete: function () {
                        $("#loading-reviews-gif").hide();
                        $("#countryusersort input:radio[name='group__1']").prop('disabled', false);
                        $("#typeofusersort input:radio[name='group__2']").prop('disabled', false);
                        $("input:checkbox[name='group__3']").prop('disabled', false);
                    },
                    success: function (data)
                    {
                        //alert(rangestart);
                        //alert(rangeend);
                        var results = data;
                        var hotel = results.Hotel;
                        var comments = results.Comments;

                        for (var i = 0; i < comments.length; i++) {
                            total++;
                            var k = 0;
                            var comment = comments[i].com;
                            var sentences = comments[i].sentence;

                            //alert(comment.commentBodyPos + " /// " + comment.commentBodyNeg);

                            $("#reviews-reviews-reviews").append(
                                    '<li data-id="' + comment.commentId + '" data-pagerposition="' + i + '" data-reviewer-type="' + comment.commentReviwerType + '" data-reviewer-country="' + comment.commentCountry + '" data-date-review="' + comment.dateReview + '" class="review_item clearfix review_item ">' +
                                    '<p class="review_item_date" data-et-view="bLTLRZYGaQDDFWJcXe:1">' +
                                    comment.dateReview +
                                    '</p>' +
                                    '<div class="review_item_reviewer">' +
                                    '<div>' +
                                    '<img class="avatar-mask ava-pad-bottom ava-default" src="https://t-ec.bstatic.com/static/img/review/avatars/ava-p/866dca38dcc31cb6fa2e9b4c475bd32e681b0080.png" alt="" onerror="this.onerror = null;this.src = "https://t-ec.bstatic.com/static/img/review/avatars/ava-p/866dca38dcc31cb6fa2e9b4c475bd32e681b0080.png";">' +
                                    '</div>' +
                                    '<h4>' +
                                    '</h4>' +
                                    '<span class="reviewer_country">' + comment.commentCountry +
                                    '<span class="reviewer_country_flag sflag slang-sk  ">' +
                                    '</span>' +
                                    '</span>' +
                                    '<div class="user_age_group">' +
                                    comment.commentReviwerType +
                                    '</div>' +
                                    '<div class="user_badge_list">' +
                                    '<img width="20' +
                                    '" src="https://s-ec.bstatic.com/static/img/ugc/badges/badge_review_level1/f11fbfff4efab774acb19f9ad27422c9ed2fe154.png" class="js-fly-content-tooltip " data-content-tooltip="<p><strong>Status: Wordsmith Level 1</strong><br><br>Wordsmiths love to write about their trips and keep coming back to tell us more!</p>" data-extra-class-tooltip="fly-content-tooltip r-badge-tooltip">' +
                                    '</div>' +
                                    '<div class="review_item_user_review_count">' +
                                    '</div>' +
                                    '<div class="review_item_user_helpful_count">' +
                                    '</div>' +
                                    '</div>' +
                                    '<div class="review_item_review">' +
                                    '<div class="review_item_review_container lang_ltr">' +
                                    '<div class="review_item_review_header">' +
                                    '<div class="' +
                                    'review_item_header_score_container' +
                                    '">' +
                                    '<span class=" review-score-widget review-score-widget__superb review-score-widget__score-only      review-score-widget__no-subtext    jq_tooltip  " data-et-click="customGoal:adUAAdCMAZTZWKNYT:1" data-et-mouseenter="customGoal:adUAAdCMAZTZWKNYT:2" id="b_tt_holder_1">' +
                                    '<span id="topics-reviews-badge-' + comment.commentId + '" class="review-score-badge">' +
                                    '</span>' +
                                    '</span>' +
                                    '</div>' +
                                    '<div class="review_item_header_content_container">' +
                                    '<div id="topics-reviews-container-' + comment.commentId + '" class="review_item_header_content' +
                                    '"><ul class="topics-ul-container"></ul>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>' +
                                    '<div class="review_item_review_content">' +
                                    '<p id="reviews-content-negative-' + comment.commentId + '" class="review_neg"><i class="far fa-minus-square"></i>' +
                                    '</p>' +
                                    '<p id="reviews-content-positive-' + comment.commentId + '" class="review_pos"><i class="far fa-plus-square"></i>' +
                                    '</p>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>' +
                                    '</li>');
                            var negative = 0;
                            var positive = 0;
                            var Inside = [];
                            for (var j = 0; j < sentences.length; j++) {
                                if (sentences[j].sentenceobject.sentiment === 0) {
                                    $('#reviews-content-negative-' + comment.commentId).append("<span id='sentence" + sentences[j].sentenceobject.sentenceid + "'>" + sentences[j].sentenceobject.sentenceText + "</span>");
                                } else {
                                    $('#reviews-content-positive-' + comment.commentId).append("<span id='sentence" + sentences[j].sentenceobject.sentenceid + "'>" + sentences[j].sentenceobject.sentenceText + "</span>");
                                }
                                var aspects = sentences[j].Aspects;
                                for (var k = 0; k < aspects.length; k++) {
                                    $("#sentence" + sentences[j].sentenceobject.sentenceid).addClass("category-" + aspects[k].aspect.replace(/[^a-zA-Z0-9]+/g, "") + "-" + aspects[k].polarity);
                                    //alert(aspects[k].aspect +" "+aspects[k].polarity);
                                    Inside.push(aspects[k].aspect.toString() + "-" + aspects[k].polarity.toString());
                                    if (aspects[k].polarity === "positive") {
                                        positive++;
                                    }
                                    if (aspects[k].polarity === "negative") {
                                        negative++;
                                    }
                                }
                            }
                            //alert(positive);
                            //alert(negative);
                            if (positive < negative) {
                                $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-thumbs-down"></i>');
                            } else if (positive > negative) {
                                $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-thumbs-up"></i>');


                            } else {
                                $("#topics-reviews-badge-" + comment.commentId).append('<i class="review-score-badge-badge far fa-question-circle"></i>');



                            }
                            Inside = Inside.filter(onlyUnique);
                            for (var q = 0; q < Inside.length; q++) {
                                //aleady()t(Inside[q]);
                                $("#topics-reviews-container-" + comment.commentId + " ul").append(
                                        '<li>' +
                                        '<div data-comment="' + comment.commentId + '" class="topic-div-container-' + Inside[q].split("-")[1] + '" data-topic="' + Inside[q].split("-")[0] + '" data-sentiment="' + Inside[q].split("-")[1] + '">' +
                                        Inside[q].split("-")[0] +
                                        '</div>' +
                                        '</li>'

                                        );

                            }


                        }
                        $(document).on("click", ".topics-ul-container li div", function () {
                            $("#" + $(this).parent().parent().parent().attr("id") + " li div").removeClass("picked-div");
                            //$(".topics-ul-container li div").removeClass("picked-div");
                            $(this).addClass("picked-div");
                            var topic = $(this).attr("data-topic");
                            var idcom = $(this).attr("data-comment");
                            var sentiment = $(this).attr("data-sentiment");
                            $("li[data-id='" + idcom + "'] span").removeClass("higlighted-sentence");
                            $("li[data-id='" + idcom + "']" + " .category-" + topic.replace(/[^a-zA-Z0-9]+/g, "") + "-" + sentiment).addClass("higlighted-sentence");
                        });

                        var forward;
                        var backward;
                        var reviewstotal = rangeend - rangestart;
                        if (reviewstotal > 10) {
                            forward = 9;
                        } else {
                            forward = reviewstotal;
                        }
                        if (reviewstotal > 0) {



                            $(".review-list-topic-filter").first().append('<div class="row pager-revies" id="pager-div-panel">' +
                                    '<div class="col-sm-6">' +
                                    '<p class="text-center">' +
                                    '<button class="disabled-button" disabled id="review_previous_page_link_my" >' +
                                    'Previous page' +
                                    '</button>' +
                                    '</p>' +
                                    '</div>' +
                                    '<div class="col-sm-6">' +
                                    '<p class="text-center">' +
                                    '<a href="javascript:getnextpagecomments(' + (rangestart) + ',' + (rangestart + forward) + ',' + beforetotal + ',' + total + ',' + rangestart + ',' + rangeend + ');" id="review_next_page_link_my" >' +
                                    'Next page' +
                                    '</a>' +
                                    '</p>' +
                                    '</div>' +
                                    '<div class="col-sm-12">' +
                                    '<p class="text-center page-number-reviews">1 -' + comments.length +
                                    '</p> ' +
                                    '</div>' +
                                    '</div>');

                        }
                    }
                });
            }});


    }
    ///////
    $('#nextStep').attr('disabled', "disabled");
    $("#sendsignup").attr("disabled", "disabled");
    $(function () {
        $("#email, #pass").bind("change keyup focus",
                function () {
                    if ($("#email").val().length !== 0 && $("#pass").val().length !== 0)
                        $("#nextStep").removeAttr("disabled");
                    else
                        $("#nextStep").attr("disabled", "disabled");
                });
    });
    $(function () {
        $("#FirstName, #LastName").bind("change keyup focus",
                function () {
                    if ($("#FirstName").val().length !== 0 && $("#LastName").val().length !== 0)
                        $("#nextStep").removeAttr("disabled");
                    else
                        $("#nextStep").attr("disabled", "disabled");
                });
    });
    $(function () {
        $("#nationality").bind("change keyup focus",
                function () {
                    if ($("#nationality").val() !== "") {
                        $("#nextStep").removeAttr("disabled");
                        $("#sendsignup").removeAttr("disabled");
                    } else {
                        $("#nextStep").attr("disabled", "disabled");
                        $("#sendsignup").attr("disabled", "disabled");
                    }
                });
    });
    $(function () {
        $(".brand-box-sign-up").bind("click",
                function () {
                    var userid;
                    var access_token;
                    $.ajaxSetup({cache: true});
                    $.getScript('//connect.facebook.net/en_US/sdk.js', function () {
                        FB.init({
                            appId: '1996491037034668',
                            version: 'v2.11' // or v2.1, v2.2, v2.3, ...
                        });
                        FB.login(function (response) {
                            access_token = response.accestoken;
                            userid = response.authResponse.userID;
                            FB.api('/' + userid + '?fields=email,birthday,address,name,gender', function (response) {

                                $.ajax({
                                    type: "POST",
                                    contentType: "json",
                                    url: "Home?action=FbSignUp",
                                    data: JSON.stringify(response), // serializes the form's elements.
                                    success: function (data)
                                    {
                                        location.reload();
                                    }
                                });
                            }
                            );
                        });
                    });
                });
    });
    $(function () {
        $(".brand-box-login").bind("click",
                function () {
                    var userid;
                    var access_token;
                    $.ajaxSetup({cache: true});
                    $.getScript('//connect.facebook.net/en_US/sdk.js', function () {
                        FB.init({
                            appId: '1996491037034668',
                            version: 'v2.11' // or v2.1, v2.2, v2.3, ...
                        });
                        FB.login(function (response) {
                            access_token = response.accestoken;
                            userid = response.authResponse.userID;
                            FB.api('/' + userid + '?fields=email,birthday,address,name,gender', function (response) {

                                $.ajax({
                                    type: "POST",
                                    contentType: "json",
                                    url: "Home?action=FbLogin",
                                    data: JSON.stringify(response), // serializes the form's elements.
                                    success: function (data)
                                    {
                                        location.reload();
                                    }
                                });
                            }
                            );
                        });
                    });
                });
    });
    $("#nextStep").click(function () {
        var active;
        jQuery.each($(".tab-pane"), function (i, al) {
            if ($(al).hasClass("active")) {
                active = i;


            }
        });
        if (active < 2) {
            $($(".tab-pane")[active]).removeClass("active");

            $($("#nav-steps a")[active]).removeClass("active");
            $($("#nav-steps a")[active + 1]).addClass("active");
            $($(".tab-pane")[active + 1]).addClass("active");
            $(".progress-bar-animated").width($(".progress-bar-animated").width() + $(".progress").width() / 3);
            $($(".tab-pane")[active + 1]).find("input").focus();
            $($(".tab-pane")[active + 1]).find("select").focus();
        }
        if ($("#step3").hasClass("active")) {
            $("#nextStep").hide();
        } else {
            $("#nextStep").show();
        }
    });

    $("#backStep").click(function () {
        var active;
        jQuery.each($(".tab-pane"), function (i, al) {
            if ($(al).hasClass("active")) {
                active = i;


            }
        });
        if (active > 0) {
            $($(".tab-pane")[active]).removeClass("active");
            $($("#nav-steps a")[active]).removeClass("active");
            $($("#nav-steps a")[active - 1]).addClass("active");
            $($(".tab-pane")[active - 1]).addClass("active");
            $(".progress-bar-animated").width($(".progress-bar-animated").width() - $(".progress").width() / 3);
        }
        if ($("#step3").hasClass("active")) {
            $("#nextStep").slideUp();
        } else {
            $("#nextStep").slideDown();
        }
    });


    //new jsp1

    function AnimateRotate(el, d) {
        var elem = $(el);

        $({deg: 0}).animate({deg: d}, {
            duration: 300,
            step: function (now) {
                elem.css({
                    transform: "rotate(" + now + "deg)"
                });
            }
        });
    }



    function opinionchcked(objecti) {
        var checked_in = $("#form-sort-results input.option-checker:checked").length;
        var value = $(objecti).val();
        //alert(value);
        if ($("#container-2-options").find("#" + value.replace(/[^a-zA-Z0-9]+/g, "")).length === 0) {
            $("#container-2-options ul").append("<li ondrop=\"alertdrop(event)\" id='" + value.replace(/[^a-zA-Z0-9]+/g, "") + "'><div class='row'><div class='col-sm-12'><span class='numer-sort'></span> <span>" + $(objecti).val() + "</span></div></div><div class='row'><div class='col-sm-12' style='margin-top:10px'><div class='input-range-css'><input id='" + value.replace(/[^a-zA-Z0-9]+/g, "") + "-range' value='100' name='range-" + value.replace(/[^a-zA-Z0-9]+/g, "") + "' type='range' min='10' max='100' step='10'></div></div></div><div class='row'><div class='col-sm-12'><span class='arrow_box' id='" + value.replace(/[^a-zA-Z0-9]+/g, "") + "-range-span'>100%</span></div></div></li>");
            $(objecti).parent().parent().parent().find(".check").css('stroke-dashoffset', 0);
            $(objecti).parent().css("color", "#239B56");
            $(objecti).parent().parent().parent().parent().find(".underline-border").css("width", "90%");
            $("#" + value.replace(/[^a-zA-Z0-9]+/g, "") + "-range").bind("input change", function () {
                $("#" + value.replace(/[^a-zA-Z0-9]+/g, "") + "-range-span").html($(this).val() + "%");
            });
        } else {
            $("#" + value.replace(/[^a-zA-Z0-9]+/g, "")).remove();
            $(objecti).parent().parent().parent().find(".check").css('stroke-dashoffset', 130);
            $(objecti).parent().css("color", "black");
            $(objecti).parent().parent().parent().parent().find(".underline-border").css("width", "0");
        }

        jQuery.each($("#container-2-options ul li"), function (i, element) {
            $(element).find(".numer-sort").html(i + 1);

        });
        if (checked_in === 0) {
            $("#form-sort-results button:submit").prop("disabled", true);
        } else {
            $("#form-sort-results button:submit").prop("disabled", false);
        }

    }
    $(document).ready(function () {
        $(".sr_room_reinforcement").css("display","inline-block");
        $(".sr_item").removeClass("card_elevation");
        $(".sr_item").removeClass("card_elevation_clickable");
        $(".sr_item").removeClass("card_elevation_nodebounce");
        $(".sr_item").removeClass("card_elevation_hover");
         
        $(".sr_item .btn-primary").click(function(e) {
   //do something
   $(""+$(this).attr("data-target")+"").modal("show");
   e.stopPropagation();
});
        $(".seac-bar-modal").keyup(function(){
            mySearchModal($(this).attr("id").split("-")[1]);
        });
        $("#opensortoptionbutton_delete").click(function () {
            $("#form-sort-results input").prop("checked", "false");
            $("#DeleteOptions").submit();
        });
        $(".tab-link-noncss").click(function () {
            $(".tab-link-noncss").parent().parent().removeClass("Clicked-link-blue");
            $(this).parent().parent().toggleClass("Clicked-link-blue");

        });
        $(".card").css("height", "inherit");
        //jQuery.each($("#frm input"), function (i, element) { 
        //alert($(element).attr("name")+"="+$(element).val());

        // });


        //////////

        $("#select-sort-option-container").parent().append("<form action='Home?action=SortOnReviews' method='post' id='form-sort-results'><div class='row'><div class='col-sm-6' ><div class='option-panel-sort' id='container-1-options'><div style='display:block' class=\"alert alert-info\"><strong>Select one or more from the options available !</strong></div><div><input type=\"text\" id=\"myInput\" onkeyup=\"searchaspectsbox()\" placeholder=\"Search for aspects..\"></div><div><input type='button' class='btn btn-primary'value='Select All' onclick='SelectAllOptions();'></div></div></div><div class='col-sm-6'><div class='option-panel-sort' id='container-2-options'><div id='sort-option-info-panel' style='display:block' class=\"alert alert-info\"><strong>You can also adjust the weigth of importance of your choises!</strong></div><ul></ul></div></div></div><div class='row'><div style='margin-bottom:0px;' class=\"col-sm-12\"><button type=\"submit\" style='width: 100%;height: 5%;' class=\"btn btn-primary\">Sort The Results</button></div></div></form>");
        jQuery.each($("#select-sort-option-container option"), function (i, al) {
            $("#container-1-options").append("<div class='div-container-sort' id='select-list" + $(al).val() + "'><div class='row'><div class='col-sm-8'><label class='label-sort'><input name='sort_based_option' class='option-checker' type='checkbox' value='" + $(al).val() + "'><span class='label-sort-span'>" + $(al).val() + "</span></label></div>" + "<div class='col-sm-4'><svg version=\"1.1\" id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"100%\" height=\"15px\" viewBox=\"0 0 90.594 59.714\" enable-background=\"new 0 0 90.594 59.714\" xml:space=\"preserve\"> <polyline class=\"check\" fill=\"none\" stroke=\"#006837\" stroke-width=\"5\" stroke-miterlimit=\"10\" points=\"1.768,23.532 34.415,56.179 88.826,1.768\"/></svg>" + "</div></div><div class='underline-border'></div></div>");

        });

        $("#DeleteOptions").submit(function (event) {
            $(".modalpagebody").css("height", "100%");
            // $(".modalpagebody").append("<iframe width=\"0\" height=\"0\" src=\"https://youtu.be/dQw4w9WgXcQ?autoplay=1\"></iframe>");
            $('#DeleteOptions').attr('action', window.location.href + "&action=SortOnReviews");
        });
        $("#form-sort-results button:submit").prop("disabled", "true");
        $("#form-sort-results").submit(function (event) {
            $(".modalpagebody").css("height", "100%");
            // $(".modalpagebody").append("<iframe width=\"0\" height=\"0\" src=\"https://youtu.be/dQw4w9WgXcQ?autoplay=1\"></iframe>");
            $('#form-sort-results').attr('action', window.location.href + "&action=SortOnReviews");
        });


        $(".option-checker").change(function () {
            opinionchcked(this);
        });

        /*  $("#container-2-options ul").sortable({
         update: function ( ) {
         jQuery.each($("#container-2-options ul li"), function (i, element) {
         $(element).find(".numer-sort").html(i + 1);
         });
         }
         , placeholder: "border"
         
         
         });
         */


        ///////


        $(function () {
            $(".sort_option_avoid ").bind("click", function () {
                if ($("#sort-option-div").css("display") === "none") {
                    $("#sort-option-div").slideDown();
                    AnimateRotate($("#rotation-bouli"), 360);
                    $('html, body').animate({scrollTop: $('#sort-option-div').offset().top}, 'slow');
                } else {
                    $("#sort-option-div").slideUp();
                    AnimateRotate($("#rotation-bouli"), 360);
                }
            });
        });

        $("#events-neraby").click(function () {

            if ($("#Fb-events-container").css("display") === "none") {
                $("#Fb-events-container").html("");
                $.ajaxSetup({cache: true});
                $.getScript('//connect.facebook.net/en_US/sdk.js', function () {
                    FB.init({
                        appId: '1996491037034668',
                        version: 'v2.11' // or v2.1, v2.2, v2.3, ...
                    });
                    FB.login(function (response) {
                        access_token = response.accestoken;
                        userid = response.authResponse.userID;
                        FB.api(
                                '/search',
                                'GET',
                                {"q": $('[name="ssne"]').val().split(",")[0] + ",greece", "access_token": access_token, "type": "event", "limit": "10"},
                                function (response) {
                                    $.ajax({
                                        url: 'Home?action=EventsFB',
                                        contentType: "json",
                                        data: JSON.stringify(response),
                                        type: 'POST',
                                        cache: false,
                                        success: function (result) {

                                        }
                                    });
                                    help = $(".event-card").length;
                                    $("#Fb-events-container").append("<div class='container-fluid event-panel'><div class='row button-mini'><div class='col-sm-12'><button onclick='maximizeevents(" + "\"accordion-event\"" + ");'><i class='far fa-minus-square'></i></button></div></div><div class='row'  id='accordion-event'></div></div>");
                                    jQuery.each(response.data, function (i, al) {

                                        if ("place" in al) {
                                            if ("location" in al.place) {
                                                if ("start_time" in al) {
                                                    if ("end_time" in al) {

                                                        var datestart = al.start_time.split("T")[0];

                                                        var dateend = al.end_time.split("T")[0];
                                                        if ("latitude" in al.place.location && "longitude" in al.place.location) {

                                                            var marker = new google.maps.Marker({
                                                                position: new google.maps.LatLng(al.place.location.latitude, al.place.location.longitude),
                                                                map: mapp
                                                            });

                                                            mapp.setZoom(13);
                                                            mapp.setCenter(new google.maps.LatLng(al.place.location.latitude, al.place.location.longitude));
                                                            mapp.setMapTypeId(google.maps.MapTypeId.ROADMAP);
                                                            $("#accordion-event").append("<div class='col-sm-4 '><div class='card event-card'><div class='card-block event-block'><h3 class='card-title'>" + al.name + "</h3><h5 class='date-event'><i class='fas fa-calendar-alt'></i>" + datestart + "</h5><button type='button' class='btn btn-primary' data-toggle='modal' data-target='.bd-example-modal-lg" + i + "'>See more</button></div></div></div><div class='modal fade bd-example-modal-lg" + i + "' tabindex='-1' role='dialog' aria-labelledby='mySmallModalLabel' aria-hidden='true'><div class='modal-dialog modal-lg'> <div class='modal-content'> <div class='modal-content content-description-event'><h5>Description:</h5><p>" + al.description + "</p><button type='button'  class='btn btn-primary login' data-toggle='modal' data-target='#mapEvents' onclick='newLocation(" + al.place.location.latitude + "," + al.place.location.longitude + ")" + "'>See Map</button></div></div></div>");

                                                        }

                                                    }
                                                }
                                            }
                                        }
                                                    
                                    });
                                    if ("paging" in response) {
                                        next = response.paging.next;
                                        before = response.paging.before;
                                        $("#accordion-event").append("<div class='col-sm-12 rem'><button onclick='paginationFab(this)'>more</button></div>");
                                    }
                                    $("#Fb-events-container").show("slow");
                                }
                        );
                        FB.api('/' + userid + '?fields=email,birthday,address,name,gender', function (response) {

                        }
                        );

                    });
                }, {scope: 'email,user_likes,user_events'});

            } else {
                $("#Fb-events-container").hide("slow");
            }
        });
    });



    $("#frm").submit(function (e) {

        var url = "UrlContoller"; // the script where you handle the form input.

        $.ajax({
            type: "GET",
            url: url,
            data: $("#frm").serialize(), // serializes the form's elements.
            success: function (data)
            {

            }
        });
        // avoid to execute the actual submit of the form.
    });
   
});
function paginationFab(button) {
    if (next) {
        FB.api(next,
                function (response) {

                    $("#Fb-events-container").append("<div class='container-fluid event-panel'><div class='row button-mini'><div class='col-sm-12'><button onclick='maximizeevents(\"accordion-event" + $(".event-card").length + "\");'><i class='far fa-minus-square'></i></button></div></div><div class='row ' data-ride='carousel' id='accordion-event" + $(".event-card").length + "'></div></div>");
                    if (help !== 0) {
                        $("#accordion-event" + help + "").hide("slow");
                    } else {
                        $("#accordion-event").hide("slow");
                    }
                    help = $(".event-card").length;
                    jQuery.each(response.data, function (i, al) {

                        if ("place" in al) {
                            if ("location" in al.place) {
                                if ("start_time" in al) {
                                    if ("end_time" in al) {
                                        var datestart = al.start_time.split("T")[0];
                                        var dateend = al.end_time.split("T")[0];
                                        if ("latitude" in al.place.location && "longitude" in al.place.location) {

                                            var marker = new google.maps.Marker({
                                                position: new google.maps.LatLng(al.place.location.latitude, al.place.location.longitude),
                                                map: mapp
                                            });

                                            mapp.setZoom(13);
                                            mapp.setCenter(new google.maps.LatLng(al.place.location.latitude, al.place.location.longitude));
                                            mapp.setMapTypeId(google.maps.MapTypeId.ROADMAP);
                                        }

                                        $("#accordion-event" + help + "").append("<div class='col-sm-4'><div class='card event-card'><div class='card-block event-block'><h3 class='card-title'>" + al.name + "</h3><h5 class='date-event'>" + datestart + "</h5><button type='button' class='btn btn-primary' data-toggle='modal' data-target='.bd-example-modal-lg" + i + $(".event-card").length + "'>See more</button></div></div></div><div class='modal fade bd-example-modal-lg" + i + $(".event-card").length + "' tabindex='-1' role='dialog' aria-labelledby='mySmallModalLabel' aria-hidden='true'><div class='modal-dialog modal-lg'> <div class='modal-content'> <div class='modal-content content-description-event'><h5>Description:</h5><p>" + al.description + "</p><button type='button'  class='btn btn-primary login' data-toggle='modal' data-target='#mapEvents' onclick='newLocation(" + al.place.location.latitude + "," + al.place.location.longitude + ")" + "'>See Map</button></div></div></div>");
                                    }
                                }
                            }
                        }
                    });
                    if ("paging" in response) {
                        next = response.paging.next;
                        before = response.paging.before;


                        $("#accordion-event" + help + "").append("<div class='col-sm-12 rem'><button onclick='paginationFab(this)'>more</button></div>");
                    }
                });
    }

    $(button).parent().html("");
}
function initMap() {
      if($("#sort_by").length!==0){
        var uluru = {lat: -25.363, lng: 131.044};
        var map = new google.maps.Map(document.getElementById('mapforevents'), {
            zoom: 7,
            center: uluru
        });
    var LocationCity = $('[name="ssne"]').val().split(",")[0];
        $.ajax({
            url: 'Home?action=mapMarkers',
            contentType: "json",
            data: {"city": LocationCity},
            type: 'GET',
            cache: false,
            success: function (result) {
                var Hotels = result.Hotels;

                
                for (var i = 0; i < Hotels.length; i++) {
                    var k=0;
                    var Location = JSON.parse(Hotels[i].Location);
                    var Info = JSON.parse(Hotels[i].Info);
                    var urll=String(Info.hotelUrl);
                            var url=urll.replace("http://www.booking.com/","/UrlContoller?q=").replace("highlight_room=#hotelTmpl","")+"&parameterUrl=link";
                   // alert(url);
                    var contentString = '<div id="content'+i+'">'+'<div id="siteNotice'+i+'">'+'</div>'+'<h1 id="firstHeading'+i+'" class="firstHeading">'+String(Info.hoteName)+'</h1>'+'<div id="bodyContent">'+            '<a  href="'+encodeURI(url)+'">'+'see the hotel page'+'</a> '+
            '</div>'+
            '</div>';

        var infowindow = new google.maps.InfoWindow({
          content: contentString
        });
                    var icon = {
    url: "Css\\15-512.png", // url
    scaledSize: new google.maps.Size(30, 30), // scaled size
    origin: new google.maps.Point(0,0), // origin
    anchor: new google.maps.Point(0, 0) // anchor
};
                    //;
                    //
                     var marker = new google.maps.Marker({
                position: new google.maps.LatLng(Location.hoteLat, Location.hoteLong),
                icon:icon,
                map: mapp
            });
          google.maps.event.addListener(marker,'click', (function(marker,contentString,infowindow){ 
    return function() {
        infowindow.setContent(contentString);
        infowindow.open(mapp,marker);
    };
})(marker,contentString,infowindow));
                   
                }
            }
        });

         
        
        mapp = map;
    }
    
    }
function newLocation(newLat, newLng)
{
    setTimeout(function() { google.maps.event.trigger(mapp, 'resize'); 
      
    mapp.setCenter({
        lat: newLat,
        lng: newLng
    });
  

   }, 500);

}
function onlyUnique(value, index, self) {
    return self.indexOf(value) === index;
}
function searchaspectsbox() {
    // Declare variables
    var input, filter, ul, li, a, i;
    input = document.getElementById('myInput');
    filter = input.value.toUpperCase();
    ul = document.getElementById("container-1-options");
    li = document.getElementsByClassName('div-container-sort');

    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("span")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}
function mySearchModal(idhotel){
 
        // Declare variables
    var input, filter, li, a, i;
    input = document.getElementById('search-'+idhotel);
    filter = input.value.toUpperCase();
   
    li = document.getElementsByClassName("card-hotel-"+idhotel);

    // Loop through all list items, and hide those who don't match the search query
    for (i = 0; i < li.length; i++) {
        
        a = li[i].getElementsByTagName("a")[0];
        if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
            $(li[i]).slideDown("slow");
         
            
        } else {
           $(li[i]).slideUp("slow");
            
        }
    }
    
}
$(document).ready(function(){
    //alert(extractRootDomain(window.location.href));
    $(".cross-product-bar__wrapper").css("height", "auto");


})
function extractRootDomain(url) {
    var domain = extractHostname(url),
        splitArr = domain.split('.'),
        arrLen = splitArr.length;

    //extracting the root domain here
    //if there is a subdomain 
    if (arrLen > 2) {
        domain = splitArr[arrLen - 2] + '.' + splitArr[arrLen - 1];
        //check to see if it's using a Country Code Top Level Domain (ccTLD) (i.e. ".me.uk")
        if (splitArr[arrLen - 1].length == 2 && splitArr[arrLen - 1].length == 2) {
            //this is using a ccTLD
            domain = splitArr[arrLen - 3] + '.' + domain;
        }
    }
    return domain+"/mavenproject2_war";
}
function extractHostname(url) {
    var hostname;
    //find & remove protocol (http, ftp, etc.) and get hostname

    if (url.indexOf("://") > -1) {
        hostname = url.split('/')[2];
    }
    else {
        hostname = url.split('/')[0];
    }

    //find & remove port number
  
    //find & remove "?"
    hostname = hostname.split('?')[0];

    return hostname;
}
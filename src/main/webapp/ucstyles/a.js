/* user-myjd-2015 common.js Date:2016-03-29 11:19:07 */
$(function(){pageConfig.hfInit||(pageConfig.hfInit=!0,seajs.use(["jdf/1.0.0/unit/globalInit/2.0.0/globalInit.js","jdf/1.0.0/unit/hotwords/1.0.0/hotwords"],function(a,b){a(),b()}))}),seajs.use(["user/myjd-2015/js/fas"],function(){var b={init:function(){this.setSelectItem(),this.loadAds(),this.bindEvent(),this.loadExtraMenu()},setSelectItem:function(){var a=$("body").attr("myjd");$("#"+a).addClass("curr")},loadAds:function(){pageConfig.asyncScript("//nfa.jd.com/loadFa.js?aid=2_959_6296-2_959_6297")},loadExtraMenu:function(){$("body").attr("myjd");$.ajax({url:"//joycenter.jd.com/msgCenter/getUnreadNum.action",dataType:"jsonp",success:function(a){if(a.msgUnreadCount){var b=a.msgUnreadCount>99?"n+":a.msgUnreadCount;$(".fore-5 a[tid=_MYJD_joy]").append("<i>"+b+"</i>")}}}),$.ajax({url:"//btshow.jd.com/ious/queryBT.do",dataType:"jsonp",data:{sourceType:135},scriptCharset:"utf-8",success:function(a){var b=$("body").attr("myjd");$("#_MYJD_credit").html('<a target="_blank" class="'+("_MYJD_credit"==b?" curr":"")+'" clstag="homepage|keycount|home2013|hbt" tag="213" href='+a.btList[0].btUrl+">"+a.btList[0].btName+"</a>")}}),$.getJSON("//mygiftcard.jd.com/service/getGiftCardCount.action?callback=?",function(a){a.GiftCardCount&&$("#_MYJD_card a").after("<b>("+a.GiftCardCount+")</b>")}),$.getJSON("//mygiftcard.jd.com/service/getBookCardCount.action?callback=?",function(a){a.BookCardCount&&$("#_MYJD_bookcard a").after("<b>("+a.BookCardCount+")</b>")}),$.getJSON("//quan.jd.com/getcouponcount.action?callback=?",function(a){a.CouponCount&&$("#_MYJD_ticket a").after("<b>("+a.CouponCount+")</b>")})},bindEvent:function(){var a;$(".navitems .dl").hover(function(){var b=this;clearTimeout(a),a=setTimeout(function(){$(b).addClass("hover")},300)},function(){var b=this;clearTimeout(a),$(b).removeClass("hover")})}};b.init()});
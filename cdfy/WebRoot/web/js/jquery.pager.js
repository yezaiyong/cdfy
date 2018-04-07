
(function($) {

    $.fn.pager = function(options) {

        var opts = $.extend({}, $.fn.pager.defaults, options);

        return this.each(function() {
            $(this).empty().append(renderpager(parseInt(opts.pagenumber), parseInt(opts.pagecount), parseInt(opts.pageSize), opts.buttonClickCallback));
        });
    };

    
    function renderpager(pagenumber, pagecount,pageSize, buttonClickCallback) {
        var $pager = $('<div class="bg2 mod_comment_page"></div>');

        $pager.append(renderButton('首页', pagenumber, pagecount, buttonClickCallback)).append(renderButton('上一页', pagenumber, pagecount, buttonClickCallback));

        
        //当前页数位于中间位置  ，显示分页数量必须为奇数个(3,5,7,9......)
        var startPoint = 1;
        var endPoint = pageSize;

        if (pagenumber > ((pageSize-1)/2)) {
            startPoint = pagenumber - ((pageSize-1)/2);
            endPoint = pagenumber + ((pageSize-1)/2);
        }

        if (endPoint > pagecount) {
            startPoint = pagecount - (pageSize-1);
            endPoint = pagecount;
        }

        if (startPoint < 1) {
            startPoint = 1;
        }

        for (var page = startPoint; page <= endPoint; page++) {

          //  var currentButton = $('<li class="page-number">' + (page) + '</li>');
              var currentButton = $('<a href="javascript:;" class="page-number c_tx">' + (page) + '</a>');

            page == pagenumber ? currentButton.removeClass("c_tx").addClass('current') : currentButton.click(function() { buttonClickCallback(this.firstChild.data); });
            currentButton.appendTo($pager);
        }

        $pager.append(renderButton('下一页', pagenumber, pagecount, buttonClickCallback)).append(renderButton('末页', pagenumber, pagecount, buttonClickCallback));
        $pager.append('<span class="c_tx3 to">转到：<input name="pagervalue" class="textinput">页：</span><input onclick="goPager(this);" name="pager" type="submit" class="bt_tx2" value="确定">');
        return $pager;
    }

    
    //生成分页按钮
    function renderButton(buttonLabel, pagenumber, pagecount, buttonClickCallback) {

        var $Button = $('<a class="pgNext c_tx3">' + buttonLabel + '</a>');

        var destPage = 1;

        switch (buttonLabel) {
            case "首页":
                destPage = 1;
                break;
            case "上一页":
                destPage = pagenumber - 1;
                break;
            case "下一页":
                destPage = pagenumber + 1;
                break;
            case "末页":
                destPage = pagecount;
                break;
        }

        if (buttonLabel == "首页" || buttonLabel == "上一页") {
            pagenumber <= 1 ? $Button.addClass('pgEmpty').css("cursor","default").css("color","#cad3d3") : $Button.click(function() { buttonClickCallback(destPage); });
        }
        else {
            pagenumber >= pagecount ? $Button.addClass('pgEmpty').css("cursor","default").css("color","#cad3d3") : $Button.click(function() { buttonClickCallback(destPage); });
        }

        return $Button;
    }

    $.fn.pager.defaults = {
        pagenumber: 1,
        pagecount: 1,
        pageSize: 9   //显示分页条数必须为奇数
    };

})(jQuery);






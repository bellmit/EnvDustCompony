(function ($) {
    $.fn.extend({
        Scroll: function (opt, callback) {
            //参数初始化
            if (!opt) var opt = {};
            var _this = this.eq(0).find("ul:first");
            var lineH = _this.find("li:first").height(), //获取行高
                line = opt.line ? parseInt(opt.line, 10) : parseInt(this.height() / lineH, 10), //每次滚动的行数，默认为一屏，即父容器高度
                speed = opt.speed ? parseInt(opt.speed, 10) : 500, //卷动速度，数值越大，速度越慢（毫秒）
                timer = opt.timer ? parseInt(opt.timer, 10) : 2000; //滚动的时间间隔（毫秒）
            if (line == 0) line = 1;
            var upHeight = 0 - line * lineH;
            var downHeight=line * lineH - 0;
            //滚动函数
            scrollUp = function () {
                _this.animate(
                    { marginTop: upHeight },
                    speed,
                    function () {
                        for (i = 1; i <= line; i++) {
                            _this.find("li:first").appendTo(_this);
                        }
                        _this.css({ marginTop: 0 });
                    }
                );
            },
                //向下滚动函数
                scrollDown = function () {
                    _this.animate(
                        { marginTop: downHeight },//动画展示css样式
                        speed,
                        function () {
                            _this.find("li:last").prependTo(_this);
                            _this.css({ marginTop: 0 });
                        }
                    )
                }
            var timerID
            //鼠标事件绑定
            _this.hover(function () {
                clearInterval(timerID);
            }, function () {
                timerID = setInterval("scrollDown()", timer);//这里调用向下或者向上滚动函数
            }).mouseout();
        }
    })
})(jQuery);
/**
 * jQuery tab Plugin v0.0.1
 *
 * Created by song on 16-8-14.
 */
;(function ($) {
    $.fn.tab = function (options) {
        var defaults = {
            eventType: 'mouseover',
            tab: '.nav-tabs > li',
            tabContent: '.tab-content > div'
        };

        options = $.extend(defaults, options);

        this.each(function () {
            var _this = this;
            $(_this).find(options.tab).on(options.eventType, function () {
                var index = $(this).index();

                $(this).addClass('active').siblings().removeClass('active');

                $(_this).find(options.tabContent)
                    .eq(index).addClass('in active').siblings().removeClass('in active');
            }).find('a').css('cursor', 'pointer');
        });

        return this;
    }
})(jQuery);

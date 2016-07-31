/**
 * Created by song on 16-7-30.
 *
 * 检查表单元素
 */

$(function () {
    checkEmpty();
});

/**
 * 检查表单是否为空
 */
function checkEmpty() {
    $('#form').find('input').on('focusout', function () {
        if ($(this).val().trim() == '') {
            $(this).addClass('error').attr({
                'data-toggle': 'tooltip',
                'title': $(this).attr('placeholder') + '不能为空'
            }).tooltip();
        } else {
            $(this).removeClass('error').tooltip('destroy')
                // .removeAttr('data-toggle title');
        }
    });
}

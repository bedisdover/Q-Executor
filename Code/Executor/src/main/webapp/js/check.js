/**
 * Created by song on 16-7-30.
 *
 * 检查表单元素
 */

$(function () {
    checkEmpty();
    checkPassword();
});

/**
 * 检查表单是否为空
 */
function checkEmpty() {
    $(':input').on('focusout', function () {
        if ($(this).val().trim() == '') {
            $(this).addClass('error').attr({
                'data-toggle': 'tooltip',
                'title': $(this).attr('placeholder') + '不能为空'
            }).tooltip();
        } else {
            $(this).removeClass('error').tooltip('destroy');
        }
    });
}

/**
 * 检查密码是否一致
 */
function checkPassword() {
    $('#confirmPassword').on('focusout', function () {
        if ($(this).val().trim() != $('#password').val().trim()) {
            $(this).addClass('error').attr({
                'data-toggle': 'tooltip',
                'title': '密码不一致'
            }).tooltip();
        } else {
            $(this).removeClass('error').tooltip('destroy');
        }
    });
}

/**
 * Created by song on 16-7-31.
 *
 * 导航栏相关操作
 */

$(function () {
    isLogin();
});

/**
 * 判断用户是否登录
 */
function isLogin() {
    jQuery.ajax({
        url: '/isLogin',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.state == true) {
                afterLogin(data.object.nickName);
            }
        }
    });
}

/**
 * 登录后的操作
 */
function afterLogin(userName) {
    $('.navbar-right').empty().append(
        '<li class="dropdown">' +
        '<a href="#" class="dropdown-toggle" data-toggle="dropdown">' +
        userName + '<b class="caret"></b></a>' +
        '<ul class="dropdown-menu">' +
        '<li><a href="../page/account.html">个人中心</a></li>' +
        '<li><a href="../page/history.html">交易历史</a></li>' +
        '<li><a href="../page/message.html">我的消息</a></li>' +
        '<li class="divider"></li>' +
        '<li><a id="log-out">登出</a></li>' +
        '</ul></li>'
    );

    $('#log-out').on('click', function (event) {
        event.preventDefault();
        logOut();
    });
}

/**
 * 登出
 */
function logOut() {
    jQuery.ajax({
        url: '/logout',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            if (data.state == true) {
                afterLogOut();
            }
        }
    })
}

/**
 * 登出后的操作
 */
function afterLogOut() {
    $('.navbar-right').empty().append(
        '<li>' +
        '<a href="../page/login.html">登录</a>' +
        '</li>'
    ).append(
        '<li>' +
        '<a href="../page/signup.html">注册</a>' +
        '</li>'
    );
}

// TODO 优化交互
// $('.dropdown-toggle').hover(function () {
//     $(this).next().slideDown();
// }).on('mouseout', function() {
//     // $(':focus').css({
//     //     'backgroundColor': 'red',
//     //     'color': 'red'
//     // });
//     if (!$(this).next().is(':focus')) {
//         $(this).next().slideUp();
//     }
// });

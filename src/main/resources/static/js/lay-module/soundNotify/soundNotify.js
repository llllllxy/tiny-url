/**
 * 文字转语音、音频、桌面通知
 *
 * 使用方法
 * 1、语音转文字 soundNotify.sound('人间一场烟火 你曾盛开过');
 * 2、音频播报 soundNotify.audio('http://music.163.com/song/media/outer/url?id=1929363849.mp3');
 * 3、桌面通知
 *  //参数对应 https://developer.mozilla.org/zh-CN/docs/Web/API/notification/Notification
 *     soundNotify.notify('人间烟火', {
 *            body: '人间一场烟火 你曾盛开过\n' +
 *                  '刻几人在心窝 从此孤独活\n' +
 *                  '江南花已凋落 怎堪再斟酌\n' +
 *                  '可怜良辰无多 竟似无人说\n' +
 *                  '你撑纸伞回头望 千年乌衣巷\n' +
 *                  '问君青丝有几丈 能把风月量\n' +
 *                  '谁言杯酒醉他乡 红尘皆可忘\n' +
 *                  '凭栏数尽孤帆 泪两行',
 *             sound: 'http://music.163.com/song/media/outer/url?id=1929363849.mp3'
 *     })
 */
layui.define(function (exports) {
    "use strict";

    function SoundNotify() {}

    // 文字转语音
    SoundNotify.prototype.sound = function (text) {
        let utterance = new SpeechSynthesisUtterance(text);
        return window.speechSynthesis.speak(utterance);
    };

    // 音频
    SoundNotify.prototype.audio = function (url) {
        let audio = new Audio(url);
        audio.autoplay = true;
        audio.play();
    };

    // 桌面通知
    // 消息授权必须是https协议的，否则一直会是 denied
    SoundNotify.prototype.notify = function (title, options) {
        // 先检查浏览器是否支持
        if (!window.Notification) {
            throw new Error('浏览器不支持Notification');
        }

        return new Promise((resolve, reject) => {
            const map = {
                // 用户同意授权
                granted() {
                    // 显示通知
                    resolve(new Notification(title, options));
                },
                // 用户还未选择 可以询问用户是否同意发送通知
                default() {
                    Notification.requestPermission().then(permission => {
                        map[permission]();
                    }).catch(error => {
                        reject(error);
                    });
                },
                // 用户拒绝授权 不能显示通知
                denied() {
                    reject('用户拒绝授权');
                }
            }

            map[Notification.permission]();
        })
    };

    exports("soundNotify", new SoundNotify());
});
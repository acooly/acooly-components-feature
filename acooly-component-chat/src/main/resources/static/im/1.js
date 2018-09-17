webpackJsonp([1],{

/***/ 1125:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
// 登陆页面
tslib_1.__exportStar(__webpack_require__(1488), exports);


/***/ }),

/***/ 1132:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "assets/images/message-loading.50c5e3e7.gif";

/***/ }),

/***/ 1138:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "assets/images/copyright.2d481b8d.svg";

/***/ }),

/***/ 1151:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "assets/images/password-icon.571e9268.svg";

/***/ }),

/***/ 1160:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "assets/images/logo.92cbe7aa.svg";

/***/ }),

/***/ 1162:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__.p + "assets/images/username-icon.ec65ccba.svg";

/***/ }),

/***/ 1206:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
var core_1 = __webpack_require__(1);
var store_1 = __webpack_require__(32);
var actions_1 = __webpack_require__(317);
var common_1 = __webpack_require__(28);
var tools_1 = __webpack_require__(160);
var router_1 = __webpack_require__(89);
var actions_2 = __webpack_require__(90);
var LoginComponent = /** @class */ (function () {
    function LoginComponent(store$, storageService, router, elementRef) {
        this.store$ = store$;
        this.storageService = storageService;
        this.router = router;
        this.elementRef = elementRef;
        this.username = '';
        this.password = '';
        this.rememberPassword = ''; // 获取cookie中记住的密码
        this.loginRemember = false; // 是否记住密码
        this.loginTip = '';
        this.isButtonAvailable = false;
        this.emptyPassword = false;
        this.loginLoading = false;
        // 为了解决safari下记住密码时placeholder依然存在的bug
        this.usernamePlaceholderText = '请输入用户名';
        this.passwordPlaceholderText = '请输入密码';
    }
    LoginComponent.prototype.ngOnInit = function () {
        var _this = this;
        // 创建JIM 对象，退出登录后重新创建对象
        common_1.global.JIM = new JMessage();
        if (this.username !== '' && this.password !== '') {
            this.isButtonAvailableAction();
        }
        // JIM 初始化
        this.store$.dispatch({
            type: actions_2.mainAction.jimInit,
            payload: null
        });
        this.loginStream$ = this.store$.select((function (state) {
            var loginState = state['loginReducer'];
            switch (loginState.actionType) {
                case actions_1.loginAction.loginSuccess:
                    _this.loginSuccess(loginState);
                    break;
                case actions_1.loginAction.isButtonAvailableAction:
                    _this.isButtonAvailable = loginState.isButtonAvailable;
                    break;
                case actions_1.loginAction.loginFailed:
                case actions_1.loginAction.emptyTip:
                    if (!loginState.isLoginSuccess) {
                        _this.loginTip = loginState.loginTip;
                        _this.loginLoading = false;
                    }
                    break;
                case actions_2.mainAction.jimInitSuccess:
                    _this.jimInitSuccess();
                    break;
                default:
            }
            return state;
        })).subscribe((function (state) {
            // pass
        }));
    };
    LoginComponent.prototype.ngAfterViewInit = function () {
        if (this.username !== '' && this.password === '') {
            this.elementRef.nativeElement.querySelector('#loginPassword').focus();
        }
        else {
            this.elementRef.nativeElement.querySelector('#loginUsername').focus();
        }
    };
    LoginComponent.prototype.ngOnDestroy = function () {
        this.loginStream$.unsubscribe();
    };
    LoginComponent.prototype.loginSuccess = function (loginState) {
        var md5Username = tools_1.md5('jchat-remember-username');
        var md5Password = tools_1.md5('jchat-remember-password');
        // 是否记住密码
        if (loginState.loginRemember) {
            this.storageService.set(md5Username, loginState.userInfo.username, true);
            this.storageService.set(md5Password, loginState.userInfo.password, true);
        }
        else {
            // 不记住密码移除cookie
            var rememberUsername = this.storageService.get(md5Username, true);
            if (this.username === rememberUsername) {
                this.storageService.remove(md5Username);
                this.storageService.remove(md5Password);
            }
        }
        common_1.global.password = loginState.userInfo.password;
        this.router.navigate(['main']);
        this.loginLoading = false;
    };
    LoginComponent.prototype.jimInitSuccess = function () {
        var username = this.storageService.get(tools_1.md5('jchat-remember-username'), true);
        var password = this.storageService.get(tools_1.md5('jchat-remember-password'), true);
        if (username && password) {
            this.username = username;
            this.rememberPassword = password;
            this.password = password.substring(0, 6);
            this.loginRemember = true;
            this.emptyPassword = true;
            this.usernamePlaceholderText = '';
            this.passwordPlaceholderText = '';
        }
        if (this.storageService.get('register-username')) {
            this.username = this.storageService.get('register-username');
            this.usernamePlaceholderText = '';
            this.storageService.remove('register-username');
            this.password = '';
            this.passwordPlaceholderText = '请输入密码';
        }
    };
    // 点击登陆、keyup.enter登陆、keyup, change判断button是否可用
    LoginComponent.prototype.login = function (event) {
        var password;
        if (this.rememberPassword) {
            password = this.rememberPassword;
            this.isButtonAvailable = true;
        }
        else {
            password = tools_1.md5(this.password);
        }
        if (!this.isButtonAvailable) {
            return;
        }
        this.loginLoading = true;
        this.store$.dispatch({
            type: actions_1.loginAction.login,
            payload: {
                username: this.username,
                password: password,
                md5: true,
                isButtonAvailable: this.isButtonAvailable,
                event: event,
                loginRemember: this.loginRemember
            }
        });
    };
    LoginComponent.prototype.isButtonAvailableAction = function (type) {
        this.rememberPassword = '';
        this.store$.dispatch({
            type: actions_1.loginAction.isButtonAvailableAction,
            payload: {
                password: this.password,
                username: this.username
            }
        });
        // 当input keyup进行修改时清空提示语
        if (type === 'usernameKeyup' || type === 'passwordKeyup') {
            this.store$.dispatch({
                type: actions_1.loginAction.emptyTip,
                payload: type
            });
        }
        // 密码被记住后修改用户名，清空密码
        if (type === 'usernameKeyup' && this.emptyPassword) {
            this.password = '';
            this.emptyPassword = false;
            this.passwordPlaceholderText = '请输入密码';
        }
        // 解决safari placeholder问题
        if ((type === 'usernameKeyup' || type === 'usernameChange') && this.username === '') {
            this.usernamePlaceholderText = '请输入用户名';
        }
        if ((type === 'passwordKeyup' || type === 'passwordChange') && this.password === '') {
            this.passwordPlaceholderText = '请输入密码';
        }
    };
    LoginComponent.prototype.inputFocus = function (id) {
        this.elementRef.nativeElement.querySelector('#' + id).focus();
    };
    LoginComponent = tslib_1.__decorate([
        core_1.Component({
            selector: 'app-login',
            styles: [__webpack_require__(1557)],
            template: __webpack_require__(1350)
        }),
        tslib_1.__metadata("design:paramtypes", [store_1.Store,
            common_1.StorageService,
            router_1.Router,
            core_1.ElementRef])
    ], LoginComponent);
    return LoginComponent;
}());
exports.LoginComponent = LoginComponent;


/***/ }),

/***/ 1258:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(314)();
// imports


// module
exports.push([module.i, ".login-container {\n  width: 400px;\n  min-height: 400px;\n  background-color: #fff;\n  border-radius: 4px;\n  margin: 0 auto;\n  overflow: hidden;\n  padding: 0 40px;\n  -webkit-box-sizing: border-box;\n  box-sizing: border-box;\n  text-align: center;\n  position: fixed;\n  top: 50%;\n  left: 50%;\n  -webkit-transform: translate(-50%, -50%);\n  transform: translate(-50%, -50%);\n  padding-bottom: 32px; }\n  .login-container h1 {\n    height: 143px;\n    line-height: 143px;\n    margin: 0; }\n    .login-container h1 img {\n      vertical-align: middle; }\n  .login-container .login-info input {\n    height: 42px;\n    width: 320px;\n    border: 1px solid #D9DEE4;\n    padding-left: 45px;\n    -webkit-box-sizing: border-box;\n    box-sizing: border-box;\n    border-radius: 2px;\n    font-size: 12px; }\n  .login-container .login-info .login-username {\n    margin-bottom: 22px;\n    position: relative;\n    cursor: text; }\n    .login-container .login-info .login-username:after {\n      background: url(" + __webpack_require__(1162) + ") center center no-repeat;\n      background-size: 18px;\n      width: 18px;\n      height: 18px;\n      position: absolute;\n      content: '';\n      display: block;\n      top: 12px;\n      left: 13px; }\n    .login-container .login-info .login-username .login-tip {\n      position: absolute;\n      top: 46px;\n      left: 0;\n      color: #EB424C;\n      font-size: 12px;\n      width: 320px;\n      line-height: 17px;\n      text-align: left; }\n    .login-container .login-info .login-username.login-tip-wrap {\n      margin-bottom: 32px; }\n  .login-container .login-info .login-pwd {\n    margin-bottom: 10px;\n    position: relative;\n    cursor: text; }\n    .login-container .login-info .login-pwd:after {\n      background: url(" + __webpack_require__(1151) + ") center center no-repeat;\n      background-size: 18px;\n      width: 18px;\n      height: 18px;\n      position: absolute;\n      content: '';\n      display: block;\n      top: 12px;\n      left: 13px; }\n  .login-container .login-info .login-remember {\n    line-height: 18px;\n    font-size: 12px;\n    color: #808080;\n    margin-bottom: 22px; }\n    .login-container .login-info .login-remember label {\n      cursor: pointer; }\n      .login-container .login-info .login-remember label input {\n        display: none; }\n      .login-container .login-info .login-remember label input + b {\n        border: 1px solid #D9DEE4;\n        width: 12px;\n        height: 12px;\n        border-radius: 2px;\n        position: relative;\n        z-index: 1;\n        background: #fff;\n        margin-top: 2px;\n        float: left;\n        margin-right: 11px; }\n      .login-container .login-info .login-remember label input:checked + b {\n        border-color: #2DD0CF; }\n        .login-container .login-info .login-remember label input:checked + b:before {\n          content: '';\n          position: absolute;\n          width: 12px;\n          height: 12px;\n          display: block;\n          background: #2DD0CF; }\n        .login-container .login-info .login-remember label input:checked + b:after {\n          content: '';\n          position: absolute;\n          width: 8px;\n          height: 4px;\n          border-left: 1px solid #fff;\n          border-bottom: 1px solid #fff;\n          -webkit-transform: rotateZ(-45deg);\n          transform: rotateZ(-45deg);\n          display: block;\n          top: 3px;\n          left: 2px; }\n  .login-container .login-info .login-btn {\n    position: relative; }\n    .login-container .login-info .login-btn.loading:after {\n      content: '';\n      position: absolute;\n      top: 0;\n      left: 0;\n      width: 100%;\n      height: 100%;\n      background: url(" + __webpack_require__(1132) + ") center center no-repeat;\n      background-size: 24px; }\n    .login-container .login-info .login-btn button {\n      background: #2DD0CF;\n      border-radius: 2px;\n      width: 320px;\n      height: 42px;\n      font-size: 16px;\n      color: #FFFFFF; }\n      .login-container .login-info .login-btn button:disabled {\n        color: #d9f6f6 !important;\n        background-color: #81e3e2 !important;\n        border-color: #81e3e2 !important;\n        cursor: default; }\n  .login-container .login-info .login-to-register {\n    margin-top: 10px;\n    font-size: 12px;\n    color: #808080;\n    letter-spacing: 0; }\n    .login-container .login-info .login-to-register a {\n      font-size: 12px;\n      color: #2DD0CF;\n      letter-spacing: 0;\n      line-height: 17px;\n      cursor: pointer; }\n      .login-container .login-info .login-to-register a:hover {\n        color: #02BDBC; }\n\n.copyright-container {\n  height: 55px;\n  position: absolute;\n  bottom: 76px;\n  text-align: center;\n  width: 100%;\n  font-size: 14px;\n  color: #FFFFFF;\n  opacity: 0.7; }\n  .copyright-container img {\n    margin-bottom: 8px; }\n\n@media (max-height: 650px) {\n  .copyright-container {\n    display: none; } }\n\n@media (min-height: 650px) and (max-height: 720px) {\n  .copyright-container {\n    bottom: 20px; } }\n\n@media (min-height: 720px) and (max-height: 780px) {\n  .copyright-container {\n    bottom: 45px; } }\n", ""]);

// exports


/***/ }),

/***/ 1350:
/***/ (function(module, exports, __webpack_require__) {

//	module.exports = "<div class=\"login-wrap\">\n    <div class=\"login-container\">\n        <h1>\n            <img src=\"" + __webpack_require__(1160) + "\" alt=\"\">\n        </h1>\n        <div class=\"login-info\">\n            <div class=\"login-username\" (click)=\"inputFocus('loginUsername')\" [ngClass]=\"{'login-tip-wrap': loginTip}\">\n                <input autocomplete=\"off\" spellcheck=\"false\" id=\"loginUsername\" class=\"input-focus\" [ngClass]=\"{'input-error': loginTip}\" [(ngModel)]=\"username\" type=\"text\" [placeholder]=\"usernamePlaceholderText\" (keyup)=\"isButtonAvailableAction('usernameKeyup')\" (change)=\"isButtonAvailableAction('usernameChange')\">\n                <p class=\"login-tip\" *ngIf=\"loginTip\">{{loginTip}}</p>                \n            </div>\n            <div class=\"login-pwd\" (click)=\"inputFocus('loginPassword')\">\n                <input autocomplete=\"off\" spellcheck=\"false\" id=\"loginPassword\" class=\"input-focus\" [(ngModel)]=\"password\" type=\"password\" [placeholder]=\"passwordPlaceholderText\" (keyup.enter)=\"login()\" (keyup)=\"isButtonAvailableAction('passwordKeyup')\" (change)=\"isButtonAvailableAction('passwordChange')\">\n            </div>\n            <p class=\"login-remember clearfix\">\n                <label for=\"remember\" class=\"float-left\">\n                    <input spellcheck=\"false\" [(ngModel)]=\"loginRemember\" id=\"remember\" class=\"float-left\" type=\"checkbox\">                    \n                    <b></b>                    \n                    记住用户名和密码\n                </label>\n            </p>\n            <p class=\"login-btn\" [ngClass]=\"{'loading': loginLoading}\">\n                <button [disabled]=\"!isButtonAvailable && !rememberPassword\" class=\"btn-active\" type=\"button\" (click)=\"login()\">登录</button>\n            </p>\n            <p class=\"login-to-register\">\n                没有账号？\n                <a routerLink=\"/register\">立即注册</a>\n            </p>\n        </div>\n    </div>\n</div>\n<div class=\"copyright-container\">\n    <img src=\"" + __webpack_require__(1138) + "\" alt=\"\">\n    <p>\n        @Copyright © 2018 acooly team \n    </p>\n</div>\n\n";
	module.exports = "<div class=\"login-wrap\">\n    <div class=\"login-container\">\n        <h1>\n            <img src=\"" + __webpack_require__(1160) + "\" alt=\"\">\n        </h1>\n        <div class=\"login-info\">\n            <div class=\"login-username\" (click)=\"inputFocus('loginUsername')\" [ngClass]=\"{'login-tip-wrap': loginTip}\">\n                <input autocomplete=\"off\" spellcheck=\"false\" id=\"loginUsername\" class=\"input-focus\" [ngClass]=\"{'input-error': loginTip}\" [(ngModel)]=\"username\" type=\"text\" [placeholder]=\"usernamePlaceholderText\" (keyup)=\"isButtonAvailableAction('usernameKeyup')\" (change)=\"isButtonAvailableAction('usernameChange')\">\n                <p class=\"login-tip\" *ngIf=\"loginTip\">{{loginTip}}</p>                \n            </div>\n            <div class=\"login-pwd\" (click)=\"inputFocus('loginPassword')\">\n                <input autocomplete=\"off\" spellcheck=\"false\" id=\"loginPassword\" class=\"input-focus\" [(ngModel)]=\"password\" type=\"password\" [placeholder]=\"passwordPlaceholderText\" (keyup.enter)=\"login()\" (keyup)=\"isButtonAvailableAction('passwordKeyup')\" (change)=\"isButtonAvailableAction('passwordChange')\">\n            </div>\n            <p class=\"login-remember clearfix\">\n                <label for=\"remember\" class=\"float-left\">\n                    <input spellcheck=\"false\" [(ngModel)]=\"loginRemember\" id=\"remember\" class=\"float-left\" type=\"checkbox\">                    \n                    <b></b>                    \n                    记住用户名和密码\n                </label>\n            </p>\n            <p class=\"login-btn\" [ngClass]=\"{'loading': loginLoading}\">\n                <button [disabled]=\"!isButtonAvailable && !rememberPassword\" class=\"btn-active\" type=\"button\" (click)=\"login()\">登录</button>\n            </p>\n            <p class=\"login-to-register\">\n           </p>\n        </div>\n    </div>\n</div>\n<div class=\"copyright-container\">\n    <img src=\"" + __webpack_require__(1138) + "\" alt=\"\">\n    <p>\n        @Copyright © 2018 acooly team \n    </p>\n</div>\n\n";

/***/ }),

/***/ 1488:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
var common_1 = __webpack_require__(71);
var forms_1 = __webpack_require__(159);
var core_1 = __webpack_require__(1);
var router_1 = __webpack_require__(89);
var login_router_1 = __webpack_require__(1489);
var login_component_1 = __webpack_require__(1206);
var LoginModule = /** @class */ (function () {
    function LoginModule() {
    }
    LoginModule.routes = login_router_1.LOGIN_ROUTER;
    LoginModule = tslib_1.__decorate([
        core_1.NgModule({
            declarations: [
                login_component_1.LoginComponent
            ],
            imports: [
                common_1.CommonModule,
                forms_1.FormsModule,
                router_1.RouterModule.forChild(login_router_1.LOGIN_ROUTER)
            ],
            providers: []
        })
    ], LoginModule);
    return LoginModule;
}());
exports.LoginModule = LoginModule;


/***/ }),

/***/ 1489:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var login_component_1 = __webpack_require__(1206);
exports.LOGIN_ROUTER = [
    {
        path: '',
        component: login_component_1.LoginComponent
    }
];


/***/ }),

/***/ 1557:
/***/ (function(module, exports, __webpack_require__) {


        var result = __webpack_require__(1258);

        if (typeof result === "string") {
            module.exports = result;
        } else {
            module.exports = result.toString();
        }
    

/***/ })

});
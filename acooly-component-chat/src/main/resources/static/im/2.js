webpackJsonp([2],{

/***/ 1128:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
// 注册页面
tslib_1.__exportStar(__webpack_require__(1494), exports);


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

/***/ 1209:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
var core_1 = __webpack_require__(1);
var store_1 = __webpack_require__(32);
var router_1 = __webpack_require__(89);
var common_1 = __webpack_require__(28);
var actions_1 = __webpack_require__(318);
var actions_2 = __webpack_require__(90);
var RegisterComponent = /** @class */ (function () {
    function RegisterComponent(store$, router, storageService, elementRef, apiService) {
        this.store$ = store$;
        this.router = router;
        this.storageService = storageService;
        this.elementRef = elementRef;
        this.apiService = apiService;
        this.info = {
            username: '',
            password: '',
            repeatPassword: ''
        };
        this.tip = {
            usernameTip: '',
            passwordTip: '',
            repeatPasswordTip: ''
        };
        this.isButtonAvailable = false;
        this.tipModal = {
            show: false,
            info: {}
        };
    }
    RegisterComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.store$.dispatch({
            type: actions_1.registerAction.init,
            payload: null
        });
        // JIM初始化
        this.store$.dispatch({
            type: actions_2.mainAction.jimInit,
            payload: null
        });
        this.registerStream$ = this.store$.select((function (state) {
            var registerState = state['registerReducer'];
            switch (registerState.actionType) {
                case actions_1.registerAction.init:
                    _this.init();
                    break;
                case actions_1.registerAction.registerSuccess:
                    _this.tipModal = registerState.tipModal;
                    break;
                default:
                    _this.tip.usernameTip = registerState.usernameTip;
                    _this.tip.passwordTip = registerState.passwordTip;
                    _this.isButtonAvailable = registerState.isButtonAvailable;
                    _this.tip.repeatPasswordTip = registerState.repeatPasswordTip;
            }
            return state;
        })).subscribe((function (state) {
            // pass
        }));
    };
    RegisterComponent.prototype.ngAfterViewInit = function () {
        this.elementRef.nativeElement.querySelector('#registerUsername').focus();
    };
    RegisterComponent.prototype.ngOnDestroy = function () {
        this.registerStream$.unsubscribe();
    };
    RegisterComponent.prototype.register = function () {
        this.store$.dispatch({
            type: actions_1.registerAction.register,
            payload: {
                username: this.info.username,
                password: this.info.password,
                repeatPassword: this.info.repeatPassword,
                isButtonAvailable: this.isButtonAvailable
            }
        });
    };
    RegisterComponent.prototype.isButtonAvailableAction = function (type) {
        this.store$.dispatch({
            type: actions_1.registerAction.isButtonAvailableAction,
            payload: {
                username: this.info.username,
                password: this.info.password,
                repeatPassword: this.info.repeatPassword
            }
        });
        // 当input keyup进行修改时清空提示语
        this.store$.dispatch({
            type: actions_1.registerAction.emptyTip,
            payload: type
        });
    };
    RegisterComponent.prototype.usernameBlur = function () {
        this.store$.dispatch({
            type: actions_1.registerAction.isUsernameAvailableAction,
            payload: {
                username: this.info.username
            }
        });
    };
    RegisterComponent.prototype.passwordBlur = function () {
        this.store$.dispatch({
            type: actions_1.registerAction.password,
            payload: {
                password: this.info.password
            }
        });
    };
    RegisterComponent.prototype.modalTipEmit = function () {
        this.tipModal.show = false;
        this.storageService.set('register-username', this.info.username);
        this.router.navigate(['/login']);
    };
    RegisterComponent.prototype.init = function () {
        this.info = {
            username: '',
            password: '',
            repeatPassword: ''
        };
        this.tip = {
            usernameTip: '',
            passwordTip: '',
            repeatPasswordTip: ''
        };
        this.isButtonAvailable = false;
        this.tipModal = {
            show: false,
            info: {}
        };
    };
    RegisterComponent = tslib_1.__decorate([
        core_1.Component({
            selector: 'app-register',
            styles: [__webpack_require__(1560)],
            template: __webpack_require__(1353)
        }),
        tslib_1.__metadata("design:paramtypes", [store_1.Store,
            router_1.Router,
            common_1.StorageService,
            core_1.ElementRef,
            common_1.ApiService])
    ], RegisterComponent);
    return RegisterComponent;
}());
exports.RegisterComponent = RegisterComponent;


/***/ }),

/***/ 1261:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(314)();
// imports


// module
exports.push([module.i, ".register-container {\n  width: 400px;\n  min-height: 454px;\n  background-color: #fff;\n  border-radius: 4px;\n  margin: 0 auto;\n  overflow: hidden;\n  text-align: center;\n  position: fixed;\n  top: 50%;\n  left: 50%;\n  -webkit-transform: translate(-50%, -50%);\n  transform: translate(-50%, -50%);\n  padding-bottom: 42px;\n  -webkit-box-sizing: border-box;\n  box-sizing: border-box; }\n  .register-container h1 {\n    height: 143px;\n    line-height: 143px;\n    margin: 0; }\n  .register-container .register-info > div {\n    position: relative;\n    width: 320px;\n    margin: 0 auto; }\n  .register-container .register-info .register-tip {\n    position: absolute;\n    top: 46px;\n    left: 0;\n    font-size: 12px;\n    line-height: 17px;\n    color: #EB424C; }\n  .register-container .register-info input {\n    height: 42px;\n    width: 320px;\n    border: 1px solid #D9DEE4;\n    padding-left: 45px;\n    -webkit-box-sizing: border-box;\n    box-sizing: border-box;\n    background-size: 18px;\n    border-radius: 2px;\n    font-size: 12px; }\n  .register-container .register-info .register-username {\n    margin-bottom: 22px;\n    position: relative; }\n    .register-container .register-info .register-username input {\n      background: url(" + __webpack_require__(1162) + ") 13px center no-repeat; }\n    .register-container .register-info .register-username.register-tip-wrap {\n      margin-bottom: 32px; }\n  .register-container .register-info .register-pwd {\n    margin-bottom: 22px; }\n    .register-container .register-info .register-pwd input {\n      background: url(" + __webpack_require__(1151) + ") 13px center no-repeat; }\n    .register-container .register-info .register-pwd.register-tip-wrap {\n      margin-bottom: 32px; }\n  .register-container .register-info .register-re-pwd {\n    margin-bottom: 30px; }\n    .register-container .register-info .register-re-pwd input {\n      background: url(" + __webpack_require__(1151) + ") 13px center no-repeat; }\n    .register-container .register-info .register-re-pwd.register-tip-wrap {\n      margin-bottom: 32px; }\n  .register-container .register-info .register-btn button {\n    background-color: #2DD0CF;\n    border-radius: 2px;\n    width: 320px;\n    height: 42px;\n    font-size: 16px;\n    color: #FFFFFF; }\n    .register-container .register-info .register-btn button:disabled {\n      color: #d9f6f6 !important;\n      background-color: #81e3e2 !important;\n      border-color: #81e3e2 !important;\n      cursor: default; }\n  .register-container .register-info .register-to-login {\n    margin-top: 10px;\n    font-size: 12px;\n    color: #808080;\n    letter-spacing: 0; }\n    .register-container .register-info .register-to-login a {\n      font-size: 12px;\n      color: #2DD0CF;\n      letter-spacing: 0;\n      line-height: 17px;\n      cursor: pointer; }\n      .register-container .register-info .register-to-login a:hover {\n        color: #02BDBC; }\n\n.copyright-container {\n  height: 55px;\n  position: absolute;\n  bottom: 76px;\n  text-align: center;\n  width: 100%;\n  font-size: 14px;\n  color: #FFFFFF;\n  opacity: 0.7; }\n  .copyright-container img {\n    margin-bottom: 8px; }\n\n@media (max-height: 650px) {\n  .copyright-container {\n    display: none; } }\n\n@media (min-height: 650px) and (max-height: 720px) {\n  .copyright-container {\n    bottom: 20px; } }\n\n@media (min-height: 720px) and (max-height: 780px) {\n  .copyright-container {\n    bottom: 45px; } }\n", ""]);

// exports


/***/ }),

/***/ 1353:
/***/ (function(module, exports, __webpack_require__) {

module.exports = "<div class=\"register-wrap\">\n    <div class=\"register-container\">\n        <h1>\n            <img src=\"" + __webpack_require__(1160) + "\" alt=\"\">\n        </h1>\n        <div class=\"register-info\">\n            <div class=\"register-username\" [ngClass]=\"{'register-tip-wrap': tip.usernameTip}\">\n                <input spellcheck=\"false\" id=\"registerUsername\" class=\"input-focus\" [ngClass]=\"{'input-error': tip.usernameTip}\" [(ngModel)]=\"info.username\" type=\"text\" placeholder=\"请输入用户名\" (blur)=\"usernameBlur()\" (keyup)=\"isButtonAvailableAction('username')\">\n                <p *ngIf=\"tip.usernameTip\" class=\"register-tip\">{{tip.usernameTip}}</p>\n            </div>\n            <div class=\"register-pwd\" [ngClass]=\"{'register-tip-wrap': tip.passwordTip}\">\n                <input spellcheck=\"false\" class=\"input-focus\" [ngClass]=\"{'input-error': tip.passwordTip}\" [(ngModel)]=\"info.password\" type=\"password\" placeholder=\"请输入密码\" (blur)=\"passwordBlur()\" (keyup)=\"isButtonAvailableAction('password')\">\n                <p *ngIf=\"tip.passwordTip\" class=\"register-tip\">{{tip.passwordTip}}</p>\n            </div>\n            <div class=\"register-re-pwd\" [ngClass]=\"{'register-tip-wrap': tip.repeatPasswordTip}\">\n                <input spellcheck=\"false\" class=\"input-focus\" [ngClass]=\"{'input-error': tip.repeatPasswordTip}\" [(ngModel)]=\"info.repeatPassword\" type=\"password\" placeholder=\"请确认密码\" (keyup.enter)=\"register()\" (keyup)=\"isButtonAvailableAction('repeatPassword')\">\n                <p *ngIf=\"tip.repeatPasswordTip\" class=\"register-tip\">{{tip.repeatPasswordTip}}</p>\n            </div>\n            <p class=\"register-btn\">\n                <button [disabled]=\"!isButtonAvailable\" class=\"btn-active\" type=\"button\" (click)=\"register()\">注册</button>\n            </p>\n            <p class=\"register-to-login\">\n                已有账号？\n                <a routerLink=\"/login\">立即登录</a>\n            </p>\n        </div>\n    </div>\n</div>\n<div class=\"copyright-container\">\n    <img src=\"" + __webpack_require__(1138) + "\" alt=\"\">\n    <p>\n        @Copyright © 2018 acooly team \n    </p>\n</div>\n<tip-modal-component *ngIf=\"tipModal.show\" [info]=\"tipModal.info\" (modalTipEmit)=\"modalTipEmit($event)\"></tip-modal-component>\n";

/***/ }),

/***/ 1494:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
var common_1 = __webpack_require__(71);
var forms_1 = __webpack_require__(159);
var core_1 = __webpack_require__(1);
var router_1 = __webpack_require__(89);
var register_router_1 = __webpack_require__(1495);
var register_component_1 = __webpack_require__(1209);
var tip_modal_1 = __webpack_require__(493);
var RegisterModule = /** @class */ (function () {
    function RegisterModule() {
    }
    RegisterModule.routes = register_router_1.REGISTER_ROUTER;
    RegisterModule = tslib_1.__decorate([
        core_1.NgModule({
            declarations: [
                register_component_1.RegisterComponent
            ],
            imports: [
                common_1.CommonModule,
                forms_1.FormsModule,
                router_1.RouterModule.forChild(register_router_1.REGISTER_ROUTER),
                tip_modal_1.TipModalModule
            ],
            providers: []
        })
    ], RegisterModule);
    return RegisterModule;
}());
exports.RegisterModule = RegisterModule;


/***/ }),

/***/ 1495:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var register_component_1 = __webpack_require__(1209);
exports.REGISTER_ROUTER = [
    {
        path: '',
        component: register_component_1.RegisterComponent
    }
];


/***/ }),

/***/ 1560:
/***/ (function(module, exports, __webpack_require__) {


        var result = __webpack_require__(1261);

        if (typeof result === "string") {
            module.exports = result;
        } else {
            module.exports = result.toString();
        }
    

/***/ })

});
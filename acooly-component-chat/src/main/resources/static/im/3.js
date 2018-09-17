webpackJsonp([3],{

/***/ 1127:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
// 地图页面
tslib_1.__exportStar(__webpack_require__(1492), exports);


/***/ }),

/***/ 1208:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
var core_1 = __webpack_require__(1);
var router_1 = __webpack_require__(89);
var util_1 = __webpack_require__(72);
var MapComponent = /** @class */ (function () {
    function MapComponent(activatedRoute) {
        this.activatedRoute = activatedRoute;
        this.errorTipShow = false;
    }
    MapComponent.prototype.ngOnInit = function () {
        // 获取url参数（地理坐标）
        var pointer = this.activatedRoute.snapshot.params.pointer;
        var arr = pointer.split('&');
        arr[0] = Number(arr[0]);
        arr[1] = Number(arr[1]);
        arr[2] = Number(arr[2]);
        if (Number.isNaN(arr[0]) || Number.isNaN(arr[1])) {
            this.errorTipShow = true;
        }
        else {
            util_1.Util.theLocation({
                id: 'mapContainer',
                longitude: arr[0],
                latitude: arr[1],
                scroll: true,
                scale: arr[2]
            });
        }
    };
    MapComponent = tslib_1.__decorate([
        core_1.Component({
            selector: 'app-map',
            styles: [__webpack_require__(1559)],
            template: __webpack_require__(1352)
        }),
        tslib_1.__metadata("design:paramtypes", [router_1.ActivatedRoute])
    ], MapComponent);
    return MapComponent;
}());
exports.MapComponent = MapComponent;


/***/ }),

/***/ 1260:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(314)();
// imports


// module
exports.push([module.i, ".map-error-container {\n  width: 100%;\n  height: 100%;\n  background-color: #fff;\n  font-size: 20px;\n  text-align: center; }\n\n.map-container {\n  width: 100%;\n  height: 100%; }\n", ""]);

// exports


/***/ }),

/***/ 1352:
/***/ (function(module, exports) {

module.exports = "<div *ngIf=\"errorTipShow\" class=\"map-error-container\">地图参数错误</div>\n<div id=\"mapContainer\" class=\"map-container\"></div>";

/***/ }),

/***/ 1492:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var tslib_1 = __webpack_require__(4);
var common_1 = __webpack_require__(71);
var forms_1 = __webpack_require__(159);
var core_1 = __webpack_require__(1);
var router_1 = __webpack_require__(89);
var map_router_1 = __webpack_require__(1493);
var map_component_1 = __webpack_require__(1208);
var MapModule = /** @class */ (function () {
    function MapModule() {
    }
    MapModule.routes = map_router_1.MAPROUTER;
    MapModule = tslib_1.__decorate([
        core_1.NgModule({
            declarations: [
                map_component_1.MapComponent
            ],
            imports: [
                common_1.CommonModule,
                forms_1.FormsModule,
                router_1.RouterModule.forChild(map_router_1.MAPROUTER),
            ]
        })
    ], MapModule);
    return MapModule;
}());
exports.MapModule = MapModule;


/***/ }),

/***/ 1493:
/***/ (function(module, exports, __webpack_require__) {

"use strict";

Object.defineProperty(exports, "__esModule", { value: true });
var map_component_1 = __webpack_require__(1208);
exports.MAPROUTER = [
    {
        path: '',
        component: map_component_1.MapComponent
    }
];


/***/ }),

/***/ 1559:
/***/ (function(module, exports, __webpack_require__) {


        var result = __webpack_require__(1260);

        if (typeof result === "string") {
            module.exports = result;
        } else {
            module.exports = result.toString();
        }
    

/***/ })

});
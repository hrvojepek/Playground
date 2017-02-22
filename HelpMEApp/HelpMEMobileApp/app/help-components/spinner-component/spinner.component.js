/**
 * Created by Hrvoje on 31.3.2016.
 * Spinner Component with spinner template which is showing when something is loading
 */
"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require("angular2/core");
var SpinnerComponent = (function () {
    function SpinnerComponent() {
    }
    SpinnerComponent = __decorate([
        core_1.Component({
            selector: 'my-spinner',
            template: "\n        <div style=\" background:rgba(255,255,255,0.7); width: 100%; text-align: center; height: 100%; position:fixed; left:0px; top:0px; z-index:99;\">\n            <i style=\"margin-top: 3em;\" class=\"fa fa-spinner fa-pulse fa-4x\"></i>\n        </div>     \n    "
        }), 
        __metadata('design:paramtypes', [])
    ], SpinnerComponent);
    return SpinnerComponent;
}());
exports.SpinnerComponent = SpinnerComponent;

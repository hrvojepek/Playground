/**
 * Created by Hrvoje on 28.3.2016..
 */
System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var Sneaker;
    return {
        setters:[],
        execute: function() {
            Sneaker = (function () {
                function Sneaker(id, name, description, photo, price) {
                    this.id = id;
                    this.name = name;
                    this.description = description;
                    this.photo = photo;
                    this.price = price;
                }
                return Sneaker;
            }());
            exports_1("Sneaker", Sneaker);
        }
    }
});
//# sourceMappingURL=sneaker.component.js.map
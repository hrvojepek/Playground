/**
 * Created by Hrvoje on 29.3.2016..
 */
System.register([], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var User;
    return {
        setters:[],
        execute: function() {
            User = (function () {
                function User(name, email, pass) {
                    this.name = name;
                    this.email = email;
                    this.pass = pass;
                }
                return User;
            }());
            exports_1("User", User);
        }
    }
});
//# sourceMappingURL=user.component.js.map
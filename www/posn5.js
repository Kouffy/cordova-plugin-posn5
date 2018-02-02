var exec = require("cordova/exec");
module.exports = {
    test: function(arg0, success, error) {
        exec(success, error, 'PosN5', "test", [arg0]);
    },
    scanner: function(arg0, success, error) {
        exec(success, error, 'PosN5', "scanner", [arg0]);
    },
    printer: function(arg0, success, error) {
        exec(success, error, 'PosN5', "printer", [arg0]);
    }
}
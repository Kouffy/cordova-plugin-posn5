var exec = require("cordova/exec");
module.exports = {
    test: function(arg0, success, error) {
        exec(success, error, 'PosN5', "test", [arg0]);
    }
}
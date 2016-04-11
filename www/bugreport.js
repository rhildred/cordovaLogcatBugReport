/*global cordova, module*/

module.exports = {
    report: function (reportername, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "BugReport", "report", [reportername]);
    }
};

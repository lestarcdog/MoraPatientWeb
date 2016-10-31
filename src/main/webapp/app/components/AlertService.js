angular.module("MoraPatientApp").factory("AlertService", function ($mdDialog, $mdToast) {

    var showAlert = function (text) {
        var alert = $mdDialog.alert({
            title: "Hiba történt",
            textContent: text,
            ok: "OK"
        });
        $mdDialog.show(alert);
    };

    var showSuccess = function (text) {
        var success = $mdToast.simple()
            .position("bottom right")
            .textContent(text)
            .hideDelay(4000)
            .toastClass("success-toast");
        $mdToast.show(success);
    };

    return {
        showAlert: showAlert,
        showSuccess: showSuccess
    }
});
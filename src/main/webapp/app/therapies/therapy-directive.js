angular.module("MoraPatientApp").directive("therapy", function (TherapyDialog) {
    return {
        restrict: "E",
        scope: {
            "therapy": "=",
            "delete": "&"
        },
        templateUrl: "app/therapies/therapy-tmpl.html",
        link: function (scope, element, attr, controller) {

            scope.openEditor = function (title, model) {
                var text = scope.therapy[model];
                TherapyDialog.openDialog(title, text).then(function (newContent) {
                    scope.therapy[model] = newContent;
                })
            }
        }
    }
});
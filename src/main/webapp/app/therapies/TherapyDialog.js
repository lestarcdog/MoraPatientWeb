angular.module("MoraPatientApp").factory("TherapyDialog", function ($mdDialog) {

    var controller = function DialogController($scope, $mdDialog, content) {
        $scope.content = content;
        $scope.accept = function () {
            $mdDialog.hide($scope.content.text);
        };
        $scope.cancel = function () {
            $mdDialog.cancel();
        }
    };


    var openDialog = function (header, text) {
        return $mdDialog.show({
            templateUrl: "app/therapies/therapydialog-tmpl.html",
            controller: controller,
            locals: {
                content: {
                    header: header,
                    text: text
                }
            }
        });
    };

    return {
        openDialog: openDialog
    }
});
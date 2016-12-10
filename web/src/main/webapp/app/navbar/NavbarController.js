angular.module("MoraPatientApp").controller("NavbarController", function ($scope, $location, MoraEvents) {
    $scope.isActive = function (path) {
        return $location.path().startsWith(path);
    };

    $scope.patient = null;

    $scope.$on(MoraEvents.PATIENT_CHANGE, function (evt, patient) {
        if (patient) {
            $scope.patient = patient;
        } else {
            $scope.patient = null;
        }
    });


});
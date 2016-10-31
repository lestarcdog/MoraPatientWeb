angular.module("MoraPatientApp").controller("NavbarController", function ($scope, $location, MoraEvents) {
    $scope.isActive = function (path) {
        return $location.path().startsWith(path);
    };

    $scope.patientName = null;

    $scope.$on(MoraEvents.PATIENT_CHANGE, function (evt, patient) {
        if (patient) {
            console.log(patient);
            $scope.patientName = patient.name;
        } else {
            $scope.patientName = null;
        }
    });


});
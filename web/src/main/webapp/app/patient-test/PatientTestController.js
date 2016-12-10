angular.module("MoraPatientApp").controller("PatientTestController", function ($scope, $rootScope, $routeParams, MoraDataService, MoraEvents) {

    var patientId = $routeParams.id;
    // other is hu
    $scope.lang = "en";

    var refresh = function () {
        MoraDataService.patientById(patientId).then(function (patient) {
            $rootScope.$broadcast(MoraEvents.PATIENT_CHANGE, patient);
            $scope.elhElements = patient.elhElements;
        });
    };

    refresh()
});

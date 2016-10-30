angular.module("MoraPatientApp")
    .controller("PatientListController", function ($scope, DateFormatConst, MoraDataService) {
        $scope.patients = [];

        MoraDataService.allPatients().then(function (patients) {
            $scope.patients = patients;
        });

        $scope.dateformat = DateFormatConst;

        $scope.modifiedSince = function (epochMilli) {
            if (epochMilli != null) {
                return moment(epochMilli).fromNow();
            } else {
                return null;
            }
        }
    });
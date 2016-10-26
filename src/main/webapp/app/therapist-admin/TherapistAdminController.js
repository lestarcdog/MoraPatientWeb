angular.module("MoraPatientApp")
    .controller("TherapistAdminController", function ($scope, PatientDataService) {

        var loadTherapist = function () {
            PatientDataService.allTherapist.then(function (therapists) {
                $scope.therapists = therapists;
            });
        }

        loadTherapist();

        $scope.saveTherapist = function () {
            if ($scope.newTherapistForm.$valid) {
                PatientDataService.saveTherapist({name: $scope.newTherapistName}).then(function () {
                    loadTherapist();
                });
            } else {
                console.log("hibás mező")
            }
        }
    });
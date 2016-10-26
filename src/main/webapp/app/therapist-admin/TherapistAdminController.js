angular.module("MoraPatientApp")
    .controller("TherapistAdminController", function ($scope, MoraDataService, $rootScope, $location) {

        var loadTherapist = function () {
            MoraDataService.allTherapist().then(function (therapists) {
                console.log(therapists);
                $scope.therapists = therapists;
            });
        };

        loadTherapist();

        $scope.saveTherapist = function () {
            if ($scope.therapistForm.$valid) {
                MoraDataService.saveTherapist({name: $scope.newTherapistName}).then(function () {
                    loadTherapist();
                });
            }
        }

        $scope.deleteTherapist = function (therapistId) {
            MoraDataService.deleteTherapist(therapistId).then(function () {
                // deleted myself
                if ($rootScope.loginTherapist.id === therapistId) {
                    //gp to login page
                    $location.path("/");
                } else {
                    loadTherapist();
                }
            });
        }
    });
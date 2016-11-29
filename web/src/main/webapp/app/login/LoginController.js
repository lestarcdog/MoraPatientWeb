angular.module("MoraPatientApp")
    .controller("LoginController", function ($rootScope, $scope, $location, MoraDataService) {
        $rootScope.title = "Bejelentkezés";
        $rootScope.loginTherapist = null;
        $scope.selectedTherapist = null;

        MoraDataService.allTherapist().then(function (therapists) {
            $scope.therapists = therapists;
        });

        $scope.login = function () {
            $rootScope.loginTherapist = $scope.selectedTherapist;
            $location.path("/patient-list");

        }
    });
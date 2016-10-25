angular.module("MoraPatientApp")
    .controller("LoginController", function ($rootScope, $scope, $location, PatientDataService) {
        $rootScope.title = "Bejelentkezés";
        $rootScope.loginTherapist = null;
        $scope.selectedTherapist = null;

        PatientDataService.allTherapist.then(function (therapists) {
            $scope.therapists = therapists;
        });

        $scope.login = function () {
            $rootScope.loginTherapist = $scope.selectedTherapist;
            $location.path("/patient-list");

        }
    });
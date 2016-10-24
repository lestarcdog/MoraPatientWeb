angular.module("MoraPatientApp")
    .controller("LoginController", function ($rootScope, $scope, $location) {
        console.log("asdf");
        $rootScope.title = "Bejelentkezés";
        $rootScope.loginTherapist = null;
        $scope.selectedTherapist = null;

        $scope.therapists = [{id: 0, name: "csabi"}, {id: 1, name: "béla"}];

        $scope.login = function () {
            $rootScope.loginTherapist = $scope.selectedTherapist;
            $location.path("/patient-list");

        }
    });
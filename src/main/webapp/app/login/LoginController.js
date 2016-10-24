angular.module("MoraPatientApp")
    .controller("LoginController", function ($rootScope, $scope) {
        $rootScope.title = "Bejelentkezés";
        $rootScope.loginTherapist = null;
        $scope.data = {};

        $scope.data.therapists = [{id: 0, name: "csabi"}, {id: 1, name: "béla"}];

        $scope.login = function () {
            console.log("bejelentkezés ", $scope.data.loginTherapist);
            $rootScope.loginTherapist = $scope.data.loginTherapist;
        }
    });
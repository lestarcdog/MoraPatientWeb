angular.module("MoraPatientApp")
    .controller("LoginController", function ($scope) {
        $scope.patients = [{id: 0, name: "Csabi", birthdate: new Date()}]
    });
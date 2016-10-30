angular.module("MoraPatientApp")
    .controller("TherapiesController", function ($scope, $rootScope, $location, $routeParams, MoraDataService) {
        var patientId = $routeParams.id
        //var therapistId = $rootScope.loginTherapist.id;
        $scope.showFormError = false;

        var refresh = function () {
            MoraDataService.patientById(patientId).then(function (patient) {
                $scope.patient = patient;
                $scope.therapies = patient.therapies;
                $scope.addNewTherapy();
            })
        };

        $scope.addNewTherapy = function () {
            var therapy = {
                therapyDate: moment().startOf("day").toDate(),
                therapy: "",
                treatment: "",
                interview: "",
                result: "",
                newTherapy: true,
                therapist: $rootScope.loginTherapist
            };
            $scope.therapies.unshift(therapy);
        };

        $scope.delete = function (idx) {
            $scope.therapies.splice(idx, 1);
        };

        $scope.save = function () {
            if ($scope.therapyForm.$valid) {

            } else {
                $scope.showFormError = true;
            }
        };

        refresh();
    });
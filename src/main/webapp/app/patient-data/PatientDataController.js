angular.module("MoraPatientApp")
    .controller("PatientDataController", function ($scope, $rootScope, $location, $routeParams, $filter, MoraDataService, HunCityService) {
        $scope.patient = {};
        if ($routeParams.id != null) {
            MoraDataService.patientById($routeParams.id).then(function (patient) {
                $scope.patient = patient;
            })
        }

        $scope.showError = false;

        $scope.patientCity = null;
        $scope.patientBirthDate = null;

        var hunCities = [];
        HunCityService.getHunCities().then(function (cities) {
            hunCities = cities;
        });

        $scope.hasError = function (fieldName) {
            if ($scope.patientForm[fieldName].$invalid) {
                return "has-error";
            } else {
                return null;
            }
        };

        $scope.getMatches = function (searchTxt) {
            if (hunCities.length > 0) {
                var lower = searchTxt.toLowerCase();
                return _.filter(hunCities, function (o) {
                    return o.smallcaps.indexOf(lower) > -1;
                });
            } else {
                return hunCities;
            }

        };

        $scope.save = function () {
            if ($scope.patientForm.$valid) {
                //flatten city
                if ($scope.patientCity != null) {
                    $scope.patient.city = $scope.patientCity.name;
                }

                //convert time to millis
                $scope.patient.birthDate = $scope.patientBirthDate.getTime();

                console.log("Saving patient", $scope.patient);

                MoraDataService.savePatient($scope.patient);
            } else {
                $scope.showError = true
            }
        }
    });
angular.module("MoraPatientApp")
    .controller("PatientDataController", function ($scope, $rootScope, $location, $routeParams, $filter, MoraDataService, HunCityService) {
        //default patient
        $scope.patient = {
            male: true
        };

        //form extra properties
        $scope.patientCity = null;
        $scope.patientBirthDate = null;

        //show error after validation failed
        $scope.showError = false;


        if ($routeParams.id != null) {
            MoraDataService.patientById($routeParams.id).then(function (patient) {
                $scope.patient.male = patient.male;
                $scope.searchText = patient.city;
                console.log(patient.city);
                $scope.patientBirthDate = moment(patient.birthDate).toDate();
                $scope.patient = patient;
            })
        }


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
                $scope.patient.birthDate = $scope.patientBirthDate;

                console.log("Saving patient", $scope.patient);

                MoraDataService.savePatient($scope.patient);
            } else {
                $scope.showError = true
            }
        }
    });
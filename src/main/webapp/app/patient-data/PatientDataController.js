angular.module("MoraPatientApp")
    .controller("PatientDataController", function ($scope, $rootScope, $location, $routeParams, $filter, MoraDataService, HunCityService,
                                                   DateFormatConst, AlertService, $mdDialog) {
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

        $scope.delete = function () {
            var confirm = $mdDialog.confirm()
                .title("Törlés " + $scope.patient.name)
                .textContent("Biztosan szeretné törölni a " + $scope.patient.name + " nevű " + $scope.patient.birthDate + " születésű pácienst?")
                .ok("Törlés")
                .cancel("Mégsem");
            $mdDialog.show(confirm).then(function () {
                MoraDataService.deletePatient($scope.patient.id).then(function () {
                    AlertService.showSuccess($scope.patient.name + " sikeresen törölve.");
                    $location.path("/patient-list");
                });
            });
        };

        $scope.save = function () {
            if ($scope.patientForm.$valid) {
                //flatten city
                if ($scope.patientCity != null) {
                    $scope.patient.city = $scope.patientCity.name;
                }

                //convert time to millis
                $scope.patient.birthDate = moment($scope.patientBirthDate).format(DateFormatConst.MOMENT_DATE);

                console.log("saving patient", $scope.patient);

                MoraDataService.savePatient($scope.patient).then(function () {
                    AlertService.showSuccess($scope.patient.name + " sikeresen elmentve.");
                    $location.path("/patient-list");
                });
            } else {
                $scope.showError = true
            }
        }
    });
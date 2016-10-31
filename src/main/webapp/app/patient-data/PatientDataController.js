angular.module("MoraPatientApp")
    .controller("PatientDataController", function ($scope, $rootScope, $location, $routeParams, $filter, MoraDataService, HunCityService,
                                                   DateFormatConst, AlertService, $mdDialog, PatientCache, MoraEvents) {
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
                $rootScope.$broadcast(MoraEvents.PATIENT_CHANGE, patient);
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
                    $location.path("/patient-list").search("passThrough", true);
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

                MoraDataService.savePatient($scope.patient).then(function (newPatientId) {
                    AlertService.showSuccess($scope.patient.name + " sikeresen elmentve.");
                    //load the new patient list in the background to the cache
                    PatientCache.patients(true);
                    $location.path("/therapies/" + newPatientId);
                });
            } else {
                $scope.showError = true
            }
        }
    });
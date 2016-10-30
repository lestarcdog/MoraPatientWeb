angular.module("MoraPatientApp")
    .controller("TherapiesController", function ($scope, $rootScope, $location, $routeParams, $mdDialog, MoraDataService, DateFormatConst,
                                                 AlertService) {
        var patientId = $routeParams.id;
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

        $scope.reset = function () {
            var confirm = $mdDialog.confirm()
                .title("Visszaállítás")
                .textContent("A nem mentett változtatások elvesznek. Biztos benne?")
                .ok("Igen")
                .cancel("Mégsem");
            $mdDialog.show(confirm).then(function () {
                refresh();
            })
        };

        $scope.save = function () {
            if ($scope.therapyForm.$valid) {

                //format date to yyyy-mm-dd
                var therapiesCopy = angular.copy($scope.therapies);
                angular.forEach(therapiesCopy, function (therapy) {
                    if (therapy.newTherapy) {
                        therapy.therapyDate = moment(therapy.therapyDate).format(DateFormatConst.MOMENT_DATE);
                    }
                });

                MoraDataService.saveTherapies(therapiesCopy, $scope.patient.id, $rootScope.loginTherapist.id).then(function () {
                    AlertService.showSuccess("Sikeres terápia mentés " + $scope.patient.name + " pácienshez.");
                    refresh();
                });
            } else {
                $scope.showFormError = true;
            }
        };

        refresh();
    });
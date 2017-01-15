angular.module("MoraPatientApp")
    .controller("PatientListController", function ($scope, $rootScope, DateFormatConst, $location, PatientCache, MoraEvents) {
        $scope.patients = [];
        $scope.patientsLoading = true;
        $scope.dateformat = DateFormatConst;
        $scope.scrollLimit = 30;

        $scope.order = {
            field: null,
            isAscending: true
        };

        $rootScope.$broadcast(MoraEvents.PATIENT_CHANGE, null);

        var forceReload = $location.search().forceReload;

        PatientCache.patients(forceReload).then(function (patients) {
            $scope.patients = patients;
            $scope.patientsLoading = false;
        });

        $scope.openTherapies = function (patientId) {
            $location.path("/treatment/therapies/" + patientId);
        };

        $scope.morePatients = function () {
            if ($scope.patients != null && $scope.patients.length > $scope.scrollLimit) {
                $scope.scrollLimit += 20;
            }
        };

        $scope.modifiedSince = function (epochMilli) {
            if (epochMilli != null) {
                return moment(epochMilli).fromNow();
            } else {
                return null;
            }
        }
    });
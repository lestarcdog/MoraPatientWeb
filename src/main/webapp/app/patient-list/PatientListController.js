angular.module("MoraPatientApp")
    .controller("PatientListController", function ($scope, $rootScope, DateFormatConst, $location, PatientCache, MoraEvents) {
        $scope.patients = [];
        $scope.dateformat = DateFormatConst;
        $scope.scrollLimit = 30;

        $rootScope.$broadcast(MoraEvents.PATIENT_CHANGE, null);

        var passThrough = $location.search().passThrough;

        PatientCache.patients(passThrough).then(function (patients) {
            $scope.patients = patients;
        });

        $scope.openTherapies = function (patientId) {
            $location.path("/therapies/" + patientId);
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
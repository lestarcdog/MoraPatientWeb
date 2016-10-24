angular.module("MoraPatientApp")
    .factory("PatientDataService", function ($http) {
        var allTherapist = $http.get("/api/allTherapists").then(function (resp) {

        }, function (resp) {

        });
        return {
            allTherapist: allTherapist
        };
    });
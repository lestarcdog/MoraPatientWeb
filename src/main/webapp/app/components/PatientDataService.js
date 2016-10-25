angular.module("MoraPatientApp")
    .factory("PatientDataService", function ($http) {
        var allTherapist = $http.get("api/therapists").then(function (resp) {
            console.log(resp);
            return resp.data;
        });

        var allPatients = $http.get("api/therapists").then(function (resp) {
            return resp.data;
        });
        return {
            allTherapist: allTherapist,
            allPatients: allPatients
        };
    });
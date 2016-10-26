angular.module("MoraPatientApp")
    .factory("PatientDataService", function ($http) {
        var allTherapist = $http.get("api/therapists").then(function (resp) {
            return resp.data;
        });

        var allPatients = $http.get("api/therapists").then(function (resp) {
            return resp.data;
        });

        var saveTherapist = function (therapist) {
            return $http.post("api/therapist", therapist);
        };

        var savePatient = function (patient) {
            return $http.post("api/patient", patient);
        };


        return {
            allTherapist: allTherapist,
            allPatients: allPatients,
            saveTherapist: saveTherapist,
            savePatient: savePatient
        };
    });
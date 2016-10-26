angular.module("MoraPatientApp")
    .factory("MoraDataService", function ($http, $q) {

        var emptyHandler = function (resp) {
        };
        var errorHandler = function (resp) {
            console.log(resp.data.message);
            return $q.reject()
        };

        var allTherapist = function () {
            return $http.get("api/therapists").then(function (resp) {
                return resp.data;
            }, errorHandler);
        };

        var allPatients = function () {
            return $http.get("api/patients").then(function (resp) {
                return resp.data;
            });
        };

        var patientById = function (patientId) {
            return $http.get("api/patient/" + patientId).then(function (resp) {
                return resp.data;
            }, errorHandler)
        };

        var saveTherapist = function (therapist) {
            return $http.post("api/therapist", therapist).then(emptyHandler, errorHandler);
        };

        var savePatient = function (patient) {
            return $http.post("api/patient", patient).then(emptyHandler, errorHandler);
        };

        var deleteTherapist = function (therapistId) {
            return $http.delete("api/therapist/" + therapistId).then(emptyHandler, errorHandler);
        };

        var deletePatient = function (patientId) {
            return $http.delete("api/patient/" + patientId).then(emptyHandler, errorHandler);
        };


        return {
            allTherapist: allTherapist,
            allPatients: allPatients,
            patientById: patientById,
            saveTherapist: saveTherapist,
            savePatient: savePatient,
            deleteTherapist: deleteTherapist,
            deletePatient: deletePatient
        };
    });
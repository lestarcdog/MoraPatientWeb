angular.module("MoraPatientApp")
    .factory("MoraDataService", function ($http, $q, AlertService) {

        var emptyHandler = function (resp) {
        };
        var errorHandler = function (resp) {
            AlertService.showAlert(resp.data.message);
            return $q.reject()
        };

        // api calls

        var allTherapist = function () {
            return $http.get("api/therapists").then(function (resp) {
                return resp.data;
            }, errorHandler);
        };

        var allPatients = function () {
            return $http.get("api/patients").then(function (resp) {
                return resp.data;
            }, errorHandler);
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
            return $http.post("api/patient", patient).then(function (resp) {
                return resp.data;
            }, errorHandler);
        };

        var deleteTherapist = function (therapistId) {
            return $http.delete("api/therapist/" + therapistId).then(emptyHandler, errorHandler);
        };

        var deletePatient = function (patientId) {
            return $http.delete("api/patient/" + patientId).then(emptyHandler, errorHandler);
        };

        var saveTherapies = function (therapies, patientId, therapist) {
            var data = {
                therapies: therapies,
                therapistId: therapist.id
            };
            return $http.post("api/patient/" + patientId + "/therapies", data).then(emptyHandler, errorHandler);
        };


        return {
            allTherapist: allTherapist,
            allPatients: allPatients,
            patientById: patientById,
            saveTherapist: saveTherapist,
            savePatient: savePatient,
            deleteTherapist: deleteTherapist,
            deletePatient: deletePatient,
            saveTherapies: saveTherapies
        };
    });
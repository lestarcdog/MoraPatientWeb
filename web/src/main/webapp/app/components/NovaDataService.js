angular.module("MoraPatientApp")
    .factory("NovaDataService", function ($http, $q, AlertService) {

        var emptyHandler = function (resp) {
        };
        var errorHandler = function (resp) {
            AlertService.showAlert(resp.data.message);
            return $q.reject()
        };

        // api calls

        var allNovaPatient = function () {
            return $http.get("api/nova/patients").then(function (resp) {
                return resp.data;
            }, errorHandler);
        };

        var novaPatientResults = function (patientId) {
            return $http.get("api/nova/patient/" + patientId + "/results").then(function (resp) {
                return resp.data;
            }, errorHandler);
        };

        var novaResult = function (patientId, resultId) {
            return $http.get("api/nova/patient/" + patientId + "/result/" + resultId).then(function (resp) {
                return resp.data;
            }, errorHandler);
        };


        var joinMoraNovaPatient = function (novaPatientId, moraPatientId) {
            return $http.post("api/nova/patient/" + novaPatientId + "/join", moraPatientId).then(function (resp) {
            }, errorHandler);
        };


        return {
            allNovaPatient: allNovaPatient,
            novaPatientResults: novaPatientResults,
            novaResult: novaResult,
            joinMoraNovaPatient: joinMoraNovaPatient
        };
    });
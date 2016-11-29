angular.module("MoraPatientApp").factory("PatientCache", function ($cacheFactory, MoraDataService, $q) {
    var patientCache = $cacheFactory("patientsCache");
    var patientsKey = "patients";

    var patients = function (passThrough) {
        var patients = patientCache.get(patientsKey);
        var deferred = $q.defer();

        if (patients == null || passThrough) {
            MoraDataService.allPatients().then(function (patients) {
                patientCache.put(patientsKey, patients);
                deferred.resolve(patients);
            }, function (error) {
                deferred.reject(error);
            });
        } else {
            deferred.resolve(patients);
        }
        return deferred.promise;
    };

    return {
        patients: patients
    }
});
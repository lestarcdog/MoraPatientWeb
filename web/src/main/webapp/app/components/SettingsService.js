angular.module("MoraPatientApp").factory("SettingsService", function ($http, $q, AlertService) {
    var emptyHandler = function (resp) {
    };
    var errorHandler = function (resp) {
        AlertService.showAlert(resp.data.message);
        return $q.reject()
    };

    var cityCache = null;
    var getHunCities = function () {
        var defer = $q.defer();
        if (cityCache == null) {
            $http.get("api/hunCities").then(function (resp) {
                cityCache = resp.data;
                defer.resolve(cityCache);
            })
        } else {
            defer.resolve(cityCache);
        }

        return defer.promise;
    };

    var getSettingValue = function (name) {
        return $http.get("api/settings/" + name).then(function (response) {
            return response.data;
        }, errorHandler);
    };

    var setSettingValue = function (name, val) {
        return $http.post("api/settings/" + name, val).then(emptyHandler, errorHandler);
    };

    return {
        getHunCities: getHunCities,
        getSettingValue: getSettingValue,
        setSettingValue: setSettingValue
    }
});

angular.module("MoraPatientApp").factory("HunCityService", function ($cacheFactory, $q, $http) {
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

    return {
        getHunCities: getHunCities
    }
});

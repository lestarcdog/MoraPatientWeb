angular.module("MoraPatientApp")
    .controller("PatientListController", function ($scope, DateFormatConst) {
        $scope.patients = [
            {
                id: 0,
                name: "Csabi",
                birthDate: 1477345293308,
                birthPlace: "Kecskemét",
                phone: "1234-4567",
                lastModified: 1477345293308
            },
            {
                id: 1,
                name: "Csabi213",
                birthDate: 1477745293308,
                birthPlace: "qwerwerq",
                phone: "1234-4567",
                lastModified: 1433345293308
            },
            {
                id: 2,
                name: "Móczy",
                birthDate: 1477341293308,
                birthPlace: "ztretzet",
                phone: "1234-4567",
                lastModified: 1447345293308
            }];

        $scope.dateformat = DateFormatConst;

        $scope.modifiedSince = function (epochMilli) {
            return moment(epochMilli).fromNow();
        }
    });
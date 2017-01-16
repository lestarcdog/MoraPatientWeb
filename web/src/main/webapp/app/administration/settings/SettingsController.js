angular.module("MoraPatientApp")
    .controller("SettingsController", function ($scope, SettingsService, AlertService) {
        $scope.settings = {};

        SettingsService.getSettingValue("NOVA_DB_HOME").then(function (val) {
            $scope.settings.novaPath = val.value;
        });

        $scope.save = function (name, val) {
            SettingsService.setSettingValue(name, val).then(function () {
                AlertService.showSuccess("A paraméter új értéke " + val);
            });
        }

    });
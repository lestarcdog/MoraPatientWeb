angular.module("MoraPatientApp")
    .controller("NovaMoraPatientsController", function ($scope, NovaDataService, PatientCache, $mdDialog) {
        $scope.patients = {
            nova: null,
            mora: null
        };
        $scope.selected = {
            nova: null,
            mora: null
        };

        PatientCache.patients(true).then(function (moraPatients) {
            $scope.patients.mora = moraPatients;
        });

        NovaDataService.allNovaPatient().then(function (novaPatients) {
            $scope.patients.nova = novaPatients;
        });

        $scope.selectNova = function (nova) {
            $scope.selected.nova = nova;
        };

        $scope.selectMora = function (mora) {
            $scope.selected.mora = mora;
        };


        $scope.joinPatients = function () {
            if ($scope.selected.nova && $scope.selected.mora) {
                var confirm;
                //the two are already joined
                if ($scope.selected.nova.moraPatientId === $scope.selected.mora.id) {
                    $mdDialog.show($mdDialog.alert()
                        .title("Páciensek")
                        .textContent("A kiválasztott páciensek már össze vannak kapcsolva.")
                        .ok("Értettem"));

                    // no further processing
                    return;
                }

                //both morapatient and novapatient IDs are set
                if ($scope.selected.nova.moraPatientId && $scope.selected.mora.novaPatientId) {
                    confirm = $mdDialog.confirm()
                        .title("Nova és Mora páciens")
                        .textContent("A Nova és Mora páciensek már össze vannak egy harmadik pácienssel kötve. Mégis folytatja?")
                        .ok("Igen")
                        .cancel("Mégsem");
                    $mdDialog.show(confirm).then(function (result) {
                        console.log("Both join", result);
                        join()

                    });
                    // nova patient id is connected
                } else if ($scope.selected.nova.moraPatientId) {
                    confirm = $mdDialog.confirm()
                        .title("Nova páciens")
                        .textContent("A Nova páciens már össze van egy másik pácienssel kötve. Mégis folytatja?")
                        .ok("Igen")
                        .cancel("Mégsem");
                    $mdDialog.show(confirm).then(function (result) {
                        console.log("nova join", result);
                        join();

                    });
                    // mora patient id is connected
                } else if ($scope.selected.mora.novaPatientId) {
                    confirm = $mdDialog.confirm()
                        .title("Mora páciens")
                        .textContent("A Mora páciens már össze van egy másik pácienssel kötve. Mégis folytatja?")
                        .ok("Igen")
                        .cancel("Mégsem");
                    $mdDialog.show(confirm).then(function (result) {
                        console.log("mora join", result);
                        join()
                    });
                } else {
                    // no problem save the connection
                    join();
                }
            }
        };

        var join = function () {
            NovaDataService.joinMoraNovaPatient($scope.selected.nova.id, $scope.selected.mora.id).then(function () {
                //remove previous connections
                angular.forEach($scope.patients.mora, function (val) {
                    if (val.novaPatientId === $scope.selected.nova.id) {
                        val.novaPatientId = null;
                    }
                });

                angular.forEach($scope.patients.nova, function (val) {
                    if (val.moraPatientId === $scope.selected.mora.id) {
                        val.moraPatientId = null;
                    }
                });


                // update the tables with new connections
                $scope.selected.nova.moraPatientId = $scope.selected.mora.id;
                $scope.selected.mora.novaPatientId = $scope.selected.nova.id;

                $scope.selected = {};
            });
        }


    });
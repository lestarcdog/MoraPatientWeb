angular.module("MoraPatientApp").controller("NovaResultsController",
    function ($scope, $rootScope, $routeParams, NovaDataService) {

        var patientId = $routeParams.id;

        $scope.hasNovaPatient = patientId != null;

        // no nova connection to this patient.
        if (!$scope.hasNovaPatient) {
            return;
        }

        $scope.novaResult = null;
        // https://github.com/wix/angular-tree-control
        $scope.treeControlOptions = {
            nodeChildren: "children",
            dirSelectable: false
        };

        $scope.selectTest = function (testId) {
            $scope.selectedTestId = testId;
            NovaDataService.novaResult(patientId, testId).then(function (result) {
                console.log(result);
                $scope.novaResult = result;
                $scope.openTree();
            });


        };

        $scope.openTree = function () {
            $scope.allNodes = [];
            getAllNodes($scope.novaResult.root);
        };

        $scope.closeTree = function () {
            $scope.allNodes = [];
        };
        var getAllNodes = function (node) {
            $scope.allNodes.push(node);
            if (node.children && node.children.length > 0) {
                angular.forEach(node.children, function (child) {
                    getAllNodes(child);
                });
            }
        };


        var refresh = function () {
            NovaDataService.novaPatientResults(patientId).then(function (results) {
                $scope.results = results;
            });
        };

        refresh()
    });

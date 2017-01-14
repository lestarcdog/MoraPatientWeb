angular.module("MoraPatientApp").directive("thSortIndicator", function () {

    return {
        restrict: "E",
        template: "<span ng-if='visible'>" +
        '<i ng-if="!direction" class="fa fa-sort-asc" aria-hidden="true"></i>' +
        '<i ng-if="direction" class="fa fa-sort-desc" aria-hidden="true"></i>' +
        "</span>",
        scope: {
            watched: "@",
            selectedField: "=",
            direction: "="
        },
        link: function (scope) {
            scope.visible = false;
            scope.$watch("selectedField", function (newVal) {
                scope.visible = newVal == scope.watched
            });
        }
    }
});
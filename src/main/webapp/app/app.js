angular.module("MoraPatientApp", ["ngAnimate", "ngAria", "ngRoute", "ngMessages", "ngMaterial"])
    .config(function ($routeProvider) {
        $routeProvider
            .when("/login", {
                controller: "LoginController",
                templateUrl: "app/login/login.html"
            })
            .when("/patient-data/:id?", {
                controller: "PatientDataController",
                templateUrl: "app/patient-data/patient-data.html"
            })
            .when("/patient-list", {
                controller: "PatientListController",
                templateUrl: "app/patient-list/patient-list.html"
            })
            .when("/therapies/:id", {
                controller: "TherapiesController",
                templateUrl: "app/therapies/therapies.html"
            })
            .when("/therapist-admin", {
                controller: "TherapistAdminController",
                templateUrl: "app/therapist-admin/therapist-admin.html"
            })
            .otherwise("/login")
    })
    .run(function ($rootScope) {
        moment.locale("hu");
        $rootScope.loginTherapist = {};
    });
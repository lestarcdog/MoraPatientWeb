angular.module("MoraPatientApp", ["ngAnimate", "ngAria", "ngRoute", "ngMessages", "ngMaterial"])
    .config(function ($routeProvider) {
        $routeProvider
            .when("/login", {
                controller: "LoginController",
                templateUrl: "app/login/login.html"
            })
            .when("/patient_data/:id", {
                controller: "PatientDataController",
                templateUrl: "app/patient_data/patient_data.html"
            })
            .when("/list_patient", {
                controller: "ListPatientController",
                templateUrl: "app/list_patient/list_patient.html"
            })
            .when("therapies/:id", {
                controller: "TherapiesController",
                templateUrl: "app/therapies/therapies.html"
            })
            .otherwise("/login")
    })
    .run(function ($rootScope) {
        $rootScope.loginTherapist = {};
    });
angular.module("MoraPatientApp", ["ngAnimate", "ngAria", "ngRoute", "ngMessages", "ngMaterial", "infinite-scroll"])
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
            .when("/treatment/mora-result/:id", {
                controller: "MoraResultsController",
                templateUrl: "app/treatment/mora-result/mora-results.html"
            })
            .when("/treatment/therapies/:id", {
                controller: "TherapiesController",
                templateUrl: "app/treatment/therapies/therapies.html"
            })
            .when("/administration/therapist-admin", {
                controller: "TherapistAdminController",
                templateUrl: "app/therapist-admin/therapist-admin.html"
            })
            .otherwise("/login")
    })
    .config(function ($mdDateLocaleProvider) {
        moment.locale("hu");

        $mdDateLocaleProvider.months = moment.months();
        $mdDateLocaleProvider.shortMonths = moment.monthsShort();
        $mdDateLocaleProvider.days = moment.weekdays();
        $mdDateLocaleProvider.shortDays = moment.weekdaysShort();

        $mdDateLocaleProvider.firstDayOfWeek = 1;

        $mdDateLocaleProvider.parseDate = function (dateString) {
            var m = moment(dateString, ["YYYYMMDD", 'L', "YYYY-MM-DD", "YYYY.MM.DD", "YYYY MM DD"], true);
            console.log(m.isValid(), m.toDate());
            return m.isValid() ? m.toDate() : new Date(NaN);
        };

        $mdDateLocaleProvider.formatDate = function (date) {
            var m = moment(date);
            return m.isValid() ? m.format('YYYY.MM.DD') : '';
        };

        $mdDateLocaleProvider.msgCalendar = 'Naptár';
        $mdDateLocaleProvider.msgOpenCalendar = 'Naptár megnyitás';
    })
    .constant("DateFormatConst", {
        DATE: "yyyy-MM-dd",
        MOMENT_DATE: "YYYY-MM-DD",
        DATETIME: "yyyy-MM-dd HH:mm:ss"
    })
    .constant("MoraEvents", {
        PATIENT_CHANGE: "moraPatientChangedEvt"
    })
    .run(function ($rootScope, $location) {
        // uncomment to testing
        $rootScope.loginTherapist = {"id": 1, "name": "admin"};
        // comment to testing
        // if ($location.path() !== "/login") {
        //     $location.path("/login");
        // }
    });
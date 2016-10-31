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
    .config(function ($mdDateLocaleProvider) {
        moment.locale("hu");

        $mdDateLocaleProvider.months = moment.months();
        $mdDateLocaleProvider.shortMonths = moment.monthsShort();
        $mdDateLocaleProvider.days = moment.weekdays();
        $mdDateLocaleProvider.shortDays = moment.weekdaysShort();

        $mdDateLocaleProvider.firstDayOfWeek = 1;

        // Example uses moment.js to parse and format dates.
        $mdDateLocaleProvider.parseDate = function (dateString) {
            var m = moment(dateString, ['L', "YYYY-MM-DD", "YYYY.MM.DD", "YYYY MM DD"], true);
            return m.isValid() ? m.toDate() : null;
        };

        $mdDateLocaleProvider.formatDate = function (date) {
            var m = moment(date);
            return m.isValid() ? m.format('L') : '';
        };

        $mdDateLocaleProvider.msgCalendar = 'Naptár';
        $mdDateLocaleProvider.msgOpenCalendar = 'Naptár megnyitás';
    })
    .constant("DateFormatConst", {
        DATE: "yyyy-MM-dd",
        MOMENT_DATE: "YYYY-MM-DD",
        DATETIME: "yyyy-MM-dd HH:mm:ss"
    })
    .run(function ($rootScope, $location) {
        $rootScope.loginTherapist = {"id": 1, "name": "admin"};
        // if ($location.path() !== "/login") {
        //     $location.path("/login");
        // }
    });
var mainApp = angular.module("mainApp", ["ngRoute","ab-base64"]);

mainApp.config(function($routeProvider) {
    $routeProvider
        .when('/showAccounts', {
            templateUrl: 'html/demo/showAccounts.html',
            controller: 'ShowAccounts'
        })
        .when('/createAccount', {
            templateUrl: 'html/demo/createAccount.html',
            controller: 'CreateAccount'
        })
        .when('/showUrls/:id', {
            templateUrl: 'html/demo/showUrls.html',
            controller: 'ShowUrls'
        })
        .when('/registerUrl/:id', {
            templateUrl: 'html/demo/registerUrl.html',
            controller: 'RegisterUrl'
        })
        .when('/showStatistic/:id', {
            templateUrl: 'html/demo/showStatistic.html',
            controller: 'ShowStatistic'
        })
        .otherwise({
            redirectTo: '/showAccounts'
        });
});
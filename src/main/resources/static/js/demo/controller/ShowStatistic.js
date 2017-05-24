mainApp.controller('ShowStatistic', function($scope, $http, $location, $routeParams, base64,
                                             MessageService, AccountService) {
    $scope.message = MessageService.get();
    $scope.account = AccountService.getById($routeParams.id);
    if ($scope.account === null) {
        $location.path("/showAccounts");
    }

    $scope.statistic = {};
    $http.get("statistic/" + $scope.account.id, {
        headers: {
            Authorization: "Basic " + base64.encode($scope.account.id + ":" + $scope.account.password)
        }})
        .then(function (response) {
            $scope.statistic = response.data;
        }, function (response) {
            MessageService.set(response.data.error);
            $location.path("/showAccounts");
        });
});
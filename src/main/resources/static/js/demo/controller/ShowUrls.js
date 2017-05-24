mainApp.controller('ShowUrls', function($scope, $http, $location, $routeParams,
                                        MessageService, AccountService) {
    $scope.message = MessageService.get();
    $scope.account = AccountService.getById($routeParams.id);
    if ($scope.account === null) {
        $location.path("/showAccounts");
    }
});
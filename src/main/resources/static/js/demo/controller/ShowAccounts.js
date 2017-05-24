mainApp.controller('ShowAccounts', function($scope, $http, $location,
                                            MessageService, AccountService) {
    $scope.message = MessageService.get();
    $scope.accounts = AccountService.getAll();
});
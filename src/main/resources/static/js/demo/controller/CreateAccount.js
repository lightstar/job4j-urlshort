mainApp.controller('CreateAccount', function($scope, $http, $location,
                                             MessageService, AccountService) {
    $scope.message = MessageService.get();

    $scope.createAccount = function() {
        $http.post("account/", $scope.account)
            .then(function (response) {
                AccountService.add({
                    id: $scope.account.AccountId,
                    password: response.data.password,
                    urls: []
                });
                MessageService.set(response.data.description);
                $location.path("/showAccounts");
            }, function (response) {
                if (response.data.description) {
                    MessageService.set(response.data.description);
                } else if (response.data.error) {
                    MessageService.set(response.data.error);
                }
                $location.path("/showAccounts");
            });
    };
});
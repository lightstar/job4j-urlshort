mainApp.controller('RegisterUrl', function($scope, $http, $location, $routeParams, base64,
                                           MessageService, AccountService) {
    $scope.message = MessageService.get();
    $scope.account = AccountService.getById($routeParams.id);
    if ($scope.account === null) {
        $location.path("/showAccounts");
    }

    $scope.registerUrl = function() {
        $http.post("register/", $scope.url, {
            headers: {
                Authorization: "Basic " + base64.encode($scope.account.id + ":" + $scope.account.password)
            }})
            .then(function (response) {
                $scope.account.urls.push({
                    long: $scope.url.url,
                    short: response.data.shortUrl,
                    redirectType: $scope.url.redirectType
                });
                MessageService.set("URL successfully registered");
                $location.path("/showUrls/" + $scope.account.id);
            }, function (response) {
                MessageService.set(response.data.error);
                $location.path("/showUrls/" + $scope.account.id);
            });
    };
});
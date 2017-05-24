mainApp.service('AccountService',
    function()  {
        var accounts = [];

        function add(account) {
            accounts.push(account);
        }

        function getAll() {
            return accounts;
        }

        function getById(id) {
            for (var i = 0; i < accounts.length; i++) {
                if (accounts[i].id === id) {
                    return accounts[i];
                }
            }
            return null;
        }

        return {
            add: add,
            getAll: getAll,
            getById: getById
        }
    }
);
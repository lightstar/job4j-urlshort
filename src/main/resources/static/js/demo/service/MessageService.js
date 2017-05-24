mainApp.service('MessageService',
    function()  {
        var message = "";

        function set(inMessage) {
            message = inMessage;
        }

        function get() {
            var outMessage = message;
            message = "";
            return outMessage;
        }

        return {
            set: set,
            get: get
        }
    }
);
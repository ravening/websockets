var MessageTypes = MessageTypes || {}

MessageTypes.JOIN = 1;
MessageTypes.MESSAGE = 2;
MessageTypes.GETUSERS = 3;
MessageTypes.USERLIST = 4;
MessageTypes.GETMESSAGES = 5;
MessageTypes.MESSAGELIST = 6;

function ChatViewModel(chatClient) {

    function User(name, me) {
        this.name = name;
        this.me = me;
    }

    // 'private' members
    var self = this;

    var initialize = function () {
        chatClient.initialize(self.handler);
    };

    var processMessage = function (msg) {
        console.log(msg);
        self.users.push(new User(msg, true));
    };


// public members
    self.users = ko.observableArray();
    self.messages = ko.observableArray();

    self.chat = chatClient;

    self.userName = ko.observable("");

    self.handler = {
        onopen: function () {
            console.log("connected")
        },
        onclose: function () {

        },
        onmessage: function (message) {
            processMessage(message);
        }
    };

    self.join = function () {
        if (!self.joined() && self.userName() && self.userName().length > 0) {
            self.joined(true);
            chat.sendMessage(self.userName());
        }
    };

    self.messages = ko.observableArray();

    self.joined = ko.observable(false);

    self.message = ko.observable('');

    self.enableSendMessage = ko.computed(function () {
        return self.joined() && self.message() && self.message().length > 0;
    });

    self.sendMessage = function () {
        var msg = new NewChatMessage(self.userName(), self.message());
        chat.sendMessage(msg);
    };

    initialize();
}


define(['angular','../services/NotifyService.js','../services/UserService.js'], function (angular) {

    // controller
    return ["$scope", 'NotifyService','UserService', function ($scope, notifyService, userService) {
        $scope.notifications = [];
        $scope.messages = [];
        $scope.tasks = [];
        $scope.userInfo = {};
        userService.async().then(function(rs){
            if(rs.success) {
                $scope.userInfo = rs.data;
            }

        });

        $scope.$on("reloadUser",function(evt, data){

            userService.async().then(function(rs){
                if(rs.success) {
                    $scope.userInfo = rs.data;
                }

            });
        });

        var pushTask = function (tasks) {
            angular.forEach(tasks, function (value) {
                console.log('push task');
                console.log(value);
                $scope.tasks.unshift(value);
            });
        }
        var pushMessage = function (msgs) {
            angular.forEach(msgs, function (value) {
                console.log('push message');
                console.log(value);
                $scope.messages.unshift(value);
            });
        }
        var pushNotification = function (notifys) {

            angular.forEach(notifys, function (value) {
                console.log('push notification');
                console.log(value);
                $scope.notifications.unshift(value.message);
            });
        };
        //subscribe alert from server
        notifyService.connect('/app').then(function (username) {
            $scope.userName = username;
            return notifyService.loadNotification();
        }).then(function (notifications) {
            pushNotification(notifications);
            return notifyService.loadMessage();
        }).then(function (messages) {
            pushMessage(messages);
            return notifyService.loadTask();
        }).then(function (tasks) {
            pushTask(tasks);

            //TODO test
            notifyService.fetchMessageUpdateStream().then(null,null,function(message){
                userService.alertFetch(message);
            });
        });
    }];
});
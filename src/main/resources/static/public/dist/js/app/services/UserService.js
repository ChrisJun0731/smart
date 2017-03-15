define(['app'], function(app){
    app.factory('UserService', function($http, $rootScope) {
        var userService = {
            async: function() {
                var promise = $http.get('/user/info').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            list: function() {
                var promise = $http.get('/user/list').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadHome : function() {
                var promise = $http.get('/user/home').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadTop : function() {
                var promise = $http.get('/board/top').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadHomeMessage : function() {
                var promise = $http.get('/board/home').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            postMessage : function(data) {
                var promise = $http.post('/board/message', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            alertFetch : function(msg) {
                $rootScope.$broadcast("messageAdded"+msg.boardId, msg);
            },
            loadTopFile : function(config) {
                var promise = $http.get('/board/topfiles',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            saveProfile : function(data) {
                var promise = $http.post('/user/profile', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            saveUser : function(data) {
                var promise = $http.post('/user/info', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            alertLoadUser : function() {
                $rootScope.$broadcast("reloadUser", {status:'ok'});
            },
            removeMsg : function(config){
                var promise = $http.delete("/board/removeMsg",config).then(function(response){
                    return response.data;
                });
                return promise;
            },
            postComment : function(data,config){
                var promise = $http.post("/board/comment",data,config).then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
        return userService;
    })
    .directive('myMention', function(){
        return {
            restrict: 'A',
            require: "uiMention",
            link: function($scope, $element, $attrs, uiMention){
                uiMention.findChoices = function(match, mentions){
                    return $scope.choices
                    .filter(function(choice){
                        return !mentions.some(function(mention){
                            return choice.id === mention.id;
                        });
                    })
                    .filter(function(choice){
                        return ~(choice.first+ " " +choice.last).indexOf(match[1]);
                    });
                }
            }
        }
    })
    .directive('myBindHtml', function($compile){
        return {
            restrict: 'A',
            scope: {
                rawHtml: '=myBindHtml'
            },
            link: function(scope, ele, attrs){
                scope.$watch('rawHtml', function(value){
                    if(!value){
                        return;
                    }
                    var newElem = $compile(value)(scope.$parent);
                    ele.contents().remove();
                    ele.append(newElem);
                })
            }
        }
    });
});
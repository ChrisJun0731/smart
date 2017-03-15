define(['app'], function(app){
    app.factory('BoardService', function($http) {
        var boardService = {

            loadAccountBoardInfo : function(config) {
                var promise = $http.get('/board/account-info',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadInfo : function(config) {
                var promise = $http.get('/board/info',config).then(function (response) {
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
            followBoard : function(config) {
                var promise = $http.get('/board/follow',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            unfollowBoard : function(config) {
                var promise = $http.get('/board/unfollow',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            getActivity : function(config) {
                var promise = $http.get('/board/activity',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadTopFile : function(config) {
                var promise = $http.get('/board/topfiles',config).then(function (response) {
                    return response.data;
                });
                return promise;
            }
        };
        return boardService;
    });
});
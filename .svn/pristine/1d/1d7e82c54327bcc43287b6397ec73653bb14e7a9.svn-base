define(['app'], function(app){
    app.factory('DiskService', function($http) {
        var pathArr = [];
        var diskService = {
            addFolder : function(data) {
                var promise = $http.post('/disk/folder', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            remove : function(data) {
                var promise = $http.post('/disk/remove', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadInfo : function(config) {
                var promise = $http.get('/disk/list',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            goPath: function(config) {
                var promise = $http.get('/disk/path',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            getLocalPath: function(param) {
                var inx = pathArr.findIndex(function(obj){
                    return obj.root == param.root;
                });
                if(inx <0) {
                    pathArr.push(param);
                } else {
                    pathArr.splice(inx+1);
                }
                return pathArr;
            },
            setLocalPath: function(param) {
                pathArr = param;
            }
        };
        return diskService;
    });
});
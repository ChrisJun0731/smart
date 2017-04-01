define(['app'], function(app){
    app.factory('SalesService', function($http) {
        var salesService = {
            loadSales : function() {
                var promise = $http.get('/sale/list').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadPagedSales : function(pageConfig){
                var promise = $http.get('/sale/page',pageConfig).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            getSortedList : function(config){
                var promise = $http.get('/common/sort',config).then(function(response){
                    return response.data;
                });
                return promise;
            },
            saveSale : function(data) {
                var promise = $http.post('/sale/info', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadSale : function(config) {
                var promise = $http.get('/sale/info',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            getPercent : function(config) {
                var promise = $http.get('/sale/percent',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            getRecent : function(config) {
                var promise = $http.get('/sale/recent',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            searchSales: function(config){
                var promise = $http.get('/sale/search', config).then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
        return salesService;
    });
});
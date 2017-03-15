define(['app'], function(app){
    app.factory('AccountService', function($http) {
        var accountService = {
            loadAccounts : function() {
                var promise = $http.get('/account/list').then(function (response) {
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
            loadCompanys : function() {
                var promise = $http.get('/company/list').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            createCompany : function(data) {
                var promise = $http.post('/company/info', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadCompany : function(config) {
                var promise = $http.get('/company/info',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadCompanyNameList : function($query){
                return $http.get('/company/names', { cache: true}).then(function(response) {
                    var data = response.data.data;
                    return data.filter(function(item) {
                        return item.shortName.toLowerCase().indexOf($query.toLowerCase()) != -1;
                    });
                });
            },
            saveAccount : function(data) {
                var promise = $http.post('/account/info', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadAccount : function(config) {
                var promise = $http.get('/account/info',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            addPortfolio: function(data){
                var promise = $http.post('/account/portfolio', data).then(function(response){
                    return response.data;
                });
                return promise;
            },
            loadPortfolio: function(config){
                var promise = $http.get('/account/loadPortfolio',config).then(function(response){
                    return response.data;
                });
                return promise;
            },
            updatePortfolio: function(data, config){
                var promise = $http.post('/account/updatePortfolio', data, config).then(function(response){
                    return response.data;
                });
                return promise;
            },
            loadNewAccounts: function(config){
                var promise = $http.get('/account/trade-list',config).then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
        return accountService;
    });
});
define(['app'], function(app){
    app.factory('ContactService', function($http) {
        var contactService = {
            //add by zj
            saveFormField : function(config){
                var promise = $http.get('/contact/field',config).then(function(response){
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
            loadContacts : function() {
                var promise = $http.get('/contact/list').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            saveContact : function(data) {
                var promise = $http.post('/contact/info', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            inviteContact : function(data) {
                var promise = $http.post('/contact/invite', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadContact : function(config) {
                var promise = $http.get('/contact/info',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadMoreContacts : function(config){
                var promise = $http.get('/contact/loadMoreContacts',config).then(function(response){
                    return response.data;
                });
                return promise;
            }
        };
        return contactService;
    });
});
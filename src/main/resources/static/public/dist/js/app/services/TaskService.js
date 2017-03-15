define(['app'], function(app){
    app.factory('TaskService', function($http) {
        var taskService = {
            loadTasks : function() {
                var promise = $http.get('/task/list').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            saveTask : function(data){
                var promise = $http.post('/task/save', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            toggleMarkup:function(data){
                var promise = $http.get('/task/markup?id='+data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            cancelTask:function(data){
                var promise = $http.get('/task/cancel?id='+data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadTask:function(data){
                var promise = $http.get('/task/load?id='+data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            editTask:function(){
                var promise = {
                    editTask:null,
                    loadTask:function(task){
                        editTask = task;
                    },
                    getTask:function(){
                        return editTask;
                    }
                };
                return promise;
            },
            loadAccountNames: function(){
                var promise = $http.get('/account/accountNames').then(function(response){
                    return response.data;
                });
                return promise;
            },
            loadContactNames: function(){
                var promise = $http.get('/contact/contactNames').then(function(response){
                    return response.data;
                });
                return promise;
            },
            loadPortfolioTitles: function(config){
                var promise = $http.get('/account/portfolioTitles',config).then(function(response){
                    return response.data;
                });
                return promise;
            },
            statusFilter: function(config){
                var promise = $http.get('/task/taskFilter',config).then(function(response){
                    return response.data;
                });
                return promise;
            },
            finishTask: function(config){
                var promise = $http.get('/task/finishTask',config);
            },
            loadMatchInfo: function(titleStr){
                var config = {params:{portfolioTitles:titleStr}};
                var promise = $http.get('/account/matchInfo',config).then(function(response){
                    return response.data;
                })
                return promise;
            },
            addActivityInfo: function(data){
                $http.post('/task/addActivityInfo', data);
            }


        };
        return taskService;
    });
});
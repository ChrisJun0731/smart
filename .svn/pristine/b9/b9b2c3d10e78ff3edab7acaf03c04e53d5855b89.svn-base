define(['app'], function(app){
	app.factory('ReportService', function($http) {
		var reportService = {
			loadReports:function(){
				var promise = $http.get('/report/list').then(function (response) {
                    return response.data;
                });
                return promise;
			},
            saveReport : function(data) {
                var promise = $http.post('/report/info', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            removeReport: function(id) {
                var promise = $http.delete('/report/info/'+id).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadReport: function(config) {
                var promise = $http.get('/report/info',config).then(function (response) {
                    return response.data;
                });
                return promise;
            }
		};
		return reportService;
	});
});
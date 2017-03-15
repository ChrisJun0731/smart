define(['app'], function(app){
	app.factory('CalendarService', function($http) {
		var calendarService = {
			loadAppointments:function(){
				var promise = $http.get('/appointment/list').then(function (response) {
                    return response.data;
                });
                return promise;
			},
            loadRecentAppointments:function(){
                var promise = $http.get('/appointment/recent').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            saveAppointment : function(data) {
                var promise = $http.post('/appointment/info', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            removeAppointment: function(id) {
                var promise = $http.delete('/appointment/info/'+id).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            addActivityInfo: function(data){
                var promise = $http.post('/appointment/addActivityInfo',data);
            }
		};
		return calendarService;
	});
});
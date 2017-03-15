define(['app'], function(app){
    app.factory('TeamService', function($http) {
        var teamService = {

            list: function() {
                var promise = $http.get('/team/list').then(function (response) {
                    return response.data;
                });
                return promise;
            },
            saveTeam : function(data) {
                var promise = $http.post('/team/info', data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadTeam : function(config) {
                var promise = $http.get('/team/info',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadBoard : function(config) {
                var promise = $http.get('/team/board',config).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            saveMember : function(data, teamId) {
                var promise = $http.post('/team/member/'+teamId, data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            deleteMember : function(data, teamId) {
                var promise = $http.post('/team/rmmember/'+teamId, data).then(function (response) {
                    return response.data;
                });
                return promise;
            },
            loadTeamNameList : function($query){
                return $http.get('/team/names', { cache: true}).then(function(response) {
                    var data = response.data.data;
                    return data.filter(function(item) {
                        return item.teamName.toLowerCase().indexOf($query.toLowerCase()) != -1;
                    });
                });
            }

        };
        return teamService;
    });
});
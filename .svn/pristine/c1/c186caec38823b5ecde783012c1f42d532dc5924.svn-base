define(['../services/UserService.js','../services/TeamService.js'], function () {
	
	// controller
	return ["$scope", '$state', 'UserService', 'TeamService', '$uibModal','FileUploader',function ($scope, $state, userService, teamService,$uibModal,FileUploader) {
		
		// properties
        userService.list().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.userList = rs.data;
            }

        });

        teamService.list().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.teamList = rs.data;
            }

        });

        //add user
        $scope.openUserModal = function(item) {
            $scope.userInfo = item;

            var modalInstance = $uibModal.open({
                templateUrl: 'userCreator.html',
                controller: userModalController,
                resolve: {
                    info: function () {
                        return $scope.userInfo;
                    }
                }
            });


            modalInstance.result.then(function (item) {
                console.log(item);
                userService.saveUser(item.info).then(function(rs){

                    return userService.list();
                }).then(function(rs){
                    if(rs.success) {
                        $scope.userList = rs.data;
                    }
                });

            });

        };



        var userModalController = function($scope, $filter, $uibModalInstance, info){
            $scope.info = info;

            $scope.loadTeam = function($query) {
                return teamService.loadTeamNameList($query);
            };

            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            };

        };

        //create team
        $scope.openTeamModal=function(item){
            $scope.teamInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'teamCreator.html',
                controller: teamModalController,
                resolve: {
                    info: function () {
                        return $scope.teamInfo;
                    }
                }
            });


            modalInstance.result.then(function (item) {
                console.log(item);
                teamService.saveTeam(item.info).then(function(rs){

                    return teamService.list();
                }).then(function(rs){
                    if(rs.success) {
                        $scope.teamList = rs.data;
                    }
                });

            });
        };

        var teamModalController = function($scope, $filter, $uibModalInstance, info){
            $scope.info = info;

            $scope.cancel=function(){
                console.log($scope.info.accountMap);
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            };

        };

        $scope.goBoard = function(item) {
            teamService.loadBoard({params:{id:item.id}}).then(function(rs){
                console.log(item);
                if(rs.success) {
                    $state.go("root.board",{id:rs.data});
                }
            });
        }

        $scope.editTeam = function(item) {
            teamService.loadTeam({params:{id:item.id}}).then(function(rs){
                if(rs.success) {
                    $scope.openTeamModal(rs.data);
                }
            });
        };

        //create team
        $scope.openMemberModal=function(item){
            $scope.teamInfo = item;
            $uibModal.open({
                templateUrl: 'team-member.html',
                controller: memberModalController,
                resolve: {
                    info: function () {
                        return $scope.teamInfo;
                    }
                }
            });

        };

        ////invite team user
        //$scope.inviteTeamUser = function(item) {
        //    //TODO
        //}


        var memberModalController = function($scope, $filter, $uibModalInstance, info){
            $scope.info = info;
            if($scope.info.userList == null) {
                $scope.info.userList = [];
            }
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.statuses = [
                {value: '1', text: 'Editor'},
                {value: '0', text: 'Member'}
            ];

            $scope.showStatus = function(map) {
                var selected = [];
                if(map.role) {
                    selected = $filter('filter')($scope.statuses, {value: map.role});
                }
                return selected.length ? selected[0].text : 'Not set';
            };

            $scope.checkName = function(data, id) {
                //if (id === 2 && data !== 'awesome') {
                //    return "Username 2 should be `awesome`";
                //}
            };

            $scope.saveMap = function(data, id) {
                console.log(data);
                if(!data.id) {
                    data.id = id;
                }
                teamService.saveMember(data, info.id).then(function(rs){
                    data.id = rs.data.id;
                    console.log(data);
                    return data;

                });
            };

            // remove user
            $scope.removeMap = function(index) {
                var data = $scope.info.userList[index];
                console.log(data);
                if(data.id) {
                    var formData = {id:data.id,text:data.text,role:data.role};
                    teamService.deleteMember(formData, info.id);
                }
                $scope.info.userList.splice(index, 1);
            };

            // add user
            $scope.addMap = function() {
                $scope.inserted = {
                    text: '',
                    role: '0'
                };
                $scope.info.userList.push($scope.inserted);
            };

        };

        $scope.editMember = function(item) {
            teamService.loadTeam({params:{id:item.id}}).then(function(rs){
                if(rs.success) {
                    $scope.openMemberModal(rs.data);
                }
            });
        };

        //add by zj
        //file uploader
        $scope.openUploadModal=function(size){
            var modalInstance = $uibModal.open({
                templateUrl: 'fileUpload.html',
                controller: uploadModalController,
                size: size
            });

            modalInstance.result.then(function (item) {


            });
        };
        var uploadUrl = '/user/upload';
        var uploader = new FileUploader({
            url:uploadUrl
        });
        // FILTERS
        uploader.filters.push({
            name: 'customFilter',
            fn: function() {
                return this.queue.length < 10;
            }
        });

        uploader.onCompleteAll = function() {
            console.info('onCompleteAll');
            userService.list().then(function(rs){
                console.log(rs);
                if(rs.success) {
                    $scope.userList = rs.data;
                }
            });
        };

        var uploadModalController = function($scope, $uibModalInstance){
            // properties
            $scope.uploader = uploader;

            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

        };

    }];
});
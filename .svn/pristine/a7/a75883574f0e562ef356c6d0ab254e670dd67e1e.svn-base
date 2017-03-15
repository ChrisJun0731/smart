define(['../services/BoardService.js'], function () {
	
	// controller
	return ["$scope", '$state','BoardService', function ($scope, $state, boardService) {

        console.log($state.params);
	    $scope.chatEnable = false;
        $scope.board = {};
        $scope.messageContent = "";
        $scope.messageType = "common";
        $scope.messageList = [];
        $scope.fileList = [];
        boardService.loadInfo({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.board = rs.data;
            }
        });

        boardService.getActivity({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.messageList = rs.data;
            }
        });

        boardService.loadTopFile({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.fileList = rs.data;
            }
        });



        $scope.followBoard = function(){

            boardService.followBoard({params:$state.params}).then(function(rs){
                console.log('board follow');
                console.log(rs);
                if(rs.success) {
                    $scope.board.follow = true;
                    $scope.board.followerCount++;
                }
            });

        };

        $scope.unfollowBoard = function(){

            boardService.unfollowBoard({params:$state.params}).then(function(rs){
                console.log('board follow');
                console.log(rs);
                if(rs.success) {
                    $scope.board.follow = false;
                    $scope.board.followerCount--;
                }
            });

        };

        //post message

        $scope.sendMessage = function(){
            console.log($scope.messageContent);

            boardService.postMessage({"boardId":$scope.board.id,"messageContent":$scope.messageContent,"messageType":$scope.messageType}).then(function(rs){
                $scope.messageContent = "";
            });
        };

        $scope.$on("messageAdded"+$state.params.id,function(evt, data){

            $scope.messageList.splice(0,0,data);
        });
    }];
});
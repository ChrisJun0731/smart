define(['../services/BoardService.js','../services/ContactService.js'], function () {
	
	// controller
	return ["$scope", '$state','ContactService','BoardService', function ($scope, $state,contactService, boardService) {
		
		// properties
        boardService.loadAccountBoardInfo({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.board = rs.data;
            }
            return boardService.getActivity({params:{id:$scope.board.id}});
        }).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.messageList = rs.data;
            }
            return boardService.loadTopFile({params:{id:$scope.board.id}});
        }).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.fileList = rs.data;
            }
        });

        contactService.loadContact({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.contactInfo = rs.data;
            }
        });

        $scope.communicationList = [];
    }];
});
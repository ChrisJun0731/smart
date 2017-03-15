define(['../services/ReportService.js'], function () {
	
	// controller
	return ["$scope", '$state', "ReportService",'$sce',function ($scope, $state,reportService,$sce) {

        reportService.loadReport({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.reportInfo = rs.data;
                $scope.reportInfo.url = $sce.trustAsResourceUrl($scope.reportInfo.reportUrl);
            }
        });

    }];
});
define(['../services/ReportService.js'], function () {
	
	// controller
	return ["$scope", "ReportService",function ($scope,reportService) {
        $scope.reportList = [];
        reportService.loadReports().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.reportList = rs.data;
            }
        });

    }];
});
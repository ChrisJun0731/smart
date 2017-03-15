define(['../services/ReportService.js'], function () {
	
	// controller
	return ["$scope", 'ReportService',function ($scope, reportService) {
		
		// properties
        //$rootScope.hasSide = true;
        //console.log($rootScope.hasSide);
        //$scope.$on('$destroy', function() {
         //   $rootScope.hasSide = false;
        //});
        $scope.$root.$on('$messageIncoming', function (event, data){
            console.log(event);
            console.log(data);
            if(data.reportUrl) {
                //save report to db
                reportService.saveReport({id:data.id,title:data.title,reportUrl:data.reportUrl});
            }
        });
    }];
});
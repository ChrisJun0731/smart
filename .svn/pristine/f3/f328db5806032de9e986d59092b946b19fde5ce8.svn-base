define(['../services/SalesService.js','../services/TaskService.js','../services/CalendarService.js','../services/AccountService.js'], function () {
	
	// controller
	return ["$scope",'SalesService','TaskService', 'CalendarService', 'AccountService', function ($scope,salesService,taskService, calendarService, accountService) {

		// properties
	    $scope.title = "This is Home page";
        $scope.labels = [];
        $scope.data = [];

        // percent
        salesService.getPercent().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.percentList = rs.data;
                for(var i = 0;i< rs.data.length; i++) {
                    $scope.labels.push(rs.data[i].contactName);
                    $scope.data.push(rs.data[i].quantity);
                }

            }
        });

        //sales list
        salesService.getRecent().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.saleList = rs.data;
            }
        });
        //task list
        taskService.loadTasks().then(function(rs){
            if(rs.success) {
                $scope.taskList = rs.data;
            }
        });
        $scope.getTaskInfoClass = function(label){
            if(label == null){
                return "";
            }else if(label.indexOf("year") > -1){
                return "label-primary";
            }else if(label.indexOf("month") > -1){
                return "label-success";
            }else if(label.indexOf("week") > -1){
                return "label-warning";
            }else if(label.indexOf("day") > -1){
                return "label-info";
            }else if(label.indexOf("hour") > -1){
                return "label-danger";
            }
        };

        //appointment list
        calendarService.loadRecentAppointments().then(function(rs){
            if(rs.success) {
                $scope.appointList = rs.data;
                console.log(rs.data);
            }
        });

        //add by zj 16/12/27
        //new account list
        accountService.loadNewAccounts({params:{fromDate:'2016-05-01',toDate:'2016-05-10'}}).then(function(rs){
            if(rs.success){
                $scope.newAccounts = rs.data;
                console.log(rs.data);
            };
        });

        // add by zj 17/01/09
        $scope.finishTask = function(index, id){
            $scope.taskList.splice(index, 1);
            var config = {params: {id:id}};
            taskService.finishTask(config);
        };

    }];
});
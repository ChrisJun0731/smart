define(['../services/TaskService.js'], function () {
	// controller
	return ['$scope','$uibModal','TaskService', function ($scope,$uibModal,taskService) {
		
		// properties
	    $scope.title = "This is Task page";
	    $scope.format = "MM/dd/yyyy";
	    $scope.taskList = [];
	    taskService.loadTasks().then(function(rs){
            if(rs.success) {
                $scope.taskList = rs.data;
            }
        });
	    $scope.dateOptions = {
		    maxDate: new Date(2029, 12, 31),
		    minDate: new Date(),
		    startingDay: 1
		};
		$scope.loadTaskData = function(data){
			$scope.taskList = data;
		}
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
		$scope.getMarkupClass = function(markup){
			return markup == true ? 'fa-star' : 'fa-star-o';
		};

        $scope.openMatchModal=function(item, size){
            console.log(item);
            $scope.taskInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'sendMatchCreator.html',
                controller: matchModalController,
                size: size,
                resolve: {
                    info: function () {
                        return $scope.taskInfo;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                console.log(item);
                //matchService.saveMatch(item.info).then(function(rs){
                //
                //    return matchService.loadMatchList();
                //}).then(function(rs){
                //    if(rs.success) {
                //        $scope.saleList = rs.data;
                //        console.log(rs);
                //    }
                //});
            });
        };

        var matchModalController = function($scope, $uibModalInstance, info) {

            $scope.info = info;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            };

            //add by zj 17/01/10
            //load account and portfolio info via portfolio title
            taskService.loadMatchInfo(info.portfolios).then(function(rs){
                var records = rs.data;
                $scope.records = JSON.parse(records);
            })
        };

	    $scope.openAddModal = function(task){
	    	var modalInstance = $uibModal.open({
                templateUrl: 'taskCreator.html',
                controller: taskModalController
            });
            taskService.editTask().loadTask(task);
	    };
	    $scope.markup = function(id, $event){
	    	var ele = angular.element($event.target);
	    	ele.toggleClass('fa-star');
	    	ele.toggleClass('fa-star-o');
	    	taskService.toggleMarkup(id);
	    };
	    $scope.cancel = function(task, $index){
	    	taskService.cancelTask(task.id).then(function(rs){
	    		if(rs.success){
	    			$scope.taskList.splice($index, 1);
	    		}
	    	});
	    };
	    //add by zj 17/01/09
        $scope.statusFilter = function(status){
            var config = {params:{status:status}};
            taskService.statusFilter(config).then(function(rs){
                $scope.taskList = rs.data;
            });
        };

	    $scope.cancelTask = function(){
            $uibModalInstance.dismiss('cancel');
        };

	    //add by zj 17/01/07
        $scope.loadTasksByStatus = function(status){
            var config = {params: {status: status}};
            taskService.loadTasksByStatus(config).then(function(rs){
                if(rs.success){
                    $scope.taskList = rs.data;
                }
            })
        };

        var sScope = $scope;

	    var taskModalController = function($scope, $uibModalInstance){

	        //add by zj 16/12/28 begin
        	$scope.repeatTypes = [
        	    {name:'Never repeat', value:0},
        	    {name:'Only Once', value:1},
                {name:'Everyday', value:2},
                {name:'Workday', value:3},
                {name:'Every week', value:4},
                {name:'Every month', value:5},
            ];

            $scope.taskTypes = [
                {value:0, name:'Common'},
                {value:1, name:'Portfolio Match'}
            ];

            $scope.referTypes = [
                {name: 'Account', value:0},
                {name: 'Contact', value:1},
            ];

            $scope.days = [
                {name: "Sunday", value:1},
                {name: "Monday", value:2},
                {name: "Tuesday", value:3},
                {name: "Wednesday", value:4},
                {name: "Thursday", value:5},
                {name: "Friday", value:6},
                {name: "Saturday", value:7}
            ];

            $scope.dates = [];
            for(var i=0; i<=31; i++){
                $scope.dates.push(i);
            }

            $scope.loadTags = function($query){
                if($scope.task.referType == 0){
                    return taskService.loadAccountNames().then(function(rs){
                        if(rs.success){
                            var accounts = rs.data;
                            return accounts.filter(function(account){
                                return account.text.toLowerCase().indexOf($query.toLowerCase()) != -1;
                            });
                        }
                    });
                }
                else if($scope.task.referType == 1){
                    return taskService.loadContactNames().then(function(rs){
                        if(rs.success){
                            contactNames = rs.data;
                            return contactNames.filter(function(item){
                                return item.toLowerCase().indexOf($query.toLowerCase()) != -1;
                            });
                        }
                    });
                }
            };

            $scope.loadPortfolioTitles = function($query, accounts){
                var accountIds = [];
                angular.forEach(accounts, function(account){
                    accountIds.push(account.id);
                })
                var config = {params:{accountIds: accountIds}};
                return taskService.loadPortfolioTitles(config).then(function(rs){
                    if(rs.success){
                        var portfolios = rs.data;
                        return portfolios.filter(function(portfolio){
                            return portfolio.text.toLowerCase().indexOf($query.toLowerCase()) != -1;
                        });
                    }
                });
            }


            $scope.$watch('task.referType',function(newVal, oldVal){
                $scope.task.tags = null;
            })

	    	$scope.cancelTask = function(){
                $uibModalInstance.dismiss('cancel');
            };
            $scope.submitForm = function(isValid){
            	if(isValid){
            		delete $scope.task.status;
            		taskService.addActivityInfo($scope.task);
            		taskService.saveTask($scope.task).then(function(rs){
            			if(rs.success){
            				$uibModalInstance.dismiss('done');
            				taskService.loadTasks().then(function(rs){
            					sScope.taskList = rs.data;
            				});
            			}
            		});
            	}
            };
            $scope.title = "Add Task";
            var task = taskService.editTask().getTask();
            if(task){
            	$scope.title = "Edit Task";
            	$scope.task = task;
            }else{
            	$scope.title = "Add Task";
            	$scope.task = {};
            	var date = new Date();
            	date.setHours(9);
            	date.setMinutes(0);
            	date.setSeconds(0);
                $scope.task.alertTime = date;
            }
        };

    }];
});
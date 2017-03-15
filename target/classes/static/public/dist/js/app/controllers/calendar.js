define(['../services/CalendarService.js'], function () {
	return ["$scope",'$uibModal','uiCalendarConfig','CalendarService', function ($scope,$uibModal,uiCalendarConfig,calendarService) {
		var date = new Date();
	    var d = date.getDate();
	    var m = date.getMonth();
	    var y = date.getFullYear();
	    /* event sources array*/
    	$scope.eventSources = [];
        $scope.alertOnEventClick = function( date, jsEvent, view){
            console.log(date);
            //$scope.alertMessage = (date.title + ' was clicked ');
            var item = $scope.makeAppItem(date);
            $scope.openAppointmentModal(item);
        };

        $scope.makeAppItem = function(date) {
            var item = {};
            item.id = date.id;
            item.allDay=date.allDay;
            item.color=date.color;
            item.startTime=date.start.toDate();
            if(date.end == null) {
                item.endTime = item.startTime;
            } else {
                item.endTime=date.end.toDate();
            }

            item.title=date.title;
            item.description=date.description;
            item.status = date.status;
            return item;
        };

        $scope.alertOnDrop = function(event, delta, revertFunc){
            //console.log('Event Droped to make dayDelta ' + delta);
            //console.log(event);
            var item = $scope.makeAppItem(event);
            calendarService.saveAppointment(item);
            //if (!confirm("Are you sure about this change?")) {
            //    revertFunc();
            //}
        };

        /* alert on Resize */
        $scope.alertOnResize = function(event, delta, revertFunc){
            console.log('alertOnResize');

        };
        ///* Render Tooltip */
        //$scope.eventRender = function( event, element, view ) {
        //    console.log('@@@@@@@@@@@');
        //    element.attr({'tooltip': event.title,
        //        'tooltip-append-to-body': true});
        //    $compile(element)($scope);
        //};

		$scope.uiConfig = {
	      calendar:{
	        height: "100%",
	        editable: true,
	        header:{
	          left: 'today prev,next',
	          center: '',
	          right: 'month agendaWeek agendaDay'
	        },
            eventClick: $scope.alertOnEventClick,
            eventDrop: $scope.alertOnDrop
	      }
	    };

        $scope.loadAppData = function() {

            calendarService.loadAppointments().then(function(rs){
                console.log(rs);
                if(rs.success) {
                    var events = [];
                    for(var i = 0; i < rs.data.length; i++){
                        var tempData = rs.data[i];
                        var tempEvent = {
                            title:tempData.title,
                            start:new Date(tempData.startTime),
                            color :tempData.color,
                            allDay: tempData.allDay,
                            id: tempData.id,
                            description: tempData.description,
                            status: tempData.status
                        };
                        if(tempData.endTime){
                            tempEvent.end = new Date(tempData.endTime);
                        }
                        events.push(tempEvent);
                    }
                    $scope.eventSources.pop();
                    $scope.eventSources.push(events);
                }
            });
        };

        $scope.loadAppData();




        //event color choose data
        $scope.newEvent = {color:'#0073b7'};

        $scope.colorList = [{style:'text-aqua',color:'#00c0ef'}
            ,{style:'text-blue',color:'#0073b7'}
            ,{style:'text-light-blue',color:'#3c8dbc'}
            ,{style:'text-teal',color:'#39cccc'}
            ,{style:'text-yellow',color:'#f39c12'}
            ,{style:'text-orange',color:'#ff851b'}
            ,{style:'text-green',color:'#00a65a'}
            ,{style:'text-lime',color:'#01ff70'}
            ,{style:'text-red',color:'#dd4b39'}
            ,{style:'text-purple',color:'#605ca8'}
            ,{style:'text-fuchsia',color:'#f012be'}
            ,{style:'text-muted',color:'#777'},
            {style:'text-navy',color:'#001f3f'}];
        $scope.chooseColor = function(item) {
            $scope.newEvent.color = item.color;
        }

        //add event

        //create event
        // properties
        $scope.format = "MM/dd/yyyy";
        $scope.dateOptions = {
            maxDate: new Date(2029, 12, 31),
            minDate: new Date(),
            startingDay: 1
        };
        $scope.openAppointmentModal=function(item){
            $scope.appInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'appCreator.html',
                controller: appModalController,
                resolve: {
                    info: function () {
                        return $scope.appInfo;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                console.log(item);
                if(item.opt == 'save') {
                    calendarService.addActivityInfo(item.info);
                    calendarService.saveAppointment(item.info).then(function(rs){
                        $scope.loadAppData();
                    });
                } else {
                    calendarService.removeAppointment(item.info.id).then(function(rs){
                        $scope.loadAppData();
                    });
                }

            });
        };

        var appModalController = function($scope, $uibModalInstance, info){
            $scope.info = info;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info,opt:'save'});
            };

            $scope.remove=function(){
                //validate
                $uibModalInstance.close({info:$scope.info,opt:'delete'});
            };

        };

	}];
});
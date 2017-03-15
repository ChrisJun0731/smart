define(['../services/ContactService.js'], function () {
	
	// controller
	return ["$scope",'ContactService', '$uibModal', 'FileUploader',function ($scope,contactService,$uibModal,FileUploader) {
		
		// properties

        contactService.loadContacts().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.contactList = rs.data;
            }
        });

        $scope.getSortedList = function(orderName){
            var collectionName = "contact";
            var order;

            if(orderName=='lastName'){
                $scope.nameOrder = ($scope.nameOrder=='asc')?'desc':'asc';
                order = $scope.nameOrder;
            }
            else if(orderName=='createDate'){
                $scope.createOrder = ($scope.createOrder=='asc')?'desc':'asc';
                order = $scope.createOrder;
            }
            else if(orderName=='connectDate'){
                $scope.conOrder = ($scope.conOrder=='asc')?'desc':'asc';
                order = $scope.conOrder;
            }
            contactService.getSortedList({params:{orderName:orderName,order:order,collection:collectionName}}).then(function(rs){
                console.log(rs);
                if(rs.success){
                    $scope.contactList = rs.data;
                }
            });
        };

        //create contact
        $scope.openContactModal=function(item){
            $scope.contactInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'contactCreator.html',
                controller: contactModalController,
                resolve: {
                    info: function () {
                        return $scope.contactInfo;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                console.log(item);

                contactService.saveContact(item.info).then(function(rs){
                    return contactService.loadContacts();
                }).then(function(rs){
                    if(rs.success) {
                        $scope.contactList = rs.data;
                    }
                });
            });
        };

        var contactModalController = function($scope, $filter, $uibModalInstance, info){
            //add by zj
            $scope.privileges = ["public","private","team","company"];
            $scope.info = info;
            if(info.visibleList == undefined){
                info.visibleList = [];
                info.visibleList[0] = {};
                info.visibleList[0].visType = "public";
            }
            if($scope.info.salesMap == null) {
                $scope.info.salesMap = [];
            };

            //add by zj
            $scope.openFieldModal = function(){
                var modalInstance = $uibModal.open({
                    size: "lg",
                    controller: FormFieldController,
                    templateUrl: "formField.html",
                    resolve: {
                        customFields: function(){
                            return $scope.info.customFields;
                        }
                    }
                });

                modalInstance.result.then(function(item){
                    $scope.info.customFields = item.customFields;
                });

            };

            var FormFieldController = function($scope, $uibModalInstance, customFields){

                //初始页面，若info是从Add Contact页面传入，则customFields是未定义。
                //若info页面是从Edit Contact页面，则customFields是存在的。
                if(customFields == undefined){
                    $scope.newCustomFields = [{fieldName:"",fieldValue:""}];
                }
                else{
                    $scope.newCustomFields = customFields;
                }

                //增加一行Field
                $scope.addField = function(){
                    $scope.newCustomFields.push({fieldName:"",fieldValue:""});
                };

                //删除一行Field
                $scope.removeField = function(index){
                    $scope.newCustomFields.splice(index,1);
                }

                $scope.saveFields = function(){
                    var retFields = [];
                    angular.forEach($scope.newCustomFields,function(field,index){
                        field.fieldName = field.fieldName.replace(/(^\s*)|(\s*$)/g,"");
                        if(field.fieldName.length!=0){
                            retFields.push(field);
                        };
                    })
                    $uibModalInstance.close({customFields:retFields});
                };

                $scope.cancel = function(){
                    $uibModalInstance.dismiss("cancel");
                }
            }

            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            };

            //sales mapping
            $scope.statuses = [
                {value: 'MX', text: 'marketaxess'},
                {value: 'ALLQ', text: 'bloomberg'}
            ];

            //$scope.groups = [];
            //$scope.loadGroups = function() {
            //    return $scope.groups.length ? null : $http.get('/groups').success(function(data) {
            //        $scope.groups = data;
            //    });
            //};
            //
            //$scope.showGroup = function(user) {
            //    if(user.group && $scope.groups.length) {
            //        var selected = $filter('filter')($scope.groups, {id: user.group});
            //        return selected.length ? selected[0].text : 'Not set';
            //    } else {
            //        return user.groupName || 'Not set';
            //    }
            //};

            $scope.showStatus = function(map) {
                var selected = [];
                if(map.source) {
                    selected = $filter('filter')($scope.statuses, {value: map.source});
                }
                return selected.length ? selected[0].text : 'Not set';
            };

            $scope.checkName = function(data, id) {
                //if (id === 2 && data !== 'awesome') {
                //    return "Username 2 should be `awesome`";
                //}
            };

            $scope.saveMap = function(data, id) {
                //$scope.user not updated yet
                return data;
            };

            // remove user
            $scope.removeMap = function(index) {
                $scope.info.salesMap.splice(index, 1);
            };

            // add user
            $scope.addMap = function() {
                $scope.inserted = {
                    id: '',
                    source: null
                };
                $scope.info.salesMap.push($scope.inserted);
            };
        };


        $scope.editContact = function(item) {
            contactService.loadContact({params:{id:item.id}}).then(function(rs){
                if(rs.success) {
                    $scope.openContactModal(rs.data);
                }
            });
        };

        $scope.inviteContact = function(item) {

            $scope.openInviteModal(item);

        };

        //invite contact
        $scope.openInviteModal=function(item){
            $scope.contactInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'contactInvite.html',
                controller: contactInviteModalController,
                resolve: {
                    info: function () {
                        return $scope.contactInfo;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                console.log(item);
                contactService.inviteContact(item.info).then(function(rs){

                    if(rs.success) {
                        //TODO add notify dialog
                    }
                });
            });
        };

        var contactInviteModalController = function($scope, $filter, $uibModalInstance, info){
            $scope.info = info;

            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            }
        };

        //contact upload
        $scope.openContactUploadModal=function(size){
            var modalInstance = $uibModal.open({
                templateUrl: 'fileUpload.html',
                controller: uploadContactModalController,
                size: size
            });

            modalInstance.result.then(function (item) {


            });
        };
        var uploadContactUrl = '/contact/upload?privilege=';
        var uploaderContact = new FileUploader({
            url:uploadContactUrl
        });
        // FILTERS
        uploaderContact.filters.push({
            name: 'customFilter',
            fn: function() {
                return this.queue.length < 10;
            }
        });

        uploaderContact.onCompleteAll = function() {
            console.info('onCompleteAll');
            contactService.loadContacts().then(function(rs){
                console.log(rs);
                if(rs.success) {
                    $scope.contactList = rs.data;
                }
            });
        };

        var uploadContactModalController = function($scope, $uibModalInstance){

            // properties
            $scope.privilege = "public";
            $scope.privileges = ["public","private","team","company"];
            $scope.uploader = uploaderContact;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.$watch("privilege",function(newVal){
                uploaderContact.url = uploadContactUrl + newVal;
            });
        };

        //mapping upload
        $scope.openMappingUploadModal=function(size){
            var modalInstance = $uibModal.open({
                templateUrl: 'fileUpload.html',
                controller: uploadMappingModalController,
                size: size
            });

            modalInstance.result.then(function (item) {

            });
        };
        var uploadMapUrl = '/contact/map-upload';
        var uploaderMapping = new FileUploader({
            url:uploadMapUrl
        });
        // FILTERS
        uploaderMapping.filters.push({
            name: 'customFilter',
            fn: function() {
                return this.queue.length < 10;
            }
        });

        uploaderMapping.onCompleteAll = function() {
            console.info('onCompleteAll');
            contactService.loadContacts().then(function(rs){
                console.log(rs);
                if(rs.success) {
                    $scope.contactList = rs.data;
                }
            });
        };

        var uploadMappingModalController = function($scope, $uibModalInstance){
            // properties
            $scope.uploader = uploaderMapping;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };
        };

        //load more contacts add by zj 03/08
        $scope.loadMore = function(initial, num){
            contactService.loadMoreContacts({params:{initial:initial,contactNum:num}}).then(function(rs){
                if(rs.success){
                    angular.forEach($scope.contactList, function(contact, index){
                        if(contact.id == rs.data.id){
                            $scope.contactList[index] = rs.data;
                        }
                    });
                }
            })
        };
    }];
});
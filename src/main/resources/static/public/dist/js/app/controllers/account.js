define(['../services/AccountService.js'], function () {
	
	// controller
	return ["$scope",'AccountService', '$uibModal','FileUploader', function ($scope,accountService,$uibModal,FileUploader) {
		
		// properties
        $scope.accountList = [];
        $scope.companyList = [];

        accountService.loadAccounts().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.accountList = rs.data;
            }
        });

        accountService.loadCompanys().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.companyList = rs.data;
            }
        });

        $scope.getSortedList = function(orderName){
            var collectionName = "companyInfo";
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
            accountService.getSortedList({params:{orderName:orderName,order:order,collection:collectionName}}).then(function(rs){

            });
        };

        //create company
        $scope.openCompanyModal=function(item){
            $scope.companyInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'companyCreator.html',
                controller: companyModalController,
                resolve: {
                    info: function () {
                        return $scope.companyInfo;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                console.log(item);
                accountService.createCompany(item.info).then(function(rs){

                    return accountService.loadCompanys();
                }).then(function(rs){
                    if(rs.success) {
                        $scope.companyList = rs.data;
                    }
                });
            });
        };

        var companyModalController = function($scope, $uibModalInstance, info){
            $scope.info = info;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            }
        };

        $scope.editCompany = function(item) {
            accountService.loadCompany({params:{id:item.id}}).then(function(rs){
                if(rs.success) {
                    $scope.openCompanyModal(rs.data);
                }
            });
        };

        //create account
        $scope.openAccountModal=function(item){
            $scope.accountInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'accountCreator.html',
                controller: accountModalController,
                resolve: {
                    info: function () {
                        return $scope.accountInfo;
                    }
                }
            });


            modalInstance.result.then(function (item) {
                console.log(item);
                accountService.saveAccount(item.info).then(function(rs){

                    return accountService.loadAccounts();
                }).then(function(rs){
                    if(rs.success) {
                        $scope.accountList = rs.data;
                    }
                });

            });
        };

        var accountModalController = function($scope, $filter, $uibModalInstance, info){
            $scope.info = info;
            if($scope.info.accountMap == null) {
                $scope.info.accountMap = [];
            }
            $scope.cancel=function(){
                console.log($scope.info.accountMap);
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            };

            $scope.loadCompany = function($query) {
                return accountService.loadCompanyNameList($query);
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
                $scope.info.accountMap.splice(index, 1);
            };

            // add user
            $scope.addMap = function() {
                $scope.inserted = {
                    id: '',
                    source: null
                };
                $scope.info.accountMap.push($scope.inserted);
            };
        };

        $scope.editAccount = function(item) {
            console.log(item);
            accountService.loadAccount({params:{id:item.id}}).then(function(rs){
                if(rs.success) {
                    $scope.openAccountModal(rs.data);
                }
            });
        };


        //company file uploader
        $scope.openUploadModal=function(size){
            var modalInstance = $uibModal.open({
                templateUrl: 'fileUpload.html',
                controller: uploadModalController,
                size: size
            });

            modalInstance.result.then(function (item) {


            });
        };
        var uploadUrl = '/company/upload';
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
            accountService.loadCompanys().then(function(rs){
                console.log(rs);
                if(rs.success) {
                    $scope.companyList = rs.data;
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

        //account upload
        $scope.openAccountUploadModal=function(size){
            var modalInstance = $uibModal.open({
                templateUrl: 'fileUpload.html',
                controller: uploadAccountModalController,
                size: size
            });

            modalInstance.result.then(function (item) {


            });
        };
        var uploadAccountUrl = '/account/upload';
        var uploaderAccount = new FileUploader({
            url:uploadAccountUrl
        });
        // FILTERS
        uploaderAccount.filters.push({
            name: 'customFilter',
            fn: function() {
                return this.queue.length < 10;
            }
        });

        uploaderAccount.onCompleteAll = function() {
            console.info('onCompleteAll');
            accountService.loadAccounts().then(function(rs){
                console.log(rs);
                if(rs.success) {
                    $scope.accountList = rs.data;
                }
            });
        };

        var uploadAccountModalController = function($scope, $uibModalInstance){
            // properties
            $scope.uploader = uploaderAccount;

            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

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
        var uploadMapUrl = '/account/map-upload';
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
            accountService.loadAccounts().then(function(rs){
                console.log(rs);
                if(rs.success) {
                    $scope.accountList = rs.data;
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

        //load more accounts add by zj 17/03/24
        $scope.loadMoreAccounts = function(initial, size){
            var config = {params:{initial:initial, size: size}};
            accountService.loadMoreAccounts(config).then(function(rs){
                if(rs.success){
                    var newAccount = rs.data;
                    angular.forEach($scope.accountList, function(account, index){
                        if(account.id == newAccount.id){
                            $scope.accountList[index] = newAccount;
                        }
                    });
                }
            });
        };

        $scope.loadMoreCompany = function(initial, size){
            var config = {params:{initial:initial, size:size}};
            accountService.loadMoreCompany(config).then(function(rs){
                if(rs.success){
                    var newCompany = rs.data;
                    angular.forEach($scope.companyList, function(company, index){
                        if(company.id == newCompany.id){
                            $scope.companyList[index] = newCompany;
                        }
                    });
                }
            });
        }

    }];
});
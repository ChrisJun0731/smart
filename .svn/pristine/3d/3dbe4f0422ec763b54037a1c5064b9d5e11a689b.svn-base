define(['angular','../services/MatchService.js','../filter/FileSizeFilter.js'], function (angular) {
	
	// controller
	return ["$scope",'MatchService', '$uibModal','FileUploader', function ($scope,matchService,$uibModal,FileUploader) {

        //add by zj
        //uib-collapse初始值
        $scope.isFilterCollapsed = true;
        $scope.isSortCollapsed = true;

        $scope.allSelected = false;

        $scope.allList = null;

        $scope.start=0;
        $scope.end=0;
        $scope.size=0;
        $scope.saleList = [];



        matchService.loadMatchList().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.saleList = rs.data;
            }
        });

        //create sle
        $scope.openSaleModal=function(item, size){
            $scope.saleInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'saleCreator.html',
                controller: saleModalController,
                size: size,
                resolve: {
                    info: function () {
                        return $scope.saleInfo;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                console.log(item);
                matchService.saveMatch(item.info).then(function(rs){

                    return matchService.loadMatchList();
                }).then(function(rs){
                    if(rs.success) {
                        $scope.saleList = rs.data;
                        console.log(rs);
                    }
                });
            });
        };

        var saleModalController = function($scope, $uibModalInstance, info, $filter){
            //base control config

            $scope.info = info;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            }

            //sales mapping
            $scope.sides = [
                {value: 'B', text: 'BUY'},
                {value: 'S', text: 'SELL'}
            ];
            if($scope.info.salesMap == null) {
                $scope.info.salesMap = [];
            }
            $scope.showSides = function(map) {
                var selected = [];
                if(map.side) {
                    selected = $filter('filter')($scope.sides, {value: map.side});
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
                $scope.inserted = {};
                $scope.info.salesMap.push($scope.inserted);
            };

            $scope.loadNames = function($query) {
                console.log($query);
                //return [];
                return matchService.loadNameList($query);
            };

            //load portfolios by email add by zj 17/03/09
            $scope.inputTypes = ['manual', 'auto'];
            $scope.customerNum = 10;
            $scope.$watch('inputType',function(inputType){
                if(inputType == 'auto'){
                    $scope.customerNum = 1;
                }
                else{
                    $scope.customerNum = 10;
                }
            })


            $scope.loadPortfolios = function(email){
                if($scope.inputType == 'auto'){
                    if($scope.info.customer.length !=1){
                        alert("In the auto pattern, the customer can only has one tag!");
                        return;
                    }
                }
                matchService.loadPortfolios({params:{email:email}}).then(function(rs){
                   if(rs.success){
                        $scope.portfolios = rs.data;
                        var modalInstance = $uibModal.open({
                            templateUrl: "portfolioModal.html",
                            controller: portfolioModalController,
                            scope: $scope
                        });
                   }
                });
            };

            //smart match function add by zj 17/03/10
            $scope.smartMatch = function(){
                var cusips = [];
                angular.forEach($scope.info.salesMap, function(map){
                    cusips.push(map.cusip);
                });

                matchService.smartMatch({params:{cusipSet:cusips}}).then(function(rs){
                    if(rs.success){
                        $scope.bestMktDatas = rs.data;
                        angular.forEach($scope.bestMktDatas, function(data){
                            angular.forEach($scope.info.salesMap, function(map){
                                if(map.cusip == data.cusip){
                                    if(map.side == 'B'){
                                        map.quantity = data.askQty;
                                        map.price = data.askPrice;
                                    }
                                    else if(map.side == 'S'){
                                        map.quantity = data.bidQty;
                                        map.price = data.bidPrice;
                                    }
                                }
                            });
                        });
                    }
                });
            }

            var portfolioModalController = function($scope, $uibModalInstance){
                $scope.allSelected = false;
                $scope.selectAll = function(){
                    $scope.allSelected = !$scope.allSelected;
                    angular.forEach($scope.portfolios, function(portfolio){
                        portfolio.selected = $scope.allSelected;
                    });
                };
                $scope.close = function(){
                    $uibModalInstance.dismiss('cancel');
                }
                $scope.loadCusip = function(){
                    var portfolioIds = [];
                    angular.forEach($scope.portfolios, function(portfolio){
                        if(portfolio.selected == true){
                            portfolioIds.push(portfolio.id);
                        }
                    })
                    matchService.loadCusips({params:{portfolioIds:portfolioIds}}).then(function(rs){
                        if(rs.success){
                            var selPortfolios = rs.data;
                            angular.forEach(selPortfolios, function(portfolio){
                                $scope.info.salesMap = $scope.info.salesMap.concat(portfolio.list);
                            })
                        }
                    });
                    $uibModalInstance.dismiss('cancel');
                }
            };

        };


        //file uploader
        $scope.openUploadModal=function(size){
            var modalInstance = $uibModal.open({
                templateUrl: 'fileUpload.html',
                controller: uploadModalController,
                size: size
            });

            modalInstance.result.then(function (item) {


            });
        };
        var uploadUrl = '/sale/upload';
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
            salesService.loadSales().then(function(rs){
                console.log(rs);
                if(rs.success) {
                    $scope.saleList = rs.data;
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

    }];
});
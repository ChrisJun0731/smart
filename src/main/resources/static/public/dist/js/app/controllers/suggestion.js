define(['angular','../services/MatchService.js','../filter/FileSizeFilter.js'], function (angular) {
	
	// controller
	return ["$scope",'MatchService', '$uibModal','FileUploader','_', function ($scope,matchService,$uibModal,FileUploader, _) {

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

        /** Filter的初始值 Add by zj 2016/12/9 **/
        $scope.fields = ['Cusip', 'Price', 'Quantity', 'Time', 'From'];

        $scope.operators = {
            Cusip: ['Is', 'Is not'],
            Price: ['Less than', 'Less than or equal to', 'equal', 'greater than or equal to', 'greater than'],
            Quantity: ['Less than', 'Less than or equal to', 'equal', 'greater than or equal to', 'greater than'],
            Time: ['Before', 'Equal', 'After'],
            From: ['Is', 'Is not']
        };

        $scope.filters = [
            {field:'Cusip', operators:$scope.operators.Cusip, operator:'Is', value:''}
        ];

        $scope.setOperator = function(field, index){
            var filter = $scope.filters[index];
            filter.operators = eval('$scope.operators.'+field);
            filter.operator = eval('$scope.operators.'+field+'[0]');
            $scope.filters[index] = filter;
        };

        $scope.addFilter = function(field, operator, value){
            var operators = eval('$scope.operators.'+field);
            $scope.filters.push({field:field, operator:operator, operators:operators, value:value});
        };

        $scope.removeFilter = function(index){
            $scope.filters.splice(index,1);
        };
        /**add by zj 16/12/9 **/

        matchService.loadMatchList().then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.saleList = rs.data;
            }
        });

        matchService.loadSuggestion({params:{filter:''}}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.suggestionList = rs.data;
                _.each($scope.suggestionList, function (item) {
                    console.log(item);
                    var arrayBid = _.filter(item.detail, function(d) {
                        return d.side == 'B';
                    });

                    var arraySell = _.filter(item.detail, function(d) {
                        return d.side == 'S';
                    });

                    var bestBidItem = _.max(arrayBid, function (d) {
                        return parseFloat(d.price);
                    });

                    var bestSellItem = _.min(arraySell, function (d) {
                        return parseFloat(d.price);
                    });

                    console.log(bestBidItem);
                    console.log(bestSellItem);
                    if (bestBidItem.side == 'B') {
                        item.bidValue = bestBidItem.price + "/" + bestBidItem.quantity;
                        item.bestBid = bestBidItem.price;
                        item.bidCreateDate = bestBidItem.createDate;
                    }

                    if (bestSellItem.side == 'S') {
                        item.sellValue = bestSellItem.price + "/" + bestSellItem.quantity;
                        item.bestSell=bestSellItem.price;
                        item.sellCreateDate = bestSellItem.createDate;
                    }
                    console.log(item);
                });
            }
        });

        $scope.openSuggestModal=function(item, size) {
            $scope.saleInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'suggestDetail.html',
                controller: sggModalController,
                size: size,
                resolve: {
                    info: function () {
                        return $scope.saleInfo;
                    }
                }
            });
            modalInstance.result.then(function (item) {
                console.log(item);

            });
        };

        var sggModalController = function($scope, $uibModalInstance, info, $filter){
            //base control config

            $scope.info = info;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            };
        };

        //open match
        $scope.openMatchModal=function(item, size){
            console.log(item);
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

        var saleModalController = function($scope, $uibModalInstance, info, $filter){
            //base control config

            $scope.info = info;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            };
        };


    }];
});
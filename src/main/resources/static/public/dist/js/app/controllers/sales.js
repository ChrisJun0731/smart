define(['angular','../services/SalesService.js','../filter/FileSizeFilter.js'], function (angular) {
	
	// controller
	return ['$scope','SalesService', '$uibModal','FileUploader',function ($scope,salesService,$uibModal,FileUploader) {
		
		// properties
//        salesService.loadSales().then(function(rs){
//            console.log(rs);
//            if(rs.success) {
//                $scope.saleList = rs.data;
//            }
//        });

        //add by zj
        $scope.currentPage = 0;
        $scope.pageSize = 50;

        var pageConfig = {params:{currentPage:$scope.currentPage,pageSize:$scope.pageSize}};
        salesService.loadPagedSales(pageConfig).then(function(rs){
            console.log(rs);
            if(rs.success){
                $scope.getCurrentPage(rs);
            }
        });

        $scope.getCurrentPage = function(rs){
                $scope.saleList = rs.data.salesPage;
                $scope.maxSize = rs.data.maxSize;
                $scope.maxPage = rs.data.maxPage;
                $scope.start = $scope.currentPage*$scope.pageSize+1;
                if($scope.pageSize*($scope.currentPage+1) > $scope.maxSize)
                    $scope.end = $scope.maxSize;
                else
                    $scope.end = $scope.pageSize * ($scope.currentPage+1);
        }

        $scope.prePage = function(){
            if($scope.currentPage > 0){
                $scope.currentPage--;
                pageConfig = {params:{currentPage:$scope.currentPage, pageSize:$scope.pageSize, match:$scope.searchStr}};
                if($scope.searchMode == false || $scope.searchMode == undefined){
                    salesService.loadPagedSales(pageConfig).then(function(rs){
                        $scope.getCurrentPage(rs);
                    });
                }
                else{
                    salesService.searchSales(pageConfig).then(function(rs){
                        $scope.getCurrentPage(rs);
                    });
                }
            }
        };

        $scope.nextPage = function(){
            if($scope.currentPage < $scope.maxPage){
                $scope.currentPage++;
                if($scope.searchMode == false || $scope.searchMode == undefined){
                    pageConfig = {params:{currentPage:$scope.currentPage,pageSize:$scope.pageSize}};
                    salesService.loadPagedSales(pageConfig).then(function(rs){
                        $scope.getCurrentPage(rs);
                    });
                }
                else{
                    pageConfig = {params:{currentPage: $scope.currentPage, pageSize: $scope.pageSize, match: $scope.searchStr}};
                    salesService.searchSales(pageConfig).then(function(rs){
                        $scope.getCurrentPage(rs);
                    });
                }
            };
        };

        $scope.refresh = function(){
            pageConfig = {params:{currentPage:$scope.currentPage,pageSize:$scope.pageSize}};
            salesService.loadPagedSales(pageConfig).then(function(rs){
                $scope.getCurrentPage(rs);
            });
        };

        $scope.selectAllSales = function(){
            $scope.saleSelected =!$scope.saleSelected;
            angular.forEach($scope.saleList,function(sale){
                sale.selected = $scope.saleSelected;
            });
        }
        $scope.downloadSelInfo = function(){
            var header = "Info,Price,Qty,TradeTime\n";
            var content = "";
            angular.forEach($scope.saleList,function(sale){
                if(sale.checked == true)
                    content += sale.secId + "," + sale.netPrice + "," + sale.quantity + "," + sale.tradeDate + "\n";
            });
            var file = header + content;
            file = encodeURIComponent(file);
            $scope.downloadHref = "data:text/csv;charset=utf-8,\ufeff"+file;
        };

        $scope.getSortedList = function(orderName){
            var collection = "saleInfo";
            var order;
            if(orderName == 'secId'){
                $scope.idOrder = ($scope.idOrder=='asc')?'desc':'asc';
                order = $scope.idOrder;
            }
            else if(orderName == 'settleDate'){
                $scope.dateOrder = ($scope.dateOrder)=='asc'?'desc':'asc';
                order = $scope.dateOrder;
            }
            else if(orderName == 'quantity'){
                $scope.qtyOrder = ($scope.qtyOrder=='asc')?'desc':'asc';
                order = $scope.qtyOrder;
            }
            else if(orderName == 'netPrice'){
                $scope.priceOrder = ($scope.priceOrder=='asc')?'desc':'asc';
                order = $scope.priceOrder;
            }
            var sortConfig = {params:{currentPage:$scope.currentPage, pageSize:$scope.pageSize, orderName:orderName, order:order, collection:collection}};
            salesService.getSortedList(sortConfig).then(function(rs){
                $scope.saleList = rs.data;
            });
        };

        //create sle
        $scope.openSaleModal=function(item){
            $scope.saleInfo = item;
            var modalInstance = $uibModal.open({
                templateUrl: 'saleCreator.html',
                controller: saleModalController,
                resolve: {
                    info: function () {
                        return $scope.saleInfo;
                    }
                }
            });

            modalInstance.result.then(function (item) {
                console.log(item);
                salesService.saveSale(item.info).then(function(rs){

                    return salesService.loadSales();
                }).then(function(rs){
                    if(rs.success) {
                        $scope.saleList = rs.data;
                    }
                });
            });
        };

        var saleModalController = function($scope, $uibModalInstance, info){
            //base control config
            $scope.dateOptions = {
                formatYear: 'yy',
                startingDay: 1
            };

            $scope.sources = [
                {value: 'MX', text: 'marketaxess'},
                {value: 'ALLQ', text: 'bloomberg'}
            ];

            $scope.open2 = function() {
                $scope.popup2.opened = true;
            };
            $scope.popup2 = {
                opened: false
            };

            $scope.open1 = function() {
                $scope.popup1.opened = true;
            };
            $scope.popup1 = {
                opened: false
            };

            $scope.info = info;
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({info:$scope.info});
            }
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

        $scope.search = function($event){
            if($event.keyCode == 13){
                if($scope.match != undefined){
                    $scope.searchMode = true;
                    $scope.searchStr = $scope.match;
                    $scope.currentPage = 0;
                }
                else{
                    $scope.searchMode = false;
                }
                if($scope.searchMode == true){
                    var config = {params:{currentPage:$scope.currentPage, pageSize:$scope.pageSize, match:$scope.searchStr}};
                    salesService.searchSales(config).then(function(rs){
                        $scope.getCurrentPage(rs);
                    });
                }
                else if($scope.searchMode == false){
                    salesService.loadPagedSales({params:{currentPage:$scope.currentPage, pageSize:$scope.pageSize}}).then(function(rs){
                        console.log(rs);
                        if(rs.success){
                            $scope.getCurrentPage(rs);
                        }
                    });
                }
            }
        };

    }];
});
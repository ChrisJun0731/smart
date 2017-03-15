define(['../services/BoardService.js','../services/AccountService.js'], function () {
	
	// controller
	return ["$scope", '$state','AccountService','BoardService','$uibModal', 'FileUploader', function ($scope, $state,accountService, boardService, $uibModal, FileUploader) {
		
		// properties
        boardService.loadAccountBoardInfo({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.board = rs.data;
            }
            return boardService.getActivity({params:{id:$scope.board.id}});
        }).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.messageList = rs.data;
            }
            return boardService.loadTopFile({params:{id:$scope.board.id}});
        }).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.fileList = rs.data;
            }
        });

        accountService.loadAccount({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.accountInfo = rs.data;
            }
        });

        //add by zj 16/12/15 start
        accountService.loadPortfolio({params:$state.params}).then(function(rs){
            if(rs.success==true){
                if(rs.data != null)
                    $scope.portfolios = rs.data.reverse();
                else
                    $scope.portfolios = [];
            }
        });
        //end

        $scope.salesList = [];

        /** add by zj 16/12/13 start **/
        $scope.openPortfolioModal = function(){
            var modalInstance = $uibModal.open({
                templateUrl: 'portfolioModal.html',
                controller: portfolioModalController
            });

            modalInstance.result.then(function(portfolios ){
                $scope.portfolios = portfolios;
            });
        };

        var portfolioModalController = function($scope, $uibModalInstance){
            $scope.portfolio = {list:[{cusip:'', quantity:'', price:''}]};
            $scope.delPortfolio = function(index){
                $scope.portfolio.list.splice(index,1);
            };
            $scope.addPortfolio = function(){
                $scope.portfolio.list.push({cusip:'', quantity:'', price:''});
            };
            $scope.save = function(isValid){
                if(isValid){
                    $scope.portfolio.accountId = $state.params.id;
                    accountService.addPortfolio($scope.portfolio)
                        .then(function(){
                            return accountService.loadPortfolio({params:$state.params});
                        })
                        .then(function(rs){
                            if(rs.success==true){
                                portfolios = rs.data;
                                $uibModalInstance.close(portfolios.reverse());
                            }
                        });
                }
            };

            $scope.cancel = function(){
                $uibModalInstance.dismiss("cancel");
            };
        }


        $scope.importPortfolio = function(){
            var modalInstance = $uibModal.open({
                templateUrl: 'fileUpload.html',
                controller: fileUploadController,
                size: 'lg'
            });

            fileUploader.onCompleteAll = function(){
                accountService.loadPortfolio({params:$state.params}).then(function(rs){
                if(rs.success==true){
                    $scope.portfolios = rs.data.reverse();
                    }
                });
            }
        };
        var uploaderUrl =  '/account/uploadPortfolio?id='+$state.params.id+'&title=';
        var fileUploader = new FileUploader({url: uploaderUrl});

        var fileUploadController = function($scope, $uibModalInstance){
            $scope.uploader = fileUploader;
            $scope.cancel = function(){
                $uibModalInstance.dismiss("cancel");
            }
            $scope.$watch("title",function(newVal){
                fileUploader.url = uploaderUrl + newVal;
            })
        }
        /** add by zj 16/12/13 end **/

        /** add by zj 16/12/15 start **/
        $scope.showPortfolios = function(index){
            var modalInstance = $uibModal.open({
                templateUrl: 'portfolioDetail.html',
                controller: portfoliosDetailController,
                size: 'lg',
                resolve:{index:index, portfolio:$scope.portfolios[index]}
            });
            modalInstance.result.then(function(portfolios){
                $scope.portfolios = portfolios;
            });
        }
        var portfoliosDetailController = function($scope, index, portfolio, $uibModalInstance){
            $scope.portfolio = portfolio;
            //记录初始的list
            var originalList = new Array();
            originalList = originalList.concat($scope.portfolio.list);
            $scope.delPortfolio = function(ind){
                $scope.portfolio.list.splice(ind,1);
            };
            $scope.addPortfolio = function(){
                $scope.portfolio.list.push({cusip:'', quantity:'', price:''});
            };
            $scope.updatePortfolio = function($valid){
                if($valid){
                    var data = $scope.portfolio;
                    accountService.updatePortfolio(data)
                        .then(function(rs){
                            return accountService.loadPortfolio({params:$state.params})
                        })
                        .then(function(rs){
                            if(rs.success==true){
                                $scope.portfolios = rs.data.reverse();
                                $uibModalInstance.close($scope.portfolios);
                            }
                        });
                }
            }
            $scope.cancel = function(){
                $scope.portfolio.list = originalList;
                $uibModalInstance.dismiss("cancel");
            };
        }
        /** add by zj 16/12/15 end **/

    }];
});
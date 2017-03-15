define(['angular','../services/DiskService.js','../filter/FileSizeFilter.js'], function (angular) {
	
	// controller
	return ["$scope", '$state','DiskService','$uibModal','FileUploader', function ($scope, $state, diskService, $uibModal, FileUploader) {
		

        $scope.fileList = [];
        $scope.root = $state.params.root;
        $scope.path = $state.params.path;
        $scope.boardId = $state.params.id;
        $scope.pathList = diskService.getLocalPath($state.params);
        if($scope.pathList.length == 0) {
            diskService.goPath({params:$state.params}).then(function(rs){
                console.log(rs);
                if(rs.success) {
                    diskService.setLocalPath(rs.data);
                    $scope.pathList = diskService.getLocalPath($state.params);
                }
            });
        }

        diskService.loadInfo({params:$state.params}).then(function(rs){
            console.log(rs);
            if(rs.success) {
                $scope.fileList = rs.data;
            }
        });

        //toolbar
        $scope.refresh = function(){
            diskService.loadInfo({params:$state.params}).then(function(rs){
                if(rs.success) {
                    $scope.fileList = rs.data;
                    $scope.selectedAll = false;
                }
            });
        }
        $scope.selectedAll = false;

        $scope.checkAll = function() {
            if(!$scope.selectedAll) {
                $scope.selectedAll = true;
            } else {
                $scope.selectedAll = false;
            }
            angular.forEach($scope.fileList, function(item){
                item.Selected = $scope.selectedAll;
            });
        };

        $scope.deleteBatch = function(){
            var deleteFiles = $scope.fileList.filter(function(file){
                return file.Selected;
            });
            console.log(deleteFiles);
            var postData = [];
            angular.forEach(deleteFiles, function(file){
                postData.push({id:file.id});
            });

            diskService.remove(postData).then(function(rs){
                console.log(rs);
                return diskService.loadInfo({params:$state.params});
            }).then(function(rs){
                if(rs.success) {
                    $scope.fileList = rs.data;
                    $scope.selectedAll = false;
                }
            });
        }

        //create folder
        $scope.openFolderModal=function(size){
            var modalInstance = $uibModal.open({
                templateUrl: 'folderCreator.html',
                controller: folderModalController,
                size: size
            });

            modalInstance.result.then(function (item) {

                item = angular.extend(item,{boardId:$scope.boardId,root:$scope.root});
                console.log(item);
                diskService.addFolder(item).then(function(rs){
                    return diskService.loadInfo({params:$state.params});
                }).then(function(rs){
                    if(rs.success) {
                        $scope.fileList = rs.data;
                    }
                });
            });
        };

        var folderModalController = function($scope, $uibModalInstance){
            $scope.radioModel = 'public';
            $scope.folderName = '';
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({status:$scope.radioModel,foldName:$scope.folderName});
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
        var uploadUrl = '/disk/upload?id='+$scope.boardId;
        if($scope.root) {
            uploadUrl +='&root='+$scope.root;
        }
        uploadUrl+='&status=';
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
            diskService.loadInfo({params:$state.params}).then(function(rs){
                if(rs.success) {
                    $scope.fileList = rs.data;
                }
            });
        };

        var uploadModalController = function($scope, $uibModalInstance){
            // properties
            $scope.uploader = uploader;
            $scope.radioModel = 'public';

            $scope.$watch('radioModel', function(newValue) {
                uploader.url = uploadUrl+newValue;

            });
            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

        };



	}];	
});
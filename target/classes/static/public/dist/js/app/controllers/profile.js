define(['../services/UserService.js'], function () {

    // controller
    return ["$scope",'$uibModal','FileUploader','UserService', function ($scope,$uibModal, FileUploader, userService) {

        // properties
        $scope.myCroppedImage='';
        $scope.userInfo = {};
        userService.async().then(function(rs){
            if(rs.success) {
                $scope.userInfo = rs.data;
                console.log($scope.userInfo);
            }

        });
        $scope.openImgModal = function(size) {
            console.log("open img");
            var modalInstance = $uibModal.open({
                templateUrl: 'imgCreator.html',
                controller: imgModalController,
                size: size
            });

            modalInstance.result.then(function (item) {

                $scope.userInfo.photo = item.croppedImage;
            });
        };

        $scope.saveProfile = function() {
            userService.saveProfile($scope.userInfo).then(function(rs){
                console.log(rs);
               if(rs.success){
                   //TODO alert success
                   userService.alertLoadUser();
               }
            });
        };

        var imgModalController = function($scope, $uibModalInstance){
            $scope.myImage='';
            $scope.croppedImage='';
            var uploader = $scope.uploader = new FileUploader();

            // FILTERS

            uploader.filters.push({
                name: 'imageFilter',
                fn: function(item /*{File|FileLikeObject}*/, options) {
                    //TODO size filter
                    var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                    return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
                }
            });

            var reader = new FileReader();
            reader.onload = function (evt) {
                $scope.$apply(function($scope){
                    $scope.myImage=evt.target.result;
                });
            };

            uploader.onAfterAddingFile = function(fileItem) {
                console.info('onAfterAddingFile', fileItem);
                reader.readAsDataURL(fileItem._file);
            };

            $scope.cancel=function(){
                $uibModalInstance.dismiss('cancel');
            };

            $scope.ok=function(){
                //validate
                $uibModalInstance.close({croppedImage:$scope.croppedImage});
            }
        };
    }];
});
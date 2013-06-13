

angular.module('logbook').controller('EditServerController', function($scope, $routeParams, $location, ServerResource ) {
    var self = this;
    $scope.disabled = false;

    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.server = new ServerResource(self.original);
        };
        var errorCallback = function() {
            $location.path("/Servers");
        };
        ServerResource.get({ServerId:$routeParams.ServerId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.server);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.server.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Servers");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Servers");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.server.$remove(successCallback, errorCallback);
    };
    
    
    $scope.get();
});
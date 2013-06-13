
angular.module('logbook').controller('NewServerController', function ($scope, $location, locationParser, ServerResource ) {
    $scope.disabled = false;
    $scope.server = $scope.server || {};
    

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Servers/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ServerResource.save($scope.server, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Servers");
    };
});
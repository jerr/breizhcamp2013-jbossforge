
angular.module('logbook').controller('NewApplicationController', function ($scope, $location, locationParser, ApplicationResource , ServerResource) {
    $scope.disabled = false;
    $scope.application = $scope.application || {};
    
    $scope.serversList = ServerResource.queryAll(function(items){
        $scope.serversSelectionList = $.map(items, function(item) {
            return ( {
                value : item,
                text : item.name
            });
        });
    });
    
    $scope.$watch("serversSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.application.servers = [];
            $.each(selection, function(idx,selectedItem) {
                $scope.application.servers.push(selectedItem.value);
            });
        }
    });

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Applications/edit/' + id);
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        ApplicationResource.save($scope.application, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Applications");
    };
});
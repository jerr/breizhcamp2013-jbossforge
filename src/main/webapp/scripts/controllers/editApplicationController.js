

angular.module('logbook').controller('EditApplicationController', function($scope, $routeParams, $location, ApplicationResource , ServerResource) {
    var self = this;
    $scope.disabled = false;

    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.application = new ApplicationResource(self.original);
            ServerResource.queryAll(function(items) {
                $scope.serversSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        value : item,
                        text : item.name
                    };
                    if($scope.application.servers){
                        $.each($scope.application.servers, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.serversSelection.push(wrappedObject);
                            }
                        });
                    }
                    return wrappedObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Applications");
        };
        ApplicationResource.get({ApplicationId:$routeParams.ApplicationId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.application);
    };

    $scope.save = function() {
        var successCallback = function(){
            $scope.get();
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.application.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Applications");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Applications");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.application.$remove(successCallback, errorCallback);
    };
    
    $scope.serversSelection = $scope.serversSelection || [];
    $scope.$watch("serversSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.application) {
            $scope.application.servers = [];
            $.each(selection, function(idx,selectedItem) {
                $scope.application.servers.push(selectedItem.value);
            });
        }
    });
    
    $scope.get();
});
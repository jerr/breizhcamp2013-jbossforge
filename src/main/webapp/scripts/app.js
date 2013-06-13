'use strict';

angular.module('logbook',['ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/Applications',{templateUrl:'views/Application/search.html',controller:'SearchApplicationController'})
      .when('/Applications/new',{templateUrl:'views/Application/detail.html',controller:'NewApplicationController'})
      .when('/Applications/edit/:ApplicationId',{templateUrl:'views/Application/detail.html',controller:'EditApplicationController'})
      .when('/Events',{templateUrl:'views/Event/search.html',controller:'SearchEventController'})
      .when('/Events/new',{templateUrl:'views/Event/detail.html',controller:'NewEventController'})
      .when('/Events/edit/:EventId',{templateUrl:'views/Event/detail.html',controller:'EditEventController'})
      .when('/Servers',{templateUrl:'views/Server/search.html',controller:'SearchServerController'})
      .when('/Servers/new',{templateUrl:'views/Server/detail.html',controller:'NewServerController'})
      .when('/Servers/edit/:ServerId',{templateUrl:'views/Server/detail.html',controller:'EditServerController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });

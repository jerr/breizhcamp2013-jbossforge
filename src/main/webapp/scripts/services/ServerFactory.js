angular.module('logbook').factory('ServerResource', function($resource){
    var resource = $resource('rest/servers/:ServerId',{ServerId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});
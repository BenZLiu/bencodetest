var benTestApp = angular.module('benTestApp', ['ngRoute']);

benTestApp.config(function($routeProvider) {
    $routeProvider
        // route for the about page
        .when('/crawling', {
            templateUrl : 'view/crawling.html',
            controller  : 'crawController'
        })

        // route for the contact page
        .when('/searching', {
            templateUrl : 'view/search.html',
            controller  : 'searchController'
        })
});
benTestApp.controller('mainController',['$scope',function($scope){
	$scope.init = function(){
		$scope.isLoading=false;
	};
	
	$scope.setLoading=function(status){
		$scope.isLoading=status;
	};
}]);

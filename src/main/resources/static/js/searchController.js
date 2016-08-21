benTestApp.controller('searchController',['$scope' ,'$http',function($scope,$http){
	$scope.init = function(){
//		console.log("init...");
		$scope.searchInput= "";
		$scope.newsList =[];
	};
	
	$scope.search = function(){
		$scope.setLoading(true);
		$http.get("/search/searchInput/"+$scope.searchInput).then(function(response){
			$scope.setLoading(false);
			$scope.newsList = response.data;
			console.log(response);
		});
	};
}])
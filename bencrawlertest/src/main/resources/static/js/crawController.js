benTestApp.controller('crawController',['$scope', '$http' ,function($scope,$http){
	$scope.init = function(){
		$scope.url = "";
		$scope.result = "";
		$scope.crawlerMessage = "";
		$scope.showMsgFlag = false;
	};
	
	$scope.crawl = function(){
		$scope.setLoading(true);
		$scope.showMsgFlag=false;
		$scope.submitUrl = window.encodeURIComponent($scope.url);
		
		$http.post("/crawling/url/",$scope.submitUrl).then(function(response){
			$scope.setLoading(false);
			$scope.crawlerMessage = "";
			console.log(response);
			$scope.result = response.data.result;
			if("SUCCESS"==response.data.result){
				$scope.crawlerMessage = "The crawling is finised";
				$scope.showMsgFlag=true;
			} else {
				$scope.crawlerMessage = "Failed to crawling the page by the url, please check the url and try again";
				$scope.showMsgFlag=true;
			}
		});
		
	};
	
}]);

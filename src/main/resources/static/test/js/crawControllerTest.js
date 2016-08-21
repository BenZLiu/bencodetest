describe('crawController', function() {
  beforeEach(module('benTestApp'));

  var $controller, $httpBackend;

  beforeEach(inject(function(_$controller_,$injector){
    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
    $httpBackend = $injector.get('$httpBackend');
  }));

  describe('$scope.init', function() {
    it('test crawController init', function() {
      var $scope = {};
      var controller = $controller('crawController', { $scope: $scope });
      $scope.init();
      expect($scope.url).toEqual('');
      expect($scope.result).toEqual('');
      expect($scope.crawlerMessage).toEqual('');
      expect($scope.showMsgFlag).toEqual(false);
    });
  });
  
  describe('$scope.crawl', function() {
	  
	  it('should demonstrate using when success', inject(function($http) {
		  var $scope = {};
		  $scope.searchInput="http.www.bbc.com/";
		  /* Code Under Test */
		  $http.post("/crawling/url/",$scope.submitUrl).then(function(response){
				$scope.crawlerMessage = "";
				console.log(response);
				$scope.result = response.data.result;
				if("SUCCESS"==response.data.result){
					$scope.crawlerMessage = "The crawling is in process, please go search page";
					$scope.showMsgFlag=true;
				} else {
					$scope.crawlerMessage = "Failed to crawling the page by the url, please check the url and try again";
					$scope.showMsgFlag=true;
				}
			});
		  /* End */
		  $httpBackend
		    .when('POST', '/crawling/url/' , $scope.searchInput)
		    .respond(200, { result: 'FAILURE' });
		  $httpBackend.flush();
		  expect($scope.result).toEqual('FAILURE');
		}));
	  
	  it('should demonstrate using when failure', inject(function($http) {
		  var $scope = {};
		  $scope.searchInput="test";
		  /* Code Under Test */
		  $http.post("/crawling/url/",$scope.submitUrl).then(function(response){
				$scope.crawlerMessage = "";
				console.log(response);
				$scope.result = response.data.result;
				if("SUCCESS"==response.data.result){
					$scope.crawlerMessage = "The crawling is in process, please go search page";
					$scope.showMsgFlag=true;
				} else {
					$scope.crawlerMessage = "Failed to crawling the page by the url, please check the url and try again";
					$scope.showMsgFlag=true;
				}
			});
		  /* End */
		  $httpBackend
		    .when('POST', '/crawling/url/' , $scope.searchInput)
		    .respond(200, { result: 'SUCCESS' });
		  $httpBackend.flush();
		  expect($scope.result).toEqual('SUCCESS');
		}));
	  
  });
});
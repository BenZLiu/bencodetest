describe('searchController', function() {
//	var $httpBackend,
	
  beforeEach(module('benTestApp'));
  var $controller, $httpBackend;

  beforeEach(inject(function(_$controller_,$injector){
	  $httpBackend = $injector.get('$httpBackend');
    $controller = _$controller_;
  }));

  describe('$scope.init', function() {
    it('test searchController init', function() {
      var $scope = {};
      var controller = $controller('searchController', { $scope: $scope });
      $scope.init();
      expect($scope.searchInput).toEqual('');
      expect($scope.newsList).toEqual([]);
    });
  });
  
  
  describe('$scope.search', function() {
	  it('should demonstrate using when (200 status)', inject(function($http) {
		  var $scope = {};
		  $scope.searchInput="test";
		  /* Code Under Test */
		  $http.get("/search/searchInput/"+$scope.searchInput).then(function(response){
//				$scope.setLoading(false);
				$scope.newsList = response.data;
			});
		  /* End */
		  $httpBackend
		    .when('GET', '/search/searchInput/' + $scope.searchInput)
		    .respond(200, { result: 'SUCCESS' });
		  $httpBackend.flush();
//		  expect($scope.isLoading).toBe(false);
		  expect($scope.newsList).toEqual({ result: 'SUCCESS' });
		}));
	  });
  
  
});
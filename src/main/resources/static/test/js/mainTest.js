describe('mainController', function() {
  beforeEach(module('benTestApp'));

  var $controller;

  beforeEach(inject(function(_$controller_){
    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
  }));

  describe('$scope.init', function() {
    it('test mainController init', function() {
      var $scope = {};
      var controller = $controller('mainController', { $scope: $scope });
      $scope.init();
      expect($scope.isLoading).toEqual(false);
    });
  });
  
  describe('$scope.setLoading', function() {
	    it('test crawController setLoading url', function() {
	      var $scope = {};
	      var controller = $controller('mainController', { $scope: $scope });
	      $scope.setLoading(true);
	      expect($scope.isLoading).toBe(true);
	    });
	  });
});
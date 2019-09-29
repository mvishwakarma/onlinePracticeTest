(function() {

	var app = angular.module("instructionsApp", []);

	var instructionsCtrl = function($scope, $http) {
		$scope.languageList = [{name:'English', code:'en'},
								{name:'Hindi', code:'hi'}];
		
		$scope.selectedLang = $scope.languageList[0].code;
		
		$scope.setLanguage = function(code){
			$scope.selectedLang = code;
		}
	};

	app.controller("instructionsCtrl", ["$scope", "$http",instructionsCtrl]);
	app.filter("standardize", function(){  
	    return function(n) {  
	      if(n<10) return '0'+n;
	       else return n 
	    }  
	}) 
}());
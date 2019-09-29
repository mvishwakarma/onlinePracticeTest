(function() { 
	var app = angular.module("homeApp", []); 
	var homeCtrl = function($scope, $http) {	  
	   var pathName = location.pathname;
	   $scope.showPaymentPopup = false;
	   $scope.buyQuiz=  {};
	   $scope.togglePayMentPopup = function(quiz){
		   $scope.showPaymentPopup = !$scope.showPaymentPopup;
		   $scope.quizObj =  quiz;
	   }
		$scope.pagination = {
			freePageNumber: 0,
			enrolledPageNumber: 0,
			freeMorePagesAvailable: true,
			enrolledMorePagesAvailable: true,
		};
		$scope.saveQuestionPortions = function(quizDetails){
			var portions = {};
			if(quizDetails.portions){
				quizDetails.portions.split('|').forEach(function(obj){
					portions[obj.split(',')[0]] = obj.split(',')[1];
				});
			}
			var qd ={'qPortions':portions,'qTime':quizDetails.time || 180}
			localStorage.setItem('_qd',btoa(JSON.stringify(qd))); 
		};
		
		$scope.loadNextPage = function(quizType) {
			var pagesAvailable = $scope.pagination.freeMorePagesAvailable;
			var endPoint = "/api/quizzes/open?sort=id,desc&page=" + $scope.pagination.freePageNumber;
			if(quizType == 'enrolled'){
				pagesAvailable = $scope.pagination.enrolledMorePagesAvailable;
				endPoint = "/api/quizzes/opted?sort=id,desc&page=" +  $scope.pagination.enrolledPageNumber;
			}
			if (pagesAvailable) {
				$http.get(endPoint)
					.then(
						function(response) {
							if ($scope.quizzes == undefined) {
								$scope.quizzes = response.data.content;
							} else {
								$scope.quizzes = $scope.quizzes.concat(response.data.content);
							} 
							if(quizType == 'enrolled'){
								$scope.pagination.enrolledMorePagesAvailable = !response.data.last;
								$scope.pagination.enrolledPageNumber++;
							}
							else {
								$scope.pagination.freeMorePagesAvailable = !response.data.last;
								$scope.pagination.freePageNumber++;
							}
							 
						}, 
						function(reason) {
							$scope.error = "Could not fetch the data.";
						}
					);
			}
		};
	
		//$scope.loadNextPage();
		
		function openQuizes(){
			//showLoader();
			$http.get("/api/quizzes/open?sort=id,desc&page=" + $scope.pagination.freePageNumber)
			.then( function(response) {
					hideLoader();
					$scope.openQuizesData = response.data; 
					$scope.pagination.freePageNumber++;
				},
			function(er){
				hideLoader();
				$scope.error = "Could not fetch the data.";
				console.log(er)
			})
		};
		if(pathName == '/') openQuizes();
		
		function optedQuizes(){
			showLoader();
			var selectedTag  = localStorage.getItem('qTag'); 
			if(selectedTag){
				$http.get("/api/quizzes/opted?sort=id,desc&page=" + $scope.pagination.enrolledPageNumber+ "&size=1000&tags="+selectedTag)
				.then(
					function(response) { 
						hideLoader();
						$scope.optedQuizesData = response.data;
						$scope.freeQuizesData = [];
						$scope.enrolledQuizesData = [];
						$scope.pagination.enrolledPageNumber++;
						var content = $scope.optedQuizesData.content;
						for(var i=0; i<content.length; i++){
							if(content[i].isFree) $scope.freeQuizesData.push(content[i]);
							else $scope.enrolledQuizesData.push(content[i]);
							
						}
					}, 
					function(reason) {
						hideLoader();
						$scope.error = "Could not fetch the data.";
					}
				);
			}else{
				$scope.freeQuizesData = [];
				$scope.enrolledQuizesData = [];
				hideLoader();
			}
			
		};
		if(pathName == '/quizLists') optedQuizes();
		
		$scope.initPay = function(){
				if($scope.buyQuiz.mobile){
					showLoader();
					var reqObj = {
							"mobileno":$scope.buyQuiz.mobile,
							"quizId":$scope.quizObj.id
						}
					$http.post('/api/payment/create', reqObj)
						.then(function(res){
							hideLoader();
							var response = res.data;
							window.open(response.paymentUrl,"_self");
						}).then(function(err){
							console.log('err=',err)
						});
				}else{
					alert('Please enter valid mobile no.')
				}
		}  
		
		$scope.bannerOptions = { 
	            autoplayHoverPause:true,
	            paginationSpeed: 600, 
	            loop:true,
    		    margin:0,
    		    autoplay:true,
    		    autoplayTimeout:5000, 
    		    dots:true, 
	            responsive:{
		            0:{
		                items:1
		            },
		            600:{
		                items:1
		            },
		            1000:{
		                items:1
		            }
	            }         
	     };
		$scope.bannerImages = [{image:'https://s3.ap-south-1.amazonaws.com/onlinepractice/images/homeImage.png'},
			{image:'https://s3.ap-south-1.amazonaws.com/onlinepractice/images/homeImage-1.png'},
			{image:'https://s3.ap-south-1.amazonaws.com/onlinepractice/images/homeImage-2.png'},
			{image:'https://s3.ap-south-1.amazonaws.com/onlinepractice/images/homeImage-3.png'},
			{image:'https://s3.ap-south-1.amazonaws.com/onlinepractice/images/homeImage-4.png'}]
		
	};
	app.directive("owlCarousel", function($timeout) {
	    return {
	        restrict: 'E',
	        transclude: false,
	        link: function (scope) {
	            scope.initCarousel = function(element) {
	                 $timeout(function () {
	              // provide any default options you want
	                var defaultOptions = {
	                     
	                };
	                var customOptions = scope.$eval($(element).attr('data-options'));
	                // combine the two options objects
	                for(var key in customOptions) {
	                    defaultOptions[key] = customOptions[key];
	                }
	                // init carousel
	                $(element).owlCarousel(defaultOptions);
	                var triggered = false;
	                element.on('mouseleave', function () { 
	                	if(!triggered){
	                		triggered = true;
	                		$timeout(function(){ 
	                			$(element).trigger('next.owl.carousel');
	                			triggered = false;
	                		},3000);
	                	}
	                });
	            },50);
	            };
	        }
	    };
	})
	.directive('owlCarouselItem', [function() {
	    return {
	        restrict: 'A',
	        transclude: false,
	        link: function(scope, element) {
	          // wait for the last item in the ng-repeat then call init
	            if(scope.$last) {
	                scope.initCarousel(element.parent());
	            }
	        }
	    };
	}]);
	app.controller("HomeCtrl", ["$scope", "$http", homeCtrl]);
	app.filter("standardize", function(){  
	    return function(n) {  
	      if(n<10) return '0'+n;
	       else return n 
	    }  
	});
}());
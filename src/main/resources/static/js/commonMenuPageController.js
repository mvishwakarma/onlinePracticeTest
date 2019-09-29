(function() {

	var app = angular.module("commonPageApp", []);

	var commonPageCtrl = function($scope, $http, $location, $interval, $anchorScroll,$window) {	
		
		var pathName = location.pathname;
		var contactType = location.search;
		$scope.contact = {};
		if(contactType){
			$scope.contact = {
					'name':'',
					'email':'',
					'phone':'',
					'message':'Please enroll me for this quiz.'
					};
			var sp = contactType.split('&')[1].split('=')[1]; 
			$scope.enrollQuizName = decodeURI(sp);
			$scope.contactType = 'enrollReq';
		}
		$scope.languageList = [{name:'English', code:'en'},
			{name:'Hindi', code:'hi'}];

		$scope.selectedLang = $scope.languageList[0].code;

	$scope.setLanguage = function(code){
		$scope.selectedLang = code;
	}
				
			
			$scope.submitQuery = function(){
				var endPoint = '/api/query/anonymous';
				if(contactType) endPoint = '/api/query/submit'; 
				if($scope.contactForm.$valid){
					$http.post(endPoint, $scope.contact)
					.then(function(res){
						var response = res.data;
						if(response.hasOwnProperty('email') && response['email']){
							alert('Your query has been submitted successfully.');
							$scope.contact = {};
						}
					})
				}else{
					alert('Please enter valid values.')
				}
				
			}
			$scope.feedback={};
			$scope.submitFeedback = function(){ 
				if($scope.feedbackForm.$valid){
					$http.post('/api/feedback/submit', $scope.feedback)
					.then(function(res){
						var response = res.data;
						if(res.status == 201){
							alert('Your query has been submitted successfully.');
							$scope.feedback = {};
						}
					})
				}else{
					alert('Please enter valid values.')
				}
				
			};
			
			function getPaymentStatus(){
				var spl = location.search.split('&');
				var reqObj = {
						'pgPaymentId':spl[0].split('?')[1].split('=')[1],
						'pgPaymetStatus':spl[1].split('=')[1],
						'pgId':spl[2].split('=')[1],
						'pgtxnId':spl[3].split('=')[1]
				}; 
				$http.post('/api/payment/update', reqObj)
					.then(function(res){
						$scope.paymentStatus = res.data;
					})
			}
			if(pathName == '/payment-response' )getPaymentStatus();
			
			
			$scope.downloadStudyMaterials  = function(lang, name){
				if(lang && name)
					window.open('/api/assets/downloadFile/maths_'+lang+'_'+name+'.pdf')
			
			}
			
			$scope.streamList = [{
				'id':0,
				'name':'all'
			}];
			
			$scope.changeStream = function(stream,f){
				$scope.selectedStream = stream;
				if(f){
					$scope.getStorePackages(stream);
				}else{
					$scope.getFreeEBooks(stream);
				} 
			}
			
			$scope.selectedStream = $scope.streamList[0].name;
			
			$scope.getFreeEBooks = function(stream){
				stream = stream ? stream : 'All';
				$http.get('/api/ebooks/'+stream)
				.then(function(res){
					var response = res.data;
					$scope.finalRes=[];
					$scope.finalRes = response.reduce(function(a,c){
						var matched = false;
						a.map(function(v){
							if(v.tag== c.stream){
								matched = true;
								v.data.push(c)	
							}   	
						});
						if(!matched)a.push({'tag':c.stream, data:[c]});
						return a;
					},[]);
					if(stream == 'all' && $scope.streamList.length<=1){
						for(var i=0; i<$scope.finalRes.length;i++){
							$scope.streamList.push({'id':i+1,'name':$scope.finalRes[i].tag});
						}
					}
				});
			}
			if(pathName == '/free-eBooks') $scope.getFreeEBooks($scope.selectedStream);
			
			$scope.getStorePackages= function(stream){
				stream = stream ? stream : 'all';
				var endpoint = '/api/packs/'+stream+'/list';
				if(stream == 'all')endpoint = '/api/packs/all';
				$http.get(endpoint)
				.then(function(res){
					var response = res.data;
					$scope.finalRes=[];
					$scope.finalRes = response.reduce(function(a,c){
						var matched = false;
						a.map(function(v){
							if(v.tag== c.tags){
								matched = true;
								v.data.push(c)	
							}   	
						});
						if(!matched)a.push({'tag':c.tags, data:[c]});
						return a;
					},[]); 
					if(stream == 'all' && $scope.streamList.length<=1){
						for(var i=0; i<$scope.finalRes.length;i++){
						$scope.streamList.push({'id':i+1,'name':$scope.finalRes[i].tag});
					}
					}
				});
			}
			if(pathName == '/packages') $scope.getStorePackages($scope.selectedStream);
			$scope.pagination = {
					freePageNumber: 0,
					enrolledPageNumber: 0,
					freeMorePagesAvailable: true,
					enrolledMorePagesAvailable: true,
				};
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
			if(pathName == '/training-quizes') openQuizes();
			
			$scope.packageDetails  = false;
			$scope.openDetails = function(data){
				$scope.packageDetailsObj = data;
				$scope.packageDetails  = true;
			}
			$scope.closePackDetails = function(){
				$scope.packageDetails = false; 
			};
			$scope.showDescription  = function(id){ 
				document.getElementById(id).style.display = 'block';
			};
			$scope.hideDescription  = function(id){ 
				document.getElementById(id).style.display = 'none';
			};
			$scope.downloadFreeBook = function(lang, name){
				if(lang && name)
					window.open('/api/assets/downloadFile/'+lang+'_'+name+'.pdf');
			};
			$scope.downloadEbooks  = function(path){ 
				if(path)
					window.open('/api/assets/downloadFile/'+path);
			};
			 $scope.showPaymentPopup = false;
			   $scope.buyQuiz=  {};
			   $scope.togglePayMentPopup = function(quiz){
				   $scope.showPaymentPopup = !$scope.showPaymentPopup;
				   $scope.quizObj =  quiz;
			   }
			   $scope.initPay = function(type){
					if($scope.buyQuiz.mobile){
						showLoader();
						var reqObj = {
								"mobileno":$scope.buyQuiz.mobile,
								"quizId":$scope.quizObj.id,
								"packId":-1,
								"isBulkOrder":false
							};
						if(type == 'pack'){
							reqObj = {
									"mobileno":$scope.buyQuiz.mobile,
									"quizId":-1,
									"packId":$scope.quizObj.id,
									"isBulkOrder":true
							}
						}
						$http.post('/api/payment/create', reqObj)
							.then(function(res){
								hideLoader();
								var response = res.data;
								window.open(response.paymentUrl,"_self");
							}).then(function(err){
								hideLoader();
								console.log('err=',err)
							});
					}else{
						alert('Please enter valid mobile no.')
					}
			}  
			
	};

	app.controller("commonPageCtrl", ["$scope", "$http","$location","$interval",'$anchorScroll','$window', commonPageCtrl]);
	app.filter("standardize", function(){  
	    return function(n) {  
	      if(n<10) return '0'+n;
	       else return n 
	    }  
	}) 

}());
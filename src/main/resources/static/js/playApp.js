(function() {

	var app = angular.module("playQuizApp", []);

	var playQuizCtrl = function($scope, $http, $location, $interval, $anchorScroll,$window) {	
		
		$scope.Math = window.Math;
		
		var questions = [];
		$scope.answers = [];
		$scope.selectedAnswer= {};
		$scope.totalQuestions = 0;
		$scope.questionCount = 0;
		$scope.isPlaying = true; 
		$scope.showExamSummary  = false;
		$scope.doubleConfirmation = false;
		$scope.thankyouSection = false;
		$scope.showResult = false;
		$scope.timesupPopup = false;
		$scope.timerOn = true;
		$scope.multiAnswersChecked = {};
		
		$scope.languageList = [{name:'English', code:'en'},
			{name:'Hindi', code:'hi'}];

		$scope.selectedLang = $scope.languageList[0].code;
		
		$scope.setLanguage = function(code){
			$scope.selectedLang = code;
		}
		$scope.closeTimepopup = function(){
			$scope.timesupPopup = !$scope.timesupPopup
		}
		var localD = JSON.parse(atob(localStorage.getItem('_qd') || '{}')); 
		if(Object.keys(localD.qPortions).length){
			$scope.qPortions = localD.qPortions
		}
		function getQuestionPortions(o){
			var portions = o.split('|');
			var obj = {};
			portions.forEach(function(ob){
				var sp = ob.split(',');
				obj[sp[0]] = sp[1];
			});
		};
		
		$scope.returnToQuiz = function(){
			$scope.isPlaying = true;
			$scope.showExamSummary  = false;
			$scope.doubleConfirmation = false;
			$scope.thankyouSection = false;
			$scope.showResult = false;
		}
		$scope.doubleConf = function(){
			$scope.doubleConfirmation = true;
			$scope.showExamSummary = false;
		}
				
		$scope.clearAns = function(){
				$scope.selectedAnswer.answer = 0;
		}
		$scope.viewResultFn = function(){
			var result = JSON.parse(localStorage.getItem('result_'+$scope.quizId)||{});
			if(Object.keys(result).length){
				$scope.viewReport = result;
			}
			$scope.viewResult = true;
		}
		angular.element($window).on('click', function (event) {
	        if($scope.showSignIn)$scope.showSignIn = false;
//	        if($scope.showSignUp)$scope.showSignUp = false;
		});
		$scope.initialize = function() {
			if ($scope.quizId == 0)
				return; 
			$scope.playing = true;
			$http.get("/api/quizzes/" + $scope.quizId + "/questions?onlyValid=true")
				.then(
					function(response) {
						response.data.forEach(function(obj, i){
							
							    obj.index = i;
								obj.status ={
										'not_visited':true,
										'not_answered':false,
										'answered':false,
										'mark_for_review':false,
										'answered_marked_review':false
								}
								if(obj.isMorethenOneCorrect)$scope.multiAnswersChecked[obj.id]=[];
								setAnswer(obj, null);
						});
						questions = questions.concat(response.data);
						$scope.questionList  = questions;
						$scope.totalQuestions = questions.length; 
						$scope.setQuestion($scope.questionCount);
						var time = localD.qTime;
						timer(parseInt(time));						
					}, 
					function(reason) {
						$scope.error = "Could not fetch the data.";
					}
				);
		}	
		$scope.questionNotAnswered = function(question){
			if(!question.status.answered && !question.status.mark_for_review && !question.status.answered_marked_review)(
					question.status = {
							'not_visited':false,
							'not_answered':true,
							'answered':false,
							'mark_for_review':false,
							'answered_marked_review':false
						}
			)
			
			$scope.getCounts($scope.questionList);
		}
		$scope.questionAnswered = function(question){
			question.status = {
					'not_visited':false,
					'not_answered':false,
					'answered':true,
					'mark_for_review':false,
					'answered_marked_review':false
				}
			$scope.getCounts($scope.questionList);
		}
		$scope.saveAndMark = function(question, selection,isMultiple ){
			if (!selection && (isMultiple && !$scope.multiAnswersChecked[question.id].length )) {
				alert("Please, choose an answer");
				return;
			}
						
			setAnswer(question, selection,isMultiple);
			question.status = {
					'not_visited':false,
					'not_answered':false,
					'answered':false,
					'mark_for_review':true,
					'answered_marked_review':false
				}
			$scope.getCounts($scope.questionList);
		};
		
		$scope.markReviewAndNext = function(question, answer){
			question.status = {
					'not_visited':false,
					'not_answered':false,
					'answered':false,
					'mark_for_review':false,
					'answered_marked_review':true
				}
			setAnswer(question, null)
			$scope.getCounts($scope.questionList);
			if($scope.questionCount < $scope.totalQuestions-1) $scope.questionCount++;
			if ($scope.questionCount >= $scope.totalQuestions) {
				$scope.isPlaying = false;
			} else { 
				$scope.setQuestion($scope.questionCount);
			}	
		}
		$scope.clearResponse = function(question, isMultiple){
			if(!isMultiple){
			var matched = $scope.answers.filter(function(obj){
				return obj.question == question.id;
			})[0];
			var _i = $scope.answers.indexOf(matched);
			if(_i >= 0){
				$scope.answers[_i].selectedAnswers = [];
				question.status = {
						'not_visited':false,
						'not_answered':true,
						'answered':false,
						'mark_for_review':false,
						'answered_marked_review':false
					}
				$scope.getCounts($scope.questionList);
				$scope.selectedAnswer.answer = undefined;
			}
			}else{
				 $scope.multiAnswersChecked[question.id] = [];
				 question.status = {
							'not_visited':false,
							'not_answered':true,
							'answered':false,
							'mark_for_review':false,
							'answered_marked_review':false
						}
				 $scope.getCounts($scope.questionList);
					$scope.selectedAnswer.answer = undefined;
			}
				
	}
		$scope.selectQuestion = function(selectedAnswer){
				if(!selectedAnswer.selected) selected.selected = true;
		};
		
		function setAnswer(question, selectedAns, isMultiple){
				var ansExist = $scope.answers.filter(function(obj, i){
					return obj.question == question.id; 
				})[0];
				var ansIndex = $scope.answers.indexOf(ansExist);
				var candAnswer = selectedAns ? [selectedAns] : [];
				if(selectedAns && (isMultiple && $scope.multiAnswersChecked[question.id].length) ) candAnswer = $scope.multiAnswersChecked[question.id];
				if(ansIndex <= -1){ 
					$scope.answers.push({
						question:question.id,
						selectedAnswers: candAnswer
					 });
				}else{ 
					var newAnser = [];
					candAnswer = Array.isArray(candAnswer[0])? candAnswer[0]:candAnswer;
					candAnswer.forEach(function(num){ 
						newAnser.push(parseInt(num));
					})
					$scope.answers[ansIndex].selectedAnswers = newAnser;
				}
		}
		
		
		$scope.setQuestion = function(questionNumber) {
			$scope.selectedAnswer.answer = undefined;
			$scope.questionCount = questionNumber;
			$http.get("/api/questions/" + questions[questionNumber].id + "/answers")
				.then(
					function(response) {
						$scope.currentQuestion = questions[questionNumber];
						response.data.forEach(function(obj){
							obj.selected = false;
						})
						$scope.currentAnswers = response.data; 
						$scope.questionList.forEach(function(ques){
							if(ques.id == questions[questionNumber].id) $scope.questionNotAnswered(ques);
						}); 
						//set Answer if Already selected
						var selectedAns = $scope.answers.filter(function(ob){
							return ob.question == $scope.currentQuestion.id
						})[0];
						if(selectedAns && selectedAns.selectedAnswers.length ) $scope.selectedAnswer.answer = selectedAns.selectedAnswers.join(); 
					}, 
					function(reason) {
						$scope.error = "Could not fetch the data.";
					}
				);
		}
		
		$scope.answerQuestion = function(selection, isMultiple) {
			if (selection === undefined && (isMultiple && !$scope.multiAnswersChecked[$scope.currentQuestion.id].length )) {
				alert("Please, choose an answer");
				return;
			} 
			$scope.questionList.forEach(function(ques){
				if(ques.id == $scope.currentQuestion.id) $scope.questionAnswered(ques);
			});
			if($scope.questionCount < $scope.totalQuestions-1) $scope.questionCount++;
			if ($scope.questionCount >= $scope.totalQuestions) {
				setAnswer($scope.currentQuestion, selection, isMultiple); 
			} else { 
				setAnswer($scope.currentQuestion, selection, isMultiple);
				$scope.setQuestion($scope.questionCount);
			}	
		}
		$scope.submit_all = function(type){
			$scope.isPlaying = false;
			$scope.showExamSummary  = true;
			$scope.timesupPopup = false;
			$scope.doubleConfirmation = false; 
			$scope.showResult = false;
			if(type == 'timeEnd'){
				$scope.showExamSummary  = false;
				$scope.submitAnswers('timeEnd');
				$scope.thankyouSection = false;
			}
		}
		
		$scope.showNext = function(question, answer){
			if(answer){
				$scope.answers.push({
					question: question.id,
					selectedAnswer: answer
				});
				$scope.questionAnswered(question);
			} 
			if($scope.questionCount < $scope.totalQuestions-1) $scope.questionCount++;
			if ($scope.questionCount >= $scope.totalQuestions) {
				
				//$scope.submitAnswers();
				//$scope.isPlaying = false;
			} else {  
				$scope.setQuestion($scope.questionCount);
			}	
		};
		
		$scope.showPrevious = function(question, answer){
			if($scope.questionCount) --$scope.questionCount;
			if($scope.questionCount >=0)
				$scope.setQuestion($scope.questionCount);
		};
		
		$scope.scrollTo = function(id, where) {
			   var div = document.getElementById(id);
			   if(where == 'bottom')
				   div.scrollTop = div.scrollHeight - div.clientHeight;
			   else if(where = 'top')
				   div.scrollTop = 0;
		}
		
		$scope.submitAnswers = function(type) {
			$scope.showRecaptchaErrMsg = false;
			var reCaptcha = '';
			if(type == 'timeEnd'){
				 reCaptcha = 'timesUp';
			}else
				 reCaptcha = document.getElementById('g-recaptcha-response').value;
			if(!reCaptcha){
				$scope.showRecaptchaErrMsg = true;
				return;
			}
			console.log($scope.answers)
			$http.post("/api/quizzes/" + $scope.quizId + "/submitAnswers",
					JSON.stringify($scope.answers))
			.then(
				function(response) {
					$scope.results = response.data;
					$scope.doubleConfirmation = false;
					$scope.thankyouSection = true;
					$scope.timerOn = false;
					$scope.showNav = true;
				}, 
				function(reason) {
					$scope.error = "Could not fetch the data.";
				}
			);
		}
		
		$scope.viewResult  = function(){
			$scope.showResult = true;
			$scope.thankyouSection = false;
			$scope.resultQuestions = [];
			var questionList = $scope.questionList ;
			var answers = $scope.results.responseBundle;
			console.log(answers);
			$scope.incorrectAnswers = 0; 
			questionList.forEach(function(obj, index){
				(function(){
					answers.forEach(function(o,i){
				    	(function(){
				    		if(obj.id == o.questionId){
						        if(o.isCorrect){ 
						            	$scope.resultQuestions.push({q:index+1, an:o.candidateResponseOrderIds, status:'Correct',correctAnswer:o.correctAnswerOrderIds});	
						        
						        } else if(!o.isCorrect && o.candidateResponseIds){
						        	 $scope.incorrectAnswers++; 
						        			$scope.resultQuestions.push({q:index+1, an:o.candidateResponseOrderIds, status:'Wrong',correctAnswer:o.correctAnswerOrderIds});
						        		
						        }else if(!o.isCorrect && !o.candidateResponseIds){
						        	$scope.resultQuestions.push({q:index+1, an:'--', status:'N/A',correctAnswer:o.correctAnswerOrderIds});
						        }
						      }
				    	})(o); 
				    })
				})(obj); 
				});
			console.log($scope.resultQuestions)
		};
		
		function timer(mins){
			$scope.hours= Math.floor(mins / 60);
			$scope.minutes = mins % 60;
			if($scope.hours && !$scope.minutes)	{
				$scope.hours  = $scope.hours-1;
				$scope.minutes = 59;
			}
			if($scope.minutes) $scope.minutes = $scope.minutes-1;
			$scope.sec  = 59; 
			var timerInterval = $interval(function(){
				if($scope.sec > 0)
					$scope.sec = $scope.sec-1;
				else if($scope.sec == 0 && $scope.minutes > 0){
					$scope.sec = 59;
						$scope.minutes = $scope.minutes-1; 
				}else if($scope.sec == 0 && $scope.minutes == 0 && $scope.hours>0){
						$scope.sec = 59;
						$scope.minutes = 59;
						$scope.hours = $scope.hours-1;
				}else{ 
					$interval.cancel(timerInterval);
					$scope.submit_all('timeEnd');
					$scope.timesupPopup = true;
					$scope.showExamSummary  = false;
					$scope.doubleConfirmation = false;
					$scope.thankyouSection = false;
					$scope.showResult = false;
				} 
			},1000)
		} 
		
		
		
		$scope.setBgClass = function(ques){
				if(ques.status.not_visited) return 'not-attempted'; 
				else if(ques.status.not_answered) return 'not-Answered';
				else if(ques.status.answered) return 'answered';
				else if(ques.status.mark_for_review) return 'mark-Review';
				else if(ques.status.answered_marked_review) return 'answered_Marked_Review';
				
		}
		
		$scope.getCounts = function(qList){
			$scope.counts= {not_visited:0,not_answered:0,answered:0,mark_for_review:0,answered_marked_review:0}; 
			qList.forEach(function(obj){
				for(var k in obj.status){ 
					if(obj.status[k])
						if($scope.counts.hasOwnProperty(k)) $scope.counts[k]++;
						else $scope.counts[k] =1;
				}
					 
			});
		}
	
		$scope.initialize();	
	};

	app.controller("PlayQuizCtrl", ["$scope", "$http","$location","$interval",'$anchorScroll','$window', playQuizCtrl]);
	app.filter("standardize", function(){  
	    return function(n) {  
	      if(n<10) return '0'+n;
	       else return n 
	    }  
	}); 
	
	app.directive('checkList', function() {
		  return {
		    scope: {
		      list: '=checkList',
		      value: '@'
		    },
		    link: function(scope, elem, attrs) {
		      var handler = function(setup) {
		        var checked = elem.prop('checked');
		        var index = scope.list.indexOf(scope.value);

		        if (checked && index == -1) {
		          if (setup) elem.prop('checked', false);
		          else scope.list.push(scope.value);
		        } else if (!checked && index != -1) {
		          if (setup) elem.prop('checked', true);
		          else scope.list.splice(index, 1);
		        }
		      };
		      
		      var setupHandler = handler.bind(null, true);
		      var changeHandler = handler.bind(null, false);
		            
		      elem.on('change', function() {
		        scope.$apply(changeHandler);
		      });
		      scope.$watch('list', setupHandler, true);
		    }
		  };
		});


}());
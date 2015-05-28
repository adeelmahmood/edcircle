var app = angular.module('edcircle', 
		['ngResource', 'ui.router', 'ngMaterial', 'ngMdIcons' , 'ngMessages', 'ngAnimate']);

app.controller('HomeCtrl', [ '$scope', '$mdSidenav',  function($scope, $resource, $mdSidenav) {
	
	$scope.toggleSidenav = function(menuId) {
		$mdSidenav(menuId).toggle();
	};
	
	$scope.menu = [{
		link: 'home',
		title: 'Home',
		icon: 'home'
	}, {
		link: 'register-school',
		title: 'Register New School',
		icon: 'school'
	}];
	
}])
app.controller('RegisterSchoolCtl', [ '$scope', '$resource', '$mdToast', 
		'$mdDialog', '$filter', function($scope, $resource, $mdToast, $mdDialog, $filter) {

	$scope.school = {
			name: "test school",
			type: "HIGH_SCHOOL",
			externalResources: [{
				url: "http://github.com"
			}, {
				url: "http://linkedin.com"
			}], 
			admins: [{
				firstName: 'test',
				lastName: 'user',
				username: 'testuser'
			}]
		};
	
	$scope.deleteEntity = function(ev, items, param, val) {
		var confirm = $mdDialog.confirm()
	      .title('Delete Confirmation')
	      .content('Are you sure ?')
	      .ariaLabel('Delete Cofirmation')
	      .ok('Yes')
	      .cancel('No')
	      .targetEvent(ev);
	    $mdDialog.show(confirm).then(function() {
	    	for(var k in $scope.school[items]) {
				if($scope.school[items][k][param] == val) {
					$scope.school[items].splice(k, 1);
					break;
				}
			}
	    });
	};
	
	$scope.showPopup = function(ev, key, k, item) {
		$mdDialog.show({
			controller: function DialogController($scope, $mdDialog) {
				$scope.isNew = item == null;
				$scope[k] = $scope.isNew ? {} : item;
				$scope.close = function() {	
					var result = {isNew: $scope.isNew};
					result[k] = $scope[k];
					$mdDialog.hide(result);
				}
			},
			templateUrl: 'partials/school/' + k.toLowerCase() + '.popup.html',
			targetEvent: ev		
		})
		.then(function(data) {
			if(data.isNew) {
				if($scope.school[key] == null) { $scope.school[key] = []; }
				$scope.school[key].push(data[k]);
			}
		})
	};
	
	var saveSchool = $resource('/schools/save', {isArray:false});
	$scope.confirm = function() {
		saveSchool.save(JSON.stringify($scope.cons), function(saved) {
			$state.transitionTo('home', {}, { reload: true, inherit: true, notify: true });
			$mdToast.show(
				$mdToast.simple()
				.content('School ' + saved.name + ' saved successfully'));
		})
	};
	
}])
.config(function($stateProvider, $urlRouterProvider, $mdThemingProvider) {
	var customBlueMap = $mdThemingProvider.extendPalette('light-blue', {
	    'contrastDefaultColor': 'light',
	    'contrastDarkColors': ['50'],
	    '50': 'ffffff'
	  });
	
	  $mdThemingProvider.definePalette('customBlue', customBlueMap);
	
	  $mdThemingProvider.theme('default')
	    .primaryPalette('light-blue', {
	      'default': '500',
	      'hue-1': '50'
	    })
	    .accentPalette('pink');
	
	//default home page
	$urlRouterProvider.otherwise("/");
	
	//states
	$stateProvider
		.state('home', {
			url: '/',
			templateUrl: 'partials/home.html'
		})
		.state('register-school', {
			url: '/register-school',
			templateUrl: 'partials/school/school.html',
			controller: 'RegisterSchoolCtl'
		})
		;
});
var app = angular.module('edcircle', 
		['ngResource', 'ui.router', 'ngMaterial', 'ngMdIcons' , 'ngMessages']);

app.controller('HomeCtrl', [ '$scope', '$resource', '$mdSidenav', '$mdToast', 
		'$mdDialog', '$filter', function($scope, $resource, $mdSidenav, $mdToast, $mdDialog, $filter) {
	
	$scope.toggleSidenav = function(menuId) {
		$mdSidenav(menuId).toggle();
	};
	
	$scope.menu = [{
		link: 'registerSchool',
		title: 'Register New School',
		icon: 'dashboard'
	}];
	
	$scope.school = {
		name: "test school",
		type: "HIGH_SCHOOL",
		externalResources: [{
			url: "http://github.com"
		}, {
			url: "http://linkedin.com"
		}]
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
		.state('registerSchool', {
			url: '/register-school',
			templateUrl: 'partials/registerSchool.html'
		})
		;
});
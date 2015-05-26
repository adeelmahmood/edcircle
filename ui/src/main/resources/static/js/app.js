var app = angular.module('edcircle', 
		['ngResource', 'ui.router', 'ngMaterial', 'ngMdIcons' , 'ngMessages']);

app.controller('HomeCtrl', [ '$scope', '$resource', '$mdSidenav', '$mdToast', 
		'$mdDialog', '$filter', function($scope, $resource, $mdSidenav, $mdToast, $mdDialog, $filter) {
	
	$scope.toggleSidenav = function(menuId) {
		$mdSidenav(menuId).toggle();
	};
	
	$scope.menu = [];
	
}])
.config(function($stateProvider, $urlRouterProvider, $mdThemingProvider) {
	var customBlueMap = $mdThemingProvider.extendPalette('light-blue', {
	    'contrastDefaultColor': 'light',
	    'contrastDarkColors': ['50'],
	    '50': 'ffffff'
	  });
	
	  $mdThemingProvider.definePalette('customBlue', customBlueMap);
	
	  $mdThemingProvider.theme('default')
	    .primaryPalette('blue-grey', {
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
			template: '<p>Home</p>'
		})
		;
	
});
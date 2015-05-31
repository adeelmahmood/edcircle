var app = angular.module('edcircle', 
		['ngResource', 'ui.router', 'ngMaterial', 'ngMdIcons' , 'ngMessages', 'angularFileUpload']);

app.controller('HomeCtrl', ['$scope', '$mdSidenav', '$resource', 
                             function($scope, $mdSidenav, $resource) {
	
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
	}, {
		link: 'schools',
		title: 'Registered Schools',
		icon: 'view_list'
	}];
	
	$scope.schoolsMenu = [];
	$scope.schoolsQuery = $resource('/query/schools', {isArray: false});

	//refresh schools menu list
	$scope.refreshSchoolsMenu = function() {
		$scope.schoolsQuery.query({}, function(schools) {
			$scope.schools = schools;
			angular.forEach(schools, function(school, key) {
				if($scope.findByParam($scope.schoolsMenu, 'id', school.id) == -1) {
					$scope.schoolsMenu.push({
						id: school.id,
						link: '/school-home/' + school.id,
						title: school.name,
						icon: 'settings'
					});
				}
			});
		});
	};
	$scope.refreshSchoolsMenu();
	
	$scope.findByParam = function(arr, param, value) {
		var indx = -1;
		angular.forEach(arr, function(item, key) {
			if(item[param] == value) {
				indx = key;
			}
		});
		return indx;
	};
	
}])
app.controller('RegisterSchoolCtl', [ '$scope', '$resource', '$state', '$stateParams', '$mdToast', 
		'$mdDialog', function($scope, $resource, $state, $stateParams, $mdToast, $mdDialog) {

	$scope.school = {};
	
	if($stateParams.schoolId) {
		$resource('/query/schools/:schoolId', {isArray: false})
			.get({schoolId:$stateParams.schoolId}, function(data) {
				$scope.school = data;
		});
	}
	
	$scope.deleteSchool = $resource('/schools/delete/:schoolId', {isArray: false});
	$scope.delete = function(ev) {
		var confirm = $mdDialog.confirm()
	      .title('Delete Confirmation')
	      .content('Are you sure you want to delete ' + $scope.school.name + ' ?')
	      .ariaLabel('Delete Cofirmation')
	      .ok('Yes')
	      .cancel('No')
	      .targetEvent(ev);
	    $mdDialog.show(confirm).then(function() {
	    	$scope.deleteSchool
				.query({schoolId:$scope.school.id}, function(data) {
					$state.transitionTo('schools', {}, { reload: true, inherit: true, notify: true });
					$mdToast.show(
						$mdToast.simple()
						.content('School ' + $scope.school.name + ' delete successfully'));

					//delete from js entities
					$scope.schools.splice($scope.findByParam($scope.schools, 'id', $scope.school.id), 1);
					$scope.schoolsMenu.splice($scope.findByParam($scope.schoolsMenu, 'id', $scope.school.id), 1);
			});
	    });
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
	
	$scope.saveSchool = $resource('/schools', {isArray:false});
	$scope.confirm = function() {
		//add admin role to new admins
		angular.forEach($scope.school.admins, function(admin, key) {
			if(!admin.id) {
				admin.roles = [].concat({role: 'SCHOOL_ADMIN'});
			}
		});
		//save school
		$scope.saveSchool.save(JSON.stringify($scope.school), function(saved) {
			$state.transitionTo('schools', {}, { reload: true, inherit: true, notify: true });
			$mdToast.show(
				$mdToast.simple()
				.content('School ' + saved.name + ' saved successfully'));
			$scope.refreshSchoolsMenu();
		})
	};
	
	$scope.classesQuery = $resource('/query/classes/school/:schoolId', {isArray:false});
	$scope.classesQuery
		.query({schoolId:$stateParams.schoolId}, function(data) {
			$scope.classes = data;
	});
	
	$scope.saveClass = $resource('/classes', {isArray:false});
	$scope.showClassPopup = function(ev, cls) {
		$mdDialog.show({
			controller: function DialogController($scope, $mdDialog) {
				$scope.cls = cls || {};
				$scope.close = function() {	
					$mdDialog.hide($scope.cls);
				}
			},
			templateUrl: 'partials/class/addclass.popup.html',
			targetEvent: ev		
		})
		.then(function(_cls) {
			_cls.school = $scope.school;
			_cls.teacher = _cls.teacher || {};
			if(!_cls.teacher.id) {
				_cls.teacher.roles = [].concat('SCHOOL_TEACHER');
			}
			$scope.saveClass.save(JSON.stringify(_cls), function(saved) {
				if(!_cls.id) {
					$scope.classes.push(saved);
					$mdToast.show(
						$mdToast.simple()
						.content('Class saved successfully'));
				}
			});
		})
	};
	
	$scope.deleteClassQuery = $resource('/classes/delete/:classId', {isArray: false});
	$scope.deleteClass = function(ev, cls) {
		var confirm = $mdDialog.confirm()
	      .title('Delete Confirmation')
	      .content('Are you sure ?')
	      .ariaLabel('Delete Cofirmation')
	      .ok('Yes')
	      .cancel('No')
	      .targetEvent(ev);
	    $mdDialog.show(confirm).then(function() {
	    	$scope.deleteClassQuery
				.query({classId:cls.id}, function(data) {
					$state.transitionTo($state.current, {}, { reload: true, inherit: true, notify: true });
					$mdToast.show(
						$mdToast.simple()
						.content('Class delete successfully'));
					$scope.classes.splice($scope.findByParam($scope.classes, 'id', cls.id), 1);
			});
	    });
	};
	
}])
app.controller('StudentsCtl', [ '$scope', '$resource', '$state', '$stateParams', '$mdToast', '$mdDialog', 'FileUploader', 
		function($scope, $resource, $state, $stateParams, $mdToast, $mdDialog, FileUploader) {

	$scope.studentsQuery = $resource('/query/students/class/:classId', {isArray:false});
	$scope.studentsQuery
		.query({classId:$stateParams.classId}, function(data) {
			$scope.students = data;
	});
	
	$scope.classQuery = $resource('/query/classes/:classId', {isArray:false});
	$scope.classQuery
		.get({classId:$stateParams.classId}, function(data) {
			$scope.cls = data;
	});
	
	$scope.uploader = new FileUploader({
		url: '/students/import'
	});
	
	$scope.uploader.onBeforeUploadItem = function(item) {
		 item.formData.push({classId: $scope.cls.id});
		 $scope.importing = true;
	};
	
	$scope.uploader.onSuccessItem = function(item, response, status, headers) {
		$scope.showAlert('Success', response);
		$scope.importing = false;
	};
	
	$scope.uploader.onErrorItem = function(item, response, status, headers) {
		$scope.showAlert('Error', response);
		$scope.importing = false;
	};
	
	$scope.uploadStudentsFiles = function() {
		$scope.uploader.uploadAll();
	};
	
	$scope.showAlert = function(ev, title, msg) {
	    $mdDialog.show(
	      $mdDialog.alert()
	        .parent(angular.element(document.body))
	        .title(title)
	        .content(msg)
	        .ariaLabel(title)
	        .ok('Close')
	        .targetEvent(ev)
	    ).then(function(){
	    	$state.transitionTo('students', {classId:$scope.cls.id}, { reload: true, inherit: true, notify: true });
	    });
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
		.state('schools', {
			url: '/schools',
			templateUrl: 'partials/school/schools.html'
		})
		.state('register-school', {
			url: '/register-school',
			templateUrl: 'partials/school/school.html',
			controller: 'RegisterSchoolCtl'
		})
		.state('school', {
			url: '/school/:schoolId',
			templateUrl: 'partials/school/school.html',
			controller: 'RegisterSchoolCtl'
		})
		.state('school-home', {
			url: '/school-home/:schoolId',
			templateUrl: 'partials/school/school-home.html',
			controller: 'RegisterSchoolCtl'
		})
		.state('students', {
			url: '/students/:classId',
			templateUrl: 'partials/student/students.html',
			controller: 'StudentsCtl'
		})
		.state('import-students', {
			url: '/students/:classId/import',
			templateUrl: 'partials/student/import.html',
			controller: 'StudentsCtl'
		})
		;
});
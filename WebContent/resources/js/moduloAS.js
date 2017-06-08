var app = angular.module('moduloAS', [ 'ngRoute', 'ngCookies' ]);

/**
 * Configuracion de las vistas del aplicativo
 */
app.config([ '$routeProvider', function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : 'templates/login.html',
		controller : 'loginController'
	});
	$routeProvider.when('/solicitud', {
		templateUrl : 'templates/solicitud.html'
	});
	$routeProvider.when('/responderSolicitud', {
		templateUrl : 'templates/responderSol.html'
	});
} ]);

/**
 * Controlador para manejar el formulario de autenticacion, se le inyecta el
 * servicio Autenticacion
 */
app.controller('loginController', function(Autenticacion, $scope, $location, $cookies) {
	$scope.usuario = '';
	$scope.pwd = '';
	/**
	 * Funcion para llamar el servicio de autenticar usuario y contraseña
	 */
	$scope.autenticar = function() {
		Autenticacion.autenticar($scope.usuario, $scope.pwd).then(
				function sucess(data) {
					if (data.data != '') {
						if ((data.data == 'Gerente') || (data.data == 'Empleado')) {
							$cookies.nombreUsuario = $scope.usuario;
							$cookies.passwd = $scope.pwd;
							$location.url('/responderSolicitud');
						} else if (data.data == 'Cliente') {
							$cookies.nombreUsuario = $scope.usuario;
							$cookies.passwd = $scope.pwd;
							$location.url('/solicitud');
						}
						$scope.usuario = '';
						$scope.pwd = '';
						return;
					}
				}, function failure(data) {
					alert(data.data);
				});
	}
	
	/**
	 * Funcion para llamar el servicio de crear una cuenta
	 
	$scope.createAccount = function() {
		Autenticacion.crearCuenta($scope.usuario, $scope.pwd, $scope.rol).then(
				function sucess(data) {
					if (data.data != '') {
						if ((data.data == 'Gerente') || (data.data == 'Empleado')) {
							$cookies.nombreUsuario = $scope.usuario;
							$cookies.passwd = $scope.pwd;
							$location.url('/responderSolicitud');
						} else if (data.data == 'Cliente') {
							$cookies.nombreUsuario = $scope.usuario;
							$cookies.passwd = $scope.pwd;
							$location.url('/solicitud');
						}
						$scope.usuario = '';
						$scope.pwd = '';
						return;
					}
				}, function failure(data) {
					alert(data.data);
				});
	}*/
});

/*
 * Servicio de Angular Encargado de hacer los llamados de los servicios Web para
 * Autenticar un usuario
 */
app.service('Autenticacion',  function($http) {
	this.autenticar = function(user, password) {
		return $http({
			method : 'GET',
			url : 'http://localhost:8080/AtencionSolicitudesJersey/rest/Usuario/Autenticar',
			params : {
				user : user,
				password : password
				}
		});
		}
	
/*	this.crearCuenta = function(user, password) {
		return $http({
			method : 'GET',
			url : 'http://localhost:8080/AtencionSolicitudesJersey/rest/Usuario/Guardar',
			params : {
				user : user,
				password : password,
				rol: 'Cliente'
				}
		});
		}*/
	});
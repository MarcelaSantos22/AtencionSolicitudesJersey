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
		templateUrl : 'templates/solicitud.html',
		controller : 'solicitudController'
	});
	$routeProvider.when('/responderSolicitud', {
		templateUrl : 'templates/responderSol.html',
		controller : 'responderSolController'
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
				rol: rol
				}
		});
		}*/
	});

/**
 * Controlador con el fin de manejar las solciitudes, se hace 
 * la inyección para poder usar el servicio web de Solicitudes
 */
app.controller('solicitudController',function(Solicitudes,$scope,$location){
	
	$scope.solicitud={
			cliente:'',
			tipoSolicitud:'',
			descripcion: '',	
	};
	$scope.validar=function(){
		texto=solicitud.tipo;
		alert(texto);
	}
	$scope.guardar=function(){
		Solicitudes.guardarSolicitud($scope.solicitud).success(function(data){
			alert(data);
			$location.url('/solicitud');
			
		})
	}
	
})

/**
 * Controlador para manejar el formulario de Respuesta a solicitudes,
 * se le inyecta el servicio Solicitudes
 */
app.controller('responderSolController', function(Solicitudes, $scope, $location) {
	$scope.mostrar=false;
	$scope.respuesta={
			idSol:'',
			respuesta:'',
			responsable: ''
	}
	/*
	Solicitudes.obtenerTodas().success(function(data) {		
		$scope.solicitudes = data.solicitudDTOws;
	});
	$scope.mostrarSolicitudes = function() {
		if($scope.mostrar==true){
			$scope.mostrar=false;
		}else{
			$scope.mostrar=true;
		}
	}*/
	$scope.guardarRespuesta=function(){
		Solicitudes.responderSolicitud($scope.respuesta).success(function(data){
			alert(data);
			
			//Recargar la tabla solicitudes para mostrar en la pagina
			Solicitudes.obtenerTodas().success(function(data) {		
				$scope.solicitudes = data.SolicitudDTOws;
			});
			$scope.respuesta={
			idSol:'',
			respuesta:'',
			responsable: ''
			}
		})
		
	}

});

/*
 * Servicio de Angular
 * Encargado de hacer los llamados de los servicios Web para manejar las Solicitudes
 */
app.service('Solicitudes',function($http) {
					/*this.obtenerTodas = function() {
						return $http({
							method : 'GET',
							url : 'http://localhost:8080/AtencionSolicitudesJersey/rest/Solicitud/Obtener',
							params : {
								user : 'Ana2017'
							}
						});
					}*/
					this.guardarSolicitud = function(solicitud) {
						return $http({
							method : 'POST',
							url : 'http://localhost:8080/AtencionSolicitudesJersey/rest/Solicitud/Guardar',
							params : {
								descripcion: solicitud.descripcion,
								tiposolicitud: solicitud.tipo,
								cliente:'1484635'
							}
						});
					}
					this.responderSolicitud=function(respuesta){
						return $http({
							method: 'PUT',
							url:'http://localhost:8080/AtencionSolicitudesJersey/rest/Solicitud/ResponderSolicitud',
							params:{
								idSolicitud: respuesta.idSol,
								respuesta: respuesta.descripcion,
								responsable: '45466'
							}
								
						});
					}
				});

